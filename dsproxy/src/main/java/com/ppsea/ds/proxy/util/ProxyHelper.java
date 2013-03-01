package com.ppsea.ds.proxy.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.json.JSONException;
import org.json.JSONObject;

import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.Zone;
import com.ppsea.ds.util.DateUtil;
import com.ppsea.ds.util.GlobalConfig;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateHashModel;

public class ProxyHelper {
	private static final Logger log = Logger.getLogger(ProxyHelper.class);
	private static String GLOBAL_CONFIG_FILE;
	private static String BLOCK_IP_FILE;
	private static String ALLOW_IP_FILE;
	
	public static final String UNDERLINING="-";
	public static final String DELIMITER="_";
    public static String COMMAND_USER_PREFIX = "u_";
    public static String COMMAND_PLAYER_ENTRY = "p_E";
    
    public static String MSG_CHARSET;
    public static String REGEX_FILE;
    public static String TEMPLATE_DIR;
    public static int POOLSIZE;
    
    private static String TEMPLATE_DOAMAIN = "TemplateFile";
    private static RegexMatch.MatchDomain TEMPLATE_CONF;
    private static Configuration FREEMARKER_CONF; 
    public static Map<String, String> ALLOW_IP =  new HashMap<String, String>();
    public static Map<String, String> BLOCK_IP =  new HashMap<String, String>();
    public static Map<String, String> BJ_IP =  new HashMap<String, String>();
    
    public static Map<String, String> VIP = new HashMap<String, String>();
    public static HashSet<Integer> zone_0_qq = new HashSet<Integer>();
    
    public static String INDEX_URL;
    public static String QUERY_URL;
    public static String PAY_URL;
    public static String GLOBAL_MD5KEY;
    public static String STATUS;
    public static Zone TARGET_ZONE = null;
    public static List<Zone> ZONES = new LinkedList<Zone>();
    public static String BBS;
    
    public static final int TIMEOUT = 900;
    public static String encode(String pid, String time, String key) throws NoSuchAlgorithmException{
    	MessageDigest md5=MessageDigest.getInstance("MD5");
		String input = pid+"-"+time+"-"+key;
		byte[] d = md5.digest(input.getBytes());
		return byte2hex(d, d.length-4, d.length);
    }
   
    public static String byte2hex(byte[] b, int start, int end) {
    	//�?��字节的数�?转成16进制字符�?  
  
        String hs = "";   
        String tmp = "";   
        for (int n = start; n < end; n++) {   
            //整数转成十六进制表示   
  
            tmp = (java.lang.Integer.toHexString(b[n] & 0XFF));   
            if (tmp.length() == 1) {   
                hs = hs + "0" + tmp;   
            } else {   
                hs = hs + tmp;   
            }   
        }   
        tmp = null;   
        return hs;
    }   
    
    public static String timeNow(){
    	Calendar ca = Calendar.getInstance();
    	String hour = String.valueOf(ca.get(Calendar.HOUR_OF_DAY));
    	String minute;
    	int m = ca.get(Calendar.MINUTE);
    	if( m < 10 ){
    		minute = "0"+m;
    	}
    	else{
    		minute = ""+m;
    	}
    	return hour+":"+minute;
    }
    
	@SuppressWarnings("unchecked")
	public static void initCongfig(String configFile) throws Exception{	    
		GLOBAL_CONFIG_FILE = configFile;
		GlobalConfig.initForProxy(configFile);

		INDEX_URL = GlobalConfig.global.attributeValue("indexUrl");
		QUERY_URL = GlobalConfig.global.attributeValue("queryUrl");
		PAY_URL = GlobalConfig.global.attributeValue("payUrl");
		
		
		GLOBAL_MD5KEY = GlobalConfig.global.attributeValue("md5Key");
		BBS = GlobalConfig.global.attributeValue("bbs");
		
		STATUS = GlobalConfig.global.attributeValue("status");
		
		//获得每个区信�?
		ZONES.clear();
		List<Element> nodes = GlobalConfig.root.elements("zone");
        for (Iterator<Element> it = nodes.iterator(); it.hasNext();) {
           Element elm = it.next();
           Zone zone = new Zone();
           zone.setId(elm.attributeValue("id"));
           zone.setName(elm.attributeValue("name"));
           zone.setHost(elm.attributeValue("host"));
           zone.setPort(Integer.valueOf(elm.attributeValue("port")));
           zone.setAdminPort(Integer.valueOf(elm.attributeValue("adminPort")));
           zone.setSortId( Integer.valueOf(elm.attributeValue("sortId")));
           zone.setOnlinePlayer(0);
           
           Element desc = elm.element("desc");
           zone.setDescDefault(desc.attributeValue("default"));
           zone.setDescFullNum(Integer.valueOf(desc.attributeValue("fullNum")));
           zone.setDescBusyNum(Integer.valueOf(desc.attributeValue("busyNum")));
           
           Element proxy = elm.element("proxy");
           zone.setProxyIp(proxy.attributeValue("host"));
           zone.setProxyPort(Integer.valueOf(proxy.attributeValue("port")));
           zone.setProxyKey(proxy.attributeValue("md5Key"));
           
           ZONES.add(zone);
           if(GlobalConfig.zone.attributeValue("id").equals(zone.getId())){
        	   TARGET_ZONE = zone;
           }
        }
        Collections.sort(ZONES,new Comparator<Zone>(){
			public int compare(Zone o1, Zone o2) {
				return o1.getSortId() - o2.getSortId();
			}  
        });
       
	    //初始化VIP用户
        VIP.clear();
	    Element access = GlobalConfig.zone.element("access");
	    String vips = access.attributeValue("vip");
	    if(vips != null){
	    	String[] vipArr = vips.split(",");
	    	for(int i=0; i<vipArr.length; i++){
	    		VIP.put(vipArr[i], "1");
	    	}
	    }	    
	}
	
	/**
	 * 初始化freemark
	 * */
	public static void initFreeMarker(Object servletContext, String regexFile){
		RegexMatch reg = new RegexMatch();
		try {
			reg.loadFromFile(regexFile);
		} catch (Exception e) {
			log.error("exception", e);
		}
		TEMPLATE_CONF = reg.getDomain(TEMPLATE_DOAMAIN);
        FREEMARKER_CONF = new Configuration();
        FREEMARKER_CONF.setDefaultEncoding(MSG_CHARSET);
        FREEMARKER_CONF.setServletContextForTemplateLoading(servletContext, "WEB-INF/"+TEMPLATE_DIR); 
        
		BeansWrapper wrapper = (BeansWrapper) BeansWrapper.BEANS_WRAPPER;
		wrapper.setExposureLevel(BeansWrapper.EXPOSE_ALL);
		try {
			TemplateHashModel staticModels = wrapper.getStaticModels();
			// 设置页面上需要静态访问的�?
			TemplateHashModel tempStatics = (TemplateHashModel) staticModels.get("com.ppsea.ds.util.MD5");
			FREEMARKER_CONF.setSharedVariable("MD5", tempStatics);

			FREEMARKER_CONF.setSharedVariable("statics", BeansWrapper.getDefaultInstance().getStaticModels());
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("exception", ex);
		} finally {
		}
        
	}
	
	public static void initResource() {
		BeansWrapper wrapper = (BeansWrapper) BeansWrapper.BEANS_WRAPPER;
		wrapper.setExposureLevel(BeansWrapper.EXPOSE_ALL);
		try {
			TemplateHashModel staticModels = wrapper.getStaticModels();
			TemplateHashModel ppseaDateUtils = (TemplateHashModel) staticModels.get(DateUtil.class.getName());
			FREEMARKER_CONF.setSharedVariable("DateUtils", ppseaDateUtils);
			
		} catch (Exception ex) {
			log.error("exception", ex);
		}
	}
	
	/**
	 * 获取模板文件
	 * @throws IOException 
	 * */
	public static Template getTemplate(CommandResult result) throws IOException{
		ArrayList<String> option = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		option.add(result.getName());
		option.add(result.getStatus());
		if (TEMPLATE_CONF.match(option, values)) {
			return FREEMARKER_CONF.getTemplate( values.get(0));
		}
		return null;
	}
	/**
	 * 加载0区（特邀体验区）的QQ�?
	 * */
	public static void loadZone0QQ(String zoneFile){
		zone_0_qq.clear();
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(zoneFile));
			String line = null;
			while ( (line=in.readLine()) != null ){
				if( !line.startsWith("#") && line.length()>0){
					try
					{
						zone_0_qq.add(Integer.valueOf(line));
					}
					catch (Exception e) {
						log.error("exception:", e);
					}
				}
			}   
	   }
	   catch(Exception e){
	   		log.error("exception", e);
	   } finally {
		   if (in != null){
		       try {
		           in.close();
		       }catch (IOException e) {
		       		log.error("exception", e);
		       }
	       }
	   }
	   log.info("zone 0 qq num:" + zone_0_qq.size());
	}
	
	/**
	 * 加载IP白名�?
	 * */
	public static void loadAllowIP(String ipFile){
		ALLOW_IP_FILE = ipFile;
		
		ALLOW_IP.clear();
		BufferedReader in = null;
		String s = "1"; 
		try {
			in = new BufferedReader(new FileReader(ipFile));
			String line = null;
			while ( (line=in.readLine()) != null ){
				if( !line.startsWith("#") && line.length()>0){
					ALLOW_IP.put(line, s);
				}
			}   
	   }catch(Exception e){
	   	log.error("exception", e);
	   } finally {
		   if (in != null){
		       try {
		           in.close();
		       }catch (IOException e) {
		       		log.error("exception", e);
		       }
	       }
	   }
	   log.info("allowIp num:"+ALLOW_IP.size());
	}
	
	/**
	 * 加载IP白名�?
	 * */
	public static void loadBlockIP(String ipFile){
		BLOCK_IP_FILE = ipFile;
		
		BLOCK_IP.clear();
		BufferedReader in = null;
		String s = "1"; 
		try {
			in = new BufferedReader(new FileReader(ipFile));
			String line = null;
			while ( (line=in.readLine()) != null ){
				if( !line.startsWith("#") && line.length()>0){
					BLOCK_IP.put(line, s);
				}
			}   
	   }catch(Exception e){
	   	log.error("exception", e);
	   } finally {
		   if (in != null){
		       try {
		           in.close();
		       }catch (IOException e) {
		       		log.error("exception", e);
		       }
	       }
	   }
	   log.info("block num:"+BLOCK_IP.size());
	}
	
	//北京移动拨测ip
    private static String[] ips = {"211.136.28.135","211.136.28.167","221.130.33.39","221.130.33.135"};
	public static void loadBjIP(){
		for(int i=0; i<ips.length; i++){
			BJ_IP.put(ips[i], "1");
		}
	}
	
	
	public static boolean checkAccess(String realIp, String pid){
		if(isBlackIp(realIp)){
			return false;
		}
		
		if(isAllowIp(realIp)){
			return true;
		}
		
		/**
		//非vip用户，访问受
		if(pid != null && VIP.get(pid) == null){
   			return false;
		}
		*/
		return true;
	}
	/**
	 * 
	 * @param qq
	 * @return
	 */
	public static boolean isZone0QQ(int qq) {
		return zone_0_qq.contains(qq);
	}
	/**
	 * 
	 * @param ip
	 * @return
	 */
	
	private static boolean isBlackIp(String ip){
    	if(checkIp(ip, BLOCK_IP)){
    		return true;
    	}
    	return false;
	}
	
	private static boolean isAllowIp(String ip){
    	if(checkIp(ip, ALLOW_IP)){
    		return true;
    	}
    	return false;
	}
	
	
    private static boolean checkIp(String ip, Map<String,String> ipMap){    	
    	String[] ipSubs = ip.split("[.]");
    	if(ipSubs.length != 4){
    		return false;
    	}
    
    	String ipSub = null;
    	//先检�?段IP
    	ipSub = ipSubs[0]+"."+ipSubs[1]+"."+ipSubs[2];
    	if( ipMap.get(ipSub) != null){
    		return true;
    	}
    	
    	//�?��2段IP
    	ipSub = ipSubs[0]+"."+ipSubs[1];
    	if( ipMap.get(ipSub) != null){
    		return true;
    	}
    	
    	//�?��4段IP
    	//ipSub = ipSubs[0]+"."+ipSubs[1]+"."+ipSubs[2]+"."+ipSubs[3];
    	if( ipMap.get(ip) != null){
    		return true;
    	}
    	return false;
    }    
    
    public static boolean reloadZone(){
    	try{
    		initCongfig(GLOBAL_CONFIG_FILE);
    		return true;
    	}
    	catch(Exception e){
    		log.error("exception:", e);
    		return false;
    	}
    }
    
    
    public static boolean reloadBlockIp(){
    	loadBlockIP(BLOCK_IP_FILE);
    	return true;
    }
    
    public static boolean reloadAllowIp(){
    	loadAllowIP(ALLOW_IP_FILE);
    	return true;
    }
    
    public static JSONObject httpClientSend(String url){
   	 	// Create an instance of HttpClient.
        HttpClient client = new HttpClient();

        // Create a method instance.
        GetMethod method = new GetMethod(url);
        
        // Provide custom retry handler is necessary
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, 
        		new DefaultHttpMethodRetryHandler(3, false));

        try {
        	// Execute the method.
        	int statusCode = client.executeMethod(method);

        	if (statusCode != HttpStatus.SC_OK) {
        		log.error("Method failed: " + method.getStatusLine());
        		return null;
        	}

        	// Read the response body.
        	byte[] responseBody = method.getResponseBody();

        	// Deal with the response.
        	// Use caution: ensure correct character encoding and is not binary data
        	return new JSONObject(new String(responseBody));

        } 
        catch (HttpException e) {
        	log.error("Fatal protocol violation", e);
        } 
        catch (IOException e) {
        	log.error("Fatal transport error", e);
        } catch (JSONException e) {
			log.error("JSON error", e);
		} 
        finally {
        	// Release the connection.
        	method.releaseConnection();
        } 
        return null;
    }
 
	public static String httpClientSendPost(String url, NameValuePair[] data) {
		String res = "";
		HttpClient httpClient = new HttpClient();
		
		HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams(); 
		// 设置连接超时时间(单位毫秒) 
		managerParams.setConnectionTimeout(1000); 
		// 设置读数据超时时间(单位毫秒) 
		managerParams.setSoTimeout(1000); 
		
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestBody(data);
		try {
			long start = System.currentTimeMillis();
			int statusCode = httpClient.executeMethod(postMethod);
			long end = System.currentTimeMillis();
			System.out.print("execute time : " + (end-start));
			if (statusCode != HttpStatus.SC_OK) {
				log.error("Method failed: " + postMethod.getStatusLine());
			} 
			// res = new String(postMethod.getResponseBody());
			InputStream inputStream = postMethod.getResponseBodyAsStream();
			ByteArrayOutputStream   baos   =   new   ByteArrayOutputStream();
			int   i=-1;
			while((i=inputStream.read())!=-1){
				baos.write(i);
			}
			res = baos.toString(); 
			log.info("return response:"+baos.toString());
		}  catch (HttpException e) {
			log.error("Fatal protocol violation", e);
		} catch (IOException e) {
			log.error("Fatal transport error", e);
		} finally {
			postMethod.releaseConnection();
		}
		return res;
	}	    
    
	public static String checkSign(String sign, String key) throws ServletException{
    	try {
	    	String[] sa = sign.split(DELIMITER);
    		//格式错误
    		if(sa.length != 3){
    			throw new ServletException("签名格式错误"+sign);
    		}
    		//超时
//    		int now = (int) (System.currentTimeMillis()/1000);
//    		int diff = now - Integer.valueOf(sa[1], 16)  ;
//    		if( diff > timeout ){
//    			throw new ServletException("签名过期");
//			}
//    		//访问频率1秒以�?
//    		if(diff <=1){
//    			throw new ServletException("访问频率过高:"+diff);
//    		}
    		//md5校验失败
			if( !ProxyHelper.encode(sa[0], sa[1], key).equals(sa[2]) ){
				throw new ServletException("签名校验失败"); 
			}
			return sa[0];
		} 
	    catch (NoSuchAlgorithmException e) {
			throw new ServletException(e.getMessage()); 
	    }
    } 
	
	public static String getGameBaseUrl(){
		return "http://"+TARGET_ZONE.getProxyIp()+":"+TARGET_ZONE.getProxyPort()+"/";
	}
}

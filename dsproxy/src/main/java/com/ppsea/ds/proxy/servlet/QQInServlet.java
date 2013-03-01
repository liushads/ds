package com.ppsea.ds.proxy.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandRequest;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.Zone;
import com.ppsea.ds.proxy.util.ProxyHelper;
import com.ppsea.ds.util.DESUtil;
import com.ppsea.ds.util.Memcached;
import com.ppsea.ds.util.OnlineUserNum;
//import com.qq.jutil.common.CongfigResource;
//import com.tencent.wapgame.netgame.NetGameApp;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * This Servlet does not do anything useful, just prints "Hello World!". The
 * intent is to help you to get started if you want to build your own Controller
 * servlet that uses FreeMarker for the View. For more advanced example, see the
 * 2nd Web application example.
 */
public class QQInServlet extends HttpServlet{
	private static final Logger log = Logger.getLogger(QQInServlet.class);
	
	private static byte[] keyBytes;
	private static String Algorithm = "DESede"; //定义加密算法，默认为DESede
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init(){
	}
	
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {    	
    	req.setCharacterEncoding("UTF-8");
    	resp.setContentType("text/vnd.wap.wml; charset=" + ProxyHelper.MSG_CHARSET);
    	PrintWriter out = resp.getWriter();
    	try {
	    	String uid = req.getParameter("uid");
	    	String ouid = new String(uid);
	    	String sid = req.getParameter("sid");
	    	String sSid = req.getParameter("sSid");
	    	log.warn("uid = " + uid);
	    	/*log.warn("sid = " + sid);
	    	log.warn("sSid = " + sSid);
	    	log.warn("status = " + ProxyHelper.STATUS);
	    	if(ProxyHelper.STATUS!=null && "release".equals(ProxyHelper.STATUS)){
		    	boolean isLogin = false;
		    	try{
//		    		isLogin = NetGameApp.check(uid,sid);
		    		log.error("isLogin = " + isLogin);
		    	}
		    	catch(Exception e){
		    		e.printStackTrace();
		    	}
		    	
		    	if(!isLogin){
		    		// 登陆失败，跳转到失败页面
		    		throw new Exception("login error..");
		    	}
		    	uid = getUid(uid);
	    	}*/
	    	CommandRequest cmdReq = new CommandRequest();	
	    	
//	    	checkAndSetCookieSSid(req, resp, sSid, ouid);
	    	
	    	// add by xiaozhou(2011/6/17)
//	    	checkAndSetCookie4Qzone(req, resp);
	    	
			//设置参数
			cmdReq.setCmd("u_LZ");
		    cmdReq.setUid(uid);
		    cmdReq.setSid(sid);
		    cmdReq.setSSid(sSid);
		    CommandResult result = new CommandResult(Command.STATUS_SUCC);
	    	result.setName(cmdReq.getCmd());
	    	result.getData().put(Command.TAG_UID, cmdReq.getUid());
	    	result.getData().put("ouid", ouid);
	    	result.getData().put(Command.TAG_sSID, sSid);
	    	result.getData().put(Command.TAG_SID, sid);
	    	
	    	// add by EddyZhou(在十一前隐藏新区)
//			Calendar now = Calendar.getInstance();
//			int month = now.get(Calendar.MONTH);
//			result.setVO("month", month);
//	    	
//	    	// add by xiaozhou(2011/6/20)
//	    	String from = req.getParameter("from");
//	    	if (from != null) {
//	    		result.getData().put("from", from);
//	    	}
	    	
	    	List<Zone> lz = new LinkedList<Zone>();
	    	for (Zone z : ProxyHelper.ZONES) {
	    		lz.add(z);
	    	}
	    	result.getData().put(Command.TAG_ZONES, lz);
	
	    	// 玩家登陆状态
	    	// 列表上次登录时间和所玩区
			try{
				String info = (String) Memcached.get(String.valueOf(uid));
				log.warn("info = " + info);
				String loginInfo = null;
				if(!(info==null||"".equals(info))){
					String[] iA = info.split("[_]");
					if(iA.length==2){
						long time = Long.parseLong(iA[0]);
						String zoneId = iA[1];
						SimpleDateFormat sdf = new SimpleDateFormat("M月d日 H时m分");
						for (Zone z : ProxyHelper.ZONES) {
							if(z.getId().equals(zoneId)){
								loginInfo = "上次登录时间："+sdf.format(new Date(time))+"<br/>上次登录分区：";
								result.getData().put("loginInfo", loginInfo);
								result.getData().put("lastZone", z);
								break;
							}
						}
					}
				}
			}
			catch(Exception e){
				log.error(e.getMessage(),e);
			}
			
			try {
				for (Zone z : ProxyHelper.ZONES) {
					
//					String res = OnlineUserNum.getOnlineUserResult(z);
//					if (res != null) {
//						String[] values = res.split("/");
//						if (values.length == 2) {
//							int online = Integer.valueOf(values[0]);
//							int cached = Integer.valueOf(values[1]);
//							z.setCurrOnlineUserNum(online);
//							z.setCurrCachedUserNum(cached);		
//						}
//					}
					
					Integer online = z.getCurrOnlineUserNum();
					if (z.getSortId() != null && z.getSortId() == 1) {
						z.setNoteInfo("荐");
					} else {
						if (online > 1000) {
							z.setNoteInfo("挤");
						} else {
							z.setNoteInfo("畅");
						}
					}
					
				}
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
		  
			Template t = ProxyHelper.getTemplate(result);
	    	if (t == null) {
	    		throw new ServletException("don't find match template file");
	    	}
	    	result.getData().put("base", req.getContextPath());
	    	result.getData().put("qqtime", ProxyHelper.timeNow());
	    	result.getData().put("Md5Key", "");
	    	result.getData().put("Md5GlobalKey", ProxyHelper.GLOBAL_MD5KEY);
	    	t.process(result.getData(),out);
	    	out.close();
		}
		catch(Exception e){
			log.error("exception", e);
			CommandResult result = new CommandResult(Command.STATUS_LOGIN_FAIL);
			// result.setVO("loginUrl", NetGameApp.getLoginUrl());
//			result.getData().put("loginUrl", NetGameApp.getLoginUrl());
			Template t = ProxyHelper.getTemplate(result);
	    	try {
				t.process(result.getData(),out);
			} catch (TemplateException e1) {
				e1.printStackTrace();
			}
	    	out.close();
		}
    }
    
    /**
     * UID
     * @param uid
     * @return
     * @throws IOException
     */
    private String getUid(String uid) throws IOException{
		String CONF_FILE = "netGameApp.conf";
		Properties prop = new Properties();
//		InputStream in = CongfigResource.loadConfigFile(CONF_FILE, NetGameApp.class);
		InputStream in = new InputStream() {
			
			@Override
			public int read() throws IOException {
				return 0;
			}
		};
		prop.load(in);
		String cryptKey = prop.getProperty("cryptKey", "");
    	DESUtil desUtil1 = null;
		try
		{
     			desUtil1 = new DESUtil(cryptKey.getBytes(), "DESede/ECB/NoPadding");
		} catch (Exception e1)
		{
  			e1.printStackTrace();
		}
		byte[]  bUid =desUtil1.hexStr2Bytes(uid);
		bUid = desUtil1.decode(bUid);
		try {
			return new String(bUid, "UTF-8");
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return null;
    }

    private void checkAndSetCookieSSid(HttpServletRequest req, HttpServletResponse resp, String sSid, String uid) {
    	try {
    		Cookie[] cooks = req.getCookies();
        	String tssid = "";
        	if (cooks != null) {
        		for (Cookie ck : cooks) {
            		String name = ck.getName();
            		if (name.equalsIgnoreCase(Command.TAG_sSID)) {
            			tssid = ck.getValue();
            		}
            	}
        	}
        	
        	if ((tssid == null || tssid.equals(""))&& sSid != null) {
        		Cookie ck = new Cookie(Command.TAG_sSID, sSid);
        		ck.setDomain(".3g.qq.com");
        		ck.setPath("/");
        		ck.setMaxAge(Integer.MAX_VALUE);
        		resp.addCookie(ck);
        		Cookie ckuid = new Cookie("ouid", sSid);
        		ckuid.setDomain(".3g.qq.com");
        		ckuid.setPath("/");
        		ckuid.setMaxAge(Integer.MAX_VALUE);
        		resp.addCookie(ckuid);
        		//log.error("set ssid to cookie:"+sSid);
        		return;
        	}
        	String url = req.getRequestURL().toString();
        	if (url.contains("mogzh1") && sSid != null) {
        		Cookie ck = new Cookie(Command.TAG_sSID, sSid);
        		ck.setDomain(".3g.qq.com");
        		ck.setPath("/");
        		ck.setMaxAge(Integer.MAX_VALUE);
        		resp.addCookie(ck);
        		//log.error("reset ssid to cookie:"+sSid);
        	}
        	
        	if (url.contains("mogzh1") && uid != null) {
        		Cookie ck = new Cookie("ouid", uid);
        		ck.setDomain(".3g.qq.com");
        		ck.setPath("/");
        		ck.setMaxAge(Integer.MAX_VALUE);
        		resp.addCookie(ck);
        		//log.error("reset ssid to cookie:"+sSid);
        	}
    	} catch (Exception e) {
    		log.error("exception:checkAndSetCookieSSid" , e);
    	}
    }
    
    private void checkAndSetCookie4Qzone(HttpServletRequest req, HttpServletResponse resp) {
    	String from = req.getParameter("from");
    	log.info("from: " + from);
    	if (from != null && "zone".equals(from)) {
    		Cookie ck = new Cookie("from", from);
    		ck.setDomain(".3g.qq.com");
    		ck.setPath("/");
    		ck.setMaxAge(Integer.MAX_VALUE);
    		resp.addCookie(ck);
    	}
    }
}

/*
 * 
 */
package com.ppsea.ds.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

import com.ppsea.ds.util.DESUtil;
import com.ppsea.ds.data.model.Player;

/**
 * @author Administrator
 * @date 2010-8-9
 */
public class QQReportProxy {

	private static Logger logger = Logger.getLogger("QQReporter");
	private static QQReportProxy instance;
	private ConnectionHelper connInstance = new ConnectionHelper();
	
	
	private QQReportProxy() {
		
	}
	
	int i = 0;
	public QQReportProxy(int i){
		this.i = i;
	} 
	
	public synchronized static QQReportProxy getInstance() {
		if (instance == null) {
			instance = new QQReportProxy();
		}
		return instance;
	}
	
//	public static void main(String []args) {
//		Player player = new Player();
//		player.setUserId("101010");
//		player.setId(Integer.valueOf(888));
//		player.setName("测试啦");
//		player.setLevel(Integer.valueOf(120));
//		
//		/*
//		QQReportProxy.getInstance().testURL();
//		QQReportProxy.getInstance().testXML(player);
//		QQReportProxy.getInstance().testConnection();
//		*/
//		QQReportProxy.getInstance().testSendMessage();
//	}

	
	public void testXML(Player player) {
		String xml = createRequestedXML(player);
		System.out.println(xml);
	}

	
//	public class ReadFileThread implements Runnable{
//
//		int count=1,d,f;
//		public ReadFileThread(int d){
//			this.d = d;
//			logger.info("new Thread"+d);
//		}		
//		public ReadFileThread(int d,int f){
//			this.d = d;
//			this.f = f;
//			logger.info("new Thread"+d+"-"+f);
//		}
//		
//		public void run() {
//			logger.info("start Thread " + d + "-" + f);
//			
//			//读取文件发送请求
//			String line = null;
//			try{
//				logger.info("---------------read file " + "/usr/local/cp_mqq/tmp/jh_player.txt0"+d+"0"+f);
//				
//				BufferedReader br = new BufferedReader(new FileReader("/usr/local/cp_mqq/tmp/jh_p2/jh_player.txt0"+d));
//				while ((line = br.readLine()) != null) {
//					
//					String[] iArr = line.split("	");
//					if(iArr.length<4){
//						continue;
//					}
//					Player player = new Player();
//					player.setId(Integer.valueOf(iArr[0]));
//					player.setUserId(iArr[1]);
//					player.setLevel(Integer.valueOf(iArr[2]));
//					player.setName(iArr[3]);
//					String xml = createRequestedXML(player);
//					ConnectionHelper c = new ConnectionHelper(d,iArr[1]);
//					c.sendAndReadMessage(xml);
//				}
//			}
//			catch(Exception e){
//				logger.error(line + e.getMessage(),e);
//			}				
//
//		}
//	}
	
//	public void testSendMessage() {
//		
//		for(int i=0;i<10;i++){
//			/*
//			for(int j=0;j<6;j++){
//				new Thread(new ReadFileThread(i,j)).start();
//			}
//			*/
//			new Thread(new ReadFileThread(i)).start();
//		}
//		
//		// new Thread(new ReadFileThread(3,0)).start();
//	}
	
	/**
	 * 上报玩家数据给TX
	 * @param player
	 */
	public void sendMessage(Player player){
		try{
			String xml = createRequestedXML(player);
			connInstance.sendAndReadMessage(xml);
		}
		catch(Exception e){
			logger.error("上报数据给TX失败",e);
		}
	}
	
	public void reportPlayerInfo2QQ(Player player) {
		try{
			String xml = createRequestedXML(player);
			connInstance.sendAndReadMessage(xml);
		}
		catch(Exception e){
			logger.error("上报数据给TX失败",e);
		}
	}
	
	public String createRequestedXML(Player player) {
		String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		StringBuilder sb = new StringBuilder();
		try {
			sb.append(header);
			sb.append("<request>");
			sb.append("<msgType>UpdataReq</msgType>");
			sb.append("<uid>").append(player.getUserId()).append("</uid>");
			sb.append("<roleid>").append(player.getId().toString()).append("</roleid>");
			sb.append("<rolename>").append(formatStringToXML(player.getName())).append("</rolename>");
			sb.append("<rolelevel>").append(player.getLevel().toString()).append("</rolelevel>");
			sb.append("</request>");
		} catch (Exception e) {
		}
		return sb.toString();
	}
	
	
    public String formatStringToXML(final String value) {
    	if(value==null) return "";
        char[] content = new char[value.length()];
        value.getChars(0, value.length(), content, 0);
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < content.length; i++) {
            switch (content[i]) {
	            
	        	case '&':
	                result.append("");
	                break;
	            case '<':
	                result.append("");
	                break;
	            case '>':
	                result.append("");
	                break;
	            case '"':
	                result.append("");
	                break;
	            case '\'':
	                result.append("");
	                break;
	            case '$':
	                result.append("");
	                break;	    
	            case '?':
	                result.append("");
	                break;	     	                
	            default:
	                result.append(content[i]);
            }
        }
        return result.toString();
    }		

}

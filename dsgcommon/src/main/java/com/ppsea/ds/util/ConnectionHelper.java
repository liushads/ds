/*
 * 
 */
package com.ppsea.ds.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

/**
 * @author Administrator
 * @date 2011-4-1
 */
public class ConnectionHelper {
	
	private static Logger logger = Logger.getLogger("QQReporter");
//	 private String primaryHostAddress = "121.14.102.10";
//	 private String secondHostAddress = "119.147.11.91";
	 private String primaryHostAddress = "120.198.188.155";
	 private String secondHostAddress = "120.198.188.156";
	 private int port = 8191;
	 private String url = "onlineGameSqq/UpdataServlet.do";
	 private String cpId = "917";
	 private String gameId = "127";
	 private String requestMethod = "GET";
	 private boolean isOutput = true;
	 private boolean isInput = true;
	 private int readTimeOut = 2000;
	 private int connectTimeOut = 3000;
	 private String encoding = "UTF-8";
	 private final int MAX_ATTEMPT = 3;
	 private final String CONN_TYPE_P = "primary";
	 private final String CONN_TYPE_S = "second";
	 private final String CONN_TYPE_T = "test";
	 private HttpURLConnection connection;
	 private HttpURLConnection secondCnnection;
	 private HttpURLConnection testConnection;
	 private String key = "876543217654321065432109";
	 
	 protected ConnectionHelper() {
	 }
	 
	 int threadId = 0;
	 String userId = "";
	 protected ConnectionHelper(int i,String userId) {
		 this.threadId = i;
		 this.userId = userId;
	 }
	 
	 public ConnectionHelper(String paddr, String saddr, int port, String url) {
		 this.primaryHostAddress = paddr;
		 this.secondHostAddress = saddr;
		 this.port = port;
		 this.url = url;
	 }
	 
	 public String getPrimaryRequestURL() {
		 if (port > 0) {
			 return "http://"+primaryHostAddress+":"+port+"/"+url+"?cpId="+cpId+"&gameId="+gameId;			 
		 } else {
			 return "http://"+primaryHostAddress+"/"+url+"?cpId="+cpId+"&gameId="+gameId;
		 }
	 }
	 
	 public String getSecondRequestURL() {
		 if (port > 0) {
			 return "http://"+secondHostAddress+":"+port+"/"+url+"?cpId="+cpId+"&gameId="+gameId;			 
		 } else {
			 return "http://"+secondHostAddress+"/"+url+"?cpId="+cpId+"&gameId="+gameId;
		 }
	 }
	 
	 public String getTestRequestURL() {
		 return "http://"+secondHostAddress+":"+port+"/testGameSqq/TestUpdataServlet.do?cpId="+cpId+"&gameId="+gameId;
	 }
	 
		 
	 private HttpURLConnection initializeConnection(String url) {
			URL requestedUrl;
           try {
	            requestedUrl = new URL(url);
	            HttpURLConnection conn = (HttpURLConnection)requestedUrl.openConnection();
	            conn.setConnectTimeout(connectTimeOut);
	            conn.setReadTimeout(readTimeOut);
	            conn.setDoInput(isInput);
	            conn.setDoOutput(isOutput);
	            conn.setRequestMethod(requestMethod);
	            conn.connect();
	            logger.info("thread "+threadId+"-"+userId+" connected OK!!URL="+url);
	            return conn;
           } catch (Exception e) {
	            logger.error("connect failed!url="+url, e);
           }
			return null;
		 }
	 
	 public HttpURLConnection getConnection(String type) {
		 logger.info("get connection type " + type);
		 if (CONN_TYPE_P.equals(type)) {
			 connection = initializeConnection(getPrimaryRequestURL());					 
			 return connection;
		 } else if (CONN_TYPE_S.equals(type)) {
			 secondCnnection = initializeConnection(getSecondRequestURL());					 
			 return secondCnnection;
		 } else {
			 testConnection = initializeConnection(getTestRequestURL());					 
			 return testConnection;
		 }
	 }
	 
	 public HttpURLConnection getConnection() {
		 logger.info("get connection..");
		 int attempTimes = 0;
		 HttpURLConnection conn = getConnection(CONN_TYPE_P);
		 if (conn == null) {
			 while (attempTimes < MAX_ATTEMPT) {
				 conn = getConnection(CONN_TYPE_S);
				 if (conn == null) {
					 conn = getConnection(CONN_TYPE_P);
				 } 
				 if (conn != null) {
					 break;
				 }
				 attempTimes++;
				 logger.error("attempt "+attempTimes);
			 }
		 }
		 return conn;
	 }
	 
	 public void sendRequest(String requestXML) {
		 if (requestXML == null || "".equals(requestXML)) {
			 return;
		 }
		 HttpURLConnection conn = getConnection();
		 if (conn != null) {
			 try {
               OutputStream out = conn.getOutputStream();
               byte[] buf = requestXML.getBytes(encoding);
               out.write(new DESUtil(key.getBytes(),"DESede/ECB/NoPadding").encode(buf));
               out.flush();
               logger.info("thread "+threadId+"-"+userId+" send request OK!!xml="+requestXML);
           } catch (Exception e) {
               logger.error("send request failed! request is : "+requestXML, e);
           }
		 } else {
			 logger.error("###can not get connection to write!! request is : "+requestXML);
		 }
	 }
	 
	 public String sendAndReadMessage(String requestXML) {
		 if (requestXML == null || "".equals(requestXML)) {
			 return "";
		 }
		 HttpURLConnection conn = getConnection();
		 if (conn != null) {
			 OutputStream out = null;
			 try {
               out = conn.getOutputStream();
               byte[] buf = requestXML.getBytes(encoding);
               out.write(new DESUtil(key.getBytes(),"DESede/ECB/NoPadding").encode(buf));
               out.flush();
               logger.info("thread "+threadId+"-"+userId+" send request OK!!xml="+requestXML);
           } catch (Exception e) {
               logger.error("send request failed! request is : "+requestXML, e);
           }
           finally{
           	if(out!=null){
           		try {
						out.close();
					} catch (IOException e) {
						logger.error("out close error!", e);
					}
           	}
           }
           return readResponse(conn);
		 } else {
			 logger.error("###can not get connection to write!! request is : "+requestXML);
		 }
		 return "";
	 }
	 
	 public String readResponse(HttpURLConnection conn) {
		 InputStream in = null;
		 try {
			in = conn.getInputStream();
			int length = in.available();
			byte[] buf = new byte[length];
			in.read(buf);
			String message = new String(new DESUtil(key.getBytes(),"DESede/ECB/NoPadding").decode(buf), encoding);
			logger.info("thread "+threadId+"-"+userId+" read OK!!response=" + message);
			return message;
		} catch (Exception e) {
			logger.error("read failed!!", e);
		}
		finally{
			if(in!=null){
       		try {
       			in.close();
				} catch (IOException e) {
					logger.error("in close error!", e);
				}
       	}
		}
		return "";
	 }
	 
	 public String readResponse() throws Exception {
		 HttpURLConnection conn = getConnection();
		 if (conn != null) {
			 try {
               InputStream in = conn.getInputStream();
               int length = in.available();
               byte[] buf = new byte[length];
               in.read(buf);
               String message = new String(new DESUtil(key.getBytes(),"DESede/ECB/NoPadding").decode(buf), encoding);
               logger.info("read OK!!response="+message);
               return message.trim();
           } catch (IOException e) {
               logger.error("read failed!!", e);
           }
		 } else {
			 logger.error("###can not get connection to read!! ");
		 }
		 return "";
	 }
}
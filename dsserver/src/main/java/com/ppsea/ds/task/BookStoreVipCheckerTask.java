/*
 * 
 */
package com.ppsea.ds.task;

import java.io.StringReader;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ppsea.ds.util.ConnectionHelper;
import com.ppsea.ds.util.StringUtil;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.service.DBService;

/**
 * @author Administrator
 * @date 2011-3-31
 */
public class BookStoreVipCheckerTask {

	private static BookStoreVipCheckerTask instance;
	
	private static Map<Integer, Player> playerCheckerCache = new ConcurrentHashMap<Integer, Player>();
	
	private static Logger logger = Logger.getLogger("QQReporter");
	
	private Timer timer;
	
	private ConnectionHelper chelper;
	
	private boolean started = false;
	
	private BookStoreVipCheckerTask() {
		
	}
	
	public synchronized static BookStoreVipCheckerTask getInstance() {
		if (instance == null) {
			instance = new BookStoreVipCheckerTask();
		}
		return instance;
	}
	
	
	public void addPlayer2Check(Player player) {
		try {
			/**
			if (player == null) {
				return;
			}
			Calendar cal = Calendar.getInstance();
			int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
			Date lastDate = player.getLastLoginTime();
			Calendar lastLoginCal = Calendar.getInstance();
			lastLoginCal.setTime(lastDate);
			int loginLoginDay = lastLoginCal.get(Calendar.DAY_OF_MONTH);
			if (loginLoginDay != dayOfMonth) {
				playerCheckerCache.put(player.getId(), player);
				logger.info("bookStoreVip|put|"+player.getId());
			}
		
		*/
		} catch (Exception e) {
			logger.error("add failed."+ player.getId(), e);
		}
	}
	
	public String testBookStoreVipQuery(String uid) {
		String xml = createRequestedXML(uid);
    	String results = chelper.sendAndReadMessage(xml);
    	return results;
	}
	
	
	public synchronized void startChecker() {
		if (!started) {
			initialization();
			started = true;
		} else {
			return;
		}
		timer.schedule(new TimerTask() {

			@Override
            public void run() {
				logger.info("player|checker|cache|size|start:"+playerCheckerCache.size());
	            for (Map.Entry<Integer, Player> entry : playerCheckerCache.entrySet()) {
	            	try {
		            	Player p = entry.getValue();
		            	long t1 = System.currentTimeMillis();
		            	logger.info("bookStoreVip|report|start|"+p.getId()+"|"+p.getUserId());
		            	String xml = createRequestedXML(p.getUserId());
		            	String results = chelper.sendAndReadMessage(xml);
		            	long t2 = System.currentTimeMillis()-t1;
		            	logger.info("bookStoreVip|report|end|"+p.getId()+"|"+p.getUserId()+"|"+t2+"|"+results);
		            	try {
		            		/**
		            		SAXReader reader = new SAXReader();
		            		
		            		Document d = reader.read(new StringReader(results));
		            		Element root = d.getRootElement();
		            		Element v = root.element("isVip");
		            		**/
		            		int pos = results.indexOf("</isVip>");
		            		if (pos > 0) {
		            			String text = results.substring(pos-1, pos);
			            		Player player = PlayerMG.instance.getOnlinePlayer(p.getId());
			            		if (player == null) {
			            			player = PlayerMG.instance.getPlayerSimple(p.getId());
			            		}
//			            		if ("Y".equals(text)) {
//			            			player.setBookVip(1);
//			            		} else {
//			            			player.setBookVip(0);
//			            		}
			            		logger.info("bookStoreVip|report|result|"+p.getId()+"|"+p.getUserId()+"|"+text);
			            		DBService.commit(player);
		            		} 		            
	                    } catch (Exception e) {
	                    	logger.error("parse failed."+p.getId(), e);
	                    }
		            	
		            	playerCheckerCache.remove(p.getId());
	            	} catch (Exception e) {
	            		logger.error("run failed.", e);
	            	}
	            }
	            logger.info("player|checker|cache|size|end:"+playerCheckerCache.size());
            }
			
		}, 3000, 60000);
	}
	
	private void initialization() {
		//chelper = new ConnectionHelper("mgtest.3g.qq.com","",0,"netGameProj/BookVipServlet.do");
//		chelper = new ConnectionHelper("121.14.102.10","119.147.11.91",8191,"onlineGameSqq/BookVipServlet.do");
		chelper = new ConnectionHelper("120.198.188.155","120.198.188.156",8191,"onlineGameSqq/BookVipServlet.do");
		timer = new Timer();
		logger.info("initialzed book store vip checker successed,primaryHostURL="+chelper.getPrimaryRequestURL());
		logger.info("initialzed book store vip checker successed,secondHostURL="+chelper.getSecondRequestURL());
	}
	
	private String createRequestedXML(String uid) {
		String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		StringBuilder sb = new StringBuilder();
		try {
			sb.append(header);
			sb.append("<request>");
			sb.append("<msgType>BookVipQuery</msgType>");
			sb.append("<uid>").append(uid).append("</uid>");
			sb.append("</request>");
		} catch (Exception e) {
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		SAXReader reader = new SAXReader();
    	try {
    		String results = "<response><msgType>BookVipQueryRsp</msgType><isVip>Y</isVip></response>";
    		String str  = StringUtil.formatStringToXML(results);
    		System.out.println(str);
    		Document d = reader.read(new StringReader(results));
    		Element root = d.getRootElement();
    		Element v = root.element("isVip");
    		String text = v.getText();
    		System.out.println(text);
    		int pos = results.indexOf("</isVip>");
    		String s = results.substring(pos-1, pos);
    		System.out.println("=="+s);
    	}
      catch (Exception e) {
	        e.printStackTrace();
        }
    }
}

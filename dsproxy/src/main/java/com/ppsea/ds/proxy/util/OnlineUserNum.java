
package com.ppsea.ds.proxy.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.ppsea.ds.data.Zone;


public class OnlineUserNum {
	public static OnlineUserNum instance;
	private static Logger log = Logger.getLogger(OnlineUserNum.class);
	/**
	 * 
	 */
	private OnlineUserNum() {
	}

	/**
	 * 
	 * @param z
	 * @return
	 */
	public static String getOnlineUserResult(Zone z) {
		try
		{
			String urlStr = "http://" 
				          + z.getProxyIp() + ":" + z.getProxyPort() 
					      + "/servlet/AdminServlet?cmd=viewPlayer";
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			String sCurrentLine = "";
			String sTotalString = "";
			InputStream input = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			while ((sCurrentLine = reader.readLine()) != null) {
				sTotalString += sCurrentLine;
			}
			JSONObject obj = new JSONObject(sTotalString);
			return obj.getString("result");
		}
		catch (Exception e) {
			log.error("exception:", e);
		}
		return null;
	}
	
//	private class GetOnlineUserNum extends TimerTask
//	{
//		public void run() 
//		{
//			try
//			{
//				List<Zone> zones = ProxyHelper.ZONES;
//				for (Zone z : zones) {
//					z.setDesc(z.getDescDefault());
//					int busyNum = z.getDescBusyNum().intValue();
//					int fullNum = z.getDescFullNum().intValue();
//					String result = OnlineUserNum.getOnlineUserResult(z);
//					if (result != null) {
//						String[] values = result.split("/");
//						if (values.length == 2) {
//							int online = Integer.valueOf(values[0]);
//							int cached = Integer.valueOf(values[1]);
//							z.setCurrOnlineUserNum(online);
//							z.setCurrCachedUserNum(cached);
//							/*
//							if (fullNum > 0 && online > fullNum) {
//								z.setDesc("满");
//							}
//							else if (busyNum > 0 && online > busyNum) {
//								z.setDesc("忙");
//							}
//							else if (online > 0 && online < busyNum 
//									&& z.getDescDefault().equals("")) {
//								z.setDesc("闲");
//							}
//							*/
//						}
//					}
//				}
//			}
//			catch (Exception e) {
//				log.error("exception", e);
//			}
//		}
//	}	
	
}

package com.ppsea.ds.proxy.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ppsea.ds.data.Zone;
import com.ppsea.ds.proxy.util.OnlineUserNum;
import com.ppsea.ds.proxy.util.ProxyHelper;
import com.ppsea.ds.proxy.util.SynClient;

public class AdminServlet extends HttpServlet {
	
	 public static final Logger log = Logger.getLogger(AdminServlet.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor of the object.
	 */
	public AdminServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cmd = request.getParameter("cmd");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String realIp = request.getHeader("X-Forwarded-For-Pound");
		log.error("get|realIp|"+realIp);
		String result;
		if(cmd.equals("addIp")){
			String ip = request.getParameter("ip");
			ProxyHelper.ALLOW_IP.put(ip, "1");
			result = cmd+" ok: allowIp.size="+ProxyHelper.ALLOW_IP.size();
		}
		else if(cmd.equals("delIp")){
			String ip = request.getParameter("ip");
			ProxyHelper.ALLOW_IP.remove(ip);
			result = cmd + " ok: allowIp.size="+ProxyHelper.ALLOW_IP.size();
		}
		else if(cmd.equals("addBlockIp")){
			String ip = request.getParameter("ip");
			ProxyHelper.BLOCK_IP.put(ip, "1");
			result = cmd+" ok: blockIp.size="+ProxyHelper.BLOCK_IP.size();
		}
		else if(cmd.equals("delBlockIp")){
			String ip = request.getParameter("ip");
			ProxyHelper.BLOCK_IP.remove(ip);
			result = cmd + " ok: blockIp.size="+ProxyHelper.BLOCK_IP.size();
		}
		else if(cmd.equals("addVip")){
			String vip = request.getParameter("vip");
			ProxyHelper.VIP.put(vip, "1");
			result = cmd + " ok: vip.size="+ProxyHelper.VIP.size();
		}
		else if(cmd.equals("delVip")){
			String vip = request.getParameter("vip");
			ProxyHelper.VIP.remove(vip);
			result = cmd + " ok: vip.size="+ProxyHelper.VIP.size();
		}
		else if(cmd.equals("reloadBlockIp")){
			ProxyHelper.reloadBlockIp();
			result = cmd + " ok: blockIp.size="+ProxyHelper.BLOCK_IP.size();
		}
		else if(cmd.equals("reloadAllowIp")){
			ProxyHelper.reloadAllowIp();
			result = cmd + " ok: allowIp.size="+ProxyHelper.ALLOW_IP.size();
		}
		else if (cmd.equals("poolIdle")) {
			result = ProxyHelper.TARGET_ZONE.getId() + ":" + InitServlet.cp.getIdleNum();
		}
		else if(cmd.equals("reloadZone")){
			if( ProxyHelper.reloadZone()){
				result = cmd +" ok";
			}
			else{
				result = cmd + " error";
			}
			result +="\n";
			for(Zone zone:ProxyHelper.ZONES){
				result += zone.toPrint()+"\n"; 
			}
		} else if (cmd.equals("sendMsg")){
			String msg = request.getParameter("msg");
			String type = request.getParameter("type");
			String targetId = request.getParameter("targetId");
			String req = cmd+"|"+type+"|"+targetId+"|"+msg;	
			log.error("AdminServlet:"+cmd+":"+msg+":"+type+":"+targetId+"");
			result = process(req);
		}
		else if(cmd.equals("viewAllPlayer")) {
			result = "";
			int allOnline = 0;
			for (Zone z : ProxyHelper.ZONES) {
				
				String res = OnlineUserNum.getOnlineUserResult(z);
				if (res != null) {
					String[] values = res.split("/");
					if (values.length == 2) {
						int online = Integer.valueOf(values[0]);
						int cached = Integer.valueOf(values[1]);
						z.setCurrOnlineUserNum(online);
						z.setCurrCachedUserNum(cached);		
					}
				}
				
				Integer online = z.getCurrOnlineUserNum();
				result += "[" + z.getId() + "]" 
				       + z.getName() 
				       + "(" + online + "/" + z.getCurrCachedUserNum() + ")" + "<br/>"; 
				if (online != null) {
					allOnline += online.intValue();
				}
			}
			result += "SumOnlineUser:" + String.valueOf(allOnline) + "<br/>";
		}
		else{
			result = process(cmd);
		}
		PrintWriter out = response.getWriter();
		out.print( result);
		out.flush();
		out.close();

	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String cmd = request.getParameter("cmd");
		String result = "no cmd";
		String realIp = request.getHeader("X-Forwarded-For-Pound");
		log.info("post|realIp|"+realIp);
		log.debug("request coming!!!!");
		if(cmd.equals("sendMsg")){
			String msg = request.getParameter("msg");
			String type = request.getParameter("type");
			String targetId = request.getParameter("targetId");
			String req = cmd+"|"+type+"|"+targetId+"|"+msg;	
			log.error("request coming!!!! req="+req);
			result = process(req);
		}
		out.print( result);
		out.flush();
		out.close();
	}
	
	private String process(String cmd){
		SynClient client  = new SynClient(
				ProxyHelper.TARGET_ZONE.getHost(), 
				ProxyHelper.TARGET_ZONE.getAdminPort(), 
				3000);
		String msg = null;
		if( client.connect()){
			msg = (String)client.sendAndRecv(cmd);			
		}
		else{
			msg = "connect to admin fail";
		}
		client.close();
		return msg;
	}
	

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
	}

}

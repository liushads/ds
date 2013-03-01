package com.ppsea.ds.proxy.pay;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.proxy.util.ProxyHelper;
//import com.tencent.wapgame.netgame.NetGameApp;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Servlet implementation class QQPayAllServlet
 */
public class QQPayAllServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger("PAY");  

	private int cpId;
	private short gameId;
	private short goodsId;
	private byte[] key;
	private String targetUrl;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QQPayAllServlet() {
    	
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
//	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		req.setCharacterEncoding("UTF-8");
//		resp.setContentType("text/vnd.wap.wml; charset=" + ProxyHelper.MSG_CHARSET);
//		PrintWriter out = resp.getWriter();
//		try{
//			String uid = req.getParameter("uid");
//			String ppid = req.getParameter("pid");
//			//String pnum = req.getParameter("num");
//			String retUrl = req.getParameter("retUrl");
//			String alias = req.getParameter("alias");
//			int pid = Integer.parseInt(ppid);
//			/**
//			int goodsCount = Integer.parseInt(req.getParameter("num"));
//			if(goodsCount < 0 || goodsCount > 100){
//				throw new ServletException("请输入0~99的数字！");
//			}
//			*/
//			//计算linkId，以便发货server能正确发货给用户
//			int zoneId = Integer.parseInt(ProxyHelper.TARGET_ZONE.getId());
//			if(zoneId <0 || zoneId > 99){
//				throw new ServletException("invalid zone id");
//			}
//			
//			String linkId = String.valueOf((long)pid*100 + zoneId);
//			
//			//游戏中进入支付的游戏点
//			// CommandResult result = new CommandResult(Command.STATUS_SUCC);
//			String encodeUid = NetGameApp.getInitUid(uid);
//			log.debug("uid="+uid+"|inituid="+encodeUid+"|linkId="+linkId+"|pid="+ppid+"|alias="+alias+"|retUrl="+retUrl);
//			String retWml = NetGameApp.getPay(uid, linkId, retUrl, alias, null, null);
//			
//			/*
//		    NameValuePair infoPair = new NameValuePair("info", info);
//		    NameValuePair[] data = { infoPair };
//		    log.warn("info = " + info);
//		    log.warn("url : " + NetGameApp.getQBPayUrl());
//		    String res = ProxyHelper.httpClientSendPost(NetGameApp.getQBPayUrl(), data);
//			 */
//			log.debug("retWml = "+retWml);
//			out.print(retWml);
//			out.close();
//			/*
//			result.setName("pa_QbS");
//			result.setVO("info", info);
//			result.setVO("goodsCount", goodsCount);
//			result.setVO("payUrl", NetGameApp.getQBPayUrl());
//			
//			//log.info("info:"+info);
//			Template t = ProxyHelper.getTemplate(result);
//        	if (t == null) {
//        		throw new ServletException("don't find match template file");
//        	}
//        	t.process(result.getData(), out);
//        	out.close();
//        	*/
//		}
//		catch(Exception e){
//			log.error("exception", e);
//			CommandResult result = new CommandResult(Command.STATUS_PAY_FAIL);
//			result.setVO(Command.TAG_DESC, e.getMessage());
//			Template t = ProxyHelper.getTemplate(result);
//	    	try {
//				t.process(result.getData(),out);
//			} catch (TemplateException e1) {
//				e1.printStackTrace();
//			}
//	    	out.close();
//		}
//	}

}

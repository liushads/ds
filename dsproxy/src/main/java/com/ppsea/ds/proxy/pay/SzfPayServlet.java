package com.ppsea.ds.proxy.pay;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.log4j.Logger;

import sun.misc.BASE64Encoder;

import com.ppsea.ds.proxy.util.ProxyHelper;
import com.ppsea.ds.proxy.servlet.ProxyServlet;
import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandResult;
//import com.tencent.wapgame.netgame.NetGameApp;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Servlet implementation class QbPayServlet
 */
public class SzfPayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger("PAY");  

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SzfPayServlet() {
        super();
    }
    
    public void init(){
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//		throws ServletException, IOException {
//		doPost(request, response);
//	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		resp.setContentType("text/vnd.wap.wml; charset=" + ProxyHelper.MSG_CHARSET);
//		PrintWriter out = resp.getWriter();
//		try{
//			String uid = req.getParameter("uid");
//			String ppid = req.getParameter("pid");
//			String retUrl = req.getParameter("retUrl");
//			if (null==uid||ppid==null) {
//				throw new ServletException("参数不正确,请重新充值！");
//			}
//			int pid = Integer.parseInt(ppid);
//			
//			//计算linkId，以便发货server能正确发货给用户
//			int zoneId = Integer.parseInt(ProxyHelper.TARGET_ZONE.getId());
//			if(zoneId <0 || zoneId > 99){
//				throw new ServletException("invalid zone id");
//			}
//			String linkId = String.valueOf((long)pid*100 + zoneId);
//			
//			log.debug("uid = "+uid);
//			log.debug("pid = "+pid);
//			log.debug("retUrl = "+retUrl);
//			
//			//游戏中进入支付的游戏点		
//		    String retWml = NetGameApp.getSzfPay(uid, linkId, retUrl);
//		    /*
//		    NameValuePair infoPair = new NameValuePair("info", info);
//		    NameValuePair[] data = { infoPair };
//		    String res = ProxyHelper.httpClientSendPost(NetGameApp.getShenzhouPayUrl(), data);
//		    */
//		    log.debug("retWml = "+retWml);
//			out.print(retWml);
//			out.close();
//		    /*
//		    CommandResult result = new CommandResult(Command.STATUS_SUCC);
//			result.setName("pa_SzfS");
//			result.setVO("info", info);
//			result.setVO("payUrl", NetGameApp.getShenzhouPayUrl());
//
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

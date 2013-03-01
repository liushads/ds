package com.ppsea.ds.proxy.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ppsea.ds.proxy.util.ClientPoolMulti;
import com.ppsea.ds.proxy.util.ProxyHelper;
import com.ppsea.ds.proxy.util.SynClient;
import com.ppsea.ds.util.GlobalConfig;

public class InitServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(InitServlet.class);
	
	public static ClientPoolMulti cp;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public InitServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy();
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
//		String cmd = request.getParameter("cmd");
//		PrintWriter out = response.getWriter();
//		if(cmd.equals("pool")){
//			out.print(ClientPoolMulti.show());
//		}
//		else{
//			out.print("ok");
//		}
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		log.info("====== InitServlet do===========");
		
		ProxyHelper.MSG_CHARSET = getServletContext().getInitParameter("charSet");
		ProxyHelper.REGEX_FILE = getServletContext().getInitParameter("regexFile");
		ProxyHelper.TEMPLATE_DIR = getServletContext().getInitParameter("templateDir");
		ProxyHelper.POOLSIZE = Integer.parseInt(getServletContext().getInitParameter("poolSize"));

		//根据配置文件初始化
		String path = getServletContext().getRealPath("/");
		try{
			ProxyHelper.initCongfig(GlobalConfig.GLOBAL_CONFIG_FILE);
		}
		catch(Exception e){
			log.error("exception:", e);
			throw new ServletException(e.getMessage());
		}    
        
        log.info("======= targetZone id = "+ProxyHelper.TARGET_ZONE.getId());
        
        //加载白名单  
        ProxyHelper.loadBlockIP(path + "WEB-INF/"+ getServletContext().getInitParameter("blockIp"));
      
        
        //加载白名单  
        ProxyHelper.loadAllowIP(path + "WEB-INF/"+ getServletContext().getInitParameter("allowIp"));
//        ProxyHelper.loadZone0QQ(path + "WEB-INF/"+ getServletContext().getInitParameter("zone_0_qq"));
        
//        ProxyHelper.loadBjIP();
        
	    //初始化模板文件
        String regexFile = path+"WEB-INF/"+ ProxyHelper.REGEX_FILE;
        ProxyHelper.initFreeMarker(getServletContext(), regexFile);
        ProxyHelper.initResource();

        cp = new ClientPoolMulti(ProxyHelper.TARGET_ZONE.getHost()
        			, ProxyHelper.TARGET_ZONE.getPort()
        			, 50, SynClient.CODEC_SERIALIZATION, 3000, 10, 0);
	} 
}

package com.ppsea.ds.proxy.pay;

import java.io.IOException;
import java.io.OptionalDataException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.ppsea.ds.command.CommandRequest;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.proxy.servlet.InitServlet;
import com.ppsea.ds.proxy.util.ProxyHelper;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.util.MD5;
//import com.tencent.wapgame.netgame.NetGameApp;

import freemarker.template.Template;

public class GetAccountServlet extends HttpServlet {
	protected static final String URL_SPLIT_LINE = "/";
	private static final long serialVersionUID = 7209598159429647613L;
	private static final Logger log = Logger.getLogger("PAY");  

	private String gameBaseUrl;
	private String loginUrl;
	
	public void init(){
		gameBaseUrl = ProxyHelper.getGameBaseUrl();
//		loginUrl = NetGameApp.getLoginUrl();
	}

//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		String localUrl = req.getRequestURI() + "?" + req.getQueryString();
//
//		if (ProxyHelper.COMMAND_PLAYER_ENTRY.equals(req.getQueryString())) {
//			resp.sendRedirect(loginUrl);
//			return;
//		}
//
//		try {
//			req.setCharacterEncoding("UTF-8");
//			resp.setContentType("text/vnd.wap.wml; charset=UTF-8");
//			CommandRequest cmdReq = new CommandRequest();
//			PrintWriter out = resp.getWriter();
//
//			// 解析URL，初始化cmdReq
//			parseURL(req, cmdReq);
//			String realIp = req.getHeader("X-Forwarded-For-Pound");
//			
//			if (StringUtils.isBlank(realIp)) {
//				realIp = req.getRemoteAddr();
//			}
//
//			log.info("[request:" + cmdReq.toString() + "]");
//			cmdReq.setRealIp(realIp);
//			cmdReq.setProxyMd5Key(ProxyHelper.TARGET_ZONE.getProxyKey());
//			CommandResult result = (CommandResult) InitServlet.cp.sendAndRecv(cmdReq);
//			if (result == null) {
//				log.error("[result is null,request url:" + localUrl + "]");
//				result = new CommandResult("fail");
//				result.setVO("err_msg",  new ErrorMsg(ErrorCode.ERR_SYS, "系统忙，稍后再试"));
//				result.setVO("pid", cmdReq.getPid());
//			}
//			log.info("[recv:" + result.toString() + "]");
//
//			String type = "get";
//			String userId = cmdReq.getPs()[0];
//			//String uid = cmdReq.getUid();
//			String encodeUid = NetGameApp.getInitUid(userId);
//			//String param = "type="+type+"&userId="+encodeUid;
//			String param = "type="+type+"&userId="+userId;
//			String sign = MD5.encode(param+ProxyHelper.GLOBAL_MD5KEY); 
//			param = param + "&sign=" + sign;
//			
//	    	String url = ProxyHelper.PAY_URL+"?"+param;
//	    	log.info("url:"+url);
//
//	    	JSONObject json = ProxyHelper.httpClientSend(url);
//
//	    	if(json == null || !json.getBoolean("result")){
//				result = new CommandResult("fail");
//				result.setVO("err_msg",  new ErrorMsg(ErrorCode.ERR_PAY, "获取短信账户信息失败，请稍候再试！"));
//				result.setVO("pid", cmdReq.getPid());
//	    	}
//	    	else{
//	    		result.getData().put("gold", json.getInt("gold"));
//	    	}
//
//			log.info("[recv:" + result.toString() + "]");
//			
//			result.setName(cmdReq.getCmd());
//			Template template = ProxyHelper.getTemplate(result);
//			if (template == null) {
//				throw new ServletException("don't find match template file");
//			}
//			
//			result.getData().put("gameBaseUrl", gameBaseUrl);
//			result.getData().put("qqtime", ProxyHelper.timeNow());
//			result.getData().put("Md5Key", ProxyHelper.TARGET_ZONE.getProxyKey());
//			result.getData().put("Md5GlobalKey", ProxyHelper.GLOBAL_MD5KEY);
//			result.getData().put("zoneId", ProxyHelper.TARGET_ZONE.getId());
//			template.process(result.getData(), out);
//			out.close();
//		}
//		catch(OptionalDataException e){
//			log.error("OptionalDataException: eof=" + e.eof+", length="+e.length);
//			log.error("exception:" + localUrl, e);
//			resp.sendRedirect(loginUrl);
//			return;
//		}
//		catch (Exception e) {
//			log.error("exception:" + localUrl, e);
//			resp.sendRedirect(loginUrl);
//			return;
//		}
//	}

//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		doGet(req, resp);
//	}

	protected void parseURL(HttpServletRequest req, CommandRequest cmdReq) throws ServletException {
		String reqString = req.getQueryString();
		if (StringUtils.isNotBlank(reqString)) {
			try {
				reqString = URLDecoder.decode(reqString, "UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
		}

		String splitReqString = reqString;
		if (!splitReqString.endsWith(URL_SPLIT_LINE)) {
			splitReqString += URL_SPLIT_LINE;
		}

		// 登陆入口
		if (splitReqString.startsWith(ProxyHelper.COMMAND_PLAYER_ENTRY)) {
			String[] reqParams = splitReqString.replaceAll("&", URL_SPLIT_LINE).split(URL_SPLIT_LINE, 4);
			if (ArrayUtils.getLength(reqParams) == 4) {
				cmdReq.setCmd(reqParams[0]);
				String uid = getMatcher("uid=([\\d]+)", splitReqString);
				String sid = getMatcher("sid=([0-9a-zA-Z]+)", splitReqString);
				cmdReq.setUid(uid);
				cmdReq.setSid(sid);

				if (StringUtils.isNotBlank(req.getParameter("post-uid"))) {
					cmdReq.setUid(req.getParameter("post-uid"));
				}
				cmdReq.setPs(StringUtils.isBlank(reqParams[3]) ? new String[0] : reqParams[3].split(URL_SPLIT_LINE));
			} else {
				throw new ServletException("入口地址参数错误：" + reqString);
			}
			return;
		}

		// 普通请求
		splitReqString = splitReqString.replaceAll("(/|)((&t=|=).*)", "/");
		String[] ps = splitReqString.split(URL_SPLIT_LINE, 3);
		if (ps.length == 3) {
			String[] strs = splitReqString.split("-", 3);
			if (strs.length < 3) {
				throw new ServletException("URL参数错误：" + reqString);
			}
			String verifyKey = strs[0];
			String verifyStr = strs[2];
			String cmd = verifyStr.split(URL_SPLIT_LINE)[0];
			if (StringUtils.isNotBlank(verifyKey) && StringUtils.isNotBlank(verifyStr) && StringUtils.isNotBlank(cmd)) {
				cmdReq.setVerifyKey(verifyKey);
				cmdReq.setVerifyStr(verifyStr);
				cmdReq.setCmd(cmd);
			} else {
				cmdReq.setVerifyKey(null);
				cmdReq.setVerifyStr(null);
				cmdReq.setCmd(ps[0]);
			}
			if(cmd.startsWith(ProxyHelper.COMMAND_USER_PREFIX)){
				cmdReq.setUid(ps[1]);
			}
			else{
				cmdReq.setPid(ps[1]);
			}
			// 最多10个额外参数
			String[] psParam = new String[10];
			int maxLen = 0;
			if (!StringUtils.isBlank(ps[2])) {
				String[] fixPs = ps[2].split(URL_SPLIT_LINE);
				for (int i = 0; i < fixPs.length; ++i) {
					psParam[i] = fixPs[i];
					if (i + 1 > maxLen)
						maxLen = i + 1;
				}
			}
			for (int i = 0; i < 10; i++) {
				String par = req.getParameter(String.valueOf(i + 1));
				if (par == null || par.equals("null"))
					continue;
				psParam[i] = par;
				if (i + 1 > maxLen)
					maxLen = i + 1;
			}
			String[] psReal = new String[maxLen];
			for (int i = 0; i < maxLen; ++i) {
				psReal[i] = psParam[i];
			}
			cmdReq.setPs(psReal);
			return;
		}
		throw new ServletException("参数格式错：" + reqString);
	}

	public static String getMatcher(String regex, String source) {
		String result = "";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(source);
		while (matcher.find()) {
			result = matcher.group(1);
		}
		return result;
	}


}

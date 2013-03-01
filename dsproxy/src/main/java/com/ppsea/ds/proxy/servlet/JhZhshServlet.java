package com.ppsea.ds.proxy.servlet;

import java.io.IOException;
import java.io.OptionalDataException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandRequest;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.proxy.util.ProxyHelper;

import freemarker.template.Template;

public class JhZhshServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			req.setCharacterEncoding("UTF-8");
			resp.setContentType("text/vnd.wap.wml; charset=UTF-8");
			CommandRequest cmdReq = new CommandRequest();
			PrintWriter out = resp.getWriter();

			// 解析URL，初始化cmdReq
			String realIp = req.getHeader("X-Forwarded-For-Pound");
			
			if (StringUtils.isBlank(realIp)) {
				realIp = req.getRemoteAddr();
			}

			String uid = req.getParameter("uid");
			String id = req.getParameter("id");
			
			
			CommandResult result = new CommandResult(Command.STATUS_SUCC);
			result.setName("z_g");
			result.setVO("lable_user_id", uid);
			result.setVO("lable_id", id);
		
			Template template = ProxyHelper.getTemplate(result);
			if (template == null) {
				throw new ServletException("don't find match template file");
			}
			
			template.process(result.getData(), out);
			out.close();
		}
		catch(OptionalDataException e){
			return;
		}
		catch (Exception e) {
			return;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}

}

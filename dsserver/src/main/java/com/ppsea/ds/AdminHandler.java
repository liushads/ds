package com.ppsea.ds;

import org.apache.log4j.Logger;
import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.IoSession;

import com.ppsea.ds.command.admin.AdminCmd;
import com.ppsea.ds.command.admin.AdminCmdChain;
import com.ppsea.ds.command.admin.AdminRequest;

/**
 * gserver管理端口，重置玩家数据
 * 
 * */
public class AdminHandler extends IoHandlerAdapter {
	private static final Logger log = Logger.getLogger(AdminHandler.class);
	
	public void sessionOpened(IoSession session) {
	}

	/**
     * @see org.apache.mina.common.IoHandlerAdapter#sessionCreated(org.apache.mina.common.IoSession)
     */
    @Override
    public void sessionCreated(IoSession session) throws Exception {
    	log.info("AdminHandler sessionCreated:(" + (String)session.toString() + ")");
    }

	public void messageReceived(IoSession session, Object message) {
		try{
			if (!(message instanceof String)) {
				session.write("admin cmd invalid");
				return;
			}
			log.info("AdminHandler Message:(" + (String)message + ")");
			AdminRequest req = new AdminRequest((String)message);
			if(req.cmd == null){
				session.write("admin cmd invalid");
				return;
			}
			
			for(AdminCmd adminCmd:AdminCmdChain.getChain()){
				if(adminCmd.isResponsible(req)){
					session.write(adminCmd.process().toString());
					break;
				}
			}
		}
		catch(Exception e){
			log.error("exception", e);
			session.write("error:"+e.getMessage());
		}
	}

	public void exceptionCaught(IoSession session, Throwable cause){
		log.error("exception",cause);
		session.close();
	}
}

package com.ppsea.ds;

import org.apache.log4j.Logger;
import org.apache.mina.common.IdleStatus;
import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.IoSession;

import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandRequest;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.command.CommandSet;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.service.ErrorCode;


public class ServerHandler extends IoHandlerAdapter {
	private static final Logger log = Logger.getLogger(ServerHandler.class);
	private static final Logger logStat = Logger.getLogger("STAT");
	
	//超过5分钟没有数据读写，则关闭改连接。
	private static int SESSION_IDEL_TIME = 300;
	
	public void sessionOpened(IoSession session) {
		log.info("session opened, idle time=" + SESSION_IDEL_TIME);
		session.setIdleTime(IdleStatus.BOTH_IDLE, SESSION_IDEL_TIME);
	}
	
	public void sessionClosed(IoSession session){
		log.info("session closed!");
	}
	
	 public void sessionIdle(IoSession session, IdleStatus status) {		
		 log.info("close idel connection: session.isConnected="+session.isConnected());
		 session.close();
	 }
	

	public void messageReceived(IoSession session, Object message) {
		CommandRequest cmdReq = null;
		long u1 = System.currentTimeMillis();
		
		if (!(message instanceof CommandRequest)) {
			log.error("recv invalid message");
			session.close();
			return;
		}

		cmdReq = (CommandRequest)message;
		CommandResult result = exec(cmdReq);
		int time = (int)(System.currentTimeMillis() - u1);
		resp(session, result);

		//指令流水日志
		if(result != null){
			StringBuffer sb = new StringBuffer();
			sb.append(PlayerMG.instance.getOnlineNum()).append("|")
				.append(cmdReq.getCmd()).append("|")
				.append(result.getName()).append("|")
				.append(result.getStatus()).append("|")
				.append(time).append("|")
				.append(result.getLogMsg());
			logStat.debug(sb);
		}
	}

	public void exceptionCaught(IoSession session, Throwable cause){
		try {
			session.close();
			log.error("exception1",cause);
			log.error("exception2"+cause.getLocalizedMessage(),cause.fillInStackTrace());
			
			StackTraceElement[] s = cause.getStackTrace();
			if(s != null && s.length >0 ) {
				log.error(s[0]);			
			}
		} catch (Exception e) {
			log.error("exception3",e);
		}
	}
	
	private CommandResult exec(CommandRequest cmdReq){
		try{
			//md5key校验，防止proxy连错server
			if (cmdReq.getProxyMd5Key() == null 
				|| !cmdReq.getProxyMd5Key().equals(GameServer.SERVER_MD5KEY)) 
			{
				log.error("ERROR!proxy & gserver md5key not match:" 
						+ cmdReq.getProxyMd5Key() + "<->" + GameServer.SERVER_MD5KEY);
				throw new PpseaException("非法proxy");
			}
			
			Command cmd = CommandSet.getInstance().getCommand(cmdReq.getCmd());
			if( cmd == null ){
				throw new PpseaException("非法指令");
			}
			
			CommandResult result = cmd.execute(cmdReq);
			if(result.getName().equals(CommandResult.UNKNOWN)){ 
				result.setName(cmdReq.getCmd());
			}
			return result;
		}
		catch(PpseaException e){
			CommandResult result = new CommandResult(Command.STATUS_FAIL);
			result.setVO(Command.TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_CMD,"指令"+cmdReq.getCmd()+"不存在！"));
			return result;
		}
	}
	
	private void resp(IoSession session, CommandResult result){
		if( session.isConnected()){
			session.write(result);		
		}
		else{
			session.close();
			log.error("disconnect[cmd:" + result.getName() 
					+ ",status:" + result.getStatus()
					+ ",player:" + result.getPlayerName()
			        + "]");
		}
	}
}

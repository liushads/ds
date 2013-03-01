/**
 *AuthCommand.java
 *
 * Function 需要登陆才能执行的命令基础类 
 *
 *   ver     date      		author
 * ��������������������������������������������������������������������
 *   		 Nov 23, 2008 		Daniel.Hao
 *
 * Copyright (c) 2008, TNT All Rights Reserved.
*/

package com.ppsea.ds.command;


import org.apache.log4j.Logger;

import com.ppsea.ds.GameServer;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Npc;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItemUsing;
import com.ppsea.ds.exception.BookMarkException;
import com.ppsea.ds.exception.CacheFullException;
import com.ppsea.ds.exception.FrequencyException;
import com.ppsea.ds.exception.LockException;
import com.ppsea.ds.exception.NoMoneyException;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.NpcMG;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.FightService;

/**
 * ClassName:BaseCommand
 *
 * @author   Daniel.Hao
 * @version  
 * @since    Ver 1.1
 * @Date	 2008	Nov 23, 2008		6:24:15 PM
 *
 * @see 	 
 */
public abstract class BaseCmd implements Command{
	
	private static final Logger log = Logger.getLogger(BaseCmd.class);
	
	public static String sSid = null;
	
	/**
	 * Creates a new instance of BaseCommand.
	 *
	 */
	public BaseCmd() {
		super();
	}
	
	
	public CommandResult execute(CommandRequest cmdReq){
		return execute(cmdReq, true);
	}
		
	public CommandResult execute(CommandRequest cmdReq, boolean needVerify){

		String pid = cmdReq.getPid();
		int playerId = Integer.valueOf(pid);
		Player player = null;
		try{
			//系统正在退出
			if (GameServer.isTerminated() == true) {
				throw new PpseaException("系统维护,请稍后再试");
			}

			player = PlayerMG.instance.getAndCheckPlayer(cmdReq, playerId, needVerify);
			
			
			//更新用户最近访问时间
			player.setLastAccessTime(System.currentTimeMillis());
			player.setIp(cmdReq.getRealIp());
			
			//记录执行前以用户状态
			
			long copper =player.getCopper();
			int exp = player.getExp();
			int gold = player.getGold();
			int advGold = player.getAdvGold();
			int level = player.getLevel();
			
			CommandResult result = null;
			synchronized (player) {
				//频率检查
				
				checkFrequency(player);
								
				log.info("pre:"+ cmdReq + "," + player.printSelf());
				//检查时效道具,每次只移除一个，以免异常
				for(PlayerItemUsing usingItem:player.getUsingItemList()){
					if(usingItem.getLeft() <= 0){
						player.removeUsingItem(usingItem);
						break;
					}
				}
				
				if(result == null){
					
					// 执行命令请求
					result = done(player, cmdReq.getPs());
					
					// 检查是否有战斗消息
					FightService.checkFightMessage(player, result, null);
					
				}
				
			}
			
			//执行后player状态
			String doneLog = "done:"+ cmdReq.getCmd() + "," + result.getStatus() + "," + player.printSelf();
			if (result.getStatus().equals(STATUS_FAIL)) {
				Object err = result.getVO(TAG_ERR_MSG);
				if (err != null) {
					ErrorMsg errMsg = (ErrorMsg)err;
					doneLog += "[" + errMsg.code + ":" + errMsg.text + "]";
				}
			}
			log.info(doneLog);
			
			//设置通用状态
			result.setVO(TAG_PID, player.getId());
			result.setVO(TAG_PLAYER, player);
			result.setVO(TAG_LOCATION, player.getLocation());
			if(player.getCityFacility() != null){
				result.setHref(TAG_CURR_FACILITY, player.getCityFacility().getAlias(), player.getCityFacilityId());
				result.setVO("currentCityFacility", player.getCityFacility());
				result.setVO("currentCity", player.getCityFacility().getCity());
			}
			int lastNpcId = player.getLastNpcId();
			if(lastNpcId > 0 ){
				Npc lastNpc = NpcMG.instance.getNpc(lastNpcId);
				result.setHref(TAG_LAST_NPC, lastNpc.getName(), lastNpc.getId());
				result.setVO("lastNpc", lastNpc);
			}			
						
			
			//设置统计日志信息
			copper =player.getCopper()-copper;
			exp = player.getExp()-exp;
			gold = player.getGold()-gold;	
			advGold = player.getAdvGold()-advGold;
			level = player.getLevel()-level;
			
			StringBuffer sb =new StringBuffer();
			sb.append(player.getUserId()).append("|")
				.append(player.getId()).append("|")
				.append(exp).append("|")
				.append(copper).append("|")			
				.append(gold).append("|")
				.append(player.getLevel()).append("|")
				.append(advGold).append("|");
			if(result.getExtMsg()!=null){
				sb.append(result.getExtMsg());
			}
			result.setLogMsg(sb);
			return result;
		}
		catch(CacheFullException e){
			//内存满，不能接纳新用户
			CommandResult result = new CommandResult();
			result.setStatus(STATUS_FULL);
			result.setVO(TAG_PID, playerId);
			result.setVO(TAG_PLAYER, player);
			return result;
		}
		catch(FrequencyException e){
			CommandResult result = new CommandResult();
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_FREQUENCE, e.getMessage()));
			result.setVO(TAG_PID, playerId);
			result.setVO(TAG_PLAYER, player);
			return result;
		}
		catch(LockException e){
			CommandResult result = new CommandResult();
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_SYS, e.getMessage()));
			result.setVO(TAG_PID, playerId);
			result.setVO(TAG_PLAYER, player);
			return result;
		}
		catch(NoMoneyException e){
			if(player == null)player = PlayerMG.instance.getPlayerFromCache(Integer.parseInt(pid));
			CommandResult result = callOtherCmd("pa_I",player,"1");
			result.setStatus(STATUS_NO_MONEY);
			result.setVO(TAG_DESC, "");
			return result;
		}
		catch (BookMarkException e) {
			CommandResult result = new CommandResult(Command.STATUS_BM_ERR);
			result.setVO(TAG_PID, playerId);
			result.setVO(TAG_PLAYER, player);
			return result;
		}
		catch (PpseaException e) {
			log.error("pException", e);
			CommandResult result = new CommandResult(Command.STATUS_FAIL);
			result.setVO(TAG_PID, playerId);
			result.setVO(TAG_PLAYER, player);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_SYS, e.getMessage()));
			return result;
		} finally {
		}
	}
	

	/**
	 * 
	 * @param player
	 * @param ps
	 * @return
	 * @throws PpseaException
	 */
	protected abstract CommandResult done(Player player, String[] ps) throws PpseaException;

	/** 
	 * 检测访问频率
	 * 2秒内3次访问
	 */
	private static int MAX_REQ_TIMES = 3;
	private static long INTERVAL_TIME = 2L*1000;
	protected void checkFrequency(Player player) throws FrequencyException {
		
		long now = System.currentTimeMillis(); 
		if(player.getStartTime() == 0){
			// 第一次请求
			player.setStartTime(now);
			player.setReqTimes(1);
			return;
		}
		
		// 当前请求和上次请求时间差,大于2秒钟重新开始
		long diff = now - player.getStartTime();
		if(diff > INTERVAL_TIME){
			player.setStartTime(now);
			player.setReqTimes(1);
			return;
		}
			
		// 计算2秒钟内的请求次数
		if(player.getReqTimes() <= MAX_REQ_TIMES){
			player.setReqTimes(player.getReqTimes()+1);
			return;
		}
		
		//超过频率限制，扔出异常
		log.error("flush frequency too fast:"+player.getId()+"--"+player.getReqTimes());
		throw new FrequencyException("你的访问频率过快");
	}		

	
	
	/**
	 * 指令跳转
	 * @param data
	 * @return
	 * @throws PpseaException
	 */
	public static CommandResult callOtherCmd(CommandRequest cmdReq){
		CommandResult result = CommandSet.getInstance().getCommand(cmdReq.getCmd()).execute(cmdReq, false);
		if(result.getName().equals(CommandResult.UNKNOWN)){ 
			result.setName(cmdReq.getCmd());
		}	
		return result;
	}
	
	/**
	 * 指令跳转
	 * @param data
	 * @return
	 * @throws PpseaException
	 */
	public static CommandResult callOtherCmd(String cmd,Player player,String...ps){
		CommandRequest newCmd = new CommandRequest();
		newCmd.setCmd(cmd);
		newCmd.setPid(player.getId().toString());
		newCmd.setUid(player.getUserId().toString());
		if(ps != null && ps.length > 0) newCmd.setPs(ps);
		return callOtherCmd(newCmd);
	}
	
	// --------------------------------------------------------------------------- 静态方法
	
	/**
	 * 设置返回错误消息 
	 **/
	public void setResult(CommandResult result,String status,int errorCode,String msg){
		ErrorMsg errMsg = new ErrorMsg(errorCode, msg);
		setResult(result,status,errMsg);
	}
	
	/**
	 * 设置返回错误消息 
	 **/
	public void setResult(CommandResult result,String status,ErrorMsg errorMsg){
		result.setStatus(status);
		result.setVO(TAG_ERR_MSG, errorMsg);
	}
	/**
	 * 
	 * @param result
	 * @param status
	 * @param errorMsg
	 */
	public void setFailResult(CommandResult result,ErrorMsg errorMsg){
		result.setStatus(STATUS_FAIL);
		result.setVO(TAG_ERR_MSG, errorMsg);
	}

}

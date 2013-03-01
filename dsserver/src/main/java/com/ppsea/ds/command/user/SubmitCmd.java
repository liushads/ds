package com.ppsea.ds.command.user;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandRequest;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.GlobalGenerator;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.Sect;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.DBManager;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.manager.SectMG;
import com.ppsea.ds.service.DBService;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.service.PlayerService;
import com.ppsea.ds.task.BookStoreVipCheckerTask;
import com.ppsea.ds.util.LoggerHelper;
import com.ppsea.ds.util.StringUtil;
import com.ppsea.ds.util.WordFilter;

public class SubmitCmd extends BaseCmd{
	private static final Logger log = Logger.getLogger(SubmitCmd.class);
	
	@SuppressWarnings("unchecked")
	@Override
	/**
	 * ps[0]= name, ps[1]= sex, ps[2]=cid, ps[3]=scid
	 * */
	public CommandResult execute(CommandRequest cmdReq){
		CommandResult result = new CommandResult(STATUS_SUCC);
		
		//是否达到最大在线人数
		if(PlayerMG.instance.getOnlineNum() >= Constants.PLAYER_CACHE_LIMIT ){
			log.error("cache full!");
			result.setStatus(STATUS_FULL);
			return result;
		}
		//是否达到该区限制人数
		if(Constants.MAX_PLAYER_NUM > 0 && Constants.CURR_PLAYER_NUM > Constants.MAX_PLAYER_NUM ){
			log.error("max player limit!");
			result.setStatus(STATUS_FULL);
			return result;
		}
		
		String[] ps = cmdReq.getPs();
		ErrorMsg ret = createPlayer(cmdReq,result);
		if( ret.code != ErrorCode.SUCC){
//			String uid = cmdReq.getUid();
//			result.setVO(TAG_UID, uid);
//			List<Sect> sects = SectMG.instance.getSectList();
//			Collections.sort(sects, new Comparator() {
//				private final int[] vs = { -1, 0, 1 };
//				private final Random rnd = new Random(System.currentTimeMillis());
//
//				public int compare(final Object arg0, final Object arg1) {
//					return vs[rnd.nextInt(vs.length)];
//				}
//			});
//			result.setVO("ret", ret.text);
//			result.setVO(TAG_SECTS, sects);
			result.setVO(TAG_ERR_MSG, ret);
			result.setStatus("fail");
			return result;
		}
//		else{
//			Player player = (Player)ret.getObj();
//			//调用门派列表cmd，让用户选择门派加入
//			CommandRequest newCmd = new CommandRequest();
//			newCmd.setCmd("se_J");
//			newCmd.setPid(player.getId().toString());
//			newCmd.setPs(new String[]{ps[2].toString()});
//			result = callOtherCmd(newCmd);
//		}
		
		String uid = cmdReq.getUid();
		StringBuffer sb =new StringBuffer();
		sb.append(uid).append("|")
			.append(0).append("|");
		result.setLogMsg(sb);
		
		return result;
	}
	
	/**
	 * 创建player，并提交到数据库。
	 * @param cmdReq
	 * @return：大于0，表示playerId；小于0，标示错误
	 */
	private ErrorMsg createPlayer(CommandRequest cmdReq,CommandResult result){
		String[] ps = cmdReq.getPs();
		String name = ps[0];
		int sex = Integer.parseInt(ps[1]);
		int sect_id = Integer.parseInt(ps[2]);
		
		//检查名字长度
		if(name==null || name.length()<2 || name.length()>8 ){
			return new ErrorMsg(ErrorCode.ERR_NAME_INVALID, "角色名必须1~6个字符。 再换一个名字试试：");
		}		
		//trim掉空格和回车换行
		name = name.replaceAll(" ", "").replaceAll("\r\n", "");		
		name = StringUtil.formatStringToXML(name);
		
		//检查是否有关键字
		if(WordFilter.checkDirtyWorld(name.toLowerCase())){
			return new ErrorMsg(ErrorCode.ERR_DIRTY_WORD, "昵称中有非法字符");
		}
		
		//角色数检查
		String uid = cmdReq.getUid();
		List<Player> players = DBManager.queryPlayerByUser(uid);
		if( players != null && players.size() >= Constants.MAX_PLAYER_IN_USER){
			log.error("too many player in user: user_id="+uid);
			return new ErrorMsg(ErrorCode.ERR_PLAYER_IN_USER_LIMIT, "超过账户最大角色数："+Constants.MAX_PLAYER_IN_USER);
		}
		
		//检查角色名是否使用过
		if(DBManager.queryPlayer(name) != null){
			log.error("same player name:"+name);
			return new ErrorMsg(ErrorCode.ERR_NAME_EXIST, "角色名已经被使用，别着急哦， 再换一个名字试试");
		}
		
		//创建角色
		Player player = new Player();
		player.setId(GlobalGenerator.instance.getIdForNewObj(player));
		player.init();
		player.setName(name);
		player.setSex((byte)sex);
		player.setUserId(uid);
		player.setMap("1,2,3,4,5,6,");
		player.setSectId(sect_id);
		PlayerService.decodeWorldMap(player);
		PlayerService.setOnline(player);
		
		//写入数据库
		if(DBService.commitNoCacheUpdate(player) <0){
			log.error("commitNoCacheUpdate error: uid=" + player.getUserId());
			return new ErrorMsg(ErrorCode.ERR_SYS, "系统忙稍后再试");
		}
		
		//加入缓存
		PlayerMG.instance.addPlayer(player);
		ItemService.addItem(player, 10284, 10, false);
		BookStoreVipCheckerTask.getInstance().addPlayer2Check(player);
		
		//增加该区角色数
		Constants.CURR_PLAYER_NUM++;
		
		ErrorMsg ret = new ErrorMsg(ErrorCode.SUCC, null, player);
		result.setVO("pid", player.getId());
		return ret;
	}

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		return null;
	}
}

package com.ppsea.ds.command.player;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandRequest;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.command.user.SubmitCmd;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.GlobalGenerator;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerName;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.DBManager;
import com.ppsea.ds.service.DBService;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.util.StringUtil;
import com.ppsea.ds.util.WordFilter;

public class UpdateNameTwoCmd extends BaseCmd {

	private static final Logger log = Logger.getLogger(SubmitCmd.class);
	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		String name=ps[0];
		//检查名字长度
		if(name==null || name.length()<2 || name.length()>8 ){
			String error="昵称长度应该为2-8个字符";
			result.setVO(TAG_ERROR, error);
			return result;
		}	
		//trim掉空格和回车换行
		name = name.replaceAll(" ", "").replaceAll("\r\n", "");		
		name = StringUtil.formatStringToXML(name);
		//检查是否有关键字
		if(WordFilter.checkDirtyWorld(name.toLowerCase())){
			String error="昵称中有非法字符";
			result.setVO(TAG_ERROR, error);
			return result;
		}
		//检查角色名是否使用过
		if(DBManager.queryPlayer(name) != null){
			log.error("same player name:"+name);
			String error="角色名已经被使用";
			result.setVO(TAG_ERROR, error);
			return result;
		}
		if(DBManager.queryPlayername(name) != null){
			log.error("same player name:"+name);
			String error="角色名已经被使用";
			result.setVO(TAG_ERROR, error);
			return result;
		}
		int id=player.getId();
		if (DBManager.queryPlayerid(id)!=null) {
			log.error("same player id:"+player.getId());
			String error="你已经申请过改名";
			result.setVO(TAG_ERROR, error);
			
			return result;
		}
		PlayerName playerName=new PlayerName();
		playerName.setId(GlobalGenerator.instance.getIdForNewObj(playerName));
		playerName.setPlayerId(id);
		playerName.setName(name);
		Date date = new Date(); 
		playerName.setApplyTime(date);
		if(DBService.commitNoCacheUpdate(playerName) <0){
			String error="系统忙稍后再试";
			result.setVO(TAG_ERROR, error);
			return result;
		}
		result.setStatus(STATUS_INDEX);
		return result;
	}


}

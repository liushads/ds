package com.ppsea.ds.command.player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.PlayerService;
/**
 * 查看自己的状态 
 **/
public class PasswdSetCmd extends BaseCmd {
	
	
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(PasswdSetCmd.class);
	private static String str = "[^0-9a-zA-Z]";
	private static Pattern p = Pattern.compile(str);
	
	/**
	 * @param ps[0]: 0-书签密码 1-道具密码 
	 * @param ps[1]: oldPasswd
	 * @param ps[2]: newPasswd1
	 * @param ps[3]: newPasswd2
	 **/
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		if(ps.length < 4){
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_SYS, "参数错误"));
			return result;
		}
		//密码必须是4-10位的数字或字母
		if(ps[2].length()<4 || ps[2].length() > 10 || !StringUtils.isAlphanumeric(ps[2])){
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_PASSWD, "新密码需要是4-10位的数字或字母"));
			return result;
		} 
		
		
		if(!ps[2].equals(ps[3])){
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_PASSWD, "新密码不一致"));
			return result;
		}
		
		Matcher m = p.matcher(ps[2]);
		if (m.find()) {
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_PASSWD, "密码必须是数字或者字母"));
			return result;
		}
		
		int type = Integer.valueOf(ps[0]);
		ErrorMsg errMsg = PlayerService.setPasswd(player,type, ps[1],ps[2]);;
		if(errMsg.code != ErrorCode.SUCC){
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, errMsg);
			return result;
		}
		if (type == 0) {
			//书签密码,需要重设书签.
			player.setBookMarkFlag(false);
		}
		result.setVO(TAG_PASSWD, ps[2]);
		return result;
	}
}

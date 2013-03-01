package com.ppsea.ds.command.player;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandRequest;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.CityFacility;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.MapMG;
import com.ppsea.ds.service.ChatService;
import com.ppsea.ds.service.DBService;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.service.PlayerService;

public class EntryCmd extends BaseCmd{
	private static final Logger log = Logger.getLogger(EntryCmd.class);

	/**
	 * @param ps[0] = passwd 
	 * */
	@Override
	protected CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		//如果需要密码，则进行密码校验
		
		//书签处理
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String lastLoginDay = sdf.format(player.getLastGiven());
		String currDay = sdf.format(new Date());
		
		boolean isFirstLogin = !lastLoginDay.equals(currDay);
		boolean fromBookMark = false;
		if (ps.length >0) {
			String param = ps[0];
			
			if (param != null && param.equals("k") && ps.length >= 2) {
				fromBookMark = true;
			} else if(ps.length > 0 && !ps[0].equals(player.getPasswd())){
				log.error("书签密码错:"+ps[0]+", expected:"+player.getPasswd());
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_PASSWD, "书签密码错"));
				return result;
			}
		}
		
		//如果player没有门派，强制选择门派
		if(player.getSectId() == 0){
			CommandRequest newCmd = new CommandRequest();
			newCmd.setCmd(COMMAND_SECT_LIST);
			newCmd.setPid(player.getId().toString());
			newCmd.setUid(player.getUserId().toString());
			return callOtherCmd(newCmd);
		}
		
		//进入上次存盘点
		player.setPath(null);
		int cfid = player.getCityFacilityId();
		if( cfid == 0 ){
			cfid = player.getFromCityFacilityId();
		}
		CityFacility cf = MapMG.instance.getCityFacility(cfid);
		player.setCityFacility(cf);
		
		CommandRequest newCmd = new CommandRequest();
		newCmd.setCmd(COMMAND_WALK);
		newCmd.setPid(player.getId().toString());
		newCmd.setUid(player.getUserId().toString());
		newCmd.setPs(new String[]{String.valueOf(player.getCityFacilityId())});
		result = callOtherCmd(newCmd);
		
		if (isFirstLogin && fromBookMark) {
			player.setLastGiven(new Date());
			//加钱.
			//player.addCopper(10 * 1000);
			ItemService.addItem(player, 10284, 2, false);
			player.setBookMarkFlag(true);
			ChatService.sendMessageSystem(player.getId(), "通过书签登陆，系统奖励了你2个遁地符，不过每天只能领取一次奖励！", false);
			DBService.commit(player);
		} else if (!player.isBookMarkFlag() && (ps == null || ps.length == 0)) {
			
			result.setVO(TAG_BOOK_MARK, 1);
		} else if (!player.isBookMarkFlag() && ps.length > 0) {
			if (!ps[0].equals("k")) {
				result.setVO(TAG_BOOK_MARK, 1);
			} else if (ps.length > 1){
				player.setBookMarkFlag(true);
				result.setStatus("book_mark");
			} else {
				result.setVO(TAG_BOOK_MARK, 1);
			}
		}
		PlayerService.showPrivateMsg(player, result);
		return result;
	}
	
}

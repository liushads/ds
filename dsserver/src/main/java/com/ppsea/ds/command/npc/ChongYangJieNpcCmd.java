/*
 * 
 */
package com.ppsea.ds.command.npc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ChongYangJieService;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.service.PlayerService;

/**
 * @author Administrator
 * @date 2010-10-12
 */
public class ChongYangJieNpcCmd extends BaseCmd {

	/**
	 * @see com.ppsea.ds.command.BaseCmd#done(com.ppsea.ds.data.model.Player, java.lang.String[])
	 */
	@Override
	protected CommandResult done(Player player, String[] ps)
	        throws PpseaException {
		CommandResult result = new CommandResult(Command.STATUS_SUCC);
		if (player.getLevel() < 10) {
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_LOW, "级别不够，十级以上玩家才可以参与"));
			return result;
		}
		String action = ps[0];
		
		
		if ("in".equals(action)) {
			//进入重阳山
			result.setStatus("in");
			if (ps.length == 2) {
				result.setStatus("in_note");
			}
		} else if ("out".equals(action)) {
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH);
			//延长兑换一个星期
			if (month != Calendar.APRIL) {
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_LOW, "活动时间无效,有效时间为4月4日-21日"));
				return result;
			}
			int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
			if (dayOfMonth < 3 || dayOfMonth >= 21)	 {
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_LOW, "活动时间无效,有效时间为4月4日-21日"));
				return result;
			}
			//leave.
			result.setStatus("out");
		} else if ("r".equals(action)) {
			//rule
			result.setStatus("rule");
		} else if ("c".equals(action)) {
			//compose.
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH);
			//延长兑换一个星期
			if (month != Calendar.APRIL) {
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_LOW, "活动时间无效,有效时间为4月4日-14日"));
				return result;
			}
			int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
			if (dayOfMonth < 3 || dayOfMonth >= 21)	 {
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_LOW, "活动时间无效,有效时间为4月4日-14日"));
				return result;
			}
			if (ps.length == 1) {
				result.setStatus("compose");			
			} else {
				int type = Integer.parseInt(ps[1]);
				ErrorMsg msg = ChongYangJieService.compose(player, type);
				if(msg.code != ErrorCode.SUCC){
					result.setStatus(STATUS_FAIL);
					result.setVO(TAG_ERR_MSG, msg);
					return result;
				}
				result.setStatus("info");
				result.setVO("info", msg.getText());
			}
		} else if ("e".equals(action)) {
			//exchange.
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH);
			//延长兑换一个星期
			if (month != Calendar.APRIL) {
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_LOW, "活动时间无效,有效时间为4月4日-21日"));
				return result;
			}
			int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
			if (dayOfMonth < 3 || dayOfMonth >= 21)	 {
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_LOW, "活动时间无效,有效时间为4月4日-21日"));
				return result;
			}
			List<Integer> itemIdList = new ArrayList<Integer>();
			itemIdList.add(10654);
			itemIdList.add(10655);
			itemIdList.add(10656);
			itemIdList.add(10657);
			itemIdList.add(10658);
			//itemIdList.add(ChongYangJieService.ITEM_ID_GAO);
			//itemIdList.add(ChongYangJieService.ITEM_ID_JIU);
			//itemIdList.add(ChongYangJieService.ITEM_ID_HETAO_GAO);
			//itemIdList.add(ChongYangJieService.ITEM_ID_MEIGUI_GAO);
			//itemIdList.add(ChongYangJieService.ITEM_ID_DOUSHA_GAO);
			//itemIdList.add(ChongYangJieService.ITEM_ID_JUHUA_JIU);
	
			if (ps.length == 2) {
				ErrorMsg msg = ChongYangJieService.exchange(player, itemIdList);
				if(msg.code != ErrorCode.SUCC){
					result.setStatus(STATUS_FAIL);
					result.setVO(TAG_ERR_MSG, msg);
					return result;
				}
				result.setVO("info", "恭喜你成功兑换到"+msg.getText()+"银");
				result.setStatus("info");
			} else {
				int totalAmount = 0;
				String str = "";
				for (int id : itemIdList) {
					PlayerItem pi = ItemService.findPlayerItem(player, id);
					if (pi != null&&pi.getBindId()!=null&&pi.getBindId()==0&&pi.getInExchange()==false) {
						totalAmount += pi.getAmount();
						str += pi.getItem().getName()+"x"+pi.getAmount()+",";
					}
				}
				if (str.length() > 1) {
					str = str.substring(0, str.length() - 1);					
				}
				result.setVO("money", totalAmount / 10);
				result.setVO("note_info", str);
				result.setVO("totalAmount", totalAmount);
				result.setStatus("exchange");
			}
			
		} else if ("g".equals(action)) {
			//give to NPC.
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH);
			//延长兑换一个星期
			if (month != Calendar.APRIL) {
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_LOW, "活动时间无效,有效时间为4月4日-21日"));
				return result;
			}
			int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
			if (dayOfMonth < 3 || dayOfMonth >=21)	 {
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_LOW, "活动时间无效,有效时间为4月4日-21日"));
				return result;
			}
			if (ps.length == 2) {
				int type = Integer.parseInt(ps[1]);
				ErrorMsg msg = ChongYangJieService.give2Npc(player, type);
				if(msg.code != ErrorCode.SUCC){
					result.setStatus(STATUS_FAIL);
					result.setVO(TAG_ERR_MSG, msg);
					return result;
				}
				result.setStatus("info");
				result.setVO("info", msg.getText());
			} else {
				result.setStatus("give");
			}
		
		} else if ("t".equals(action)) {
			//travel.
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH);
			//延长兑换一个星期
			if (month != Calendar.APRIL) {
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_LOW, "活动时间无效,有效时间为4月4日-14日"));
				return result;
			}
			int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
			if (dayOfMonth < 3 || dayOfMonth >= 14)	 {
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_LOW, "活动时间无效,有效时间为4月4日-14日"));
				return result;
			}
			int city_facility_id = Integer.parseInt(ps[1]);
			ErrorMsg ret = PlayerService.move(player, city_facility_id, false);
			if(ret.code != ErrorCode.SUCC){
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, ret);
				return result;
			}
			
			return callOtherCmd(COMMAND_WALK, player, city_facility_id+"");
		}else if ("h".equals(action)) {
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH);
			//延长兑换一个星期
			if (month != Calendar.APRIL) {
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_LOW, "活动时间无效,有效时间为4月4日-21日"));
				return result;
			}
			int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
			if (dayOfMonth < 3 || dayOfMonth >= 21)	 {
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_LOW, "活动时间无效,有效时间为4月4日-21日"));
				return result;
			}
			result.setStatus("dui");
		}else if ("d".equals(action)) {
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH);
			//延长兑换一个星期
			if (month != Calendar.APRIL) {
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_LOW, "活动时间无效,有效时间为4月4日-21日"));
				return result;
			}
			int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
			if (dayOfMonth < 3 || dayOfMonth >= 21)	 {
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_LOW, "活动时间无效,有效时间为4月4日-21日"));
				return result;
			}
			result.setStatus("duihuan");
			return result;
		}else if ("dui".equals(action)) {
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH);
			//延长兑换一个星期
			if (month != Calendar.APRIL) {
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_LOW, "活动时间无效,有效时间为4月4日-21日"));
				return result;
			}
			int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
			if (dayOfMonth < 3 || dayOfMonth >= 21)	 {
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_LOW, "活动时间无效,有效时间为4月4日-21日"));
				return result;
			}
			ErrorMsg ret =ChongYangJieService.duihuan(player,result);
			if(ret.code != ErrorCode.SUCC){
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, ret);
				return result;
			}
			result.setStatus("duihuan");
		}
		return result;
	}

}

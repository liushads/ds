/*
 * 
 */
package com.ppsea.ds.command.item;

import java.util.LinkedList;
import java.util.List;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ItemService;

/**
 * @author Administrator
 * @date 2010-9-3
 */
public class SellAllArmCmd extends BaseCmd {

	/**
	 * @see com.ppsea.ds.command.BaseCmd#done(com.ppsea.ds.data.model.Player, java.lang.String[])
	 */
	@Override
	protected CommandResult done(Player player, String[] ps)
	        throws PpseaException {
		CommandResult result = new CommandResult(Command.STATUS_SUCC);
		if (ps != null && ps.length > 0) {
			result.setStatus("confirm");
			return result;
		}
		List<PlayerItem> playerItems = new LinkedList<PlayerItem>();
		for(List<PlayerItem> ls:player.getArms().values()){
			if(ls !=null){					
				for(PlayerItem pi:ls){
					if(pi.getIsUse() == 0  && (pi.getBindId() == null || pi.getBindId()==0) && !pi.getInExchange() && !(pi.getItem().getPosition()==12) && !(pi.getStarLevel() > 0) && !(pi.getAppends() != null && pi.getAppends().size() > 0) && !(pi.getItem().getItemSuitId() > 0)){
						playerItems.add(pi);
					}
				}
			}
		}
		int totalMoney = 0;
		StringBuffer sb = new StringBuffer();
		sb.append("你卖出了");
		int counter = 0;
		for (PlayerItem pi : playerItems) {
			ErrorMsg msg = ItemService.sell(player, pi, 1);	
			if (msg.code > 0) {
				counter++;
				totalMoney += msg.code;
				sb.append("1个").append(msg.text);
				if (playerItems.size() > 1) {
					sb.append(",得"+msg.code+"铜,");
				}
			}
		}
		
		result = callOtherCmd("i_LS", player, new String[]{"1","0"});
		if (counter > 0)  {
			sb.deleteCharAt(sb.length()-1);
			result.setVO(TAG_DESC, sb.toString());
			
		}
		result.setVO(TAG_PLAYER, player);
		result.setVO(TAG_COPPER, totalMoney);
		return result;
	}

}

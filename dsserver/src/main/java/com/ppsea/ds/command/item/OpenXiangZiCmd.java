package com.ppsea.ds.command.item;

import java.util.List;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandRequest;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.util.LoggerHelper;

public class OpenXiangZiCmd extends BaseCmd {
	private static Logger logger = Logger.getLogger("Reward");
	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		if (!player.isOpenxiang()) {
			CommandRequest newCmd = new CommandRequest();
			newCmd.setCmd("i_L");
			newCmd.setPid(player.getId().toString());
			newCmd.setPs(new String[]{"1"});
			result = callOtherCmd(newCmd);
			return result;
		}
		player.setOpenxiang(false);
		int itemId=Integer.valueOf(ps[0]);
		int num=Integer.valueOf(ps[1]);
		Item item=ItemMG.instance.getItem(itemId);
		String td=null;
		if (itemId==10407) {
			player.addCopper(10*1000*num);
			td="恭喜你获得了【"+100+"银币】";
		}
		else if (itemId==10272) {
			player.addExp(1000*num);
			td="恭喜你获得了【"+5000*num+"经验】";
		}
		else {
			ItemService.addItem(player, itemId, 1,false);
			td="恭喜你获得了【"+item.getName()+"】";
		}
		result.setVO(TAG_TIQIU_DIANQIU, td);
		logger.info(LoggerHelper.ZONE_ID+"|baoxiang|O|"+player.getUserId()+"|"+player.getId()+"|"+player.getName()+"|"+player.getLevel()+"|"+itemId+"|"+ps[2]);
		return result;
	}

}

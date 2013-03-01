package com.ppsea.ds.command.item;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.ItemProperty;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ErrorCode;
/**
 * 查看指定装备上面指定的特殊属性详细信息
 **/
public class ReviewPropertyCmd extends BaseCmd {
	
	/**
	 * Log
	 **/
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(ReviewPropertyCmd.class);

	/**
	 * @param ps[0] playerItem
	 * @param ps[1] propertyId
	 **/
	protected CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		if(ps.length != 2){
			setResult(result,STATUS_FAIL,ErrorCode.ERR_SYS,"非法参数");
			return result;
		}
		
		//装备是否存在
		PlayerItem playerItem = player.getAllItems().get(ps[0]);
		if(playerItem == null){
			setResult(result,STATUS_FAIL,ErrorCode.ERR_NO_ITEM,"装备不存在");
			return result;
		}
		
		//属性是否存在
		ItemProperty property = playerItem.getItemProperty(Integer.parseInt(ps[1]));
		if(property == null){
			setResult(result,STATUS_FAIL,ErrorCode.ERR_NO_PROPERTY,"属性不存在");
			return result;
		}		
		//返回装备和描述信息
		result.setVO(TAG_ITEM_PROPERTY, property);
		return result;
	}
}

package com.ppsea.ds.command.item;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.util.ItemUtil;

/**
 * 提取锻造材料清单
 **/
public class ExtractMaterialCmd extends BaseCmd {	
	
	/**
	 * 操作常量 
	 **/
	private static final int FORGE_OP = 1;//铸造
	private static final int PROMOTE_OP = 2;//升星
	private static final int IMPROVE_OP = 3;//强化
	
	/**
	 * Log
	 **/
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(ExtractMaterialCmd.class);
	
	/**
	 * @param ps[0] 装备ID 
	 * @param ps[1] 1打造 2升星 3强化
	 **/
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);		
		// 查看的道具资料
		int type = Integer.parseInt(ps[1]);
		PlayerItem playerItem = player.getAllItems().get(ps[0]);
		if(playerItem == null && type != FORGE_OP){
			setResult(result,STATUS_FAIL,ErrorCode.ERR_NO_ITEM,"装备不存在");
			return result;
		}
		Item item = null;	
		if(playerItem != null) item = playerItem.getItem();
		ItemUtil.MaterialRequirement material = null;
		ErrorMsg ret = null;
		switch(type){
		case FORGE_OP:
			//装备铸造
			item = ItemMG.instance.getItem(Integer.parseInt(ps[0]));
			if(item == null)break;
			material = ItemUtil.getForgeMaterialList(item);	
			result.setVO(TAG_ITEM, item);	
			break;
		case PROMOTE_OP:
			//升星等级验证
			ret = ItemUtil.checkPromote(player, playerItem);
			if( ret.code != ErrorCode.SUCC )break;
			//获取材料
			int currentLevel = playerItem.getStarLevel().intValue();			
			material = ItemUtil.getPromoteMaterialList(item , ++currentLevel);		
			result.setVO(TAG_PLAYER_ITEM, playerItem);	
			break;
		case IMPROVE_OP:
			//强化验证
//			ret = ItemUtil.checkImprove(player, playerItem);
//			if( ret.code != ErrorCode.SUCC ) break;
//			//获取材料
//			playerItem = ItemService.findPlayerItem(player, item);
//			currentLevel = playerItem.getImproveLevel().intValue();			
//			material = ItemUtil.getImproveMaterialList(item , ++currentLevel);
//			result.setVO(TAG_PLAYER_ITEM, playerItem);	
			break;
		default:
			//非法操作
			setResult(result,STATUS_FAIL,com.ppsea.ds.service.ErrorCode.ERR_SYS,"材料非法操作");
			return result;
		}			
		if( ret != null && ret.code != ErrorCode.SUCC ) {
			setResult(result,STATUS_FAIL,ret);
			return result;
		}
		
		//验证材料
		if(item == null || material == null){
			setResult(result,STATUS_FAIL,com.ppsea.ds.service.ErrorCode.ERR_NO_ITEM,"不能获取该道具的材料列表");
			return result;			
		}
		result.setVO(TAG_OBJ, material);
		result.setVO(TAG_RETURN, type);
		return result;
	}
}

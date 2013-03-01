package com.ppsea.ds.command.item;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.ItemForge;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.util.ItemUtil;

/**
 * 锻造装备
 * */
public class ForgeCmd extends BaseCmd {
	
	/**
	 * Log 
	 **/
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(ForgeCmd.class);
	
	/**
	 * @param ps[0] 要打造装备ID 
	 **/
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);		
		//查看的道具资料
		int itemId = Integer.parseInt(ps[0]);
		ItemForge itemForge = ItemMG.instance.getItemForge(itemId);
		Item item = ItemMG.instance.getItem(itemId);
		if(itemForge == null || item == null){//没有道具
			setResult(result,STATUS_FAIL,ErrorCode.ERR_NO_ITEM,"道具或者材料为NULL");
			return result;
		}	
		//材料列表itemForge
		String materials = itemForge.getMaterial();
		String[] materialArray = materials.split("[|]");
		//验证玩家是否有材料
		List<PlayerItem> playerItems = new ArrayList<PlayerItem>(materials.length());
		int[] amounts = new int[materialArray.length];
		ItemUtil.MaterialRequirement requireMaterial = ItemUtil.checkMaterialRequirement(player, materialArray, playerItems, amounts);
				
		//材料是否齐全
		if(requireMaterial != null){
			result.setVO(TAG_MATERIAL_REQUIRE, requireMaterial);
			return result;
		}
		
		//产生装备
		ErrorMsg ret = ItemService.forge(player,item,playerItems,amounts);	
		if (ret.code != ErrorCode.SUCC) {
			result.setVO(TAG_DESC, ret.getText());
			setResult(result,STATUS_SUCC,ret);
			return result;
		}			
		//简单清理
		playerItems.clear();
		amounts = null;
		result.setVO(TAG_DESC, ret.text);
		result.setVO(TAG_PLAYER_ITEM, ret.obj);
		return result;
	}
}
package com.ppsea.ds.util.item;

import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;

/**
 * Player属性处理器接口
 **/
public interface PropertyHandler {

	/**
	 * 设置玩家升星附加属性
	 * @param 使用者
	 * @param item 装备 
	 **/
	public void handlePromoteProperty(Player player,PlayerItem item,int flag) throws PpseaException;
	
	/**
	 * 设置玩家强化附加属性
	 * @param 使用者
	 * @param item 装备 
	 **/
	public void handleImproveProperty(Player player,PlayerItem item,int flag)throws PpseaException;
}

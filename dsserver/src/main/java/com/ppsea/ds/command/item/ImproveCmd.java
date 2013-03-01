package com.ppsea.ds.command.item;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.ItemImprove;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.util.ItemUtil;
import com.ppsea.ds.util.StringUtil;

/**
 * 装备强化
 **/
public class ImproveCmd extends BaseCmd {

	public static final int IMPROVE_SUCC = 1;// 强化成功
	public static final int IMPROVE_FAIL = 0;// 强化失败

	/**
	 * Log
	 **/
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(ImproveCmd.class);

	/**
	 * @param ps
	 *            [0] 强化造装备ID
	 **/
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		// 先查找装备
		PlayerItem playerItem = player.getAllItems().get(ps[1]);
		int fortune = 0;
		try {
			fortune = Integer.parseInt(ps[2]);
			if (fortune < 0 || fortune > 100) {
				setResult(result, STATUS_FAIL, new ErrorMsg(-1, "请输入0~100之间的数！"));
				return result;
			}
		} catch (Exception e) {
			setResult(result, STATUS_FAIL, new ErrorMsg(-1, "请输入数字！"));
			return result;
		}
		
		if (playerItem == null || playerItem.getIsUse() == 0) {// 没有道具
			setResult(result, STATUS_FAIL, ErrorCode.ERR_NO_ITEM, "非法装备操作");
			return result;
		}
		boolean is_user_spec_item = false;
		if ("1".equals(ps[0])) {
			is_user_spec_item = true;
		}
		TreeMap<String, Integer[]> maps = new TreeMap<String, Integer[]>();
		setMaps(playerItem, maps, 0);//强化前
		ErrorMsg res = ItemService.improve(player, playerItem, is_user_spec_item,fortune);
		if (res.code < ErrorCode.SUCC) {
			setResult(result, "stat_no", res);
			result.setVO("label_player_id", ps[1]);
			return result;
		}
		playerItem.computeImprove();
		if (res.code == ErrorCode.SUCC) {
			setMaps(playerItem, maps, 1);//强化后
			result.setVO("label_extra", maps);
		}
		result.setVO(TAG_PLAYER_ITEM, playerItem);
		return result;
	}
	
	private void setMaps(PlayerItem playerItem,TreeMap<String, Integer[]> maps,int index){
		Integer[] values = null;
		Class<?> f= playerItem.getClass();
		Method[] methods= f.getMethods();
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].toString().indexOf("getExtra") >= 0) {
				if (!maps.containsKey(methods[i].getName())) {
					values = new Integer[2];
					maps.put(methods[i].getName(), values);
				}
				values = maps.get(methods[i].getName());
				try {
					values[index] = ((Integer)methods[i].invoke(playerItem));
				} catch (IllegalAccessException e) {
					log.error("improve" + e,e);
				} catch (IllegalArgumentException e) {
					log.error("improve" + e,e);
				} catch (InvocationTargetException e) {
					log.error("improve" + e,e);
				}
			}
		}
	}
//	public static void main(String[] args) {
//		PlayerItem playerItem = new PlayerItem();
//		playerItem.setExtraHp(123456);
//		Class<?> f= playerItem.getClass();
//		Method[] methods= f.getMethods();
//		for (int i = 0; i < methods.length; i++) {
//			if (methods[i].toString().indexOf("getExtra") >= 0) {
//				System.out.println(methods[i].toString());
//				System.out.println(methods[i].getName());
//				try {
//					System.out.println(methods[i].invoke(playerItem));
//				} catch (IllegalAccessException e) {
//					e.printStackTrace();
//				} catch (IllegalArgumentException e) {
//					e.printStackTrace();
//				} catch (InvocationTargetException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
}

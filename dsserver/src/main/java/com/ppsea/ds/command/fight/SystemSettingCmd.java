package com.ppsea.ds.command.fight;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandRequest;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.DBService;

public class SystemSettingCmd extends BaseCmd {
	private static final Logger log = Logger.getLogger(SystemSettingCmd.class);
	public static final int FLAG_AUTO_HP = 1 << 0;
	public static final int FLAG_AUTO_STATE = 1 << 1;
	public static final int FLAG_AUTO_PICK = 1 << 2;
	public static final int FLAG_FRIEND_DESC = 1 << 3;
	public static final int FLAG_FIGHT_DESC = 1 << 4;
	public static final int FLAG_MISSION_DESC = 1 << 5;
	public static final int FLAG_FIGHT_MP = 1 << 8;
	public static final int FLAG_FIGHT_REPAIR = 1 << 9;
	/******************* ds set ******************/
	
	public static final int FLAG_IMPROVE = 1 << 10;
	
	/******************* ds set ******************/

	public static int clrFlag(int curr, int flag) {
		curr &= ~flag;

		return curr;
	}

	public static int setFlag(int curr, int flag) {
		curr |= flag;
		return curr;
	}

	public static boolean isFlagSet(int curr, int flag) {
		int value = curr & flag;
		return value != 0;
	}

	@Override
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);

		if (ps != null && ps.length >= 2) {
			String type = ps[0];
			String oper = ps[1];
			int ret = 0;
			if (type.equals("fd") && oper.equals("0")) {
				ret = clrFlag(player.getSettingFlag(), FLAG_FIGHT_DESC);
			} else if (type.equals("fd") && oper.equals("1")) {
				ret = setFlag(player.getSettingFlag(), FLAG_FIGHT_DESC);
			} else if (type.equals("as") && oper.equals("0")) {
				ret = clrFlag(player.getSettingFlag(), FLAG_AUTO_STATE);
			} else if (type.equals("as") && oper.equals("1")) {
				ret = setFlag(player.getSettingFlag(), FLAG_AUTO_STATE);
			} else if (type.equals("ah") && oper.equals("0")) {
				ret = clrFlag(player.getSettingFlag(), FLAG_AUTO_HP);
			} else if (type.equals("ah") && oper.equals("1")) {
				ret = setFlag(player.getSettingFlag(), FLAG_AUTO_HP);
			} else if (type.equals("ap") && oper.equals("0")) {
				ret = clrFlag(player.getSettingFlag(), FLAG_AUTO_PICK);
			} else if (type.equals("ap") && oper.equals("1")) {
				ret = setFlag(player.getSettingFlag(), FLAG_AUTO_PICK);
			} else if ("am".equals(type) && "1".equals(oper)) {
				ret = setFlag(player.getSettingFlag(), FLAG_FIGHT_MP);
			} else if ("am".equals(type) && "0".equals(oper)) {
				ret = clrFlag(player.getSettingFlag(), FLAG_FIGHT_MP);
			} else if ("sf".equals(type) && "1".equals(oper)) {
				ret = setFlag(player.getSettingFlag(), FLAG_FRIEND_DESC);
			} else if ("sf".equals(type) && "0".equals(oper)) {
				ret = clrFlag(player.getSettingFlag(), FLAG_FRIEND_DESC);
			}else if ("rw".equals(type) && "1".equals(oper)) {
				ret = setFlag(player.getSettingFlag(), FLAG_MISSION_DESC);
			} else if ("rw".equals(type) && "0".equals(oper)) {
				ret = clrFlag(player.getSettingFlag(), FLAG_MISSION_DESC);
			}else if ("im".equals(type) && "1".equals(oper)) {
				ret = setFlag(player.getSettingFlag(), FLAG_IMPROVE);
			}else if ("im".equals(type) && "0".equals(oper)) {
				ret = clrFlag(player.getSettingFlag(), FLAG_IMPROVE);
			}

			player.setSettingFlag(ret);
			DBService.commit(player);
		}
		Integer curr = player.getSettingFlag();
		if (curr == null)
			curr = 0;
		result.setVO("label_fight_desc", isFlagSet(curr, FLAG_FIGHT_DESC) ? 1
				: 0);
		result.setVO("label_auto_hp", isFlagSet(curr, FLAG_AUTO_HP) ? 1 : 0);
		result.setVO("label_auto_state", isFlagSet(curr, FLAG_AUTO_STATE) ? 1
				: 0);
		result
				.setVO("label_auto_pick", isFlagSet(curr, FLAG_AUTO_PICK) ? 1
						: 0);
		result.setVO("label_auto_mp", isFlagSet(curr, FLAG_FIGHT_MP) ? 1 : 0);
		result.setVO("label_friend_desc", isFlagSet(curr, FLAG_FRIEND_DESC) ? 1 : 0);
		result.setVO("label_mission_desc", isFlagSet(curr, FLAG_MISSION_DESC) ? 1 : 0);
		if (ps != null && ps.length >= 3 && "im".equals(ps[0])) {
			CommandRequest newCmd = new CommandRequest();
			newCmd.setCmd("i_IMT");
			newCmd.setPid(player.getId().toString());
			newCmd.setPs(new String[] { ps[2] });
			return callOtherCmd(newCmd);
		}
		return result;
	}
}

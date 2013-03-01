/**
 * 
 */
package com.ppsea.ds.command.fight;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.FightResult;
import com.ppsea.ds.data.model.CityFacility;
import com.ppsea.ds.data.model.CityFacilityMonster;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Monster;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.IdentifyCenter;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.manager.MonsterMG;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.service.ChatService;
import com.ppsea.ds.service.DBService;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.EventService;
import com.ppsea.ds.service.FightService;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.service.MissionService;
import com.ppsea.ds.service.PlayerService;
import com.ppsea.ds.service.WanShengJieService;
import com.ppsea.ds.util.LoggerHelper;

public class FightMonsterCmd extends BaseCmd {
	private static final Logger log = Logger.getLogger(FightMonsterCmd.class);
	private static final Logger monsterLog = Logger.getLogger("Monster");
	private int playerSkillJxId = 0;
	private int playerItemAqId = 0;

	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		Integer targetId = 0;
		// 开启自动刷怪.
		if (ps.length == 2 && ps[0].equals("am")) {
			targetId = Integer.valueOf(ps[1]);
			if (targetId < 0) {
				player.setAutoFightMonsterFlag(1);
				player.setAutoAttackFlag(1);
				targetId = player.getNextMonsterId();
			}

		} else if (ps.length == 2 && ps[0].equals("as")) {
			player.setAutoFightMonsterFlag(0);
			player.setAutoAttackFlag(0);
			targetId = Integer.valueOf(ps[1]);
		} else {
			targetId = Integer.valueOf(ps[0]);
		}

		playerSkillJxId = 0;
		playerItemAqId = 0;
		// 使用绝学
		if (ps.length >= 2 && ps[1].equals("jx")) {
			playerSkillJxId = Integer.valueOf(ps[2]);
		}
		// 使用暗器
		if (ps.length >= 2 && ps[1].equals("aq")) {
			playerItemAqId = Integer.valueOf(ps[2]);
			PlayerItem pi = player.getAllItems().get(
					String.valueOf(playerItemAqId));
			if (pi == null || pi.getAmount() == 0) {
				playerItemAqId = 0;
			}
		}
		// ------------------------------------------------------
		// 设置自动打怪开关
		if (ps.length > 1 && ps[1].equals("a")) {
			Integer auto = player.getAutoAttackFlag();
			player.setAutoAttackFlag(auto == 0 ? 1 : 0);
		}

		int tid = targetId.intValue();
		// ///////////////////////////////////////////////////////
		// 和怪物打
		if (tid < 0) {
			fightWithMonster(player, result, tid);
			return result;
		}
		// ///////////////////////////////////////////////////////
		// 和玩家打
		if (tid > 0) {
			Player enemy = PlayerMG.instance.getOnlinePlayer(targetId);
			// 等级小于10级
			if (player.getLevel() < 10) {
				setFailResult(result, ErrorCode.ERR_NO_ATK_LEVEL);
				return result;
			}

			if (enemy == null) {
				player.setLastEnemy(null);
				result.setText(TAG_ENEMY_NAME, (enemy != null ? enemy.getName()
						: "敌人"));
				setFailResult(result, ErrorCode.ERR_TARGET_LOST);
				return result;
			}
			if (player.getFightPlayerStatus() > 0
						&& enemy.getFightPlayerStatus() == 0) {
				String td=null;
				int num = player.getFightPlayerStatus();
				int number=0;
				if (num == 1) {
					td="你战胜了对手，获得36银币";
					number=36;
					player.addCopper(40*900);
					player.setLeitype(0);
					player.setFightPlayerStatus(0);
					ChatService.sendMessageSystem(player.getId(), td, true);
				}
				if (num == 2) {
					td="你战胜了对手，获得90银币";
					number=90;
					player.addCopper(100*900);
					player.setLeitype(0);
					player.setFightPlayerStatus(0);
					ChatService.sendMessageSystem(player.getId(), td, true);
				}
				if (num == 3) {
					td="你战胜了对手，获得180银币";
					number=180;
					player.addCopper(200*900);
					player.setLeitype(0);
					player.setFightPlayerStatus(0);
					ChatService.sendMessageSystem(player.getId(), td, true);
				}
				player.setLastEnemy(null);
				player.setLeitaiDate(null);
				DBService.commit(player);
				result.setVO("enemy", enemy);
				result.setVO(TAG_NUM, number);
				result.setStatus("zhansheng");
				return result;
			}
			// 不在同一设施（攻城,擂台,王战中除外，否则开战后对方不走动就打不到）
			if (!FightService.isAtSameLocation(player, enemy)) {
				boolean succ = false;
				// 在擂台中除外
				if (player.getFightPlayerStatus() > 0
						&& enemy.getFightPlayerStatus() > 0) {
					succ = true;
				}
				// 在攻城中除外
				if (player.getFightCityStatus() > 0
						&& enemy.getFightCityStatus() > 0) {
					succ = true;
				}
				// 在门派比武中
				if (player.getMatchStatus() > 0 && enemy.getMatchStatus() > 0) {
					succ = true;
				}

				if (!succ) {
					player.setLastEnemy(null);
					result.setText(TAG_ENEMY_NAME, (enemy != null ? enemy
							.getName() : "敌人"));
					setFailResult(result, ErrorCode.ERR_TARGET_LOST);
					return result;
				}
			}

			// 对方等级小于10级
			if (enemy.getLevel() < 10) {
				setFailResult(result, ErrorCode.ERR_NO_DEF_LEVEL);
				return result;
			}
			// 对方体力为0
			if (enemy.getHp().intValue() <= 0) {
				player.setLastEnemy(null);
				result.setText(TAG_ENEMY_NAME, (enemy != null ? enemy.getName()
						: "敌人"));
				setFailResult(result, ErrorCode.ERR_TARGET_DEAD);
				return result;
			}

					if ((player.getCityFacility() != null && player
							.getCityFacility().getFacility().getId().intValue() == Constants.YIGUAN_ID)
							|| (enemy.getCityFacility() != null && enemy
									.getCityFacility().getFacility().getId()
									.intValue() == Constants.YIGUAN_ID)) {
						player.setLastEnemy(null);
						if (player.getFightPlayerStatus() > 0
								|| player.getFightCityStatus() > 0
								|| player.getMatchStatus() > 0) {
							result.setText(TAG_ENEMY_NAME,
									(enemy != null ? enemy.getName() : "敌人"));
							result.setVO(TAG_RETURN, ErrorCode.ERR_TARGET_LOST);
							setFailResult(result, ErrorCode.ERR_TARGET_LOST);
							return result;
						}
						setFailResult(result, ErrorCode.ERR_NO_PK_LOCATION);
						return result;
					}
					// 航道不能PK，或者当前场所不能PK，且没有击杀令
					if (player.getFightPlayerStatus()<=0) {
						if ((player.getCityFacility() == null
								|| enemy.getCityFacility() == null || !player
								.getCityFacility().getPkable()))

						{
							player.setLastEnemy(null);
							setFailResult(result, ErrorCode.ERR_NO_PK_LOCATION);
							return result;
						}
					}
//				}
			try {
				if (playerItemAqId > 0) {
					PlayerItem pi = player.getAllItems().get(
							String.valueOf(playerItemAqId));
					if (pi != null && pi.getItem().getId() == 10565) {
						setFailResult(result, ErrorCode.ERR_ATT_TYPE);
						return result;
					}
				}
			} catch (Exception e) {

			}
			fightWithPlayer(player, result, targetId);
			return result;
		}
		result.setText(TAG_ENEMY_NAME, "敌人");
		setFailResult(result, ErrorCode.ERR_TARGET_LOST);
		return result;
	}

	/**
	 * 
	 * @param player
	 * @param result
	 * @param targetId
	 * @throws PpseaException
	 */
	public void fightWithMonster(Player player, CommandResult result,
			Integer targetId) throws PpseaException {
		Monster monster = null;
//		FightCityInfo fci = null;
		// 打普通的怪物
		if (player.getFightCityStatus() <= 0) {
			monster = player.getMonster().get(targetId);
		}
		// 敌人已经不存在
		if (monster == null) {
			player.setLastEnemy(null);
			// 在副本中
			if (player.isInInstacneCity()) {
				FightService.gotoCurrCityFacility(player, player
						.getCityFacilityId());
			}
			result.setText(TAG_ENEMY_NAME, "敌人");
			setFailResult(result, ErrorCode.ERR_TARGET_LOST);
			return;
		}

		result.setVO(TAG_PLAYER, player);
		result.setVO(TAG_IS_FACILITY, player.getCityFacility() == null ? false
				: player.getCityFacility().getFacility().getIsCity());
		// /////////////////////////////////////////////////////////
		++monster.fightCount;
		FightResult[] fightResult = FightService.fightBetween(player, monster
				.getPlayer(), playerSkillJxId, playerItemAqId,true);
		FightResult atkResult = fightResult[0];
		FightResult defResult = fightResult[1];
		player.addLastWasteAtkSum(1);
		player.addLastWasteDefSum(1);
		// ------------------------------------------------------------------------
		// 被怪物打死
		if (defResult.isKiller()) {
			// 取消和最后一个怪物的绑定，丢回教堂
			player.setLastEnemy(null);
			player.setLastAttackHp(0);
			player.setLastBeAttack(0);
			if (player.getFightCityStatus() > 0) {
				player.setFightCityStatus(0);
			}
			FightService.gotoMainCityFacility(player, Constants.YIGUAN_ID);
			LoggerHelper.RoleStatus.logForRoleDead(player);
			result.setVO(TAG_ENEMY_TYPE, 0);
			result.setText(TAG_BE_KILLED, monster.getPlayer().getName());
			result.setStatus(STATUS_FIGHT_LOSE);
			try {

				// 记录回血怪被死的时间.
				if (monster.getMonsterType() != null
						&& (monster.getMonsterType() >= 1)) {
					long time = System.currentTimeMillis()
							- monster.getTestTime();
					monsterLog.info(player.getId() + "|"
							+ monster.getMonster().getId() + "|F|" + time);
				}

			} catch (Exception e) {
			}
			return;
		}
		// ------------------------------------------------------------------------
		// 战胜怪物
		if (atkResult.isKiller()) {
			// 副本中发公聊通知
			if (player.isInInstacneCity()) {
				this.notifyTeamMessage(player, monster);
			}
			// 避免0滴血
			if (player.getHp() == 0) {
				player.setHp(FightService.rangeRandom(1, 5));
			}
			result.setList(TAG_KILLED, monster.getPlayer().getName(), String
					.valueOf(targetId), String.valueOf(monster.getPlayer()
					.getHp()), "null");

			// 取消和最后一个怪物的绑定
			player.setLastEnemy(null);
			player.setLastAttackHp(0);
			player.setLastBeAttack(0);
			int monsterId = monster.getPlayer().getId();
				// 需要继续出现，则重新初始化该怪物，否则删除死掉的怪物
			if (monster.getCityFacilityMonster().getNoDisappear() == 1) {
				CityFacilityMonster cfm = monster.getCityFacilityMonster();
				Monster m = monster.getMonster();
				monster.init(cfm, m, monsterId);
			} else {
				player.getMonster().remove(monsterId);
			}
			// 需要加的经验
			int addExp = FightService.getAddExpFromMonster(monster, player);

			// 需要加的钱
			int minMoney = monster.getMonster().getDropMoneyMin();
			int maxMoney = monster.getMonster().getDropMoneyMax();
			int dropMoney = FightService.rangeRandom(minMoney, maxMoney);
			// 怪物掉的道具
			List<Item> dropItems = MonsterMG.instance.getDropItemsFromMonster(
					player, monster.getMonster());
			if (dropItems.size() > 0) {
				for (Item it : dropItems) {
					PlayerItem pi = new PlayerItem();
					pi.init(it);
					ItemService.addTempItem(player, pi, player
							.isInInstacneCity());
				}
			}

			// 自动捡起
			Set<Integer> itemIds = new HashSet<Integer>();
			if (!SystemSettingCmd.isFlagSet(player.getSettingFlag(),
					SystemSettingCmd.FLAG_AUTO_PICK)) {
				List<Item> autoPickItems = ItemService.autoPick(player);
				if (autoPickItems != null) {
					for (Item s : autoPickItems) {
						result.setList(TAG_AUTO_PICK, s.getName(), String
								.valueOf(s.getLevel()));
						// 查询任务用
						itemIds.add(s.getId());
					}
				}
			}
			// 检查并设置没有捡起的道具
			FightService.checkWinPrize(player, result);
			
			// 更新队中全部玩家怪的数量
			PlayerService.killMonsterInTeam(player, monster.getMonster().getId());
			// 任务完成度
			try {
				String msg = MissionService.getMissionStatusByMonster(monster
						.getMonster().getId(), player);
				if (!msg.equals(""))
					result.setList(TAG_MISSION, msg, "");
				else {
					for (Integer itemId : itemIds) {
						msg = MissionService.getMissionStatusByItem(itemId,
								player);
						if (!msg.equals(""))
							result.setList(TAG_MISSION, msg, "");
						msg = "";
					}
				}
				// 随机事件中的任务完成度
				if (msg.equals("")) {
					boolean[] tmp = new boolean[1];
					msg = EventService.updateEventStatusByMonster(monster
							.getMonster().getId(), player, tmp);
					if (msg != null)
						result.setList(TAG_MISSION, msg, "");
					if (tmp[0]) {
						result.setText(TAG_WIN_UPGRADE, player.getLevel());
					}
				}
			} catch (Exception e) {
				log.error("event exception:", e);
			}
			// 加经验
			boolean upgrade = player.addExp(addExp);

			// steady 宠物获得经验,只是打怪获得
//			PetService.addExp(player, player.getUsedPet(), addExp);
			result.setVO(TAG_WIN_EXP, addExp);
			if (upgrade == true) {
				result.setText(TAG_WIN_UPGRADE, player.getLevel());
			}
			// 加钱
			player.addCopper(dropMoney);
			result.setText(TAG_WIN_MONEY, String.valueOf(dropMoney));
			DBService.commit(player);
			// 记录玩家杀死怪物
			// /RankingsService.addLog(1, player.getId().intValue());
			result.setStatus(STATUS_FIGHT_WIN);

			try {
				// 记录回血怪被死的时间.
				if (monster.getMonsterType() != null
						&& (monster.getMonsterType() >= 1)) {
					String drops = "";
					for (Item item : dropItems) {
						drops += item.getIdStr() + "|";
					}
					long time = System.currentTimeMillis()
							- monster.getTestTime();
					monsterLog.info(player.getId() + "|"
							+ monster.getMonster().getId() + "|W|" + time + "|"
							+ drops);
				}
			} catch (Exception e) {
			}

			// 开启了自动寻杀怪.
			if (player.getAutoFightMonsterFlag() == 1) {
				int tid = player.getNextMonsterId();
				// this.fightWithMonster(player, result, tid);
				result.setVO("nextMonsterId", tid);
			}
			// 杀万圣节怪
			int mid = monster.getMonster().getId();
			if (mid == 910002 || mid == 910003 || mid == 910004) {
				boolean isFinish = WanShengJieService.checkMission(player,
						result);
				result.setVO("isFinish", isFinish);
				result.setVO("wsj_monsterId", mid);
			}
			return;
		}
		// ------------------------------------------------------------------------
		// 没有分出胜负
		player.addFightAttack(atkResult);
		player.addFightDefence(defResult);
		// 需要回血类怪，执行以下操作
		if (monster.getMonsterType() != null && (monster.getMonsterType() >= 1)) {
			int autostoredHp = monster.autoRestoreHp();
			if (autostoredHp > 0) {
				result.setVO("monsterAutostoredHp", autostoredHp);
			}
		}
		if (player.getAutoFightMonsterFlag() == 1) {
			if ((player.getHp() < player.getDyn().getMaxHp() / 5)
					&& !player.isFight_note()) {
				result.setVO("time", 100);
				player.setFight_note(true);
				// result.setVO("hp_note", "体力不足，请及时补充体力!");
			}
		}
		result.setStatus(STATUS_SUCC);
		return;
	}

	/**
	 * 
	 */
	public void fightWithPlayer(Player player, CommandResult result,
			Integer targetId) throws PpseaException {
		Player enemy = PlayerMG.instance.getOnlinePlayer(targetId);
		result.setVO(TAG_PLAYER, player);
		result.setVO(TAG_IS_FACILITY, player.getCityFacility().getFacility()
				.getIsCity());
		// int abort = player.getCopper();
		// if (abort > 5*1000) abort = 5*1000;
		result.setText(TAG_MONSTER_ABORT, 0);

		FightResult[] fightResult = FightService.fightBetween(player, enemy,
				playerSkillJxId, playerItemAqId,true);
		FightResult atkResult = fightResult[0];
		FightResult defResult = fightResult[1];
		enemy.addFightAttack(defResult);
		enemy.addFightDefence(atkResult);
		// -------------------------------------------------------------------
		// 战胜玩家
		if (atkResult.isKiller()) {
			// 避免0滴血
			if (player.getHp() == 0) {
				player.setHp(FightService.rangeRandom(1, 5));
			}
			result.setList(TAG_KILLED, enemy.getName(), String
					.valueOf(targetId), String.valueOf(enemy.getHp()), "null");
			// 自动捡起
			if (!SystemSettingCmd.isFlagSet(player.getSettingFlag(),
					SystemSettingCmd.FLAG_AUTO_PICK)) {
				List<Item> autoPickItems = ItemService.autoPick(player);
				if (autoPickItems != null) {
					for (Item s : autoPickItems) {
						result.setList(TAG_AUTO_PICK, s.getName(), "");
					}
				}
			}
			// 取消和最后一个玩家的绑定
			player.setLastEnemy(null);
			player.setLastAttackHp(0);
			player.setLastBeAttack(0);
			// 道具损耗列表
//			List<PlayerItem> wasted = player.wasteAtkAndDef(false);
//			if (wasted != null && wasted.size() > 0) {
//				for (PlayerItem pi : wasted) {
//					result.setList(TAG_WASTE_ITEMS, pi.getItem().getName(), "");
//				}
//			}
			// 检查并设置没有捡起的道具
			FightService.checkWinPrize(player, result);

			// 恢复敌人的状态，解除其绑定，移到教堂
			if (enemy.getFightCityStatus() > 0)
				enemy.setFightCityStatus(0);
			if (enemy.getFightPlayerStatus() > 0)
				enemy.setFightPlayerStatus(0);
			enemy.setLastEnemy(null);
			enemy.setLastAttackHp(0);
			enemy.setLastBeAttack(0);
			FightService.gotoMainCityFacility(enemy, Constants.YIGUAN_ID);

			DBService.commit(player);
			DBService.commit(enemy);
			this.notifyPKMessage(player, enemy);
			if (player.getFightPlayerStatus() > 0) {
				String td=null;
				int num = player.getFightPlayerStatus();
				int number=0;
				if (num == 1) {
					td="你战胜了对手，获得36银币";
					number=36;
					player.addCopper(40*900);
					player.setLeitype(0);
					player.setFightPlayerStatus(0);
					ChatService.sendMessageSystem(player.getId(), td, true);
				}
				if (num == 2) {
					td="你战胜了对手，获得90银币";
					number=90;
					player.addCopper(100*900);
					player.setLeitype(0);
					player.setFightPlayerStatus(0);
					ChatService.sendMessageSystem(player.getId(), td, true);
				}
				if (num == 3) {
					td="你战胜了对手，获得180银币";
					number=180;
					player.addCopper(200*900);
					player.setLeitype(0);
					player.setFightPlayerStatus(0);
					ChatService.sendMessageSystem(player.getId(), td, true);
				}
				if (enemy.getFightPlayerStatus() > 0) {
					enemy.setFightPlayerStatus(0);
					enemy.setLeitype(0);
					enemy.setLeitaiDate(null);
				}
				player.setLeitaiDate(null);
				result.setVO("enemy", enemy);
				result.setVO(TAG_NUM, number);
				result.setStatus("zhansheng");
				return;
			}

			result.setStatus(STATUS_FIGHT_WIN);
			return;
		}
		// -----------------------------------------------------------------------
		// 被敌人打死
		if (defResult.isKiller()) {
			if (enemy.getFightPlayerStatus() > 0&&player.getFightPlayerStatus()>0) {
				String td=null;
				int num = enemy.getFightPlayerStatus();
				if (num == 1) {
					td="你战胜了对手，获得36银币";
					enemy.addCopper(40*900);
					enemy.setFightPlayerStatus(0);
					enemy.setLeitype(0);
					player.setFightPlayerStatus(0);
					player.setLeitype(0);
					ChatService.sendMessageSystem(enemy.getId(), td, true);
				}
				if (num == 2) {
					td="你战胜了对手，获得90银币";
					enemy.addCopper(100*900);
					enemy.setLeitype(0);
					enemy.setFightPlayerStatus(0);
					player.setFightPlayerStatus(0);
					player.setLeitype(0);
					ChatService.sendMessageSystem(enemy.getId(), td, true);
				}
				if (num == 3) {
					td="你战胜了对手，获得180银币";
					enemy.addCopper(200*900);
					enemy.setFightPlayerStatus(0);
					enemy.setLeitype(0);
					player.setLeitype(0);
					player.setFightPlayerStatus(0);
					ChatService.sendMessageSystem(enemy.getId(), td, true);
				}
				enemy.setLeitaiDate(null);
			}
			result.setVO(TAG_ENEMY_TYPE, 1);
			// 恢复状态，解除绑定
			if (player.getFightCityStatus() > 0) {
				player.setFightCityStatus(0);
			}
			enemy.setLastEnemy(null);
			player.setLastEnemy(null);
			player.setLastAttackHp(0);
			player.setLastBeAttack(0);
			// 道具损耗列表
//			List<PlayerItem> wasted = player.wasteAtkAndDef(true);
//			if (wasted != null && wasted.size() > 0) {
//				for (PlayerItem pi : wasted) {
//					result.setList(TAG_WASTE_ITEMS, pi.getItem().getName(), "");
//				}
//			}
			// 移动到教堂
			FightService.gotoMainCityFacility(player, Constants.YIGUAN_ID);
			result.setStatus(STATUS_FIGHT_LOSE);
			result.setText(TAG_BE_KILLED, enemy.getName());
			player.getFightAttack().clear();
			player.getFightDefence().clear();
			// 回写
			DBService.commit(player);
			DBService.commit(enemy);
			this.notifyPKMessage(enemy, player);
			LoggerHelper.RoleStatus.logForRoleDead(player);

			return;
		}
		// ------------------------------------------------------------------------
		// 没有分出胜负
		player.addFightAttack(atkResult);
		player.addFightDefence(defResult);
		result.setStatus(STATUS_SUCC);
		return;
	}

	// ////////////////////////////////////////////////////////////////////////////
	public void notifyTeamMessage(Player winner, Monster monster) {
		StringBuffer sb = new StringBuffer();
		CityFacility cf = winner.getCityFacility();
		sb.append("【" + winner.getName() + "】");
		if (cf != null)
			sb.append("在" + cf.getFacility().getName());
		sb.append("杀死了" + monster.getMonster().getName());
		ChatService.sayTeam(winner, sb.toString());
	}

	// ////////////////////////////////////////////////////////////////////////////
	public void notifyPKMessage(Player winner, Player loser) {
		String message = null;
		String messagePrivate = new String();
		message = "【" + winner.getName() + "】";
		if (winner.getCityFacility() != null) {
			message += "在" + winner.getLocation();
			messagePrivate += "在" + winner.getLocation();
		}
		message += "PK战胜了【" + loser.getName() + "】";
		ChatService.sayXiaoQ(message);
		messagePrivate += "PK赢了你";
		ChatService.sendMessagePrivate(loser.getId(), winner.getId(), winner
				.getName(), messagePrivate);
		/*
		 * // PK日志 RankingsService.addLogByPk(8,
		 * winner.getId().intValue(),loser.getId());
		 */
	}
	// ////////////////////////////////////////////////////////////////////////////

}

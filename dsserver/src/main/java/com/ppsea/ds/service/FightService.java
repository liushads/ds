/**
 * 
 */
package com.ppsea.ds.service;

import static com.ppsea.ds.command.Command.STATUS_BATTLE_END;
import static com.ppsea.ds.command.Command.STATUS_FIGHT_LOSE;
import static com.ppsea.ds.command.Command.STATUS_IN_BATTLE;
import static com.ppsea.ds.command.Command.STATUS_IN_FIGHT;
import static com.ppsea.ds.command.Command.TAG_ATK_FROM;
import static com.ppsea.ds.command.Command.TAG_ATK_TO;
import static com.ppsea.ds.command.Command.TAG_ATK_TO_DESC;
import static com.ppsea.ds.command.Command.TAG_AUTO_HP;
import static com.ppsea.ds.command.Command.TAG_AUTO_MP;
import static com.ppsea.ds.command.Command.TAG_AUTO_STATE;
import static com.ppsea.ds.command.Command.TAG_BATTLE_AWAY_NUM;
import static com.ppsea.ds.command.Command.TAG_BATTLE_DEAD_NUM;
import static com.ppsea.ds.command.Command.TAG_BATTLE_KILL_NUM;
import static com.ppsea.ds.command.Command.TAG_BATTLE_LIFE_NUM;
import static com.ppsea.ds.command.Command.TAG_BATTLE_TAX_TIME;
import static com.ppsea.ds.command.Command.TAG_BATTLE_WIN_TONG;
import static com.ppsea.ds.command.Command.TAG_BE_KILLED;
import static com.ppsea.ds.command.Command.TAG_CURR_AQ;
import static com.ppsea.ds.command.Command.TAG_CURR_JX;
import static com.ppsea.ds.command.Command.TAG_DEC_HP_SUM;
import static com.ppsea.ds.command.Command.TAG_DEC_MP_SUM;
import static com.ppsea.ds.command.Command.TAG_ENEMY_TYPE;
import static com.ppsea.ds.command.Command.TAG_MONSTER_ABORT;
import static com.ppsea.ds.command.Command.TAG_PLAYER;
import static com.ppsea.ds.command.Command.TAG_PLAYER_STAT;
import static com.ppsea.ds.command.Command.TAG_WIN_PRIZE;
import static com.ppsea.ds.command.Command.TAG_AUTO_ENDURE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.command.CommandSet;
import com.ppsea.ds.command.fight.SystemSettingCmd;
//import com.ppsea.ds.command.fightcity.FightCityGround;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.FightResult;
import com.ppsea.ds.data.PlayerDyn;
import com.ppsea.ds.data.model.City;
import com.ppsea.ds.data.model.CityFacility;
import com.ppsea.ds.data.model.CityFacilityMonster;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Monster;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.MapMG;
import com.ppsea.ds.manager.MonsterMG;

public class FightService {
	private static final Logger log = Logger.getLogger(FightService.class);
	public static final int SEG_FIGHT_CITY_MONSTER_BEG = -100000;
	public static final int SEG_FIGHT_CITY_MONSTER_END = -110000;
	public static final int SEG_FIGHT_INSTANCE_MONSTER_BEG = -50000;
	public static final int SEG_FIGHT_INSTANCE_MONSTER_END = -80000;
	public static final int SEG_FIGHT_MONSTER_FOR_PET_BEG = -49000;
	public static final int SEG_FIGHT_MONSTER_FOR_PET_END = -49999;

	public static final int FIGHT_STATUS_WILL_ATK = -1;
	public static final int FIGHT_STATUS_WILL_DEF = -2;
	public static final int FIGHT_PKAREA_WILL_ATK = -3;
	public static final int FIGHT_PKAREA_WILL_DEF = -4;
	public static final int FIGHT_STATUS_ATK = 1;
	public static final int FIGHT_STATUS_DEF = 2;
	public static final int FIGHT_STATUS_END = 3;
	public static final int FIGHT_PKAREA_ATK = 4;
	public static final int FIGHT_PKAREA_DEF = 5;
	public static final int FIGHT_PKAREA_END = 6;
	
	public static String ENEMY_NAME = "{enemy}";
	public static String SELF_PET = "{self_pet}";
	public static String ENEMY_PET = "{enemy_pet}";
	public static String NO_MONSTER_ATK = "no_monster_atk";

	/**
	 * 计算是否打出躲避
	 * @param atkAgi
	 * @param defAgi
	 * @return
	 */
	public static boolean isRealAgility(int atkAgi, int defAgi) {
		int prop = 0;
		if (atkAgi == defAgi) prop = 15; 
		else if (atkAgi > defAgi) {
			prop = (int)(8 + ((float)(atkAgi - defAgi))/10);
		}
		else if (atkAgi < defAgi) {
			prop = (int)(8 - ((float)(defAgi - atkAgi))/20);
		}
		if (prop > 50) prop = 50;
		if (prop < 2) prop = 2;
		return (rangeRandom(0, 100) < prop);
	}
	/**
	 * 计算实际的攻击输出
	 * @param atk
	 * @param def
	 * @return
	 */
	public static int getRealAttack(int atk, int def) {
		int ret = (int)(atk - def*0.8);
		if (ret < 10) return rangeRandom(5, 10);
		return ret;
	}
	/**
	 * 
	 * @param str
	 * @param name
	 * @param value
	 * @return
	 */
	public static String replaceParam(String str, String name, String value) {
		return str.replace(name, value);
	}
	/**
	 * 判断两个用户是否在同一设施
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static boolean isAtSameLocation(Player p1, Player p2) {
		if (p1 != null && p2 != null) {
			return (p1.getCityFacility().getId().intValue() == p2.getCityFacility().getId().intValue());
		}
		return false;
	}
	/**
	 * 擂台战
	 * @param player
	 * @return
	 * @throws PpseaException
	 */
	/*
	public static CommandResult doFightPlayerGround(Player player) {
		try	
		{
			FightPlayerGround cmd = (FightPlayerGround)(CommandSet.getInstance().getCommand(Command.COMMAND_PKAREA_GROUND));
			return cmd.done(player, new String[0]);
		}
		catch (Exception e) {
			log.error("exception", e);
		}
		return null;
	}
	*/
	/**
	 * 攻城战
	 * @param player
	 * @return
	 * @throws PpseaException
	 */
	
//	public static CommandResult doFightCityGround(Player player) {
//		try	
//		{
//			FightCityGround cmd = (FightCityGround)(CommandSet.getInstance().getCommand(Command.COMMAND_FIGHT_GROUND));
//			return cmd.done(player, new String[0]);
//		}
//		catch (Exception e) {
//			log.error("exception", e);
//		}
//		return null;
//	}
	/**
	 * 
	 * @param player
	 * @param facilityId
	 * @return
	 */
	
	public static void gotoMainCityFacility(Player player, int facilityId) {
		try {
			if (facilityId == Constants.YIGUAN_ID && player.getHp().intValue() < 10) {
				player.addHp(200);
			}
			Integer areaId = player.getCityFacility().getCity().getAreaId();
			City mainCity = MapMG.instance.getArea(areaId).getMainCity();
			if (mainCity != null) {
				Map<Integer, CityFacility> special = mainCity.getSpecialFacilityMap();
				Integer cityFacilityId = special.get(facilityId).getId();
				ErrorMsg ret = PlayerService.move(player, cityFacilityId, false);
				/*
				CommandResult result = new CommandResult(STATUS_SUCC);
				if(ret.code != ErrorCode.SUCC){
					result.setStatus(STATUS_FAIL);
					result.setVO(TAG_ERR_MSG, ret);
					return result;
				}
				return BaseCmd.callOtherCmd(COMMAND_WALK, player, cityFacilityId.toString());
				*/
			}
			else {
				log.error("main City is null:" + areaId + ":" + player.getName());
			}
		}
		catch (Exception e) {
			log.error("exception", e);
		}
		//return null;
	}
	/**
	 * 模拟战斗场景
	 * @param player
	 */
	public static void gotoFightStat(Player player, Player enemy) {
		try	{
			player.setLastEnemy(enemy);
			enemy.setLastEnemy(player);

			FightResult frAtk = new FightResult();
			frAtk.setSrc(player);
			frAtk.setDst(enemy);
			frAtk.setDecHp(player.getLastAttackHp());
			FightResult frDef = new FightResult();
			frDef.setSrc(enemy);
			frDef.setDst(player);
			frDef.setDecHp(player.getLastBeAttack());
			player.addFightAttack(frAtk);
			player.addFightDefence(frDef);
		}
		catch (Exception e) {
			log.error("exception", e);
		}
	}
	/**
	 * 
	 * @param fpi
	 * @param result
	 */
	/*
	public static void setFightPlayerWaitResult(FightPlayerInfo fpi, CommandResult result)
	{
		Collection<Player> atkPlayers = fpi.getAtkPlayers();
		for (Player p : atkPlayers) {
			result.setList(TAG_PKAREA_ATKS,
					p.getName(), String.valueOf(p.getLevel()));
		}
		Collection<Player> defPlayers = fpi.getDefPlayers();
		for (Player p : defPlayers) {
			result.setList(TAG_PKAREA_DEFS,
					p.getName(), String.valueOf(p.getLevel()));
		}
		result.setText(TAG_PK_DEF_NUMS, defPlayers.size());
		result.setText(TAG_PK_ATK_NUMS, atkPlayers.size());
		result.setText(TAG_RANDOM, new Random().nextInt(100000));
		result.setText(TAG_PKAREA_LEFT, fpi.getLeftTime());
	}
	*/
	/**
	 * 
	 * @param player
	 * @return
	 * @throws PpseaException
	 */
	public static void gotoCurrCityFacility(Player player, int facilityId) {
		try	{
			Map<Integer, CityFacility> special = player.getCityFacility().getCity().getSpecialFacilityMap();
			Integer cityFacilityId = special.get(facilityId).getId();
			//PlayerService.move(player, cityFacilityId, true);
			ErrorMsg ret = PlayerService.move(player, cityFacilityId, true);
			/*
			CommandResult result = new CommandResult(STATUS_SUCC);
			if(ret.code != ErrorCode.SUCC){
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, ret);
				return result;
			}
			return BaseCmd.callOtherCmd(COMMAND_WALK, player, cityFacilityId.toString());
			*/
		}
		catch (Exception e) {
			log.error("exception", e);
		}
		//return null;
	}
	/**
	 * 
	 * @param player
	 * @return
	 */
	public static String getPlayerStat(Player player) 
	{
		PlayerDyn dyn = player.getDyn();
		List<String> ls = new ArrayList<String>();
		if (ls.size() == 0) return "正常";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ls.size(); ++i) {
			if (i != 0) sb.append(",");
			sb.append(ls.get(i));
		}
		return sb.toString();
	}
	/**
	 * 设置快捷栏
	 * @param player
	 * @param result
	 * @return
	 */
	public static Player setShortcut(Player player, CommandResult result, boolean allPostion)
	{
		if (!result.getData().containsKey(Command.TAG_SHORTCUT)) {
			Map<Integer, PlayerItem> shortCuts = player.getShortCuts();
			List<Integer> noShortId = new LinkedList<Integer>();
			for (int i = 1; i <= 3; ++i) {
				PlayerItem pi = shortCuts.get(i);
				if (pi == null) {
					noShortId.add(i);
					continue;
				}
				if (pi.getAmount() <= 0) {
					noShortId.add(i);
					shortCuts.remove(i);
					continue;
				}
				String desc = pi.getItem().getName();
				desc += "(" + String.valueOf(pi.getAmount()) + ")";
				result.setList(Command.TAG_SHORTCUT, desc, pi.getIdStr());
			}
			if (allPostion == true) {
				for (Integer s : noShortId) {
					result.setList(Command.TAG_SHORTCUT, "药品", String.valueOf(0-s.intValue()));
				}
			}
		}
		PlayerItem aq = player.getCurrAQ();
		if (aq != null && !result.getData().containsKey(Command.TAG_CURR_AQ)) 
		{
			String desc = aq.getItem().getName();
			desc += "(" + String.valueOf(aq.getAmount()) + ")";
			result.setHref(TAG_CURR_AQ, desc, aq.getId());
		}
		return player;
	}
	/**
	 * 检查是否有怪主动攻击，在WalkCmd和FightWithPlayer中调用
	 * @param player
	 * @param result
	 * @throws PpseaException 
	 */
	public static Monster checkMonsterAttack(Player player, CommandResult result, String[] ps)
	{
		//内部移动，例如撤退，战败。此时不能有主动攻击
		if (ps.length > 1 && ps[1].equals(NO_MONSTER_ATK)) {
			return null;
		}
		Map<Integer, Monster> monster = player.getMonster();
		if (monster != null) {
			Collection<Monster> cm = monster.values();
			for (Monster m : cm) {
				CityFacilityMonster cfm = m.getCityFacilityMonster();
				if (cfm != null && MonsterMG.instance.isAttack(cfm.getAttackProb())) {
					FightService.gotoFightStat(player, m.getPlayer());
					return m;
				}
			}
		}
		return null;
	}
	/**
	 * 检查是否有未捡起的道具
	 * @param player
	 * @param result
	 */
	public static void checkWinPrize(Player player, CommandResult result)
	{
		//普通的独享道具，最多显示30个，需要捡起后才能看到后面的
		List<PlayerItem> playerItems = ItemService.getTempItems(player, false);
		if (playerItems != null) {
			int index = 0;
			int count = 0;
			for (PlayerItem pi : playerItems) {
				if (pi != null) {
					++count;
					result.setList(TAG_WIN_PRIZE, ItemService.getBeautifulName(pi), String.valueOf(index));				
				}
				if (count >= 30) break;
				++index;
			}			
		}
		//副本中的共享道具
		if (player.isInInstacneCity()) {
			playerItems = ItemService.getTempItems(player, true);
			if (playerItems != null) {
				int index = ItemService.SEG_INSTANCE_ITEM_BEG;
				for (PlayerItem pi : playerItems) {
					if (pi != null) {
						result.setList(TAG_WIN_PRIZE, pi.getItem().getName() + "(" + pi.getItem().getLevel() + "级)", String.valueOf(index));				
					}
					--index;
				}
			}
		}
	}
	/**
	 * 
	 * @param fr
	 * @return
	 */
	public static String getSpecialDescSimple(FightResult fr) {
		StringBuffer desc = new StringBuffer();
		if (fr.isDidSunder()) desc.append("虚");
		if (fr.isDidSeal()) desc.append("封");
		if (fr.isDidBrokensoul()) desc.append("沮");
		if (fr.isDidPoison()) desc.append("毒");
		if (fr.isDidSlow()) desc.append("缓");
		if (fr.isDidRebound()) desc.append("弹");
		if (fr.isDidAgility()) desc.append("躲");
		return desc.toString();
	}
	/**
	 * 
	 * @param fr
	 * @return
	 */
	public static String getPetSpecialDescSimple(FightResult fr) {
		StringBuffer desc = new StringBuffer();
		/*
		if (fr.isDidPetAddHPFromDst()) desc.append("嗜血.");
		if (fr.isDidPetAddSelfProp()) desc.append("怒吼.");
		if (fr.isDidPetAppendAgi()) desc.append("轻身.");
		if (fr.isDidPetAppendAtk()) desc.append("利刃.");
		if (fr.isDidPetAppendDef()) desc.append("御敌.");
		if (fr.isDidPetAppendMor()) desc.append("振幅.");
		if (fr.isDidPetCancelAutoHP()) desc.append("封血.");
		if (fr.isDidPetCancelCurse()) desc.append("神圣.");
		if (fr.isDidPetCancelDstPet()) desc.append("迷惑.");
		if (fr.isDidPetCancelParalys()) desc.append("封麻.");
		if (fr.isDidPetCancelPoison()) desc.append("封毒.");
		if (fr.isDidPetCancelWeak()) desc.append("封虚.");
		if (fr.isDidPetDecDestProp()) desc.append("威压.");
		if (fr.isDidPetDropDestBody()) desc.append("卸甲.");
		if (fr.isDidPetDropDestHand()) desc.append("卸刃.");
		if (fr.isDidPetDamnDest()) desc.append("咒骂.");
		*/
		return desc.toString();
	}
	/**
	 * 
	 * @param fr
	 * @return
	 */
	public static String getSpecialDesc(PlayerDyn prop, FightResult fr) {
		StringBuffer desc = new StringBuffer();
		if (fr.getNameJx() != null) {
			desc.append("使出招式:" + fr.getNameJx());
		}
		return desc.toString();
	}
	/**
	 * 攻防比例合并
	 * @param r1
	 * @param r2
	 * @return
	 */
	public static boolean rateMerge(int atk, int def) {
		if (atk > 100) atk = 100;
		if (def > 100) def = 100;
		int rdef = 100 - def;
		int a = (new Random()).nextInt(100) + 1;
		int b = (new Random()).nextInt(100) + 1;
		return (a <= atk && b <= rdef);
	}

	public static int getAddExpFromMonster(Monster monster, Player player) {
		Integer minExp = monster.getMonster().getDropExpMin();
		Integer maxExp = monster.getMonster().getDropExpMax();
		int dropExp = FightService.rangeRandom(minExp, maxExp);
		int levelInt = player.getLevel().intValue() - monster.getMonster().getLevel().intValue();
		
		/*
		if (levelInt > 5) {
			dropExp -= dropExp*levelInt*4/100;
			if (dropExp <= 1) dropExp = 1;
		}
		*/
		int addExp = 0;
		if (levelInt <= 5) { addExp = dropExp; }
		else if (levelInt == 6) { addExp = dropExp*80/100; }
		else if (levelInt == 7) { addExp = dropExp*65/100; }
		else if (levelInt == 8) { addExp = dropExp*50/100; }
		else if (levelInt == 9) { addExp = dropExp*35/100; }
		else if (levelInt ==10) { addExp = dropExp*20/100; }
		else if (levelInt >=11) { addExp = 5;}
		
		return addExp;
	}
	/**
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int rangeRandom(int min, int max) {
		if (max <= min)	return min;
		return min + (new Random()).nextInt(max - min);
	}
	/**
	 * 
	 * @param big
	 * @param sub
	 * @return
	 */
	public static int subLimitZero(int big, int sub) {
		return (big > sub ? big - sub : 0);
	}
	/**
	 * 在城市战败
	 * @param from
	 * @param to
	 */
	/*
	public static FightLostItem loseAtCity(Player winner, Player loser, boolean withMonster)
	{
		FightLostItem fli = new FightLostItem();
		try 
		{
			//掉钱(随身带的铜币)
			/*不再掉钱，改掉耐久
			int lostMoney = 0;
			//不在擂台,战场,王战
			if (loser.getFightPlayerStatus() == 0 
				&& loser.getFightCityStatus() <= 0
				&& loser.getMatchStatus() <= 0)
			{
				lostMoney = Math.abs(loser.getCopper().intValue());
				if (withMonster && lostMoney > 500) lostMoney = 500;
				if (lostMoney > 50*1000) lostMoney = 50*1000;
				if (winner != null) {
					int levelSub = winner.getLevel() - loser.getLevel();
					if (levelSub > 0) lostMoney = lostMoney/levelSub;
				}
				if (lostMoney < 0 || lostMoney > 50*1000) lostMoney = 0;
			}
			
			////////////////////////////////////////////////////////
			fli.setLostCopper(lostMoney);
			loser.addCopper(0 - lostMoney);
			//降士气(5-10)
			int decMorale = FightService.rangeRandom(5, 10);
			int morale = subLimitZero(loser.getDyn().getMorale(), decMorale);
			loser.getDyn().setMorale(morale);
			fli.setLostMorale(decMorale);
			DBService.commit(loser);
			
		} 
		catch (Exception e){
			log.error("exception", e);
		}
		return fli;
	}
	*/
	/**
	 * 加入仇家列表
	 * @param player
	 * @param enemy    
	 * @return void    
	 * @throws
	 */
	private static void addEnemy(Player player, Player enemy)
	{
		//攻城/擂台/王战不记仇家
		/*TODO:gz
		if (player.getFightCityStatus() > 0 || enemy.getFightCityStatus() > 0) return;
		if (player.getFightPlayerStatus() > 0 || enemy.getFightPlayerStatus() > 0) return;
		if (player.getWarStatus() > 0 || enemy.getWarStatus() > 0) return;

		PlayerService.checkReward(enemy, player);

		// 是否已经是仇家
		if(player.getEnemies().get(String.valueOf(enemy.getId())) != null)
			return;
		
		// 新仇家
		PlayerEnemy pe = new PlayerEnemy();
		pe.setId(GlobalGenerator.instance.getId(PlayerEnemy.TABLE_NAME));
		pe.setEnemyId(enemy.getId());
		pe.setEnemyName(enemy.getName());
		pe.setPlayerId(player.getId());
		player.getEnemies().put(String.valueOf(enemy.getId()), pe);
		DBService.commit(pe);
		*/
	}
	/**
	 * 
	 * @param src
	 * @param dst
	 * @return
	 * @throws PpseaException 
	 */
	public static FightResult[] fightBetween(Player src, Player dst, int playerSkillId, int playerItemAqId,boolean isEnemy) throws PpseaException 
	{
		//战斗开始前，计算该回合双方使出的绝学/招式
		//[暗器不引发招式和心法输出]
		StringBuffer failDesc = new StringBuffer();
		if (playerItemAqId <= 0) 
		{
		}
		
		//开始战斗
		FightResult frAtk = fightOneway(src, dst, playerItemAqId, true,isEnemy); //攻击
		FightResult frDef = fightOneway(dst, src, playerItemAqId, false,isEnemy); //反击

		if (failDesc.toString() != "") frAtk.setFailDesc(failDesc.toString());
		//[敏捷]取消伤害，本回合有效
		if (frAtk.isDidAgility()) frDef.setDecHp(0);
		if (frDef.isDidAgility()) frAtk.setDecHp(0);
		// 减双方的HP
		src.addHp(0 - frDef.getDecHp());
		dst.addHp(0 - frAtk.getDecHp());
		
		//防守方是否战败
		if (dst.getHp() <= 0) 
		{	
			frAtk.setKiller(true); 
			if (!dst.isIfIsMonster() && !src.isIfIsMonster()) {
				addEnemy(dst, src); 
			}
		}
		else
		{
			int autoHp = 0;
			int autoMp = 0;
			int autoState = 0;
			//自动补血(判断是否屏蔽该设置)
			if (!SystemSettingCmd.isFlagSet(dst.getSettingFlag(), SystemSettingCmd.FLAG_AUTO_HP))
			{
//				ErrorMsg msg = ItemService.autoRestoreHp(dst);
//				if (msg.code == ErrorCode.SUCC) {
//					autoHp = (Integer)msg.obj;
//				}
			}
			frDef.setAutoAddHp(autoHp > 0 ? autoHp : 0);
			frDef.setAutoAddMp(autoMp > 0 ? autoMp : 0);
			frDef.setAutoAddState(autoState > 0 ? autoState : 0);
			
			//自动修复装备
//			ErrorMsg msg = ItemService.autoRepairArm(dst, true);
//			int autoEndure = 0;
//			if (msg.code == ErrorCode.SUCC) {
//				autoEndure = (Integer)msg.obj;
//			}
//			frDef.setAutoAddEndure(autoEndure > 0 ? autoEndure : 0);
		}
		//------------------------------------------------------------------------------
		//攻击方是否战败
		if (src.getHp() <= 0) 
		{	
			frDef.setKiller(true); 
			//添加仇人
			if (!dst.isIfIsMonster() && !src.isIfIsMonster()) {
				addEnemy(src, dst); 
			}
		}
		else 
		{
			int autoHp = 0;
			int autoMp = 0;
			int autoState = 0;
			//自动补血(判断是否屏蔽该设置)
			if (!SystemSettingCmd.isFlagSet(src.getSettingFlag(), SystemSettingCmd.FLAG_AUTO_HP))
			{
//				ErrorMsg msg = ItemService.autoRestoreHp(src);
//				if (msg.code == ErrorCode.SUCC) autoHp = (Integer)msg.obj;
			}
			frAtk.setAutoAddHp(autoHp > 0 ? autoHp : 0);
			frAtk.setAutoAddMp(autoMp > 0 ? autoMp : 0);
			frAtk.setAutoAddState(autoState > 0 ? autoState : 0);
			
			//自动修复装备
//			ErrorMsg msg = ItemService.autoRepairArm(src, true);
//			int autoEndure = 0;
//			if (msg.code == ErrorCode.SUCC) {
//				autoEndure = (Integer)msg.obj;
//			}
//			frAtk.setAutoAddEndure(autoEndure > 0 ? autoEndure : 0);
		}
		/////////////////////////////////////////////////////////
		FightResult[] frs = new FightResult[2];
		frs[0] = frAtk;
		frs[1] = frDef;
		return frs;
	}
	/////////////////////////////////////////////////////////
	/**
	 * @throws PpseaException 
	 */
	public static FightResult fightOneway(Player src, Player dst, int aqId, boolean isAtk,boolean isEnemy) throws PpseaException
	{
		FightResult fr = new FightResult();
		fr.setSrc(src);		
		fr.setDst(dst);
		if (isEnemy) {
			src.setLastEnemy(dst);
		}
		//攻击方使用暗器，防守方不打出伤害
		if (aqId > 0 && isAtk == false) {
			fr.setDecHp(0);
			return fr;
		}
		//攻方使用暗器，直接计算暗器的输出
		if (aqId > 0 && isAtk == true) {
			PlayerItem pi = src.getAllItems().get(String.valueOf(aqId));
			if (pi != null 
				&& pi.getAmount() > 0
				&& pi == src.getCurrAQ() 
				&& pi.getItem() != null)
			{
				//使用暗器
				ErrorMsg ret = ItemService.useItem(src, pi.getItem().getId(), 1);
				if (ret.code == ErrorCode.SUCC) {
					fr.setDecHp(pi.getItem().getHp()); //暗器的HP表示输出的伤害
//					fr.setDecMp(pi.getItem().getMp()==null?0:pi.getItem().getMp()); //暗器的MP表示输出的伤害
					if (pi.getAmount() == 0) {
						pi.setShortcutId(0);
						src.setCurrAQ(null);
					}
					return fr;
				}
			}
		}
		// -------------------------------------------------
		int decHP = 0;
		PlayerDyn srcProp = src.getDyn();
		PlayerDyn dstProp = dst.getDyn();
		// -------------------------------------------------
		// 破魂，本战斗有效，且不叠加
		// -------------------------------------------------		
		// 封印，本战斗有效，且不叠加
		// -------------------------------------------------		
		// 迟缓，本战斗有效，且不叠加
		// -------------------------------------------------
		// 基础属性值
		int srcAtk = rangeRandom(srcProp.getAttackMin(), srcProp.getAttackMax());
		int dstDef = dstProp.getDefence();
//		int srcAgi = srcProp.getAgility();
//		int dstAgi = dstProp.getAgility();
//		int srcKof = srcProp.getMorale();
//		int dstKof = dstProp.getMorale();
		// -------------------------------------------------		
		
		//[计算攻击输出]
		decHP = getRealAttack(srcAtk, dstDef);
		
//		//暴击输出(TODO:用双方概率计算还是单方???)
//		if (rateMerge(srcKof, 0)) {
//			decHP += decHP*FightService.rangeRandom(15, 25)/10;
//			fr.setDidMorale(true);
//		}

		fr.setDecHp(decHP);
		return fr;
	}
	/**
	 * 检查攻城战斗状态
	 * @param player
	 * @param result
	 * @return
	 */
//	public static void checkFightCityStatus(Player player, CommandResult result) 
//	{
//		int status = player.getFightCityStatus();
//		if (status <= 0) return;
//		if ((status == FightService.FIGHT_STATUS_ATK 
//			|| status == FightService.FIGHT_STATUS_DEF)
//			&& player.getCityFacility().getFacility().getId() != Constants.BATTLE_ID)
//		{
//			FightService.gotoCurrCityFacility(player, Constants.BATTLE_ID);
////			CommandResult r = FightService.doFightCityGround(player);
////			result.getData().putAll(r.getData());
////			result.setStatus(STATUS_IN_BATTLE);
//		}
//		if (status == FightService.FIGHT_STATUS_END)
//		{
//			player.setFightCityStatus(0);
//			FightService.gotoCurrCityFacility(player, Constants.CENTER_ID);
//			player.clrMonster();
//			Integer cid = player.getCityFacility().getCityId();				
//			result.setText(TAG_BATTLE_TAX_TIME, Constants.TAX_ADD_TIME);
//			result.setStatus(STATUS_BATTLE_END);
//		}
//		return;
//	}
	/**
	 * 
	 * @param player
	 * @param result
	 */
	public static void checkFightMessage(Player player, CommandResult result, Player virtualEnemy)
	{
		//需要模拟进入战斗场景
		if (virtualEnemy != null) {
			FightService.gotoFightStat(player, virtualEnemy);
		}
		Map<Integer, FightResult> atkMessages = player.getFightAttack();
		Map<Integer, FightResult> defMessages = player.getFightDefence();
		//没有战斗消息
		if (atkMessages.size() == 0 && defMessages.size() == 0) {
			return;
		}
		//保存最后一回合状态，便于后续的战斗场景恢复
		Player lastEnemy = player.getLastEnemy();
		if (lastEnemy != null) {
			FightResult fightResult = atkMessages.get(lastEnemy.getId());
			if (fightResult != null) player.setLastAttackHp(fightResult.getDecHp());
			fightResult = defMessages.get(lastEnemy.getId());
			if (fightResult != null) player.setLastBeAttack(fightResult.getDecHp());
		}
		int srcAutoHp = 0;
		int srcAutoMp = 0;
		int srcAutoState = 0;
		int srcDecMpSum = 0;
		int srcEndure = 0;
		//主动攻击和反击事件
		for (FightResult r : atkMessages.values()) 
		{
			srcAutoHp += r.getAutoAddHp();
			srcAutoMp += r.getAutoAddMp();
			srcAutoState += r.getAutoAddState();
			srcDecMpSum += r.getDecMp();
			srcEndure += r.getAutoAddEndure();
			Player p = r.getDst();
			if (lastEnemy != null && lastEnemy.getId() == p.getId()) 
			{
				result.setList(TAG_ATK_TO, 
					       	   p.getName(), String.valueOf(p.getId()), 
					           String.valueOf(p.getHp()), String.valueOf(p.getDyn().getMaxHp()),
					           String.valueOf(r.getDecHp()), "null");
				result.setVO("mlevel", p.getLevel());
				//判断是否设置了关闭战斗描述
				if (!SystemSettingCmd.isFlagSet(player.getSettingFlag(), SystemSettingCmd.FLAG_FIGHT_DESC))	{
					String atkDesc = "你向" + p.getName() + "发起攻击";
					String atkZsJxDesc = FightService.getSpecialDesc(player.getDyn(), r);
					if (!atkZsJxDesc.equals("")) atkZsJxDesc += ";";
					atkZsJxDesc += r.getFailDesc();
					if (!atkZsJxDesc.equals("")) atkDesc = atkZsJxDesc;
					if (!atkDesc.equals("")) {
						result.setList(TAG_ATK_TO_DESC, atkDesc, "");
					} 
				}
			}
		}
		//被敌人攻击的事件
		int srcDecHpSum = 0;
		Player beKilledBy = null;
		for (FightResult d : defMessages.values()) 
		{
			srcDecHpSum += d.getDecHp();
			Player p = d.getSrc();
			if (d.isKiller()) beKilledBy = p;
			if (lastEnemy != null && p.getId() == lastEnemy.getId()) continue;
			String specialDesc = FightService.getSpecialDescSimple(d);
			result.setList(TAG_ATK_FROM,
				       p.getName(), String.valueOf(p.getId()),
				       String.valueOf(p.getHp()), String.valueOf(p.getDyn().getMaxHp()),
				       String.valueOf(d.getDecHp()), specialDesc);
		}
		//设置返回数据
		result.setVO(TAG_PLAYER, player);
		result.setVO(TAG_AUTO_HP, Integer.valueOf(srcAutoHp));
		result.setVO(TAG_AUTO_MP, Integer.valueOf(srcAutoMp));
		result.setVO(TAG_AUTO_ENDURE, Integer.valueOf(srcEndure));
		result.setVO(TAG_AUTO_STATE, Integer.valueOf(srcAutoState));
		result.setText(TAG_DEC_HP_SUM, String.valueOf(srcDecHpSum));
		result.setText(TAG_DEC_MP_SUM, String.valueOf(srcDecMpSum));
		//设置撤退需要的铜贝数
		Player enemy = player.getLastEnemy();
		if (enemy != null && enemy.isIfIsMonster()) {
			result.setText(TAG_MONSTER_ABORT, enemy.getBelongMonster().getAbortLostCopper());
		} else {
//			int abort = player.getCopper();
//			if (abort > 5*1000) abort = 5*1000;
//			result.setText(TAG_MONSTER_ABORT, abort);
			result.setText(TAG_MONSTER_ABORT, 0);
		}
		//设置快捷栏
		FightService.setShortcut(player, result, true);		
		//设置返回状态
		result.setStatus(STATUS_IN_FIGHT);
		if (beKilledBy != null) {
			result.setVO(TAG_ENEMY_TYPE, 1);
			result.setText(TAG_BE_KILLED, beKilledBy.getName());
			FightService.gotoMainCityFacility(player, Constants.YIGUAN_ID);
			result.setStatus(STATUS_FIGHT_LOSE);
		}
		atkMessages.clear();
		defMessages.clear();
		return;
	}
	
	
	/******************** 自动回血 *************************/
	
	/**
	 * 当玩家体力不足总体力的百分比后恢复体力
	 */
	private static final int AUTO_HP_PRE = 50;
	
	/**
	 * 根据玩家穿戴的药品道具进行回血
	 * 回血间隔为指定时间
	 * @param player
	 * @return
	 */
	public static boolean autoHp(Player player){
		boolean tmp = false;
		if (player.getHp() * 100 / player.getDyn().getMaxHp() > AUTO_HP_PRE) {
			List<PlayerItem> hpItems = player.getUsedArms(Item.POS_HP + "");
			//恢复万分比优先
			int hp = 0;
			if (hpItems != null && hpItems.size() > 0) {
				for (PlayerItem pi : hpItems) {
					hp = getAutoHp(pi, player);
					if (hp > 0) {
						tmp = true;
					}
					player.addHp(hp);
				}
			}
		}
		return tmp;
	}
	
	private static int getAutoHp(PlayerItem playerItem,Player player){
		//不是装备到药品位置的道具不能加体力
		if (playerItem == null || playerItem.getItem().getPosition() != Item.POS_HP) {
			return 0;
		}
		int res = 0;
		if (playerItem.getItem().getAutoHpPre() > 0) {
			res = player.getDyn().getMaxHp() * playerItem.getItem().getAutoHpPre() / Constants.BASE_PRE;
			res = getHp(player, playerItem, res);
		}else if (playerItem.getItem().getAutoHp() > 0) {
			res = playerItem.getItem().getAutoHp();
			res = getHp(player, playerItem, res);
		}
		return res;
		
	}
	
	private static int getHp(Player player ,PlayerItem playerItem,int hp){
		int res = hp;
		if (res < playerItem.getCurrHp()) {
			playerItem.setCurrHp(playerItem.getCurrHp() -  res);
			DBService.commit(playerItem);
		}else {
			res = playerItem.getCurrHp();
			playerItem.unuse();
			ItemService.releasePlayerItem(player, playerItem, 1, true);
		}
		return res;
	}
	/******************** 自动回血 *************************/
}

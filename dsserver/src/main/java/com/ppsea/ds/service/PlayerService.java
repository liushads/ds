package com.ppsea.ds.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import com.ppsea.ds.util.GlobalConfig;
import com.ppsea.ds.util.MD5;
import com.ppsea.ds.util.Memcached;
import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandRequest;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.command.npc.LanternDayCmd;
import com.ppsea.ds.command.npc.LoveDayCmd;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.GlobalGenerator;
import com.ppsea.ds.data.PlayerDyn;
import com.ppsea.ds.data.ResourceCache;
import com.ppsea.ds.data.model.Area;
import com.ppsea.ds.data.model.BuffRand;
import com.ppsea.ds.data.model.BuffRandVo;
import com.ppsea.ds.data.model.CityFacility;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.ItemImprove;
import com.ppsea.ds.data.model.Message;
import com.ppsea.ds.data.model.Monster;
import com.ppsea.ds.data.model.NameLuck;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.data.model.PlayerItemAppend;
import com.ppsea.ds.data.model.PlayerItemUsing;
import com.ppsea.ds.data.model.PlayerLevel;
import com.ppsea.ds.data.model.PlayerMission;
import com.ppsea.ds.data.model.PlayerNameLuck;
import com.ppsea.ds.data.model.PlayerPasswdHistory;
import com.ppsea.ds.data.model.PlayerUpgrade;
import com.ppsea.ds.data.model.RawMission;
import com.ppsea.ds.data.model.SpecBuff;
import com.ppsea.ds.exception.BookMarkException;
import com.ppsea.ds.manager.DBManager;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.manager.MapMG;
import com.ppsea.ds.manager.MissionMG;
import com.ppsea.ds.manager.MonsterMG;
import com.ppsea.ds.manager.NameLuckMG;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.manager.SectMG;
import com.ppsea.ds.task.BookStoreVipCheckerTask;
import com.ppsea.ds.util.LoggerHelper;

public class PlayerService {
	private static final Logger log = Logger.getLogger(PlayerService.class);
	private static final Logger logLogin = Logger.getLogger("LOGIN");

	private static final Random RND = new Random();
	
	/**
	 * 设置用户为离线状态，返回本次登录在线时长（秒）。
	 * @param player
	 * @return：本次登录时长（秒）。
	 */
	public static int setOffline(Player player) {
		int time = 0;
		if (player.isOnline()) {
			time = player.updateOnlineTime();
			player.setStatus(Constants.STATUS_OFFLINE);
			
			logLogin.debug("out|"+LoggerHelper.GAME_ID+"|" + player.getUserId() + "|"+LoggerHelper.ZONE_ID+ "|"+ player.getId() +  "|"+player.getName()+"|"+ player.getLevel()+"|" + time);
			// 用户离线提交上报数据到内存
//			PlayerMG.instance.addReport(player);
			
			//QQReportProxy.getInstance().sendMessage(player);
			
		}
		return time;
	}
	
	/**
	 * 将用户设置为上线状态，并记录登录日志
	 * @param player
	 */
	public static void setOnline(Player player) {
		if (player.isOffline()) {
			player.setLastGiven(player.getLoginTime());
			player.setLastLoginTime(player.getLoginTime());
			player.setLoginTime(new Date());
			player.setStatus(Constants.STATUS_ONLINE);
			
			//设置玩家位置
			CityFacility cf = MapMG.instance.getCityFacility(player.getCityFacilityId());
			player.setCityFacility(cf);
			reloadPlayerDyn(player);
			
			
			logLogin.debug("in|"+LoggerHelper.GAME_ID+"|" + player.getUserId() + "|"+LoggerHelper.ZONE_ID+ "|"+ player.getId() +  "|"+player.getName()+"|"+ player.getLevel());
			
			// 玩家登陆状态
			try{
				String info = (new Date().getTime())+"_"+GlobalConfig.zone.attributeValue("id");
				Memcached.set(player.getUserId(), info, -1);
				BookStoreVipCheckerTask.getInstance().addPlayer2Check(player);
			}
			catch(Exception e){
				log.error(e.getMessage(),e);
			}
			
		}
	}
	
	/**
	 * 签名校验
	 * @param cmdReq
	 * @param userKey
	 * @param proxyKey
	 * @throws pException
	 */
	public static void verifySign(CommandRequest cmdReq, String passwd, String proxyKey) throws BookMarkException {
		String userKey = "";
		String key = "";
		if(passwd != null){
			userKey = passwd;
		}
		
		try {
			key = MD5.encode(cmdReq.getVerifyStr(), userKey, proxyKey);
		} catch (NoSuchAlgorithmException e) {
			log.error("exception:", e);
		}
		
		if (!key.equals(cmdReq.getVerifyKey())) {
			log.error("书签错误:" + cmdReq);
			throw new BookMarkException("书签错误，请重新登录");
		}
	}
	
	/**
	 * 检查玩家当前是否再城市内，不在就过滤掉买卖装备，药品的请求.
	 * @param player
	 */
	public static ErrorMsg checkPlayerInCity(Player player) {
		if (player == null) {
			return new ErrorMsg(ErrorCode.ERR_NOT_IN_CITY, "玩家不存在");
		}
		if (player.getCityFacility() == null || player.getCityFacility().getFacility() == null) {
			return new ErrorMsg(ErrorCode.ERR_NOT_IN_CITY, "城市设施为空");
		}
		if (player.getCityFacility().getFacility().getDirect()) {
			return new ErrorMsg(ErrorCode.SUCC, "");
		}
		return new ErrorMsg(ErrorCode.ERR_NOT_IN_CITY, "你当前城市设施不在城内，请求无效 ");
	}
	
	/**
	 * 加入门派
	 * @param player
	 * @param sectId 门派id
	 * @return
	 */
	public static ErrorMsg joinSect(Player player, int sectId){
		if(player.getSectId() > 0){
			return new ErrorMsg(ErrorCode.ERR_SECT_JOINED,"你已经加入过门派了");
		}
		
		if( SectMG.instance.getSect(sectId) == null){
			return new ErrorMsg(ErrorCode.ERR_SYS, "门派不存在！");
		}
		
		player.setSectId(sectId);
		DBService.commit(player);
		return Constants.SUCC;
	}
	
	/**
	 * 移动用户到指定的设施
	 * @param cityFacilityId 目的设施
	 * @param isSameCity 是否必须是同城移动
	 * @return 
	 * true：移动到新设施，false: 仍在原设施			
	 * */
	public static ErrorMsg move(Player player, int cityFacilityId, boolean isSameCity) {
		CityFacility cityFacility =MapMG.instance.getCityFacility(cityFacilityId);

		//只能在同城移动
		if(isSameCity && cityFacility.getFacility().getIsCity()){
			if(player.getCityFacility() == null){
				return new ErrorMsg(ErrorCode.ERR_SYS, "非法操作");
			}
			//不在特殊设施中或不是主线任务不可以跨城移动
			if (!player.isSpecialCity()&& player.getCityFacility().getCityId().intValue() != cityFacility.getCityId()) {
				boolean moveB = false;
				if (player.getMissions() != null) {
					for (String key : player.getMissions().keySet()) {
						PlayerMission pm = player.getMission(key);
						if (pm.getMission().getType() == 1 && !pm.getComplete()) {
							try {
								RawMission raw = MissionMG.instance
										.getRawMission(pm.getMission().getId());
								if (raw != null) {
									if (player.getCityFacility() != null && pm.getMission().getCityfacilityId() != null
											&& pm.getMission().getCityfacilityId() > 0
											&& raw.getNpcEnd() != null 
											&& cityFacilityId == raw.getCityfacilityId()) {
										moveB = true;
									}
								}
							} catch (Exception e) {
							}
						}
					}
				}
				if (!moveB) {
					return new ErrorMsg(ErrorCode.ERR_SYS, "只能在同城移动");
				}
			}
		}
		if (player.isSpecialCity()) {
			if (!cityFacility.getCity().getIsInstance()) {
				//return new ErrorMsg(ErrorCode.ERR_SYS, "有副本任务，不能离开副本城市");
			}
		}
		if (player.isSpecialCity()) {
			if (player.getCityFacility().getCity().getIsInstance() && player.getCityFacility().getCityId().intValue() != cityFacility.getCityId()) {
				return new ErrorMsg(ErrorCode.ERR_SYS, "不能垮副本任务 ");
			}
		}
//		if (cityFacility.getCity().getId() == 134) {
//			//年兽地图
//			boolean flag = ChunJieCmd.vtime();
//			if (!flag) {
//				int cfId = PlayerService.getCenterId(player);
//				cityFacilityId = cfId;
//				cityFacility =MapMG.instance.getCityFacility(cityFacilityId);
//			}
//			
//		}
		if (cityFacility.getCity().getId()==135 || cityFacility.getCity().getId()==140 || cityFacility.getCity().getId()==141 ||cityFacility.getCity().getId()==142) {
			boolean flag = LoveDayCmd.valiate();
			if (!flag) {
				int cfId = PlayerService.getCenterId(player);
				cityFacilityId = cfId;
				cityFacility =MapMG.instance.getCityFacility(cityFacilityId);
			}
		}
		if (cityFacility.getCity().getId() == 143) {
			boolean flag = LanternDayCmd.validate();
			if (!flag) {
				int cfId = PlayerService.getCenterId(player);
				cityFacilityId = cfId;
				cityFacility =MapMG.instance.getCityFacility(cityFacilityId);
			}
		}
//		int sectWarArea = MapMG.instance.getWarAreaId(player.getSectId());
//		if (cityFacilityId == sectWarArea && player.getMatchStatus() != 1) {
//			int cfId = PlayerService.getCenterId(player);
//			cityFacilityId = cfId;
//			cityFacility =MapMG.instance.getCityFacility(cityFacilityId);
//		}
		int status = player.getFightCityStatus();
//		if (cityFacility.getFacility().getId() == Constants.BATTLE_ID && ( status != FightService.FIGHT_STATUS_ATK && status != FightService.FIGHT_STATUS_DEF)) {
//			//return new ErrorMsg(ErrorCode.ERR_SYS, "不能进入战场，可能原因:已经战败退出");
//		}
		if(player.getCityFacilityId() != cityFacilityId){
			//设置新的设施id
			player.setCityFacility(cityFacility);
			//位置有更新，重新产生怪
			player.clrMonster();
			MonsterMG.instance.getMonsterToPlayer(player);
			DBService.commit(player);
		}
		return Constants.SUCC;
	}
	
	/**
	 * 将player的地图，按map格式存储
	 * @param areas
	 * @param worldMap
	 */
	public static void decodeWorldMap(Player player){
		if(player.getMap() == null ||  player.getMap().length() < 1){
			return;
		}
		
		String[] areaArr = player.getMap().split(",");
		for(int i=0; i<areaArr.length; i++){
			Integer areaId = Integer.valueOf(areaArr[i]);			
			Area area = MapMG.instance.getArea(areaId);
			player.getWorldMap().put(areaId, area);
		}
	}	
	
	/**
	 * 重新计算用户属性,该方法只用于计算自己属性，不能计算其他玩家属性
	 * 1、玩家数据加载完之后计算一次
	 * 2、玩家穿、脱道具时计算
	 * 3、升级时
	 * 4、使用实效道具时
	 * @param player
	 */
	public static void reloadPlayerDyn(Player player){
		PlayerDyn dyn = player.getDyn();
		if(dyn == null){
			dyn =  new PlayerDyn();
			player.setDyn(dyn);
		}
		//重新初始化
		dyn.init();
		
		//设置 等级基础属性
		dyn.changeLucky(RND.nextInt(100));
		dyn.setMaxExp(PlayerMG.getPlayerLevel(player).getExp());		
		dyn.changeMaxRoom(PlayerMG.getPlayerLevel(player).getRoom());
		dyn.changeMaxHp(PlayerMG.getPlayerLevel(player).getHp());
		dyn.changeAttackMax(PlayerMG.getPlayerLevel(player).getAttackMax());
		dyn.changeAttackMin(PlayerMG.getPlayerLevel(player).getAttackMin());
		dyn.changeDefence(PlayerMG.getPlayerLevel(player).getDefence());
		dyn.changeCrit(PlayerMG.getPlayerLevel(player).getCrit());
		dyn.changeConstitution(PlayerMG.getPlayerLevel(player).getConstitution());
		dyn.changeForces(PlayerMG.getPlayerLevel(player).getForces());
		dyn.changeAgility(PlayerMG.getPlayerLevel(player).getAgility());
		dyn.changeIntelligence(PlayerMG.getPlayerLevel(player).getIntelligence());
		
		//装备的影响
		compareItemBuff(player);
		
		//计算玩家的行囊
		compareMaxRoom(player, dyn);

		compareRoom(player);
		//其他对玩家属性的影响
		
		
		//计算百分比对数据的加成
		comparePreForBuff(player);
		//计算完所有属性后进行计算特殊属性对基础属性的加成
		compareSpecBuff(player);
	}
	
	private static void comparePreForBuff(Player player){
		PlayerDyn pDyn = player.getDyn();
		int res = pDyn.getMaxHp_pre();
		if (res > 0) {
			pDyn.changeMaxHp(pDyn.getMaxHp() * res / Constants.BASE_PRE);
		}
		res = pDyn.getAttackMin_pre();
		if (res > 0) {
			pDyn.changeAttackMin(pDyn.getAttackMin() * res / Constants.BASE_PRE);
		}
		res = pDyn.getAttackMax_pre();
		if (res > 0) {
			pDyn.changeAttackMax(pDyn.getAttackMax() * res / Constants.BASE_PRE);
		}
		res = pDyn.getDefence_pre();
		if (res > 0) {
			pDyn.changeDefence(pDyn.getDefence() * res / Constants.BASE_PRE);
		}
		res = pDyn.getCrit_pre();
		if (res > 0) {
			pDyn.changeCrit(pDyn.getCrit() * res / Constants.BASE_PRE);
		}
		res = pDyn.getParry_pre();
		if (res > 0) {
			pDyn.changeParry(pDyn.getParry() * res / Constants.BASE_PRE);
		}
		res = pDyn.getConstitution_pre();
		if (res > 0) {
			pDyn.changeConstitution(pDyn.getConstitution() * res / Constants.BASE_PRE);
		}
		res = pDyn.getForces_pre();
		if (res > 0) {
			pDyn.changeForces(pDyn.getForces() * res / Constants.BASE_PRE);
		}
		res = pDyn.getAgility_pre();
		if (res > 0) {
			pDyn.changeAgility(pDyn.getAgility() * res / Constants.BASE_PRE);
		}
		res = pDyn.getIntelligence_pre();
		if (res > 0) {
			pDyn.changeIntelligence(pDyn.getIntelligence() * res / Constants.BASE_PRE);
		}
		res = pDyn.getLegacy_pre();
		if (res > 0) {
			pDyn.changeLegacy(pDyn.getLegacy() * res / Constants.BASE_PRE);
		}
		res = pDyn.getLucky_pre();
		if (res > 0) {
			pDyn.changeLucky(pDyn.getLucky() * res / Constants.BASE_PRE);
		}
	}
	
	private static void compareMaxRoom(Player player,PlayerDyn dyn){
		PlayerItem pi = null;
		for (int id : ItemService.ROOMITEMIDS) {
			pi = ItemService.findPlayerItem(player, id);
			if (pi != null) {
				dyn.changeMaxRoom(pi.getItem().getRoom());
			}
		}
	}
	
	public static void compareRoom(Player player){
		int room = 0;
		for (PlayerItem pi : player.getAllItems().values()) {
			if (Arrays.binarySearch(ItemService.ROOMITEMIDS, pi.getItem().getId()) < 0) {//不是增加负重的道具
				room += pi.getItem().getRoom() * pi.getAmount();
			}
		}
		player.setRoom(room);
	}
	/**
	 * 根据玩家穿的道具改变属性，镶嵌，强化
	 * @param player
	 */
	private static void compareItemBuff(Player player){
		List<PlayerItem> useItems = player.getUsedArmsList();
		PlayerDyn pDyn = player.getDyn();
		for (PlayerItem pi : useItems) {
			changePdynForItem(pDyn, pi.getItem());
			compareBuffRand(pDyn,pi);
			//计算镶嵌道具对玩家属性的改变
			List<PlayerItemAppend> appends = pi.getAppends();
			if (appends != null) {//暂时不放开，等镶嵌开放后再打开
				for (PlayerItemAppend pia : appends) {
					changePdynForItem(pDyn, pia.getItem());
					
				}
			}
			//根据道具强化等级改变玩家属性
			if (pi.getImproveLevel() > 0) {
				changePdynForImproveLevel(pDyn,pi);
			}
		}
	}
	
	
	/**
	 * 根据玩家道具强化等级改变玩家属性
	 * @param pDyn
	 * @param pi
	 */
	private static void changePdynForImproveLevel(PlayerDyn pDyn,PlayerItem pi){
		int level = pi.getImproveLevel();
		if (level > 0) {
			if (!ResourceCache.instance.getImproves().containsKey(level)) {
				return;
			}
			pDyn.changeAttackMax(pi.getExtraAttack());
			pDyn.changeAttackMin(pi.getExtraAttack());
			pDyn.changeDefence(pi.getExtraDefence());
			pDyn.changeMaxHp(pi.getExtraHp());
			pDyn.changeCrit(pi.getExtraCrit());
			pDyn.changeParry(pi.getExtraParry());
			pDyn.changeConstitution(pi.getExtraConstitution());
			pDyn.changeForces(pi.getExtraForces());
			pDyn.changeAgility(pi.getExtraAgility());
			pDyn.changeIntelligence(pi.getExtraIntelligence());
		}
	}
	
	/**
	 * 根据道具，计算玩家属性
	 * @param pDyn
	 * @param item
	 * @param flage
	 */
	private static void changePdynForItem(PlayerDyn pDyn, Item item) {
		// if (item.getHpPre() > 0) {
		pDyn.changeMaxHpPre(item.getHpPre());
		// }else {
		pDyn.changeMaxHp(item.getHp());
		// }
		// if (item.getAttackMinPre() > 0) {
		pDyn.changeAttackMinPre(item.getAttackMinPre());
		// }else {
		pDyn.changeAttackMin(item.getAttackMin());
		// }
		// if (item.getAttackMaxPre() > 0) {
		pDyn.changeAttackMaxPre(item.getAttackMaxPre());
		// }else {
		pDyn.changeAttackMax(item.getAttackMax());
		// }
		// if (item.getDefencePre() > 0) {
		pDyn.changeDefencePre(item.getDefencePre());
		// }else {
		pDyn.changeDefence(item.getDefence());
		// }
		// if (item.getCritPre() > 0) {
		pDyn.changeCritPre(item.getCritPre());
		// }else {
		pDyn.changeCrit(item.getCrit());
		// }
		// if (item.getParryPre() > 0) {
		pDyn.changeParryPre(item.getParryPre());
		// }else {
		pDyn.changeParry(item.getParry());
		// }
		pDyn.changeSpeed(item.getSpeed());
		// if (item.getConstitutionPre() > 0) {
		pDyn.changeConstitutionPre(item.getConstitutionPre());
		// }else {
		pDyn.changeConstitution(item.getConstitution());
		// }
		// if (item.getForcesPre() > 0) {
		pDyn.changeForcesPre(item.getForcesPre());
		// }else {
		pDyn.changeForces(item.getForces());
		// }
		// if (item.getAgilityPre() > 0) {
		pDyn.changeAgilityPre(item.getAgilityPre());
		// }else {
		pDyn.changeAgility(item.getAgility());
		// }
		// if (item.getIntelligencePre() > 0) {
		pDyn.changeIntelligencePre(item.getIntelligencePre());
		// }else {
		pDyn.changeIntelligence(item.getIntelligence());
		// }
	}
	
	
	/**
	 * 根据玩家道具附加属性增加玩家buff
	 * @param pDyn
	 * @param playerItem
	 */
	private static void compareBuffRand(PlayerDyn pDyn, PlayerItem playerItem){
		Map<Integer, BuffRandVo> buffRands = playerItem.getBuffRands();
		if (buffRands != null && buffRands.size() > 0) {
			int res = 0;
			for (BuffRandVo br : buffRands.values()) {
				res = br.getBuffRand().getBuffValue();
				switch (br.getBuffRand().getBuffTypeId()) {
				case 1:
					pDyn.changeAttack(res);
					break;
				case 2:
					pDyn.changeDefence(res);
					break;
				case 3:
					pDyn.changeMaxHp(res);
					break;
				case 4:
					pDyn.changeCrit(res);
					break;
				case 5:
					pDyn.changeParry(res);
					break;
				case 6:
					pDyn.changeConstitution(res);
					break;
				case 7:
					pDyn.changeForces(res);
					break;
				case 8:
					pDyn.changeIntelligence(res);
					break;
				case 9:
					pDyn.changeAgility(res);
					break;
				default:
					break;
				}
			}
		}
	}
	
	/**
	 * 根据玩家门派以及 特殊技能计算玩家攻防敏暴格
	 * 需要计算完玩家身上所有加成后计算
	 * @param player
	 */
	private static void compareSpecBuff(Player player){
		PlayerDyn pDyn = player.getDyn();
		SpecBuff sb = null;
		String key = "";
		if (pDyn.getConstitution() > 0) {
			key = getSpecBuffKey(player.getSectId(), getSpecBuffLevel(player), 1);//体质
			sb = ResourceCache.instance.getSpecBuffMap().get(key);
			changeSpecBuff(pDyn, sb,pDyn.getConstitution());
		}
		if (pDyn.getForces() > 0) {
			key = getSpecBuffKey(player.getSectId(), getSpecBuffLevel(player), 2);//力量
			sb = ResourceCache.instance.getSpecBuffMap().get(key);
			changeSpecBuff(pDyn, sb,pDyn.getForces());
		}
		if (pDyn.getAgility() > 0) {
			key = getSpecBuffKey(player.getSectId(), getSpecBuffLevel(player), 3);//敏捷
			sb = ResourceCache.instance.getSpecBuffMap().get(key);
			changeSpecBuff(pDyn, sb,pDyn.getAgility());
		}
		if (pDyn.getIntelligence() > 0) {
			key = getSpecBuffKey(player.getSectId(), getSpecBuffLevel(player), 4);//智力
			sb = ResourceCache.instance.getSpecBuffMap().get(key);
			changeSpecBuff(pDyn, sb,pDyn.getIntelligence());
		}
		
	}
	/**
	 * 根据特殊属性计算基础属性
	 * @param sb
	 */
	public static void changeSpecBuff(PlayerDyn pDyn,SpecBuff sb,int amount){
		if (sb == null) {
			return;
		}
		pDyn.changeMaxHp(amount * sb.getHp());
		pDyn.changeAttack(amount * sb.getAttack());
		pDyn.changeDefence(amount * sb.getDefence());
		pDyn.changeCrit(amount * sb.getCrit());
		pDyn.changeParry(amount * sb.getParry());
	}
	
	/**
	 * 根据玩家等级或者其他获取特殊技能等级
	 * @param player
	 * @return
	 */
	private static int getSpecBuffLevel(Player player){
		int res = 1;
		//没有更高等级，后面可扩展
		return res;
	}
	
	private static String getSpecBuffKey(int sect_id,int level,int spec_type_id){
		return sect_id + "_" +  level + "_" + spec_type_id;
	}
	
	public static void loadSpecialItemPro(Player player) {
		Integer itemId = ItemMG.ITEM_BIGBOX.getId();
		Integer smallItemId = ItemMG.ITEM_SMALL_PACKET.getId();
		Map<String, PlayerItem> tmp = player.getNonArms().get(Item.OTHER_TYPE_STR);
		if( tmp != null ){
			PlayerItem playerItem = tmp.get(itemId.toString());
			if (playerItem != null) {
				player.getDyn().changeMaxRoom(ItemMG.ITEM_BIGBOX_VALUE * playerItem.getAmount());
			}
			PlayerItem smallPayerItem = tmp.get(smallItemId.toString());
			if (smallPayerItem != null) {
				player.getDyn().changeMaxRoom(ItemMG.BOX_VALUE * smallPayerItem.getAmount());
			}
		}
	}
	
	/**
	 * 设置书签密码
	 * @param player
	 * @param oldPasswd
	 * @param newPassword
	 * @return
	 */
	public static ErrorMsg setPasswd(Player player, int type, String oldPasswd, String newPassword){
		//如果设置过密码,则需要验证老密码
		if(type == 0 ){
			if(player.getPasswd() != null && !player.getPasswd().equalsIgnoreCase(oldPasswd)){
				return new ErrorMsg(ErrorCode.ERR_PASSWD, "原书签密码错");
			}
		}
		else{
			if(player.getPasswd() != null && !player.getPasswd().equalsIgnoreCase(oldPasswd)){
				return new ErrorMsg(ErrorCode.ERR_PASSWD, "原道具密码错");
			}
		}

		PlayerPasswdHistory pph = new PlayerPasswdHistory();
		pph.setId(GlobalGenerator.instance.getIdForNewObj(pph));
		pph.setIp(player.getIp());
		pph.setPasswd(newPassword);
		pph.setPid(player.getId());
		pph.setUid(player.getUserId());
		pph.setType(type);
		pph.setCreateTime(new Date());
		DBService.commit(pph);
		
		if(type == 0){
			player.setPasswd(newPassword);
		}
		else{
			player.setPasswd(newPassword);
		}
		DBService.commit(player);
		return Constants.SUCC;
	}
	
	public static List<PlayerItemUsing> loadPlayerUsingItem(int pid) {
		 List<PlayerItemUsing> usingItemList = DBManager.queryPlayerLifeUsingItem(pid);
		for (PlayerItemUsing usingItem : usingItemList) {
			if (usingItem != null) {
				Item item = usingItem.getItem();
				if (item == null) {
					item = DBManager.queryItem(usingItem.getItemId());
					usingItem.setItem(item);
				}
			}
		}
		return usingItemList;
	}
	
	/**
	 * 检查是否升级了.
	 * @param player
	 * @param result
	 */
	public static void checkPlayerUpgraded(Player player, CommandResult result) {
		if (player.isUpgraded()) {
			player.setUpgraded(false);
			PlayerUpgrade upInfo = PlayerMG.instance.getPlayerUpgradeInfo(player.getLevel());
			PlayerLevel curLevel = PlayerMG.instance.getPlayerLevel(player.getLevel());
			PlayerLevel preLevel = PlayerMG.instance.getPlayerLevel(player.getLevel()-1);
			result.setStatus(Command.STATUS_PLAYER_UPGRADE);
			result.setVO("player_upgrade", upInfo);
			result.setVO("curLevel", curLevel);
			result.setVO("preLevel", preLevel);
		}
	}
	
	/**
	 * 在线礼包，数据配置[time-seconds][item id][item number].
	 * 	private static int[][][] givingConfigOld = { { { 2*60, 5*60, 10*60, 20*60, 40*60, 60*60 },
        { 10286, 10284, 10333, 47, 10349, 10284 }, { 20, 20, 1, 1, 10, 50 } } };
	 */
	private static int[][][] givingConfig = { { { 2*60, 3*60, 5*60, 5*60,3*60,2*60, 1*60, 2*60 },
        { 10286, 10284, 10333,10407, 47, 10349, 10281,10457 }, { 30, 20, 1, 1, 1, 10,1,1 } } };
	
	public static int[][][] getGivingConfig() {
		return givingConfig;
	}

	/**
	 * 列出前十个目的城市设施的玩家
	 * @param player
	 * @return
	 */
	public static Map<String, Player> listPlayersByCityFacility(Player player) {

		List<Player> playerList = listPlayersByCityFacility(player, player.getEndCity(), Constants.STATION_ID);
		Map<String, Player> players = new HashMap<String, Player>(); 
		if (playerList.size() > 0) {
			for (Player p : playerList) {
				players.put(p.getId().toString(),p);
				if (players.size() >= 10) {
					break;
				}
			}
		}
		
		return players;
	}
	
	/**
	 * 根据城市，设施查找当前在线的玩家列表.
	 * @param player
	 * @param cityId
	 * @param facilityId
	 * @return
	 */
	public static List<Player> listPlayersByCityFacility(Player player, int cityId, int facilityId) {
		List<Player> playerList = new ArrayList<Player>();
		try {
			CityFacility cf = MapMG.instance.getSpecialCityFacility(cityId, facilityId);
			for(Integer playerId : cf.getOnlinePlayers()){
				Player p = PlayerMG.instance.getOnlinePlayer(playerId);
				if(p != null && p.getId().intValue() != player.getId()){
					playerList.add(p);
				}
			}
		} catch (Exception e) {
			log.error("listPlayersByCityFacility failed,"+cityId+"/"+ facilityId ,e);
		}
		return playerList;
	}
	
	public static void showPrivateMsg(Player player, CommandResult result) {
		Message message = player.getLatestMessage();
		result.setVO("private_msg", message);
	}
	
	/**
	 * 生成姓名测试 
	 * 
	 * @param player
	 * @param target
	 * @return
	 */
	public static NameLuck fetchNameLuck(Player player, Player target) {

		// 设置小位在前，不用重复添加2条数据
		int playerId = 0;
		int targetId = 0;
		if (player.getId().intValue() < target.getId().intValue()) {
			playerId = player.getId();
			targetId = target.getId();
		} else {
			playerId = target.getId();
			targetId = player.getId();
		}
		// 性别判断:0同性1异性.
		int type = -1;
		if (player.getSex().intValue() == target.getSex().intValue()) {
			type = 0;
		} else {
			// 异性
			type = 1;
		}
		List<PlayerNameLuck> nameTestList = DBManager.queryPlayerNameLuck(playerId, targetId);
		NameLuck nameLuck = null;
		if (nameTestList == null || nameTestList.size() == 0) {

			nameLuck = NameLuckMG.instance.getNameRandLuck(type);
			if (nameLuck == null) {
				return null;
			}
			// 数据库不存在就创建新的缘分测试记录
			PlayerNameLuck playerLuck = new PlayerNameLuck();
			playerLuck.setNameLuckId(nameLuck.getId());
			playerLuck.setPlayerId(playerId);
			playerLuck.setTargetId(targetId);
			DBService.commitNoCacheUpdate(playerLuck);
		} else {
			nameLuck = NameLuckMG.instance.getNameLuckById(nameTestList.get(0).getNameLuckId());
		}

		// 给玩家发信息
		ChatService.sendMessagePrivate(target.getId(), player.getId(), player.getName(), "我测试了和你的缘份，结果是" + nameLuck.getName() + ".");

		return nameLuck;
	}
	
	
	public static Integer getCenterId(Player player){
		CityFacility cf = player.getCity().getSpecialFacilityMap().get(Constants.CENTER_ID);
		if(cf == null){
			Integer areaId = player.getCity().getAreaId();
			cf = MapMG.instance.getArea(areaId).getMainCity().getSpecialFacilityMap().get(Constants.CENTER_ID);
		}

		return cf.getId();
	}

	/**
	 * 队伍中任何人杀怪，都能给跟怪相关任务的队友怪数量+1。
	 * 怪数量是1，且是有任务才出现的怪除外。
	 * 
	 * @param player
	 * @param monsterId
	 * @return    
	 * @return int    
	 * @throws
	 */
	public static int killMonsterInTeam(Player player, Integer monsterId) {
		Monster m = MonsterMG.instance.getMonsterById(monsterId);
		if (m == null)
			return ErrorCode.ERR_SYS;

		// BOSS怪，不共享
		if (m.getShowForMission().intValue() != 0) {
			player.setInteraction(Constants.PREFIX_MONSTER + monsterId, 1);
			return ErrorCode.SUCC;
		}

		// 如果有任务，更新任务参数
		player.updateInteraction(Constants.PREFIX_MONSTER + monsterId);

//		Team team = TeamMG.instance.getTeam(player.getTeamId());
//		if (team == null) {
//			// 如果有任务，更新任务参数
//			return ErrorCode.ERR_NO_TEAM;
//		}

//		for (TeamPlayer tp : team.getMembers()) {
//			Player p = PlayerMG.instance.getOnlinePlayer(tp.getPlayerId());
//			if (p == null || player.getId() == tp.getPlayerId())
//				continue;
//
//			// 取跟怪相关的任务
//			List<Integer> list = MissionMG.instance.getMissionIdByMonster(monsterId);
//
//			if (list == null)
//				continue;
//
//			PlayerMission playerMission = null;
//
//			for (Integer i : list) {
//				playerMission = player.getMissionOnGoing(String.valueOf(i));
//				if (playerMission != null)
//					break;
//			}
//
//			if (playerMission == null)
//				continue;
//
//			// 如果有任务，更新任务参数
//			p.updateInteraction(Constants.PREFIX_MONSTER + monsterId);
//		}

		return ErrorCode.SUCC;
	}
}

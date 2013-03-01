package com.ppsea.ds.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.DialogAction;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Event;
import com.ppsea.ds.data.model.EventItem;
import com.ppsea.ds.data.model.EventMonster;
import com.ppsea.ds.data.model.EventPlayer;
import com.ppsea.ds.data.model.EventResult;
import com.ppsea.ds.data.model.EventResultItem;
import com.ppsea.ds.data.model.EventResultPlayer;
import com.ppsea.ds.data.model.EventResultSkill;
import com.ppsea.ds.data.model.EventSkill;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Monster;
import com.ppsea.ds.data.model.NpcInteraction;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.data.model.Route;
import com.ppsea.ds.manager.EventMG;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.manager.MonsterMG;

/**
 * ClassName:EventService
 *
 * @author   Daniel.Hao
 */
public class EventService {
	private static final Random RAND = new Random();
	
	/**
	 * 行走时发生的随机事件
	 * 
	 * @param player
	 * @param type
	 * @return    
	 * @return DialogAction    
	 * @throws
	 */
	public static DialogAction checkFacilityEvent(Player player){
		if(player.getCityFacility() == null) return null;
		
		if(player.getCityFacility().getEvents().size() > 0){
			// 发生事件，随机选择一个事件
			int i = RAND.nextInt(player.getCityFacility().getEvents().size());
			Event event = player.getCityFacility().getEvents().get(i);
			
			if(event.getType() != Event.TYPE_WALK) return null;
			
			if(canHappen(player, event)){
				if(event.getCityfacilityId() != null && event.getCityfacilityId() > 0){
					// 进入设施（打怪）
					player.setEventId(event.getId());
					
					player.setSpecialCity(true);

					return new DialogAction(event);
				}else{
					// 普通事件，直接获得事件结果
					DialogAction da = evaluateEventResult(player, event);
					da.setEvent(null);
					
					if(da != null) return da;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 检查航道上发生的随机事件
	 * 
	 * @param player
	 * @return    
	 * @return DialogAction    
	 * @throws
	 */
	public static DialogAction checkRouteEvent(Player player, int type){
		// 当前航道		
		Route route = TravelService.getRoute(player);
		
		// 非航行中
		if(route == null) return null;
		
		List<Event> events = route.getEvents();
		
		if(events.size() > 0){
			// 发生事件，随机选择一个事件
			int i = RAND.nextInt(events.size());
			Event event = events.get(i);
			
			// 随机事件次数
//			RankingsService.addLog(4, player.getId().intValue());
			
			if(canHappen(player, event)){
				if(event.getCityfacilityId() != null && event.getCityfacilityId() > 0){
					// 进入设施（打怪）
					player.setEventId(event.getId());
					
					player.setSpecialCity(true);

					return new DialogAction("【" + event.getName() + "】" + event.getDescription(), event.getActions(), event);
				}else if(event.getEventExtras() == null){
					// 普通事件，直接获得事件结果
					DialogAction da = evaluateEventResult(player, event);
					
					if(da != null) return da;
				}
			}
		}
		
		if(type == Event.TYPE_SAIL){
			// 未发生事件（移动中）
			if(events.size() > 0){
				return new DialogAction("周围安静得可怕。", null);
			}else	
				return new DialogAction("风和日丽，一切都很和谐。", null);
		}else 
			return null;
	}
	
	/**
	 * 发生事件的任务状态
	 * 
	 * @param monsterId
	 * @param player
	 * @return    
	 * @return String    
	 * @throws
	 */
	public static String updateEventStatusByMonster(Integer monsterId, Player player, boolean[] upgrade){
		if(player.getEventId() == null || player.getEventId() == 0) return null;
		
		List<Integer> list = EventMG.instance.getEventIdByMonster(monsterId);
		
		if(list == null)return null;
		
		Event event = null;
		
		// 是否已经碰到事件
		for(Integer i : list){
			if(i.equals(player.getEventId())){
				event = EventMG.instance.getRandomEvent(i);
				break;
			}
		}
		
		if(event == null) return null;
		
		// 计数开始
		if(player.getInteractions().get(Constants.PREFIX_MONSTER + monsterId) == null)
			player.increInteraction(Constants.PREFIX_MONSTER + monsterId);
		
		// 是否已经完成
		boolean complete = true;
		StringBuffer sb = new StringBuffer();
		for(EventMonster em : event.getEventMonsters()){
			NpcInteraction ni = player.getInteractions().get(Constants.PREFIX_MONSTER + em.getMonsterId());
			if(ni == null || ni.getValue() < em.getAmount()){
				complete = false;
			}
			
			// 任务进度描述
			Monster monster = MonsterMG.instance.getMonsterById(em.getMonsterId());
			sb.append(monster.getName());
			sb.append("(");
			sb.append(ni == null?0:(ni.getValue() > em.getAmount()?em.getAmount():ni.getValue()));
			sb.append("/");
			sb.append(em.getAmount());
			sb.append(") ");
		}
		
		if(complete){
			// 清除标识，不再计数
			player.clearInteraction(Constants.PREFIX_MONSTER + monsterId);
			
			player.setEventId(0);
			
			DialogAction da = evaluateEventResult(player, event);
			
			upgrade[0] = da.isUpgrade();
			
			return (da==null?"":da.getDialog());
		}else{
			// 进行中
			return "【" + event.getName() + "】事件进度：" + sb.toString();
		}
	}
	
	/**
	 * 是否满足事件发生条件
	 * 
	 * @param player
	 * @param event
	 * @return    
	 * @return boolean    
	 * @throws
	 */
	private static boolean canHappen(Player player, Event event){
		// 是否满足发生概率条件
		if(event.getProbability() < RAND.nextInt(100)) return false;
		
		// Player属性条件
		for(EventPlayer ep : event.getEventPlayers()){
			switch(ep.getProperty()){
				case 1:
					// 运气
					if(player.getDyn().getLucky() < ep.getMinValue() ||
					   player.getDyn().getLucky() > ep.getMaxValue())
						return false;
					break;
				case 2:
					// 等级
					if(player.getLevel() < ep.getMinValue() ||
					   player.getLevel() > ep.getMaxValue())
						return false;
					break;
				case 3:
					// 声望
					if(player.getFame() < ep.getMinValue() ||
					   player.getFame() > ep.getMaxValue())
						return false;
					break;
				case 4:
					// 金钱
					if(player.getCopper() < ep.getMinValue() ||
					   player.getCopper() > ep.getMaxValue())
						return false;
					break;
				case 5:
					// 体力
					if(player.getHp() < ep.getMinValue() ||
					   player.getHp() > ep.getMaxValue())
						return false;
					break;
				case 6:
					// 最小攻击
					if(player.getDyn().getAttackMin() < ep.getMinValue() ||
					   player.getDyn().getAttackMin() > ep.getMaxValue())
						return false;
					break;
				case 7:
					// 最大攻击
					if(player.getDyn().getAttackMax() < ep.getMinValue() ||
					   player.getDyn().getAttackMax() > ep.getMaxValue())
						return false;
					break;
				case 8:
					// 防御
					if(player.getDyn().getDefence() < ep.getMinValue() ||
					   player.getDyn().getDefence() > ep.getMaxValue())
						return false;
					break;
				case 9:
					// 敏捷
//					if(player.getDyn().getAgility() < ep.getMinValue() ||
//					   player.getDyn().getAgility() > ep.getMaxValue())
						return false;
//					break;
				case 10:
					// 士气
//					if(player.getDyn().getMorale() < ep.getMinValue() ||
//					   player.getDyn().getMorale() > ep.getMaxValue())
//						return false;
					break;
				case 12:
					// 货物总值
//					if(player.getCargoTotalValue() < ep.getMinValue() ||
//					   player.getCargoTotalValue() > ep.getMaxValue())
//						return false;
					break;
				case 13:
					// 经验
					if(player.getExp() < ep.getMinValue() ||
					   player.getExp() > ep.getMaxValue())
						return false;
					break;
			}
		}
		
		// 技能条件
		for(EventSkill es : event.getEventSkills()){
//			Skill skill = ResourceCache.instance.getSkill(es.getSkillId());
//			PlayerSkill playerSkill = player.getSkills().get(skill.getName());
			
			// 无技能或者技能等级小于要求的等级
//			if(playerSkill == null || playerSkill.getSkill().getLevel() < skill.getLevel())
//				return false;
		}
		
		// 道具条件
		for(EventItem ei : event.getEventItems()){
			PlayerItem playerItem = ItemService.findPlayerItem(player, ItemMG.instance.getItem(ei.getItemId()));
			
			// 无道具或者道具数量不足
			if(playerItem == null || playerItem.getAmount() < ei.getAmount())
				return false;
		}
		
		return true;
	}
	
	/**
	 * 取事件结果
	 * 
	 * @param player
	 * @param event
	 * @return    
	 * @return DialogAction    
	 * @throws
	 */
	private static DialogAction evaluateEventResult(Player player, Event event){
		for(EventResult er : event.getResults()){
			// 缺省条件
			if(er.getItemId() == null || er.getItemId() == 0){
				return updatePlayer(event, er, player);
			}
			
			// 道具条件
			ErrorMsg ret = ItemService.useItem(player, er.getItemId(), er.getItemCount());
			if( ret.code == ErrorCode.SUCC){
				return updatePlayer(event, er, player);
			}
		}
		
		return null;
	}
	
	/**
	 * 更新Player
	 * 
	 * @param event
	 * @param er
	 * @return    
	 * @return DialogAction    
	 * @throws
	 */
	private static DialogAction updatePlayer(Event event, EventResult er, Player player){
		StringBuffer sb = new StringBuffer();
		boolean upgrade = false;
		// 更新玩家属性
		for(EventResultPlayer erp : er.getErPlayers()){
			int value = 0;
			switch(erp.getProperty()){
				case 1:
					// 运气
					if(erp.getAbs()) value = erp.getValue();
					else value = player.getDyn().getLucky() * erp.getValue() / 100;
					
					player.getDyn().changeLucky(value);
					
					if(value != 0) sb.append("<br/>运气");

					break;
				case 2:
					// 等级
					break;
				case 3:
					// 声望
					if(erp.getAbs()) value = erp.getValue();
					else value = player.getFame() * erp.getValue() / 100;


					if(value != 0) sb.append("<br/>声望");
					
					break;
				case 4:
					// 金钱
					try{
						if(erp.getAbs()){
							sb.append("<br/>金钱");
							value = 0;
							if(erp.getValue() > 0){
								int copper = erp.getValue() / 2 + RAND.nextInt(erp.getValue() / 2);
								player.addCopper(copper);

								sb.append("+");
								sb.append(copper);
								sb.append("铜贝");
							}else{
								int copper = -erp.getValue() / 2 + RAND.nextInt(-erp.getValue() / 2);
								player.consumeInCopper(copper);

								sb.append("-");
								sb.append(copper);
								sb.append("铜贝");
							}
						}else{
							if(erp.getValue() > 0) player.addCopper(player.getTotalCopper() * erp.getValue() / 100);
							else player.consumeInCopper(-player.getTotalCopper() * erp.getValue() / 100);
						}
					}catch(Exception e){
					}
						
					break;
				case 5:
					// 体力
					if(erp.getAbs()) value = erp.getValue();
					else value = player.getHp() * erp.getValue() / 100;
					
					player.addHp(value);
					
					if(player.getHp() < 1) player.setHp(1);
					
					if(value != 0) sb.append("<br/>体力");
					
					break;
				case 6:
					// 最小攻击
					if(erp.getAbs())value = erp.getValue();
					else value = player.getDyn().getAttackMin() * erp.getValue() / 100;
					
					player.getDyn().changeAttackMin(value);
					
					if(value != 0) sb.append("<br/>最小攻击");
					
					break;
				case 7:
					// 最大攻击
					if(erp.getAbs())value = erp.getValue();
					else value = player.getDyn().getAttackMax() * erp.getValue() / 100;
					
					player.getDyn().changeAttackMax(value);
					
					if(value != 0) sb.append("<br/>最大攻击");
					
					break;
				case 8:
					// 防御
					if(erp.getAbs())value = erp.getValue();
					else value = player.getDyn().getDefence() * erp.getValue() / 100;
					
					player.getDyn().changeDefence(value);
					
					if(value != 0) sb.append("<br/>防御");
					
					break;
				case 9:
					// 敏捷
//					if(erp.getAbs())value = erp.getValue();
//					else value = player.getDyn().getAgility() * erp.getValue() / 100;
//					
//					player.getDyn().setAgility(player.getDyn().getAgility() + value);
//					
//					if(value != 0) sb.append("<br/>敏捷");
					
					break;
				case 10:
					// 士气
//					if(erp.getAbs())value = erp.getValue();
//					else value = player.getDyn().getMorale() * erp.getValue() / 100;
//					
//					player.getDyn().setMorale(player.getDyn().getMorale() + value);
//					
//					if(value != 0) sb.append("<br/>士气");
					
					break;
				case 11:
					// 罪恶
					if(erp.getAbs())value = erp.getValue();
					
					if(value != 0) sb.append("<br/>罪恶");
					
					break;
				case 12:
					break;
				case 13:
					// 经验值
					if(erp.getAbs()){
						value = erp.getValue();
						if(value > 0)
							value = erp.getValue() / 2 + RAND.nextInt(erp.getValue() / 2);
						else
							value = -erp.getValue() / 2 + RAND.nextInt(-erp.getValue() / 2);
					}
					else value = player.getExp() * erp.getValue() / 100;
					
					upgrade = player.addExp(value);
					
					if(value != 0) sb.append("<br/>经验");
					
					break;
			}
			
			// 描述
			if(value > 0){
				sb.append("+");
				sb.append(value);
			}
			else if(value < 0){
				sb.append(value);
			}
		}
		
		// Commit
		if(er.getErPlayers().size() > 0) DBService.commit(player);
		
		// 增加新技能
		for(EventResultSkill ers : er.getErSkills()){
//			PlayerService.learnSkill(player,ResourceCache.instance.getSkill(ers.getSkillId()));
		}
		
		// 修改道具
		List<EventResultItem> list = new ArrayList<EventResultItem>();		for(EventResultItem eri : er.getErItems()){
			if(eri.getAmount() < 0) ItemService.useItem(player, eri.getItemId(), -eri.getAmount());
			else list.add(eri); 
		}
		if(list.size() > 0){
			EventResultItem eri = list.get(RAND.nextInt(list.size()));
			ItemService.addItem(player, eri.getItemId(), eri.getAmount());
			
			Item item = ItemMG.instance.getItem(eri.getItemId());
			sb.append("<br/>");
			sb.append(item.getName());
			sb.append("+");
			sb.append(eri.getAmount());
		}
		
		return new DialogAction("【" + event.getName() + "】" + event.getDescription() + er.getDescription() + sb.toString(), event.getActions(), event, upgrade);
	}
}

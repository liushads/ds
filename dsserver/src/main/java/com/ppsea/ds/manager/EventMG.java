package com.ppsea.ds.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.Command;
import com.ppsea.ds.data.dao.EventActionDAO;
import com.ppsea.ds.data.dao.EventActionDAOImpl;
import com.ppsea.ds.data.dao.EventDAO;
import com.ppsea.ds.data.dao.EventDAOImpl;
import com.ppsea.ds.data.dao.EventExtraDAO;
import com.ppsea.ds.data.dao.EventExtraDAOImpl;
import com.ppsea.ds.data.dao.EventExtraDescDAO;
import com.ppsea.ds.data.dao.EventExtraDescDAOImpl;
import com.ppsea.ds.data.dao.EventFacilityDAO;
import com.ppsea.ds.data.dao.EventFacilityDAOImpl;
import com.ppsea.ds.data.dao.EventItemDAO;
import com.ppsea.ds.data.dao.EventItemDAOImpl;
import com.ppsea.ds.data.dao.EventMonsterDAO;
import com.ppsea.ds.data.dao.EventMonsterDAOImpl;
import com.ppsea.ds.data.dao.EventPlayerDAO;
import com.ppsea.ds.data.dao.EventPlayerDAOImpl;
import com.ppsea.ds.data.dao.EventResultDAO;
import com.ppsea.ds.data.dao.EventResultDAOImpl;
import com.ppsea.ds.data.dao.EventResultItemDAO;
import com.ppsea.ds.data.dao.EventResultItemDAOImpl;
import com.ppsea.ds.data.dao.EventResultPlayerDAO;
import com.ppsea.ds.data.dao.EventResultPlayerDAOImpl;
import com.ppsea.ds.data.dao.EventResultSkillDAO;
import com.ppsea.ds.data.dao.EventResultSkillDAOImpl;
import com.ppsea.ds.data.dao.EventRouteDAO;
import com.ppsea.ds.data.dao.EventRouteDAOImpl;
import com.ppsea.ds.data.dao.EventSkillDAO;
import com.ppsea.ds.data.dao.EventSkillDAOImpl;
import com.ppsea.ds.data.model.Action;
import com.ppsea.ds.data.model.CityFacility;
import com.ppsea.ds.data.model.Event;
import com.ppsea.ds.data.model.EventAction;
import com.ppsea.ds.data.model.EventExtra;
import com.ppsea.ds.data.model.EventExtraDesc;
import com.ppsea.ds.data.model.EventFacility;
import com.ppsea.ds.data.model.EventItem;
import com.ppsea.ds.data.model.EventMonster;
import com.ppsea.ds.data.model.EventPlayer;
import com.ppsea.ds.data.model.EventResult;
import com.ppsea.ds.data.model.EventResultItem;
import com.ppsea.ds.data.model.EventResultPlayer;
import com.ppsea.ds.data.model.EventResultSkill;
import com.ppsea.ds.data.model.EventRoute;
import com.ppsea.ds.data.model.EventSkill;
import com.ppsea.ds.exception.PpseaException;

/**
 * 随机事件加载器
 * 
 * */
public class EventMG {
	private static final Logger log = Logger.getLogger(MapMG.class);
	public static EventMG instance = new EventMG();
	
	private static Map<Integer, List<EventResult>> eventResults = new HashMap<Integer, List<EventResult>>();
	private static Map<Integer, List<EventResultPlayer>> eventResultPlayers = new HashMap<Integer, List<EventResultPlayer>>();
	private static Map<Integer, List<EventResultItem>> eventResultItems = new HashMap<Integer, List<EventResultItem>>();
	private static Map<Integer, List<EventResultSkill>> eventResultSkills = new HashMap<Integer, List<EventResultSkill>>();
	private static Map<Integer, List<EventPlayer>> eventPlayers = new HashMap<Integer, List<EventPlayer>>();
	private static Map<Integer, List<EventMonster>> eventMonsters = new HashMap<Integer, List<EventMonster>>();
	private static Map<Integer, List<EventItem>> eventItems = new HashMap<Integer, List<EventItem>>();
	private static Map<Integer, List<EventSkill>> eventSkills = new HashMap<Integer, List<EventSkill>>();
	private static Map<Integer, List<EventAction>> eventActions = new HashMap<Integer, List<EventAction>>();
	private static Map<Integer, List<EventFacility>> eventFacilities = new HashMap<Integer, List<EventFacility>>();
	private static Map<Integer, List<EventRoute>> eventRoutes = new HashMap<Integer, List<EventRoute>>();
	private static Map<Integer, List<EventExtra>> eventExtras = new HashMap<Integer, List<EventExtra>>();
	private static Map<Integer, List<EventExtraDesc>> eventExtraDescs = new HashMap<Integer, List<EventExtraDesc>>();

	private Map<Integer, List<Integer>> monsterEvent = new HashMap<Integer, List<Integer>>();
	private Map<Integer, Event> randomEvents = new HashMap<Integer, Event>();
	private Map<Integer, Integer> facilityEvents = new HashMap<Integer, Integer>();
	private Map<Integer, Integer> routeEvents = new HashMap<Integer, Integer>();

	public void init() throws PpseaException{
		// 事件 -- 结果
		try {
			log.info("init: 事件 -- 结果");
			EventResultDAO dao = (EventResultDAO) DBManager.getDataDao(EventResultDAOImpl.class);
			Collection<EventResult> c = dao.selectByExample(null);
			for (EventResult er : c) {
				List<EventResult> list = eventResults.get(er.getEventId());
				if (list == null)
					list = new ArrayList<EventResult>();
				list.add(er);

				eventResults.put(er.getEventId(), list);
			}
		} catch (Exception e) {
			log.error(e);
		}

		// 事件 -- 玩家属性
		try {
			log.info("init: 事件 -- 玩家属性");
			EventResultPlayerDAO dao = (EventResultPlayerDAO) DBManager.getDataDao(EventResultPlayerDAOImpl.class);

			Collection<EventResultPlayer> c = dao.selectByExample(null);
			for (EventResultPlayer erp : c) {
				List<EventResultPlayer> list = eventResultPlayers.get(erp.getEventResultId());
				if (list == null)
					list = new ArrayList<EventResultPlayer>();
				list.add(erp);

				eventResultPlayers.put(erp.getEventResultId(), list);
			}
		} catch (Exception e) {
			log.error(e);
		}

		// 事件 -- 奖励道具
		try {
			log.info("init: 事件 -- 奖励道具");
			EventResultItemDAO dao = (EventResultItemDAO) DBManager.getDataDao(EventResultItemDAOImpl.class);

			Collection<EventResultItem> c = dao.selectByExample(null);
			for (EventResultItem eri : c) {
				List<EventResultItem> list = eventResultItems.get(eri.getEventResultId());
				if (list == null)
					list = new ArrayList<EventResultItem>();
				list.add(eri);

				eventResultItems.put(eri.getEventResultId(), list);
			}
		} catch (Exception e) {
			log.error(e);
		}

		// 事件 -- 奖励技能
		try {
			log.info("init: 事件 -- 奖励技能");
			EventResultSkillDAO dao = (EventResultSkillDAO) DBManager.getDataDao(EventResultSkillDAOImpl.class);

			Collection<EventResultSkill> c = dao.selectByExample(null);
			for (EventResultSkill eri : c) {
				List<EventResultSkill> list = eventResultSkills.get(eri.getEventResultId());
				if (list == null)
					list = new ArrayList<EventResultSkill>();
				list.add(eri);

				eventResultSkills.put(eri.getEventResultId(), list);
			}
		} catch (Exception e) {
			log.error(e);
		}

		// 事件 -- 玩家状态
		try {
			log.info("init: 事件 -- 玩家状态");
			EventPlayerDAO dao = (EventPlayerDAO) DBManager.getDataDao(EventPlayerDAOImpl.class);

			Collection<EventPlayer> c = dao.selectByExample(null);
			for (EventPlayer eri : c) {
				List<EventPlayer> list = eventPlayers.get(eri.getEventId());
				if (list == null)
					list = new ArrayList<EventPlayer>();
				list.add(eri);

				eventPlayers.put(eri.getEventId(), list);
			}
		} catch (Exception e) {
			log.error(e);
		}

		// 事件 -- 关联的怪
		try {
			log.info("init: 事件 -- 关联的怪");
			EventMonsterDAO dao = (EventMonsterDAO) DBManager.getDataDao(EventMonsterDAOImpl.class);

			Collection<EventMonster> c = dao.selectByExample(null);
			for (EventMonster eri : c) {
				List<EventMonster> list = eventMonsters.get(eri.getEventId());
				if (list == null)
					list = new ArrayList<EventMonster>();
				list.add(eri);

				eventMonsters.put(eri.getEventId(), list);
			}
		} catch (Exception e) {
			log.error(e);
		}

		// 事件 -- 关联的道具
		try {
			log.info("init: 事件 -- 关联的道具");
			EventItemDAO dao = (EventItemDAO) DBManager.getDataDao(EventItemDAOImpl.class);

			Collection<EventItem> c = dao.selectByExample(null);
			for (EventItem eri : c) {
				List<EventItem> list = eventItems.get(eri.getEventId());
				if (list == null)
					list = new ArrayList<EventItem>();
				list.add(eri);

				eventItems.put(eri.getEventId(), list);
			}
		} catch (Exception e) {
			log.error(e);
		}

		// 事件 -- 关联的技能
		try {
			log.info("init: 事件 -- 关联的技能");
			EventSkillDAO dao = (EventSkillDAO) DBManager.getDataDao(EventSkillDAOImpl.class);

			Collection<EventSkill> c = dao.selectByExample(null);
			for (EventSkill eri : c) {
				List<EventSkill> list = eventSkills.get(eri.getEventId());
				if (list == null)
					list = new ArrayList<EventSkill>();
				list.add(eri);

				eventSkills.put(eri.getEventId(), list);
			}
		} catch (Exception e) {
			log.error(e);
		}

		// 事件 -- 关联的动作
		try {
			log.info("init: 事件 -- 关联的动作");
			EventActionDAO dao = (EventActionDAO) DBManager.getDataDao(EventActionDAOImpl.class);

			Collection<EventAction> c = dao.selectByExample(null);
			for (EventAction eri : c) {
				List<EventAction> list = eventActions.get(eri.getEventId());
				if (list == null)
					list = new ArrayList<EventAction>();
				list.add(eri);

				eventActions.put(eri.getEventId(), list);
			}
		} catch (Exception e) {
			log.error(e);
		}

		// 事件 -- 关联的设施
		try {
			log.info("init: 事件 -- 关联的设施");
			EventFacilityDAO dao = (EventFacilityDAO) DBManager.getDataDao(EventFacilityDAOImpl.class);

			Collection<EventFacility> c = dao.selectByExample(null);
			for (EventFacility eri : c) {
				List<EventFacility> list = eventFacilities.get(eri.getCityfacilityId());
				if (list == null)
					list = new ArrayList<EventFacility>();
				list.add(eri);

				eventFacilities.put(eri.getCityfacilityId(), list);
			}
		} catch (Exception e) {
			log.error(e);
		}

		// 事件 -- 关联的航道
		try {
			log.info("init: 事件 -- 关联的航道");
			EventRouteDAO dao = (EventRouteDAO) DBManager.getDataDao(EventRouteDAOImpl.class);

			Collection<EventRoute> c = dao.selectByExample(null);
			for (EventRoute eri : c) {
				List<EventRoute> list = eventRoutes.get(eri.getRouteId());
				if (list == null)
					list = new ArrayList<EventRoute>();
				list.add(eri);

				eventRoutes.put(eri.getRouteId(), list);
			}
		} catch (Exception e) {
			log.error(e);
		}

		// 事件 -- 钓鱼和潜水的动作
		try {
			log.info("init: 事件 -- 钓鱼和潜水的动作");
			EventExtraDAO dao = (EventExtraDAO) DBManager.getDataDao(EventExtraDAOImpl.class);

			Collection<EventExtra> c = dao.selectByExample(null);
			for (EventExtra eri : c) {
				List<EventExtra> list = eventExtras.get(eri.getEventId());
				if (list == null)
					list = new ArrayList<EventExtra>();
				list.add(eri);

				eventExtras.put(eri.getEventId(), list);
			}
		} catch (Exception e) {
			log.error(e);
		}

		// 事件 -- 钓鱼和潜水的概率和描述
		try {
			log.info("init: 事件 -- 钓鱼和潜水的概率和描述");
			EventExtraDescDAO dao = (EventExtraDescDAO) DBManager.getDataDao(EventExtraDescDAOImpl.class);

			Collection<EventExtraDesc> c = dao.selectByExample(null);
			for (EventExtraDesc eri : c) {
				List<EventExtraDesc> list = eventExtraDescs.get(eri.getEventExtraId());
				if (list == null)
					list = new ArrayList<EventExtraDesc>();
				list.add(eri);

				eventExtraDescs.put(eri.getEventExtraId(), list);
			}
		} catch (Exception e) {
			log.error(e);
		}		

		Collection<List<EventMonster>> c = eventMonsters.values();

		for (List<EventMonster> list : c) {
			for (EventMonster mm : list) {
				List<Integer> ll = monsterEvent.get(mm.getMonsterId());
				if (ll == null)
					ll = new ArrayList<Integer>();

				ll.add(mm.getEventId());
				monsterEvent.put(mm.getMonsterId(), ll);
			}
		}
		
		EventDAO dao = (EventDAO) DBManager.getDataDao(EventDAOImpl.class);

		try {
			Collection<Event> c1 = dao.selectByExample(null);

			for (Event re : c1) {
				loadEventResults(re);
				loadEventActions(re);
				loadEventExtras(re);

				re.setEventPlayers(eventPlayers.get(re.getId()));
				re.setEventMonsters(eventMonsters.get(re.getId()));
				re.setEventSkills(eventSkills.get(re.getId()));
				re.setEventItems(eventItems.get(re.getId()));
				re.setEventExtras(eventExtras.get(re.getId()));

				randomEvents.put(re.getId(), re);
			}

		} catch (SQLException e) {
			log.error("exception", e);
		}
	}
	
	public List<Integer> getEventIdByMonster(Integer monsterId) {
		return monsterEvent.get(monsterId);
	}
	
	public Event getRandomEvent(int id) {
		return this.randomEvents.get(id);
	}

	public void addFacilityEvent(Integer facId, Integer eventId) {
		facilityEvents.put(facId, eventId);
	}

	public void addRouteEvent(Integer facId, Integer eventId) {
		routeEvents.put(facId, eventId);
	}

	public boolean hasFacilityEvent(Integer facId) {
		return facilityEvents.get(facId) == null ? false : true;
	}

	public boolean hasRouteEvent(Integer facId) {
		return routeEvents.get(facId) == null ? false : true;
	}
	
	public Map<Integer, List<EventRoute>> getEventRoutes(){
		return eventRoutes;
	}
	
	/**
	 * 事件结果
	 */
	private void loadEventResults(Event event) {
		Collection<EventResult> c = eventResults.get(event.getId());
		if (c == null)
			return;

		for (EventResult er : c) {
			er.setErPlayers(eventResultPlayers.get(er.getId()));
			er.setErItems(eventResultItems.get(er.getId()));
			er.setErSkills(eventResultSkills.get(er.getId()));

			event.getResults().add(er);
		}
	}

	/**
	 * 事件动作
	 */
	private void loadEventActions(Event event) {

		try {
			Collection<EventAction> c = eventActions.get(event.getId());

			if (c != null) {
				for (EventAction ea : c) {
					event.getActions().add(MissionMG.instance.getAction(ea.getActionId()));
				}
			}

			if (event.getCityfacilityId() != null && event.getCityfacilityId() > 0) {
				if (event.getOmit()) {
					Action action = new Action();
					action.setName("继续");
					action.setCommand(Command.COMMAND_MOVE_GO);

					event.getActions().add(action);
				}

				Action action = new Action();
				action.setName((event.getWalkAlias() == null || event.getWalkAlias().equals("")) ? "攻击" : event.getWalkAlias());
				action.setCommand(Command.COMMAND_WALK);
				action.setParam(String.valueOf(event.getCityfacilityId()));

				event.getActions().add(action);
			}
		} catch (Exception e) {
			log.error("exception", e);
		}
	}

	/**
	 * 事件概率和描述
	 */
	private void loadEventExtras(Event event) {
		Collection<EventExtra> c = eventExtras.get(event.getId());
		if (c == null)
			return;

		List<EventExtra> list = new ArrayList<EventExtra>();

		for (EventExtra er : c) {
			er.setEventExtraDescs(eventExtraDescs.get(er.getId()));

			list.add(er);
		}

		event.setEventExtras(list);
	}
	
	public void loadCityFacilityEvents(CityFacility cf){
		// Load events
		Collection<EventFacility> c1 = eventFacilities.get(cf.getId());

		if (c1 != null) {
			for (EventFacility re : c1) {
				Event event = getRandomEvent(re.getEventId());
				cf.getEvents().add(event);

				addFacilityEvent(event.getCityfacilityId(), event.getId());

				// 
				if (event.getEventExtras() != null) {
					Action action = new Action();
					action.setName("挖掘");
//					action.setCommand(Command.COMMAND_DIG);
					action.setParam(String.valueOf(EventExtra.TYPE_CONTINUE));

					cf.getActions().add(action);
				}
			}
		}

	}
}

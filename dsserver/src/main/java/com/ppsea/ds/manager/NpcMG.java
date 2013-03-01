package com.ppsea.ds.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.dao.NpcMissionDAO;
import com.ppsea.ds.data.dao.NpcMissionDAOImpl;
import com.ppsea.ds.data.model.CityFacility;
import com.ppsea.ds.data.model.CityFacilityNpc;
import com.ppsea.ds.data.model.Mission;
import com.ppsea.ds.data.model.MissionPre;
import com.ppsea.ds.data.model.Npc;
import com.ppsea.ds.data.model.NpcMission;
import com.ppsea.ds.data.model.NpcRandomWords;

/**
 * Npc加载器
 * 
 * */
public class NpcMG {
	private static final Logger log = Logger.getLogger(NpcMG.class);
	public static NpcMG instance = new NpcMG(); 
	private Map<Integer, Npc> npcCache = new ConcurrentHashMap<Integer, Npc>();
	private Map<Integer, Map<Integer, CityFacilityNpc>> cityFacilityNpcCache = new ConcurrentHashMap<Integer, Map<Integer, CityFacilityNpc>>();
	private Map<Integer, List<NpcMission>> npcMissions = new ConcurrentHashMap<Integer, List<NpcMission>>();
	private Map<Integer, Integer> missionNpcs = new ConcurrentHashMap<Integer, Integer>();
	private Map<Integer, Integer> npcCityFacilityIds = new ConcurrentHashMap<Integer, Integer>();
	private Map<Integer, Npc> cityFacilityNpcs = new ConcurrentHashMap<Integer, Npc>();
	
	private List<NpcRandomWords> npcRandomWords = new ArrayList<NpcRandomWords>();
		
	public void init(){
		loadNpc();
		
		// NPC -- 任务
		try {
			log.info("init: NPC -- 任务");
			NpcMissionDAO dao = (NpcMissionDAO) DBManager.getDataDao(NpcMissionDAOImpl.class);
			Collection<NpcMission> c = dao.selectByExample(null);

			for (NpcMission nq : c) {
				List<NpcMission> list = npcMissions.get(nq.getCityfacilitynpcId());
				if (list == null)
					list = new ArrayList<NpcMission>();
				list.add(nq);

				npcMissions.put(nq.getCityfacilitynpcId(), list);

				if (missionNpcs.get(nq.getMissionId()) == null)
					missionNpcs.put(nq.getMissionId(), nq.getCityfacilitynpcId());
			}
		} catch (Exception e) {
			log.error("exception", e);
		}
		
		loadCityFacilityNpc();
		loadNpcRandomWords();
	}
	
	private void loadNpc(){
		List<Npc> list = DBManager.queryAllNpc();
		for(Npc npc:list){
//			log.info("npc="+npc.getName());
			npc.compile();
			npcCache.put(npc.getId(), npc);
		}
	}
	
	/**
	 * 
	 */
	private void loadCityFacilityNpc(){
		//在驿站设置出城和传送
		Npc chucheng = NpcMG.instance.getNpc(Constants.NPC_CHUCHENG_ID);
		Npc chuansong = NpcMG.instance.getNpc(Constants.NPC_CHUANSONG_ID);
//		Npc taishou = NpcMG.instance.getNpc(311);//太守NPCID.
		
		for(CityFacility cf: MapMG.instance.getCityFacilityCache().values()){
			if(cf.getFacilityId() == Constants.STATION_ID){
				Map<Integer, CityFacilityNpc> npcList = loadNpcInFacility(cf.getId());
				//设置出城npc
				CityFacilityNpc cfnCC = new CityFacilityNpc();
				cfnCC.setCityfacilityId(cf.getId());
				cfnCC.setNpc(chucheng);
				cfnCC.setNpcId(chucheng.getId());
				npcList.put(chucheng.getId(),  cfnCC);
				//设置传送npc
				CityFacilityNpc cfnCS = new CityFacilityNpc();
				cfnCS.setCityfacilityId(cf.getId());
				cfnCS.setNpc(chuansong);
				cfnCS.setNpcId(chuansong.getId());
				npcList.put(chuansong.getId(),  cfnCS);
			}
//			if (cf.getFacilityId() == Constants.BORN_FACILITY_ID) {
//				
//				Map<Integer, CityFacilityNpc> npcList = loadNpcInFacility(cf.getId());
//				//设置攻守城NPC
//				CityFacilityNpc cfNpc = new CityFacilityNpc();
//				cfNpc.setCityfacilityId(cf.getId());
//				cfNpc.setNpc(taishou);
//				cfNpc.setNpcId(taishou.getId());
//				npcList.put(taishou.getId(),  cfNpc);
//			}
		}
		
		List<CityFacilityNpc> list = DBManager.queryAllCityFacilityNpc();
		for(CityFacilityNpc cfn:list){
			Npc npc = (Npc) npcCache.get(cfn.getNpcId()).clone();
//			log.info("[NPC]Loading " + npc.getName()+":"+ cfn.getCityfacilityId());

			npc.setMissions(loadNpcMissions(cfn.getId()));
			npc.setUuid(cfn.getId());
			npc.setFacility(MapMG.instance.getCityFacility(cfn.getCityfacilityId()).getFacility());
			npc.setStartMissions(new ArrayList<Mission>(1));
			
			Collection<Mission> c = npc.getMissions().values();
			for(Mission mission : c){
				if(mission.getPreMissions().size() == 0)
					npc.getStartMissions().add(mission);
			}

			if (cfn.getAlias() != null && !cfn.getAlias().equals(""))
				npc.setName(cfn.getAlias());
			
			cfn.setNpc(npc);
			Map<Integer, CityFacilityNpc> npcList = loadNpcInFacility(cfn.getCityfacilityId());
			npcList.put(npc.getId(), cfn);
			npcCityFacilityIds.put(cfn.getId(), cfn.getCityfacilityId());
			cityFacilityNpcs.put(cfn.getId(), npc);
		}		
	}
	
	public void loadNpcRandomWords() {
		npcRandomWords = DBManager.queryNpcRandomWords();
	}
	
	public NpcRandomWords getNpcRandomWords() {
		try {
			Random rand = new Random();
			int index = rand.nextInt(npcRandomWords.size());
			return npcRandomWords.get(index);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Map<Integer, CityFacilityNpc> getNpcInFacility(int cityFacilityId){
		Map<Integer, CityFacilityNpc> npcMap = cityFacilityNpcCache.get(cityFacilityId);
		
		return npcMap;
	}
	
	private Map<Integer, CityFacilityNpc> loadNpcInFacility(int cityFacilityId) {
		Map<Integer, CityFacilityNpc> npcMap = getNpcInFacility(cityFacilityId);
		if(npcMap == null){
			npcMap = new LinkedHashMap<Integer, CityFacilityNpc>();
			cityFacilityNpcCache.put(cityFacilityId, npcMap);
		}
		return npcMap;
	}
	
	
	/**
	 * 返回指定ID的NPC
	 **/

	public Npc getNpc(int npcId){
		return npcCache.get(npcId);
	}

	private Map<String, Mission> loadNpcMissions(int npcId) {
		Map<String, Mission> map = new LinkedHashMap<String, Mission>();

		Collection<NpcMission> c = npcMissions.get(npcId);
		if (c == null)
			return map;

		// 任务按顺序排列
		List<Mission> list = new LinkedList<Mission>();
		for (NpcMission na : c) {
			//log.info("na.getMissionId()="+na.getMissionId());
			Mission mission = MissionMG.instance.getMission(na.getMissionId());
			if (mission.getPreMissions() == null) {
				mission.setPreMissions(loadPreMissions(mission.getId()));

				list.add(mission);
			} else {
				list.add(mission);
			}
		}

		for (Mission mission : list) {
			if (mission.getPreMissions() != null && mission.getPreMissions().size() > 0) {
				Mission m = mission.getPreMissions().get(0);

				if (list.contains(m))
					map.put(String.valueOf(m.getId()), m);
			}

			if (!map.containsKey(String.valueOf(mission.getId())))
				map.put(String.valueOf(mission.getId()), mission);
		}

		return map;
	}
	
	/**
	 * 前一个任务的限制
	 */
	private List<Mission> loadPreMissions(int fromMissionId) {
		List<Mission> list = new ArrayList<Mission>();

		Collection<MissionPre> c = MissionMG.instance.getPreMissions().get(fromMissionId);

		if (c == null)
			return list;

		for (MissionPre mp : c) {
			Mission mission = MissionMG.instance.getMission(mp.getToMissionId());
			list.add(mission);
		}

		return list;
	}
	
	/**
	 * 任务相关的发布NPC
	 */
	public Map<Integer, Integer> getMissionNpcs() {
		return missionNpcs;
	}
	
	/**
	 * 通过NPC获取所在设施
	 * 
	 * @param cityFacilityNpcId
	 * @return
	 */
	public CityFacility findCityFacilityByNpc(Integer cityFacilityNpcId){
		Integer cfId = npcCityFacilityIds.get(cityFacilityNpcId);
		
		return MapMG.instance.getCityFacility(cfId);
	}
	
	public Npc findNpcByCityFacilityNpc(Integer cityFacilityNpcId){
		return cityFacilityNpcs.get(cityFacilityNpcId);
	}
}

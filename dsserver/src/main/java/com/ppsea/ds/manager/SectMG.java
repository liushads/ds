package com.ppsea.ds.manager;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.Sect;
import com.ppsea.ds.data.model.SectTitle;

/**
 * 门派资源管理器，包含武功，招式，心法等数据
 * */
public class SectMG {
	
	/**
	 * Log 
	 **/
	private static final Logger log = Logger.getLogger(SectMG.class);
	public static SectMG instance = new SectMG(); 
	private List<Sect> sectList = null;
	/**
	 * 门派称号
	 */
	private Map<Integer, List<SectTitle>> sectTitleMap = new HashMap<Integer, List<SectTitle>>();
	
	public static int LEVEL_ZL = 9;
	public static int LEVEL_ZZ = 8;
	public static int LEVEL_HF = 7;
	public static int LEVEL_SZ = 6;
	
	
	/**
	 * 门派擅长武器
	 * index=sectId,value=arm
	 **/
	private String[] sectAdeptArray = {"扇","刀","剑","刺","棍"};
	
	
	
	
	//player level-gold
	private Map<Integer, Integer> skillGoldMap = new HashMap<Integer, Integer>();
	
	/**
	 * 初始化门派资源 
	 **/
	public void init(){
		log.info("[SectMG]{start initialization sect resource....}");
		initSkillGoldMap();
		sectList = DBManager.queryAllSect();
		log.warn("门派列表:");
		for(Sect sect:sectList){
			log.warn(sect.getName());
		}
		
		//加载门派称谓
		List<SectTitle> sectTitleList = DBManager.queryAllSectTitle();
		for(SectTitle st: sectTitleList){
			List<SectTitle> ls = sectTitleMap.get(st.getSectId());
			if(ls == null){
				ls = new LinkedList<SectTitle>();
				sectTitleMap.put(st.getSectId(), ls);
			}
			ls.add(st);
		}
	}
	
	private void initSkillGoldMap() {
		int gold = 20;
		for (int level = 10; level <= 150;) {
			skillGoldMap.put(Integer.valueOf(level), Integer.valueOf(gold));
			level += 5;
			if (level % 10 == 0) {
				gold += 10;
			}
		}
	}
	
	public int getSkillGold(int level) {
		return skillGoldMap.get(level);
	}
	
	public void getSkillGoldList(Player player) {
		player.getLevel();
		for (Map.Entry<Integer, Integer> entry : skillGoldMap.entrySet()) {
			entry.getKey();
		}
	}
	
	
	
	public List<Sect> getSectList(){
		return sectList;
	}
	
	/**
	 * 根据门派Id的得到门派对象
	 * @param sectId
	 * @return
	 */
	public Sect getSect(int sectId){
		for(Sect sect:sectList){
			if(sect.getId() == sectId){
				return sect;
			}
		}
		return null;
	} 
	
	
	public SectTitle getTitle(int sectId, int sectLevel){
		List<SectTitle> ls = sectTitleMap.get(sectId);
		if(ls == null){
			return null;
		}
		
		for(SectTitle st:ls){
			if(sectLevel == st.getSectLevel()){
				return st;
			}
		}
		return null;
		
	}
	
	public List<SectTitle> getTitles(int sectId){
		return sectTitleMap.get(sectId);
	}

	/**
	 * 返回指定门派需要的武器名称
	 * @param sectId
	 **/
	public String getRequireArm(int sectId){
		if(sectId <= 0 || sectId > sectAdeptArray.length)return "";
		return sectAdeptArray[sectId-1];
	}
}
  
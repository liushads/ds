package com.ppsea.ds.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ppsea.ds.data.model.Rank;
import com.ppsea.ds.service.RankService;

/**
 * 排名内存
 * 
 * */
public class RankMG {
	
	public final static String TONG = "tong";
	
	public static RankMG instance = new RankMG();
	
	private Map<String,List> rankMap = new HashMap<String,List>();
	
	private RankMG(){
	}
	
	/**
	 * 初始化排行榜
	 */
	public void init(){
		initRank();
	}
	private void initRank(){
		rankMap.clear();
		List<Rank> ranks = DBManager.queryAllRank();
		if (ranks != null) {
			for(Rank rank : ranks){
				String key = String.valueOf(rank.getType());
				if(rankMap.containsKey(key)){
					rankMap.get(key).add(rank);
				}
				else{
					List rs = new ArrayList();
					rs.add(rank);
					rankMap.put(key, rs);
				}
			}
		}
	}
	
	public List getRank(String key){
		return (List)rankMap.get(key);
	}
	
	public void load(){
	}
	
	public Map<String,List> getRankMap(){
		return rankMap;
	}
	
}


package com.ppsea.ds.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ppsea.ds.data.model.City;
import com.ppsea.ds.data.model.Rank;
import com.ppsea.ds.manager.DBManager;
import com.ppsea.ds.manager.MapMG;
import com.ppsea.ds.manager.RankMG;

/**
 * 
 * 排行榜定业务处理
 * 
 */
public class RankService {

	private static Logger log = Logger.getLogger(RankService.class);
	public RankService() {
	}
	
	/**
	 * 帮会排行
	 */
	/*
	public static List<Tong> tongs(){
		
		Map<Integer,String> tongMap = new HashMap<Integer,String>();
		List<City> cities = MapMG.instance.getcinstancegetCities();

		for(City city:cities){
			if (city.getTongId()>0){
				Tong tong = TongSet.getInstance().getTong(city.getTongId());
				if(tong!=null){
					if(tongMap.containsKey(tong.getId())){
						tongMap.put(tong.getId(),tong.getId()+"_"+(Integer.parseInt(tongMap.get(tong.getId()).split("_")[1])+1));
					}
					else{
						tongMap.put(tong.getId(),tong.getId()+"_1");				
					}
				}
			}
		}	
		
		// 对帮会进行排序
		List sortList = new ArrayList();
        sortList.addAll(tongMap.values());
		
		Collections.sort(sortList, new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				String a = (String)o1;
				String b = (String)o2;
				return Integer.parseInt(a.split("_")[1])-Integer.parseInt(b.split("_")[1]);
			}
		});	
		List<Tong> tongList = new ArrayList<Tong>();
		for(int i=sortList.size()-1;i>=0;i--){
			Tong tong = TongSet.getInstance().getTong(Integer.valueOf(sortList.get(i).toString().split("_")[0]));
			tongList.add(tong);
		}
		return tongList; 
	}
	 */
	
}



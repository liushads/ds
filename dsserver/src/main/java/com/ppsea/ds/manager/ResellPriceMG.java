package com.ppsea.ds.manager;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.ppsea.ds.data.model.ResellPrice;


/**
 * 倒卖静态配置
 * @author xl
 *
 */
public class ResellPriceMG {
	private static final Logger log = Logger.getLogger(ResellPriceMG.class);
	public static ResellPriceMG instance = new ResellPriceMG(); 
	private Map<Integer, List<ResellPrice>> typeMap = new ConcurrentHashMap<Integer, List<ResellPrice>>();
	private Map<Integer, ResellPrice> idMap = new ConcurrentHashMap<Integer, ResellPrice>();
	private List<ResellPrice> idList = new LinkedList<ResellPrice>();
	
	public void init(){
		loadResellPrice();
	}
	private void loadResellPrice(){
		List<ResellPrice> list = DBManager.queryAllResellPrice();
		for (ResellPrice rp : list) {
			if(typeMap.containsKey(rp.getItemType())){
				typeMap.get(rp.getItemType()).add(rp);
			}else{
				List<ResellPrice> ls = new LinkedList<ResellPrice>();
				ls.add(rp);
				typeMap.put(rp.getItemType(), ls);
			}
			idMap.put(rp.getId(), rp);
			idList.add(rp);
		}
	}
	
	public List<ResellPrice> getAllPrice(){
		return idList;
	}
	
	public List<ResellPrice> getPriceListByType(int type){
		return typeMap.get(type);
	}
	
	public int getResellGoodType(int id){
	    return idMap.get(id).getItemType();
	}
	
	public ResellPrice getResellPrice(int id){
		return idMap.get(id);
	}
}

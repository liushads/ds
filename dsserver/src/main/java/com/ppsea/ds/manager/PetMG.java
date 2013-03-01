package com.ppsea.ds.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.ppsea.ds.data.model.Pet;
import com.ppsea.ds.data.model.PetLevel;
import com.ppsea.ds.data.model.PetTalent;

/**
 * 宠物管理器 
 **/
public class PetMG {
	
	/**
	 * 宠物最大开放等级 
	 **/
	public static int MAX_PET_LEVEL = 150;
	public static int MAX_EXP;
	
	/**
	 * Log 
	 **/
	private static final Logger log = Logger.getLogger(PetMG.class);
	public static PetMG instance = new PetMG(); 
	
	/**
	 * 宠物资源 key=sect,value=pet 
	 **/
	private Map<Integer,List<Pet>> petCacheMap = new ConcurrentHashMap<Integer,List<Pet>>();
	private Map<Integer,Pet> petCache = new ConcurrentHashMap<Integer,Pet>();
	
	/**
	 * 宠物天赋 key=sect,value=talent 
	 **/
	private Map<Integer,List<PetTalent>> petTalentMap = new ConcurrentHashMap<Integer,List<PetTalent>>(); 
	private Map<Integer,PetTalent> petTalentCache = new ConcurrentHashMap<Integer,PetTalent>();
	private int talentCount = 0;
	
	/**
	 * 宠物等级缓存
	 * index=level 
	 **/
	private List<PetLevel> petLevelCache;
	
	/**
	 * 初始化,加载宠物资源和宠物的天赋资源 
	 **/
	public void init(){
		//宠物
		log.info("{load all pet resources into memory ...}");
		List<Pet> petList = DBManager.queryAllPet();
		for(Pet pet : petList){
			petCache.put(pet.getId(), pet);
			List<Pet> pets = petCacheMap.get(pet.getSectId());
			if(pets == null){
				pets = new ArrayList<Pet>(4);
				petCacheMap.put(pet.getSectId(), pets);
			}
			pets.add(pet);
			log.info("{"+pet.getName()+"}");
		}
		petList.clear();
		petList = null;
		//天赋
		log.info("{load all pet talent resources into memory ...}");
		List<PetTalent> petTalentList = DBManager.queryAllPetTalent();
		for(PetTalent talent : petTalentList){
			petTalentCache.put(talent.getId(), talent);
			List<PetTalent> talentList = petTalentMap.get(talent.getSectId());
			if(talentList == null){
				talentList = new ArrayList<PetTalent>(petTalentList.size()/4);
				petTalentMap.put(talent.getSectId(), talentList);
			}
			talentList.add(talent);
			talentCount ++;			//计数器
			log.info("{"+talent.getName()+"}");
		}
		petTalentList.clear();
		petTalentList = null;
		//等级
		log.info("{load all pet level resources into memory ...}");
		List<PetLevel> levelLst = DBManager.queryAllPetLevel();
		//初始化缓存
		petLevelCache = new ArrayList<PetLevel>(levelLst.size());
		for(int i=0;i<levelLst.size();i++){
			petLevelCache.add(i,null);
		}
		for(PetLevel level : levelLst){
			petLevelCache.add(level.getLevel(), level);
		}
		levelLst.clear();
		levelLst = null;
		//最大经验
		MAX_EXP = petLevelCache.get(MAX_PET_LEVEL).getMaxExp();
		
	}
	
	/**
	 * 返回指定ID的宠物资源
	 * @param id 宠物ID 
	 **/
	public Pet getPet(int id){
		return petCache.get(id);
	}
	
	/**
	 * 获取适合自己门派的所有宠物列表
	 * @param sectId 门派ID
	 **/
	public List<Pet> getPetBySect(int sectId){
		return petCacheMap.get(sectId);
	}
	
	/**
	 * 获取不适合自己门派的所有宠物列表
	 * @param sectId 门派ID
	 **/
	public List<Pet> getPetExceptSect(int sectId){
		List<Pet> selfList = petCacheMap.get(sectId);
		List<Pet> exceptList = new ArrayList<Pet>(petCacheMap.size()-selfList.size());
		for(Integer sect : petCacheMap.keySet()){
			if(sectId == sect.intValue())continue;//跳过自己门派的
			exceptList.addAll(petCacheMap.get(sect));
		}
		return exceptList;
	}
	
	/**
	 * 获取指定等级的宠物属性
	 * @param level 等级 
	 **/
	public PetLevel getPetLevel(int level){
		return petLevelCache.get(level);
	}
	
	/**
	 * 获取适合自己门派的宠物天赋列表
	 * @param sectId 门派ID
	 **/
	public List<PetTalent> getPetTalentBySect(int sectId){
		List<PetTalent> list = petTalentMap.get(sectId);
		return list;
	}
	
	/**
	 * 获取不适合自己门派宠物天赋
	 * @param sectId 门派ID
	 **/
	public List<PetTalent> getPetTalentExceptSect(int sectId){
		List<PetTalent> selfList = petTalentMap.get(sectId);
		List<PetTalent> exceptList = new ArrayList<PetTalent>();
		log.error("|enableTalent|getPetTalentExceptSect|"+sectId+"|");
		for(Integer sect : petTalentMap.keySet()){
			if(sectId != sect.intValue()) {
				List<PetTalent> talents = petTalentMap.get(sect);
				log.error("getPetTalentExceptSect|"+sect+"|"+talents.size());
				exceptList.addAll(talents);
			}
		}
		log.error("|enableTalent|getPetTalentExceptSect|"+sectId+"|"+exceptList.size());
		return exceptList;
	}
	
	/**
	 * 返回指定ID的天赋
	 * @param id 天赋 
	 **/
	public PetTalent getPetTalent(int id){
		return petTalentCache.get(id);
	}
}
  
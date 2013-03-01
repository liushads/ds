/**
 * 数据库递增逐渐产生器，用于一下几个表
 * player<br>
 * player_item<br>
 * player_ship<br>
 * tong<br>
 * */
package com.ppsea.ds.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.sql.rowset.CachedRowSet;

import org.apache.log4j.Logger;

import com.ppsea.ds.util.StringUtil;
import com.ppsea.ds.data.model.IdGenerator;
import com.ppsea.ds.manager.DBManager;
import com.ppsea.ds.service.DBService;

public class GlobalGenerator {
	
	private class Seg {
		public int segId = 0;
		public int initUsedNum = 0;
		public boolean allIsFree = false;
		public LinkedList<Integer> freeNodes = null;
	}
	
	private class IdResource {
		private int maxId = -1;
		private int step = 100000;
		private String tableName = "";
		private LinkedList<Seg> freeSeg = null;
		public void init(String tab) {
			tableName = tab;
			maxId = DBService.getOperaThread().getMaxId(tab);
			if (maxId == -1) {
				log.error("IdGenerator getMaxId fail, maxId==-1,tableName="+tableName);
			}
		}
		/**
		 * 
		 * @return
		 */
		public int getFirstReusedId() {
			if (maxId == -1) {
				log.error("IdGenerator getFirstReusedId fail, maxId==-1, tableName="+tableName);
				return 0;
			}
			synchronized (this) {
				if (freeSeg == null || freeSeg.size() == 0) {
					++maxId;
					log.debug("IDGEN|" + tableName + "|usemax:" + maxId);
					return maxId;
				}
			}
			Seg head = freeSeg.get(0);
			if (head != null && head.freeNodes != null) {
				synchronized (head) {
					if (head.freeNodes.size() > 0) {
						int newId = head.freeNodes.get(0);
						head.freeNodes.remove(0);
						if (head.freeNodes.size() == 0) {
							freeSeg.remove(0);
						}
						if (newId > maxId) {
							maxId = newId;
						}
						log.debug("IDGEN|" + tableName + "|seg:" + head.segId + "|reuseid:" + newId + "|freenum:" + head.freeNodes.size() + "|maxid:" + maxId);
						return newId;
					}
					else {
						freeSeg.remove(0);
					}
				}
			}
			synchronized (this) {
				++maxId;
			}
			return maxId;
		}
		/**
		 * 
		 */
		public void reload() {
			if (freeSeg == null) {
				freeSeg = new LinkedList<Seg>();
				try
				{
					String sql = "select floor(id/" + step + "),count(*) from " + tableName + " group by 1";
					CachedRowSet sets = DBService.getOperaThread().getDBEngine().executeQuery(sql);
					if (sets != null) {
						synchronized (freeSeg) {
							int lastSeg = 0;
							while (sets.next()) {
								int segId = sets.getInt(1);
								int count = sets.getInt(2);
								for (int i = lastSeg + 1; i < segId; ++i) {
									Seg g = new Seg();
									g.initUsedNum = 0;
									g.segId = i;
									g.allIsFree = true;
									freeSeg.add(g);
								}
								Seg g = new Seg();
								g.initUsedNum = count;
								g.segId = segId;
								g.allIsFree = false;
								freeSeg.add(g);
								lastSeg = segId;
							}
						}
					}
				}
				catch (Exception e) {
					log.error("exception", e);
				}
				log.debug("IDGEN|reload-create|" + tableName + "|" + freeSeg.size() + "|");
				return;
			}
			/////////////////////////////////////////////////////////////
			if (freeSeg.isEmpty()) {
				return;
			}
			Seg head = freeSeg.get(0);
			if (head != null) {
				if (head.freeNodes == null) {
					log.debug("IDGEN|reload-refill|" + tableName + "|" + freeSeg.size() + "|" + head.segId + "|");
					head.freeNodes = new LinkedList<Integer>(); 
					int beg = head.segId*step;
					int end = beg + step;
					if(maxId < end) {
						beg = maxId + 1;								
					}
					if (head.allIsFree == true) {
						synchronized (head) {
							for (int i = beg; i < end; ++i) {
								
								head.freeNodes.add(i);
							}
						}
					}
					else {
						try
						{
							String sql = "select id from " + tableName + " where id >= " + beg + " and id <" + end;
							CachedRowSet sets = DBService.getOperaThread().getDBEngine().executeQuery(sql);
							if (sets != null) {
								synchronized (head) {
									int lastId = beg;
									while (sets.next()) {
										int currId = sets.getInt("id");
										for (int i = lastId; i < currId; ++i) {
											head.freeNodes.add(i);
										}
										lastId = currId + 1;
									}
									for (int i = lastId; i < end; ++i) {
										head.freeNodes.add(i);
									}
								}
							}
						}
						catch (Exception e) {
							log.error("exception", e);
						}
					}
				}
			}
		}
	}
	public void start(){
		reloadTimer = new Timer();
		reloadTimer.schedule(new ReloadTask(), 3000, 3000);
	}
	
	private Integer getReusedIdForNewObj(String tableName) {
		return getReusedId(tableName);
	}
	/*
	public Integer getReusedIdForNewObj(Object obj) {
		return getReusedId(StringUtil.unix_simple_clazz(obj.getClass().getName()));
	}
	*/
	private Integer getReusedId(String tableName) {
		IdResource res = null;
		synchronized (idResource) {
			res = idResource.get(tableName);
			if (res == null) {
				res = new IdResource();
				res.tableName = tableName;
				res.init(tableName);
				idResource.put(tableName, res);
			}
			return res.getFirstReusedId();
		}
	}

	private class ReloadTask extends TimerTask
	{
		public void run() 
		{
			try
			{
				boolean needLog = false;
				if (System.currentTimeMillis() - lastDebugTime > 60*1000) {
					needLog = true;
					lastDebugTime = System.currentTimeMillis();
				}

				List<IdResource> newRes = new ArrayList<IdResource>();
				for (IdResource res : idResource.values()) {
					newRes.add(res);
				}
				// 列表会报ConcurrentModificationException,列表时候values个数会造成不一致的情况
			    for(IdResource res :newRes){
			    	if (needLog) {
			    		log.debug("IDGEN|ReloadTask|" + res.tableName + "|" + res.step + "|" + res.maxId + "|");
			    	}
			    	try	{
			    		res.reload();
			    	}
			    	catch (Exception e) {
			    		log.error("exception, GloablGenerator", e);
					}
			    	
			    } 	 
			}
			catch (Exception e) {
				log.error("exception, GloablGenerator2", e);
			}
		}
	}
	//////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	
	public static GlobalGenerator instance = null;
	
	private static final Logger log = Logger.getLogger("GENERATOR");
	private static Map<String, IdGenerator> idMap = new HashMap<String, IdGenerator>();
	private static Timer reloadTimer = null;
	private long lastDebugTime = 0;
	private static Map<String, IdResource> idResource = new HashMap<String, IdResource>();
	
	private GlobalGenerator(){
	}
	
	public static GlobalGenerator init() throws Exception{
		if(instance == null){
			instance = new GlobalGenerator();
		}
//		List<IdGenerator> list = DBManager.loadIdGenerator();
//		for(IdGenerator idGenerator:list){
//			idMap.put(idGenerator.getTableName(), idGenerator);
//		}
//		instance.getReusedId("player_item");
//		instance.getReusedId("player_item_append");
//		instance.getReusedId("player");
//		instance.getReusedId("player_mission");
//		instance.getReusedId("npc_interaction");
//		instance.getReusedId("player_pet");
//		instance.getReusedId("player_store_room");
//		instance.getReusedId("player_friend");
//		instance.getReusedId("player_item_using");
//		instance.getReusedId("player_enemy");
//		instance.getReusedId("player_active");
//		instance.getReusedId("coach");
//		instance.getReusedId("student");
//		instance.getReusedId("message");
//		instance.getReusedId("player_name_luck");
		instance.start();
		return instance;
	}
	
	public Integer getIdForNewObj(Object obj){
		return getReusedId(StringUtil.unix_simple_clazz(obj.getClass().getName()));
	}
	
	/*
	public Integer getId(String tableName){
		return getReusedId(tableName);
		
		IdGenerator idGenerator = idMap.get(tabelName);
		synchronized(idGenerator){
			Integer id = idGenerator.getSeqno();
			id++;
			
			//commit to db
			idGenerator.setSeqno(id);
			DBService.commit(idGenerator);
			
			return id;	
		}
	}
	*/
	public static void main(String[] args) {
		System.out.print(StringUtil.unix_simple_clazz("player_active".getClass().getName()));
	}
}
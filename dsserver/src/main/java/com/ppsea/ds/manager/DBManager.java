package com.ppsea.ds.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.GlobalGenerator;
import com.ppsea.ds.data.Path;
import com.ppsea.ds.data.dao.*;
import com.ppsea.ds.data.model.*;
import com.ppsea.ds.data.model.DartAward.DartItem;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.service.PresentService;
import com.ppsea.ds.service.SkillService;
import com.ppsea.ds.util.SqlMapConfig;
import com.ppsea.ds.util.SqlMapConfigData;

/**
 * 处理各种数据库查询请求
 * */
public class DBManager {
private static final Logger log = Logger.getLogger(DBManager.class);
	
	private static Map<String, Object> daoMap = new HashMap<String, Object>();
	private static Map<String, Object> daoDataMap = new HashMap<String, Object>();

	/**
	 *对应jianghu_main_xx 的dao
	 * @param clazz
	 * @return
	 */
	public static Object getDao(Class clazz) {
		Object o = daoMap.get(clazz.getName());

		if (o != null)
			return o;

		try {
			o = clazz.getConstructor(SqlMapClient.class).newInstance(SqlMapConfig.getSqlMapInstance());

			daoMap.put(clazz.getName(), o);

			return o;
		} catch (Exception e) {
			log.error("exception", e);
		}

		return null;
	}
	
	/**
	 * 对应jianghu_res库的dao
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object getDataDao(Class clazz) {
		Object o = daoDataMap.get(clazz.getName());

		if (o != null)
			return o;

		try {
			o = clazz.getConstructor(SqlMapClient.class).newInstance(SqlMapConfigData.getSqlMapInstance());

			daoDataMap.put(clazz.getName(), o);

			return o;
		} catch (Exception e) {
			log.error("exception", e);
		}

		return null;
	}
	
	/**
	 * 初始化id产生器的初始值
	 */
	@SuppressWarnings("unchecked")
	public static List<IdGenerator> loadIdGenerator() {
		try {
			IdGeneratorDAO dao = (IdGeneratorDAO) getDao(IdGeneratorDAOImpl.class);
			return dao.selectByExample(null);
		} catch (SQLException e) {
			log.error("exception", e);
		}
		return new LinkedList<IdGenerator>();
	}
	
	/**
	 * 从数据库查询player
	 */
	public static Player queryPlayer(int pid) {
		PlayerDAO playerDAO = (PlayerDAOImpl) getDao(PlayerDAOImpl.class);
		try {
			return playerDAO.selectByPrimaryKey(pid);
		} catch (Exception e) {
			log.error("exception:", e);
		}
		return null;
	}
	
	/**
	 * 该区总用户
	 */
	public static int queryTotalPlayer() {
		try {
			PlayerDAO dao = (PlayerDAO) getDao(PlayerDAOImpl.class);
			PlayerExample pe = new PlayerExample();
			return dao.countByExample(pe);
		} catch (SQLException e) {
			log.error("exception", e);
		}
		return 0;
	}
	
	/**
	 * 用户所有角色
	 * @param id user id
	 * 
	 * @return
	 * user 拥有的所有 player 列表
	 * */
	@SuppressWarnings("unchecked")
	public static List<Player> queryPlayerByUser(String id) {
		try {
			PlayerDAO dao = (PlayerDAO) getDao(PlayerDAOImpl.class);
			PlayerExample example = new PlayerExample();
			example.createCriteria().andUserIdEqualTo(id);
			return dao.selectByExample(example);
		} catch (SQLException e) {
			log.error("exception", e);
		}
		return new LinkedList<Player>();
	}
	
	@SuppressWarnings("unchecked")
	public static Player queryPlayer(String name){
		try {
			PlayerDAO dao = (PlayerDAO) getDao(PlayerDAOImpl.class);
			PlayerExample example = new PlayerExample();
			example.createCriteria().andNameEqualTo(name);
			List<Player> ls = dao.selectByExample(example);
			if(ls != null && ls.size() > 0){
				return ls.get(0);
			}
		} catch (SQLException e) {
			log.error("exception", e);
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public static PlayerName queryPlayername(String name) {
		PlayerNameDAO pd = (PlayerNameDAO) getDao(PlayerNameDAOImpl.class);
		PlayerNameExample exp = new PlayerNameExample();
		exp.createCriteria().andNameEqualTo(name);
		try {
			List<PlayerName> ls  = pd.selectByExample(exp);
			if(ls != null && ls.size() > 0){
				return ls.get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	@SuppressWarnings("unchecked")
	public static PlayerName queryPlayerid(int id) {
		PlayerNameDAO pd = (PlayerNameDAO) getDao(PlayerNameDAOImpl.class);
		PlayerNameExample exp = new PlayerNameExample();
		exp.createCriteria().andPlayerIdEqualTo(id);
		try {
			List<PlayerName> ls  = pd.selectByExample(exp);
			
			if(ls != null && ls.size() > 0){
				return ls.get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * 查询area
	 * */
	@SuppressWarnings("unchecked")
	public static List<Area> queryAllArea() {
		try {
			AreaDAO dao = (AreaDAOImpl) DBManager.getDataDao(AreaDAOImpl.class);
			return dao.selectByExample(null);
		} catch (SQLException e) {
			log.error("exception", e);

		}
		return new LinkedList<Area>();
	}
	
	@SuppressWarnings("unchecked")
	public static List<City> queryAllCity(){
		try {
			CityDAO dao = (CityDAOImpl)getDataDao(CityDAOImpl.class);
			return dao.selectByExample(null);
		} catch (SQLException e) {
			log.error("exception", e);

		}
		return new LinkedList<City>();
	}
	
	@SuppressWarnings("unchecked")
	public static List<Facility> queryAllFacilty(){
		try {
			FacilityDAO dao = (FacilityDAO)getDataDao(FacilityDAOImpl.class);
			return dao.selectByExample(null);
		} catch (SQLException e) {
			log.error("exception", e);

		}
		return new LinkedList<Facility>();
	}

	@SuppressWarnings("unchecked")
	public static List<CityFacility> queryAllCityFacility(){
		try {
			CityFacilityDAO dao = (CityFacilityDAOImpl)getDataDao(CityFacilityDAOImpl.class);
			return dao.selectByExample(null);
		} catch (SQLException e) {
			log.error("exception", e);

		}
		return new LinkedList<CityFacility>();
	}
	
	@SuppressWarnings("unchecked")
	public static List<Npc> queryAllNpc(){
		try {
			NpcDAO dao = (NpcDAOImpl)getDataDao(NpcDAOImpl.class);
			return dao.selectByExample(null);
		} catch (SQLException e) {
			log.error("exception", e);

		}
		return new LinkedList<Npc>();
	}
	
	@SuppressWarnings("unchecked")
	public static List<CityFacilityNpc> queryAllCityFacilityNpc(){
		try {
			CityFacilityNpcDAO dao = (CityFacilityNpcDAOImpl)getDataDao(CityFacilityNpcDAOImpl.class);
			return dao.selectByExample(null);
		} catch (SQLException e) {
			log.error("exception", e);

		}
		return new LinkedList<CityFacilityNpc>();
	}
	
	/***************************************************************************
	 * 门派,招式,绝学,心法
	 **/
	
	@SuppressWarnings("unchecked")
	public static List<Sect> queryAllSect(){
		try {
			SectDAO dao = (SectDAO) DBManager.getDataDao(SectDAOImpl.class);
			SectExample example = new SectExample();
			example.createCriteria().andIdNotEqualTo(0);
			return dao.selectByExample(example);
		} catch (SQLException e) {
			log.error("exception", e);
		}
		return new LinkedList<Sect>();
	}
	
	/**
	 * 查询数据表配置的所有门派招式和绝学数据 
	 **/
	@SuppressWarnings("unchecked")
	public static List<SectTitle> queryAllSectTitle(){
		try {
			SectTitleDAO dao = (SectTitleDAO) DBManager.getDataDao(SectTitleDAOImpl.class);
			return dao.selectByExample(null);
		} catch (SQLException e) {
			log.error("[DBManager]{query sectTitle has some exception...}", e);
		}
		return new LinkedList<SectTitle>();
	}
	
	/***************************************************************************
	 * 道具,装备
	 **/
	
	/**
	 * 查询道具资源
	 **/
	@SuppressWarnings("unchecked")
	public static List<Item> queryAllItems(){
		List<Item> list = null;
		try {
			ItemDAO dao = (ItemDAOImpl) getDataDao(ItemDAOImpl.class);
			list = dao.selectByExample(null);			
		} catch (SQLException e) {
			log.error("exception", e);
		}
		return (list == null ? new ArrayList<Item>(0):list);
	}		
	
	/**
	 * 查询游戏中所有特殊道具资源
	 **/
	@SuppressWarnings("unchecked")
	public static List<SpecialItem> queryAllSpecialItems(){
		try {
			SpecialItemDAO dao = (SpecialItemDAO) DBManager.getDataDao(SpecialItemDAOImpl.class);
			return dao.selectByExample(null);
		} catch (SQLException e) {
			log.error("exception", e);
		}
		return new LinkedList<SpecialItem>();
	}
	
	/**
	 * 查询游戏中所有NPC道具资源
	 **/
	@SuppressWarnings("unchecked")
	public static List<NpcItem> queryAllNpcItems(){
		try {
			NpcItemDAO dao = (NpcItemDAO) DBManager.getDataDao(NpcItemDAOImpl.class);
			return dao.selectByExample(null);
		} catch (SQLException e) {
			log.error("exception", e);
		}
		return new LinkedList<NpcItem>();
	}
	
	/**
	 * 查询装备位置资源
	 **/
	@SuppressWarnings("unchecked")
	public static List<ItemPosition> queryAllItemPositions(){
		try {
			ItemPositionDAO dao = (ItemPositionDAO) DBManager.getDataDao(ItemPositionDAOImpl.class);
			return dao.selectByExample(null);
		} catch (SQLException e) {
			log.error("exception", e);
		}
		return new LinkedList<ItemPosition>();
	}
	
	/**
	 * 查询装备铸造材料列表
	 **/
	@SuppressWarnings("unchecked")
	public static List<ItemForge> queryAllItemForges(){
		try {
			ItemForgeDAO dao = (ItemForgeDAO) DBManager.getDataDao(ItemForgeDAOImpl.class);
			return dao.selectByExample(null);
		} catch (SQLException e) {
			log.error("exception", e);
		}
		return new LinkedList<ItemForge>();
	}
	
	/**
	 * 查询强化装备材料列表
	 **/
	@SuppressWarnings("unchecked")
	public static List<ItemImprove> queryAllItemImproves(){
		try {
			ItemImproveDAO dao = (ItemImproveDAO) DBManager.getDataDao(ItemImproveDAOImpl.class);
			return dao.selectByExample(null);
		} catch (SQLException e) {
			log.error("exception", e);
		}
		return new LinkedList<ItemImprove>();
	}
	
	/**
	 * 查询装备分解资源
	 **/
	@SuppressWarnings("unchecked")
	public static void loadItemDecomposes(Map<Integer, List<ItemDecompose>> itemDecomposeMap){
		try {
			ItemDecomposeDAO dao = (ItemDecomposeDAO) DBManager.getDataDao(ItemDecomposeDAOImpl.class);
			List<ItemDecompose> list = dao.selectByExample(null);
			if (list != null && list.size() > 0) {
				List<ItemDecompose> itemList = null;
				Item item = null;
				String items[] = null;
				String itemNum[] = null;
				ItemVo iv = null;
				for (ItemDecompose id : list) {
					itemList = itemDecomposeMap.get(id.getColor());
					if (itemList == null) {
						itemList = new ArrayList<ItemDecompose>();
						itemDecomposeMap.put(id.getColor(), itemList);
					}
					items = id.getItemNum().split("\\|");
					
					for (int i = 0; i < items.length; i++) {
						itemNum = items[i].split("_");
						item = ItemMG.instance.getItem(Integer.parseInt(itemNum[0]));
						if (item != null) {
							iv = createItemVo(Integer.parseInt(itemNum[0]), Integer.parseInt(itemNum[1]));
							if (iv == null) {
								continue;
							}
							id.getItemVos().add(iv);
						}
					}
					itemList.add(id);
				}
			}
		} catch (SQLException e) {
			log.error("exception", e);
		}
	}
	
	/**
	 * 加载玩家所以道具资源
	 **/
	@SuppressWarnings("unchecked")
	public static List<PlayerItem> queryPlayerItems(int pid){
		try {
			PlayerItemDAO dao = (PlayerItemDAO) getDao(PlayerItemDAOImpl.class);
			PlayerItemExample exp = new PlayerItemExample();
			exp.createCriteria().andPlayerIdEqualTo(Integer.valueOf(pid));
			return dao.selectByExample(exp);
		} catch (SQLException e) {
			log.error("exception", e);
		}
		return new LinkedList<PlayerItem>();		
	}
	
	public static PlayerItem queryPlayerItemById(int pItemId){
		try {
			PlayerItemDAO dao = (PlayerItemDAO) getDao(PlayerItemDAOImpl.class);
			return dao.selectByPrimaryKey(pItemId);
		} catch (SQLException e) {
			log.error("exception", e);
		}
		return null;		
	}
	
	/**
	 * 查询玩家装备 
	 **/
	@SuppressWarnings("unchecked")
	public static PlayerItem queryPlayerItemByItem(int playerId,int itemId){
		try {
			PlayerItemDAO dao = (PlayerItemDAO) getDao(PlayerItemDAOImpl.class);
			PlayerItemExample exp = new PlayerItemExample();
			exp.createCriteria().andPlayerIdEqualTo(playerId).andItemIdEqualTo(itemId);
			List<PlayerItem> lst = dao.selectByExample(exp);
			return (lst.size() > 0?lst.get(0):null);
		} catch (SQLException e) {
			log.error("exception", e);
		}
		return null;		
	}
	
	/**
	 * 查询使用中的装备
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<PlayerItem> queryPlayerUesdItems(int pid){
		try {
			PlayerItemDAO dao = (PlayerItemDAO) getDao(PlayerItemDAOImpl.class);
			PlayerItemExample exp = new PlayerItemExample();
			exp.createCriteria().andPlayerIdEqualTo(pid).andIsUseEqualTo(1);
			return dao.selectByExample(exp);
		} catch (SQLException e) {
			log.error("exception", e);
		}
		return new LinkedList<PlayerItem>();		
	}
	
	
	/**
	 * 查询指定ID道具
	 **/
	
	public static Item queryItem(int id){
		Item item = null;
		try {
			ItemDAO dao = (ItemDAO) getDataDao(ItemDAOImpl.class);	
			item = dao.selectByPrimaryKey(Integer.valueOf(id));
		} catch (SQLException e) {
			log.error("exception", e);
		}
		return item;		
	}
	
	/**
	 * 加载玩家所以道具资源
	 **/
	@SuppressWarnings("unchecked")
//	public static List<PlayerItemAppend> queryPlayerItemAppends(int pid){
//		try {
//			PlayerItemAppendDAO dao = (PlayerItemAppendDAO) getDao(PlayerItemAppendDAOImpl.class);
//			PlayerItemAppendExample exp = new PlayerItemAppendExample();
//			exp.createCriteria().andPlayerIdEqualTo(Integer.valueOf(pid));
//			return dao.selectByExample(exp);
//		} catch (SQLException e) {
//			log.error("exception", e);
//		}
//		return new LinkedList<PlayerItemAppend>();		
//	}
	
	/**
	 * 加载道具镶嵌的宝石
	 * @param pid
	 * @return
	 */
	public static List<PlayerItemAppend> queryPlayerItemAppendsByPItemId(int pitemId){
		try {
			PlayerItemAppendDAO dao = (PlayerItemAppendDAO) getDao(PlayerItemAppendDAOImpl.class);
			PlayerItemAppendExample exp = new PlayerItemAppendExample();
			exp.createCriteria().andPlayerItemIdEqualTo(Integer.valueOf(pitemId));
			return dao.selectByExample(exp);
		} catch (SQLException e) {
			log.error("exception", e);
		}
		return new LinkedList<PlayerItemAppend>();		
	}
	
	/**
	 * 家在玩家当前正在使用中的时效道具.
	 * @param pid 玩家id.
	 * @return 道具列表.
	 */
	@SuppressWarnings("unchecked")
	public static List<PlayerItemUsing> queryPlayerLifeUsingItem(int pid) {
		try {
			PlayerItemUsingDAO dao = (PlayerItemUsingDAO) getDao(PlayerItemUsingDAOImpl.class);
			PlayerItemUsingExample exp = new PlayerItemUsingExample();
			exp.createCriteria().andPlayerIdEqualTo(Integer.valueOf(pid));
			return dao.selectByExample(exp);
		} catch (Exception e) {
			log.error("load player="+pid+" life using item falied.", e);
		}
		return new LinkedList<PlayerItemUsing>();
	}
	
	
	/**
	 * 加载附加属性资源
	 **/
	@SuppressWarnings("unchecked")
	public static List<ItemProperty> queryAllItemStarProperty(){
		try {
			ItemPropertyDAO dao = (ItemPropertyDAO) getDataDao(ItemPropertyDAOImpl.class);
			ItemPropertyExample examp = new ItemPropertyExample();
			examp.createCriteria().andUseTypeEqualTo(0);
			return dao.selectByExample(examp);
		} catch (SQLException e) {
			log.error("exception", e);
		}
		return new LinkedList<ItemProperty>();		
	}
	
	/**
	 * 加载附加属性资源
	 **/
	@SuppressWarnings("unchecked")
	public static List<ItemProperty> queryAllItemSuitProperty(){
		try {
			ItemPropertyDAO dao = (ItemPropertyDAO) getDataDao(ItemPropertyDAOImpl.class);
			ItemPropertyExample examp = new ItemPropertyExample();
			examp.createCriteria().andUseTypeEqualTo(1);
			return dao.selectByExample(examp);
		} catch (SQLException e) {
			log.error("exception", e);
		}
		return new LinkedList<ItemProperty>();		
	}
	
	
	/**
	 * 加载所有特殊装备爆出概率
	 **/
	@SuppressWarnings("unchecked")
	public static List<ItemBetterProty> queryAllItemBetterProty(){
		try {
			ItemBetterProtyDAO dao = (ItemBetterProtyDAO) getDataDao(ItemBetterProtyDAOImpl.class);
			return dao.selectByExample(null);
		} catch (SQLException e) {
			log.error("exception", e);
		}
		return new LinkedList<ItemBetterProty>();		
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<Route> queryAllRoute(){
		try {
			RouteDAO dao = (RouteDAO) DBManager.getDataDao(RouteDAOImpl.class);
			return dao.selectByExample(null);
		} catch (SQLException e) {
			log.error("exception", e);
		}
		return new LinkedList<Route>();
	}
	
	@SuppressWarnings("unchecked")
	public static List<TempRoute> queryAllTempRoute(){
		try {
			TempRouteDAO dao = (TempRouteDAO) DBManager.getDataDao(TempRouteDAOImpl.class);
			return dao.selectByExample(null);
		} catch (SQLException e) {
			log.error("exception", e);
		}
		return new LinkedList<TempRoute>();
	}
	
	public static void insertRoute(Map<String, Route> routeMap) {
		RouteDAO dao = (RouteDAO) getDataDao(RouteDAOImpl.class);
		for (Route route : routeMap.values()) {
			try {
				dao.insert(route);
			} catch (SQLException e) {
				log.error("exception", e);
			}
		}
	}
	
	public static void buildTempRoute(Map<String, Path> routeMap) {
		TempRouteDAO dao = (TempRouteDAO) getDataDao(TempRouteDAOImpl.class);
		for (Path path : routeMap.values()) {
			TempRoute tempRoute = new TempRoute();
			tempRoute.setStartCity(path.getNodes().get(0));
			tempRoute.setEndCity(path.getEnd());
			tempRoute.setDistance(path.getDistance());
			String s = "";
			for (Integer i : path.getNodes()) {
				s += i + ",";
			}
			tempRoute.setPath(s);

			try {
				dao.insert(tempRoute);
			} catch (SQLException e) {
				log.error("exception", e);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<Config> queryAllConfig(){
		try {
			ConfigDAO dao = (ConfigDAO) DBManager.getDataDao(ConfigDAOImpl.class);
			return dao.selectByExample(null);
		} catch (SQLException e) {
			log.error("exception", e);
		}
		return new LinkedList<Config>();
	}

	public static List<Action> queryAllActions() {
		try {
			ActionDAO actionDAO = (ActionDAOImpl) getDataDao(ActionDAOImpl.class);

			return actionDAO.selectByExample(null);
		} catch (SQLException e) {
			log.error("exception", e);
		}

		return null;
	}

	/**
	 * 题库
	 */
	public static Map<Integer, Question> queryAllQuestions() {
		Map<Integer, Question> questions = new HashMap<Integer, Question>();

		QuestionDAO dao = (QuestionDAO) getDataDao(QuestionDAOImpl.class);

		try {
			Collection<Question> c = dao.selectByExample(null);

			for (Question re : c) {
				List<String> list = new ArrayList<String>();

				String[] arr = re.getAnswer().split("/");
				for (String tmp : arr) {
					if (tmp != null && !tmp.equals(""))
						list.add(tmp);
				}

				re.setAnswers(list);

				questions.put(re.getId(), re);
			}

		} catch (SQLException e) {
			log.error("exception", e);
		}

		return questions;
	}

	/**
	 * 任务
	 */
	public static Map<Integer, Mission> queryAllMissions() {
		MissionDAO dao = (MissionDAO) getDataDao(MissionDAOImpl.class);
		
		Map<Integer, Mission> missions = new HashMap<Integer, Mission>();

		try {
			// 终点任务ID
			MissionPreDAO mdao = (MissionPreDAO) getDataDao(MissionPreDAOImpl.class);

			Collection<MissionPre> c1 = mdao.selectByExample(null);

			List<Integer> toMissionIds = new ArrayList<Integer>();

			for (MissionPre mp : c1) {
				toMissionIds.add(mp.getToMissionId());
			}

			// 任务数据
			Collection<MissionWithBLOBs> c = dao.selectByExampleWithBLOBs(null);
			for (MissionWithBLOBs mission : c) {
				if (!mission.getIsValidate())
					continue;

				if (mission.getCityfacilityId() == null)
					mission.setCityfacilityId(0);

				mission.compile();

				missions.put(mission.getId(), mission);
			}
		} catch (SQLException e) {
			log.error(e);
		}

		return missions;
	}

	/**
	 * 加载任务类型
	 */
	public static Map<Integer, MissionType> queryAllMissionType() {
		Map<Integer, MissionType> map = new LinkedHashMap<Integer, MissionType>();
		try {
			MissionTypeDAO mtDAO = (MissionTypeDAO) getDataDao(MissionTypeDAOImpl.class);

			for (MissionType mt : mtDAO.selectByExample(null)) {
				map.put(mt.getId(), mt);
			}
		} catch (SQLException e) {
			log.error("exception", e);
		}

		return map;
	}
	/****************************************************************
	 * Monster
	 */
	/**
	 * 设施怪
	 */
	public static List<CityFacilityMonster> loadCityFacilityMonster() {
		try {
			CityFacilityMonsterDAO dao = (CityFacilityMonsterDAOImpl) getDataDao(CityFacilityMonsterDAOImpl.class);
			return dao.selectByExample(null);
		} catch (SQLException e) {
			log.error("exception", e);
		}
		return null;
	}

	/**
	 * 怪
	 */
	@SuppressWarnings("unchecked")
	public static List<Monster> loadMonster() {
		try {
			MonsterDAO dao = (MonsterDAOImpl) getDataDao(MonsterDAOImpl.class);
			return dao.selectByExample(null);
		} catch (SQLException e) {
			log.error("exception", e);
		}
		return null;
	}

	// ---------------------------商城-----------------------------------
	/**
	 * 加载打包道具表
	 * */
	public static List<Goods> queryAllGoods() {
		try {
			// Package list
			GoodsExample ge = new GoodsExample();
			ge.createCriteria().andIsValidateEqualTo(true);
			ge.setOrderByClause("display_order desc");
			GoodsDAO goodsDao = (GoodsDAO) getDataDao(GoodsDAOImpl.class);
			List<Goods> goodsList = goodsDao.selectByExample(ge);
			GoodsItemDAO pidao = (GoodsItemDAO) getDataDao(GoodsItemDAOImpl.class);
			for (Goods goods : goodsList) {
				// packageItem list
				GoodsItemExample gie = new GoodsItemExample();
				gie.createCriteria().andGoodsIdEqualTo(goods.getId());
				goods.setGoodsItems(pidao.selectByExample(gie));
			}
			return goodsList;
		} catch (SQLException e) {
			log.error("exception", e);
		}
		return new LinkedList<Goods>();
	}	
	//------------------------------------------------------------------/
	
	@SuppressWarnings("unchecked")
	public static List<PlayerLevel> queryAllPlayerLevel(){
		try {
			PlayerLevelDAO dao = (PlayerLevelDAOImpl)getDataDao(PlayerLevelDAOImpl.class);
			return dao.selectByExample(null);
		} catch (SQLException e) {
			log.error("exception", e);

		}
		return new LinkedList<PlayerLevel>();
	}
	
	/**
	 * 取得玩家好友列表
	 * 
	 * @param 
	 * pid：player id
	 * @return 
	 * Player    
	 */
	public static List<PlayerFriend> queryPlayerFriends(int playerId) {
		PlayerFriendDAO playerFriendDAO = (PlayerFriendDAOImpl) getDao(PlayerFriendDAOImpl.class);
		PlayerFriendExample pfe = new PlayerFriendExample();
		pfe.createCriteria().andPlayerIdEqualTo(playerId);
		try {
			List<PlayerFriend> playerFriends = playerFriendDAO.selectByExample(pfe);
			return playerFriends;
		} catch (Exception e) {
			log.error("exception", e);
		}
		return null;
	}
	
	
	/**********************************************************************
	 * 宠物
	 **/
	
	/**
	 * 获取配置的宠物资源
	 * @return 
	 **/
	@SuppressWarnings("unchecked")
	public static List<Pet> queryAllPet(){
		try {
			PetDAO dao = (PetDAO)getDataDao(PetDAOImpl.class);
			return dao.selectByExample(null);
		}
		catch(Exception e){
			log.error("{load system pet resours has some exceptions}", e);
		}
		return new LinkedList<Pet>();
	}
	
	/**
	 * 获取配置的宠物天赋
	 * @return 
	 **/
	@SuppressWarnings("unchecked")
	public static List<PetTalent> queryAllPetTalent(){
		try {
			PetTalentDAO dao = (PetTalentDAO)getDataDao(PetTalentDAOImpl.class);
			return dao.selectByExample(null);
		}
		catch(Exception e){
			log.error("{load system pet talent resours has some exceptions}", e);
		}
		return new LinkedList<PetTalent>();
	}
	
	/**
	 * 获取配置的宠物等级资源
	 * @return 
	 **/
	@SuppressWarnings("unchecked")
	public static List<PetLevel> queryAllPetLevel(){
		try {
			PetLevelDAO dao = (PetLevelDAO)getDataDao(PetLevelDAOImpl.class);
			return dao.selectByExample(null);
		}
		catch(Exception e){
			log.error("{load system pet level resours has some exceptions}", e);
		}
		return new LinkedList<PetLevel>();
	}
	
	/**
	 * 查询指定玩家的所有宠物列表
	 * @param pid 玩家 
	 **/
	@SuppressWarnings("unchecked")
	public static List<PlayerPet> queryPlayerPets(int pid){
		PlayerPetDAO dao = (PlayerPetDAO) getDao(PlayerPetDAOImpl.class);
		try {
			PlayerPetExample example = new PlayerPetExample();
			example.createCriteria().andPlayerIdEqualTo(pid);
			return dao.selectByExample(example);
		} catch (Exception e) {
			log.error("exception:", e);
		}
		return new LinkedList<PlayerPet>();
	}
	
	/**************************************************************
	/**
	 * 公告列表 
	 **/
	public static List<Notice> queryAllNotice(){
		NoticeDAO dao = (NoticeDAO) getDataDao(NoticeDAOImpl.class);
		try {
			return dao.selectNotice();
		} catch (Exception e) {
			log.error("exception:", e);
		}
		return new LinkedList<Notice>();
	}	
	
	public static List<CityTong> queryAllCityTong(){
		try {
			CityTongDAO dao = (CityTongDAO)getDao(CityTongDAOImpl.class);
			return dao.selectByExample(null);
		} catch (SQLException e) {
			log.error("exception", e);

		}
		return new LinkedList<CityTong>();
	}	
	
	@SuppressWarnings("unchecked")
	public static List<PlayerWar> queryPlayerWar(){
		try {
			PlayerWarDAO dao = (PlayerWarDAO)getDao(PlayerWarDAOImpl.class);
			PlayerWarExample e = new PlayerWarExample();
			e.createCriteria().andKillerIdEqualTo(0);
			return dao.selectByExample(e);
		} catch (SQLException e) {
			log.error("exception", e);

		}
		return new LinkedList<PlayerWar>();
	}

	@SuppressWarnings("unchecked")
	public static List<PlayerEnemy> queryPlayerEnemy(Integer playerId) {
		PlayerEnemyDAO dao = (PlayerEnemyDAOImpl)getDao(PlayerEnemyDAOImpl.class);
		PlayerEnemyExample example = new PlayerEnemyExample();
		example.createCriteria().andPlayerIdEqualTo(playerId);
		try {
			return dao.selectByExample(example);
		} catch (SQLException e) {
			log.error("query player enemy failed.", e);
		}
		return new LinkedList<PlayerEnemy>();
	}
	
	@SuppressWarnings("unchecked")
	public static List<PlayerMission> queryPlayerMission(Player player) {
		PlayerMissionDAO dao = (PlayerMissionDAOImpl)getDao(PlayerMissionDAOImpl.class);
		PlayerMissionExample pme = new PlayerMissionExample();
		try {
			pme.createCriteria().andPlayerIdEqualTo(player.getId());
			return dao.selectByExample(pme);
		} catch (SQLException e) {
			log.error("query city mission failed.", e);
		}
		return new LinkedList<PlayerMission>();
	}
	
	/**
	 * 查询所有.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<MissionCondition> queryMissionCondition() {
		MissionConditionDAO dao = (MissionConditionDAOImpl) getDao(MissionConditionDAOImpl.class);
		try {
//			MissionConditionExample exm = new MissionConditionExample();
//			exm.createCriteria().andHunterIdIsNull();
			return dao.selectByExample(null);
		} catch (SQLException e) {
			log.error("query city mission failed.", e);
		}
		return new LinkedList<MissionCondition>();
	}

	@SuppressWarnings("unchecked")
	public static List<PlayerUpgrade> queryUpgradeInfo() {
		PlayerUpgradeDAO dao = (PlayerUpgradeDAO)getDataDao(PlayerUpgradeDAOImpl.class);
		try {
	        return dao.selectByExample(null);
        } catch (SQLException e) {
        	log.error("query upgrade info failed.", e);
        }
        return new LinkedList<PlayerUpgrade>();
	}

	// ---------------------------排行榜------------------------------------- 
	/**
	 * 加载排行榜
	 * @param tongId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Rank> queryAllRank() {
		RankDAO rankDAO = (RankDAOImpl) getDao(RankDAOImpl.class);
		List<Rank> ranks = null;
		try {
			RankExample rankExample = new RankExample();
			ranks = rankDAO.selectByExample(rankExample);
		} catch (Exception e) {
			log.error("exception:", e);
		}
		return ranks;
	}
	
	/**
	 * 加载密友度排行榜
	 */
	@SuppressWarnings("unchecked")
	public static List<IntimateScoreRank> queryAllIntimateScoreRank() {
		IntimateScoreRankDAO dao = (IntimateScoreRankDAOImpl) getDao(IntimateScoreRankDAOImpl.class);
		List<IntimateScoreRank> lst = null;
		try {
			IntimateScoreRankExample example = new IntimateScoreRankExample();
			example.setOrderByClause("intimate_score desc");
			lst = dao.selectByExample(example);
		} catch (Exception e) {
			log.error("exception:", e);
		}
		return lst;
	}
	// ---------------------------排行榜-------------------------------------/ 
	
	@SuppressWarnings("unchecked")
	public static List<ItemSuitProperty> queryItemSuitProperty(Integer itemSuitId) {
		ItemSuitPropertyDAO dao = (ItemSuitPropertyDAO)getDataDao(ItemSuitPropertyDAOImpl.class);
		ItemSuitPropertyExample example = new ItemSuitPropertyExample();
		try {
			example.createCriteria().andItemSuiteIdEqualTo(itemSuitId);
	        return dao.selectByExample(example);
        } catch (SQLException e) {
        	log.error("query ItemSuitProperty info failed.", e);
        }
        return new LinkedList<ItemSuitProperty>();
	}
	
	@SuppressWarnings("unchecked")
	public static List<ItemSuit> queryItemSuites() {
		ItemSuitDAO itemSuitDao = (ItemSuitDAOImpl)DBManager.getDataDao(ItemSuitDAOImpl.class);
		try {
			return itemSuitDao.selectByExample(null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<ItemSuit>();
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<NameLuck> queryNameLuck() {
		NameLuckDAO nameDao = (NameLuckDAOImpl)getDataDao(NameLuckDAOImpl.class);
		try {
	        return nameDao.selectByExample(null);
        } catch (SQLException e) {
        	log.error("exception:", e);
        }
		return new LinkedList<NameLuck>();
	}
	
	@SuppressWarnings("unchecked")
	public static List<PlayerNameLuck> queryPlayerNameLuck(Integer playerId, Integer targetId) {
		PlayerNameLuckDAO dao = (PlayerNameLuckDAOImpl)getDao(PlayerNameLuckDAOImpl.class);
		PlayerNameLuckExample pe = new PlayerNameLuckExample();
		pe.createCriteria().andPlayerIdEqualTo(playerId).andTargetIdEqualTo(targetId);
		try {
	       return dao.selectByExample(pe);
        } catch (SQLException e) {
	        e.printStackTrace();
        }
        return new LinkedList<PlayerNameLuck>();
	}
	
	/************************************************************
	 * 送礼
	 **/
	
	/**
	 * 最近收到的礼物或最近收到的金贝礼物
	 * @param receiver
	 * @param presentType 礼物类型 1=金贝礼物
	 * @param days 负数表示几天前
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Present> getTimeReceivePresents(Player receiver, int presentType, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, days);
		try {
			PresentDAO dao = (PresentDAO) DBManager.getDao(PresentDAOImpl.class);
			PresentExample presentExample = new PresentExample();
			presentExample.setOrderByClause("id desc");
			presentExample.setPage(0);
			presentExample.setPageSize(1);
			presentExample.createCriteria().andReceiverIdEqualTo(receiver.getId()).andCreatedAtGreaterThan(calendar.getTime());
			if (presentType == 1) {//gold present
				presentExample.createCriteria().andGoldGreaterThan(0);
			}
			return dao.selectByExample(presentExample);
		} catch (SQLException e) {
			log.error("exception", e);
		}
		return null;
	}
	
	/**
	 * 送礼物
	 * @param player
	 * @param receiverId
	 * @param item
	 * @return
	 */
	public static Present sendPresent(Player player, int receiverId, Item item) {
		PresentDAO presentDao = (PresentDAO) DBManager.getDao(PresentDAOImpl.class);
		Present present = new Present();
		present.setId(GlobalGenerator.instance.getIdForNewObj(present));
		present.setPlayerId(player.getId());
		present.setReceiverId(receiverId);
		present.setItemId(item.getId());
		present.setGold(item.getGold());
		present.setSilver(item.getPrice());
		present.setWorth(item.getLevel());
		present.setRelation(null);
		present.setPostscript(null);
		present.setCreatedAt(new Date(System.currentTimeMillis()));
		long countRefreshDataTime = 0;
		long sumWorthrefreshDataTime = 0;
		Date currentDate = Calendar.getInstance().getTime();
		try {
			presentDao.insert(present);
			Map<String, Object> itemMap = PresentService.createItemMap(present, item);
			Player receiver = PlayerMG.instance.getPlayerFromCache(receiverId);
			if (null != receiver) {
				countRefreshDataTime = PresentService.refreshDataTime(receiver.getLastCountPresentsTime(), currentDate);
				sumWorthrefreshDataTime = PresentService.refreshDataTime(receiver.getLastsumWorthTime(), currentDate);
				if (receiver.getCountPresents() == -1 || countRefreshDataTime > 0) {
					receiver.setCountPresents(PresentService.countPresents(receiver));
				} else {
					receiver.setCountPresents(receiver.getCountPresents() + 1);
				}

				if (receiver.getSumWorth() == -1 || sumWorthrefreshDataTime > 0) {
					receiver.setSumWorth(PresentService.sumWorth(receiver));
				} else {
					receiver.setSumWorth(receiver.getSumWorth() + item.getLevel());
				}

				receiver.setRecentPresent(itemMap);
				if (item.getGold() > 0) {
					receiver.setRecentGoldPresent(itemMap);
				}
			}
		} catch (SQLException e) {
			log.error("exception", e);
		}
		return present;
	}
	
	/**
	 * 指定用户今天送同样的礼物次数(金贝除外)
	 * @param playerId
	 * @param receiverId
	 * @param itemId
	 * @return
	 */
	public static int countTodaySendPresent(int playerId, int receiverId, int itemId) {
		int count = 0;
		try {
			PresentDAO dao = (PresentDAO) DBManager.getDao(PresentDAOImpl.class);
			PresentExample presentExample = new PresentExample();

			Calendar fromCalendar = Calendar.getInstance();
			fromCalendar.add(Calendar.DAY_OF_MONTH, -1);
			fromCalendar.set(Calendar.HOUR, 12);
			fromCalendar.set(Calendar.MINUTE, 0);
			fromCalendar.set(Calendar.SECOND, 0);

			Calendar toCalendar = Calendar.getInstance();
			toCalendar.set(Calendar.HOUR, 12);
			toCalendar.set(Calendar.MINUTE, 0);
			toCalendar.set(Calendar.SECOND, 0);

			presentExample.createCriteria().andGoldEqualTo(0).andPlayerIdEqualTo(playerId).andReceiverIdEqualTo(receiverId).andItemIdEqualTo(itemId).andCreatedAtGreaterThanOrEqualTo(fromCalendar.getTime()).andCreatedAtLessThan(toCalendar.getTime());
			count = dao.countByExample(presentExample);
		} catch (SQLException e) {
			log.error("exception", e);
		}
		return count;
	}
	
	/**
	 * 收到的礼物总数
	 * @param receiverId
	 * @return
	 */
	public static int countPresents(int receiverId) {
		int count = 0;
		try {
			PresentDAO dao = (PresentDAO) DBManager.getDao(PresentDAOImpl.class);
			PresentExample presentExample = new PresentExample();
			presentExample.createCriteria().andReceiverIdEqualTo(receiverId);
			count = dao.countByExample(presentExample);
		} catch (SQLException e) {
			log.error("exception", e);
		}
		return count;
	}
	
	/**
	 * 汇总收到的礼物总价值
	 * @param receiverId
	 * @return
	 */
	public static int sumWorth(int receiverId) {
		int sumWorth = 0;
		try {
			PresentDAO dao = (PresentDAO) DBManager.getDao(PresentDAOImpl.class);
			PresentExample presentExample = new PresentExample();
			presentExample.createCriteria().andReceiverIdEqualTo(receiverId);
			sumWorth = dao.sumWorthByExample(presentExample);
		} catch (SQLException e) {
			log.error("exception", e);
		}
		return sumWorth;
	}
	
	/**
	 * 玩家收到的所有礼物
	 * @param receiver
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Present> getRecentReceivePresents(Player receiver, int page) {
		try {
			PresentDAO dao = (PresentDAO) DBManager.getDao(PresentDAOImpl.class);
			PresentExample presentExample = new PresentExample();
			presentExample.setOrderByClause("id desc");
			presentExample.setPage((page - 1) * Constants.PAGE_SIZE);
			presentExample.setPageSize(Constants.PAGE_SIZE);
			presentExample.createCriteria().andReceiverIdEqualTo(receiver.getId());
			return dao.selectByExample(presentExample);
		} catch (SQLException e) {
			log.error("exception", e);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
    public static List<Present> queryMyPresents(Player player) {
		try {
			PresentDAO dao = (PresentDAO) DBManager.getDao(PresentDAOImpl.class);
			PresentExample presentExample = new PresentExample();
			presentExample.createCriteria().andReceiverIdEqualTo(player.getId());
			return dao.selectByExample(presentExample);
		} catch (SQLException e) {
			log.error("exception", e);
		}
		return new LinkedList<Present>();
	}
	
	@SuppressWarnings("unchecked")
    public static List<Gm> queryPlayerGM(Integer playerId) {
		GmDAO dao = (GmDAOImpl)getDao(GmDAOImpl.class);
		GmExample ge = new GmExample();
		ge.createCriteria().andPlayerIdEqualTo(playerId);
		try {
	       return dao.selectByExample(ge);
        } catch (SQLException e) {
        	log.error("exception", e);
        }
        return new LinkedList<Gm>();
	}
	
	@SuppressWarnings("unchecked")
    public static List<Gm> queryPlayerGM() {
		GmDAO dao = (GmDAOImpl)getDao(GmDAOImpl.class);
		try {
	       return dao.selectByExample(null);
        } catch (SQLException e) {
        	log.error("exception", e);
        }
        return new LinkedList<Gm>();
	}
	@SuppressWarnings("unchecked")
    public static List<GmLog> queryPlayerGMLog() {
		GmLogDAO dao = (GmLogDAOImpl)getDao(GmLogDAOImpl.class);
		try {
			GmLogExample ex=new GmLogExample();
			ex.createCriteria().andJailDaysGreaterThan(0);
	       return dao.selectByExample(ex);
        } catch (SQLException e) {
        	log.error("exception", e);
        }
        return new LinkedList<GmLog>();
	}
	
	@SuppressWarnings("unchecked")
    public static List<GmLog> queryGMLog(Integer playerId , int actionType) {
		GmLogDAO dao = (GmLogDAOImpl)getDao(GmLogDAOImpl.class);
		GmLogExample ge = new GmLogExample();
		ge.createCriteria().andPlayerIdEqualTo(playerId).andActionTypeEqualTo(actionType).andJailDaysGreaterThan(0);
		try {
	       return dao.selectByExample(ge);
        } catch (SQLException e) {
        	log.error("exception", e);
        }
        return new LinkedList<GmLog>();
	}
	
	
	
	
	@SuppressWarnings("unchecked")
    public static List<NpcRandomWords> queryNpcRandomWords() {
		NpcRandomWordsDAO dao = (NpcRandomWordsDAOImpl)getDataDao(NpcRandomWordsDAOImpl.class);
		try {
	        return dao.selectByExample(null);
        } catch (SQLException e) {
        	log.error("exception", e);
        }
        return new LinkedList<NpcRandomWords>();
	}
	
	@SuppressWarnings("unchecked")
    public static List<ChuansongItemConfig> queryChuanSongitemConfig() {
		ChuansongItemConfigDAO dao = (ChuansongItemConfigDAOImpl)getDataDao(ChuansongItemConfigDAOImpl.class);
		try {
	        return dao.selectByExample(null);
        } catch (SQLException e) {
        	log.error("exception", e);
        }
        return new LinkedList<ChuansongItemConfig>();
	}
	
	/**
	 * 加载所有道具鉴定列表 
	 **/
	@SuppressWarnings("unchecked")
	public static List<ItemIdentify> queryAllItemIdentify(){
		try{
			ItemIdentifyDAO dao = (ItemIdentifyDAO)getDataDao(ItemIdentifyDAOImpl.class);
			return dao.selectByExample(null);
		}
		catch (Exception e) {
		}
		return new LinkedList<ItemIdentify>();
	}
	
	/**
	 * 加载开箱子使用到的资源
	 **/
	@SuppressWarnings("unchecked")
	public static List<ItemBurst> queryAllItemBurst(){
		try{
			ItemBurstDAO dao = (ItemBurstDAO)getDataDao(ItemBurstDAOImpl.class);
			return dao.selectByExample(null);
		}
		catch (Exception e) {
		}
		return new LinkedList<ItemBurst>();
	}
	
	@SuppressWarnings("unchecked")
    public static List<PlayerKf> queryPlayerKF(Integer playerId) {
		try {
			PlayerKfDAO dao = (PlayerKfDAO) getDao(PlayerKfDAOImpl.class);
			PlayerKfExample exp = new PlayerKfExample();
			exp.createCriteria().andPlayerIdEqualTo(playerId);
			return dao.selectByExample(exp);
		} catch (Exception e) {
		}
		return new LinkedList<PlayerKf>();
	}
	
	@SuppressWarnings("unchecked")
    public static List<MonsterType> queryMonsterType() {
		
		MonsterTypeDAO dao = (MonsterTypeDAO)getDataDao(MonsterTypeDAOImpl.class);
		try {
	        return dao.selectByExample(null);
        } catch (SQLException e) {
        	log.error("exception", e);
        }
		return new LinkedList<MonsterType>();
	}
	
	@SuppressWarnings("unchecked")
    public static List<MonsterPropConf> queryMonsterPropConf() {
		
		MonsterPropConfDAO dao = (MonsterPropConfDAO)getDataDao(MonsterPropConfDAOImpl.class);
		try {
	        return dao.selectByExample(null);
        } catch (SQLException e) {
        	log.error("exception", e);
        }
		return new LinkedList<MonsterPropConf>();
	}
	
	@SuppressWarnings("unchecked")
    public static List<PlayerActive> queryPlayerActive(Integer playerId, String key) {

		try {
			PlayerActiveDAO dao = (PlayerActiveDAO)getDao(PlayerActiveDAOImpl.class);
			PlayerActiveExample exp = new PlayerActiveExample();
			exp.createCriteria().andPlayerIdEqualTo(playerId).andKeyWordEqualTo(key);
			return dao.selectByExample(exp);
        } catch (SQLException e) {
        	log.error("exception", e);
        }
        return new LinkedList<PlayerActive>();
	}
	
//	@SuppressWarnings("unchecked")
//    public static List<PlayerActive> queryPlayerActive(Integer playerId) {
//
//		try {
//			PlayerActiveDAO dao = (PlayerActiveDAO)getDao(PlayerActiveDAOImpl.class);
//			PlayerActiveExample exp = new PlayerActiveExample();
//			exp.createCriteria().andPlayerIdEqualTo(playerId);
//			return dao.selectByExample(exp);
//        } catch (SQLException e) {
//        	log.error("exception", e);
//        }
//        return new LinkedList<PlayerActive>();
//	}
	
	@SuppressWarnings("unchecked")
	public static List<PlayerPasswordSafty> queryPlayerPasswordSafty(Integer playerId) {
		PlayerPasswordSaftyDAO dao = (PlayerPasswordSaftyDAO)getDao(PlayerPasswordSaftyDAOImpl.class);
		PlayerPasswordSaftyExample exp = new PlayerPasswordSaftyExample();
		exp.createCriteria().andPlayerIdEqualTo(playerId);
		try {
	        return dao.selectByExample(exp);
        } catch (SQLException e) {
        	log.error("exception", e);
        }
        return new LinkedList<PlayerPasswordSafty>();
	}
	
	@SuppressWarnings("unchecked")
    public static List<PlayerFishing> queryPlayerFishing(Integer playerId)  {
		PlayerFishingDAO dao = (PlayerFishingDAO)getDao(PlayerFishingDAOImpl.class);
		PlayerFishingExample exp = new PlayerFishingExample();
		exp.createCriteria().andPlayerIdEqualTo(playerId);
		try {
	       return dao.selectByExample(exp);
        } catch (SQLException e) {
	        e.printStackTrace();
        }
        return new LinkedList<PlayerFishing>();
	}
	
	@SuppressWarnings("unchecked")
    public static List<PlayerFishing> queryPlayerFishingReceive(Integer playerId)  {
		PlayerFishingDAO dao = (PlayerFishingDAO)getDao(PlayerFishingDAOImpl.class);
		PlayerFishingExample exp = new PlayerFishingExample();
		exp.createCriteria().andReceiverIdEqualTo(playerId);
		try {
	       return dao.selectByExample(exp);
        } catch (SQLException e) {
	        e.printStackTrace();
        }
        return new LinkedList<PlayerFishing>();
	}
	
	@SuppressWarnings("unchecked")
	public static List<PlayerPay> queryPlayerPay(Integer playerId) {
		PlayerPayDAO dao = (PlayerPayDAO)getDao(PlayerPayDAOImpl.class);
		PlayerPayExample exp = new PlayerPayExample();
		exp.createCriteria().andPlayerIdEqualTo(playerId);
		try {
	       return dao.selectByExample(exp);
        } catch (SQLException e) {
	        e.printStackTrace();
        }
        return new LinkedList<PlayerPay>();
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<PointsItem> queryPointItem() {
		PointsItemDAO dao = (PointsItemDAO)getDataDao(PointsItemDAOImpl.class);
		try {
	        return dao.selectByExample(null);
        } catch (SQLException e) {
        	log.error("exception", e);
        }
        return new LinkedList<PointsItem>();
	}
	
	@SuppressWarnings("unchecked")
	public static List<MemberPriviliges> queryMemberPriviliges() {
		MemberPriviligesDAO dao = (MemberPriviligesDAO)getDataDao(MemberPriviligesDAOImpl.class);
		try {
	        return dao.selectByExample(null);
        } catch (SQLException e) {
        	log.error("exception", e);
        }
        return new LinkedList<MemberPriviliges>();
	}
	
	@SuppressWarnings("unchecked")
    public static List<PlayerPromote> queryPlayerPromote(Player player) {
		
		try {
			PlayerPromoteDAO dao = (PlayerPromoteDAO)getDao(PlayerPromoteDAOImpl.class);
			PlayerPromoteExample exp = new PlayerPromoteExample();
			exp.createCriteria().andPlayerIdEqualTo(player.getId());
	        return dao.selectByExample(exp);
        } catch (SQLException e) {
        	log.error("exception", e);
        }
        return new LinkedList<PlayerPromote>();
	}
	
	@SuppressWarnings("unchecked")
	 public static List<PlayerPromote> queryPromote(Player player) {

		try {
			PlayerPromoteDAO dao = (PlayerPromoteDAO) getDao(PlayerPromoteDAOImpl.class);
			PlayerPromoteExample exp = new PlayerPromoteExample();
			exp.createCriteria().andBelongedPlayerIdEqualTo(player.getId());
			return dao.selectByExample(exp);
		} catch (SQLException e) {
			log.error("exception", e);
		}
		return new LinkedList<PlayerPromote>();
	}
	
	@SuppressWarnings("unchecked")
	public static List<RawMission> queryRawMission() {
		RawMissionDAO dao = (RawMissionDAO)getDataDao(RawMissionDAOImpl.class);
		try {
	        return dao.selectByExample(null);
        } catch (SQLException e) {
	        e.printStackTrace();
        }
        return new LinkedList<RawMission>();
	}
	
//	@SuppressWarnings("unchecked")
//	public static List<ActiveItems> queryActiveItems() {
//		ActiveItemsDAO dao = (ActiveItemsDAOImpl)getDataDao(ActiveItemsDAOImpl.class);
//		try {
//	        return dao.selectByExample(null);
//        } catch (SQLException e) {
//	        e.printStackTrace();
//        }
//        return new LinkedList<ActiveItems>();
//	}
	@SuppressWarnings("unchecked")
	public static List<PlayerStoreRoom> queryAllStoreRoom(Player player){
		PlayerStoreRoomDAO dao=(PlayerStoreRoomDAOImpl)getDao(PlayerStoreRoomDAOImpl.class);
		try {
			PlayerStoreRoomExample exp = new PlayerStoreRoomExample();
			exp.createCriteria().andPlayerIdEqualTo(player.getId());
			return dao.selectByExample(exp);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new LinkedList<PlayerStoreRoom>();
	}
//	@SuppressWarnings("unchecked")
//	public static List<PlayerActiveRewards> queryPlayerReward(Player player){
//		PlayerActiveRewardsDAO dao=(PlayerActiveRewardsDAOImpl)getDao(PlayerActiveRewardsDAOImpl.class);
//		try {
//			PlayerActiveRewardsExample exp = new PlayerActiveRewardsExample();
//			exp.createCriteria().andTypeEqualTo(1).andRoleIdEqualTo(player.getId()).andGetTimeIsNull();
//			return dao.selectByExample(exp);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return new LinkedList<PlayerActiveRewards>();
//	}
	@SuppressWarnings("unchecked")
	public static List<PlayerActiveRewards> queryPlayerReward(Integer roleId, Integer type){
		PlayerActiveRewardsDAO dao=(PlayerActiveRewardsDAOImpl)getDao(PlayerActiveRewardsDAOImpl.class);
		try {
			PlayerActiveRewardsExample exp = new PlayerActiveRewardsExample();
			exp.createCriteria().andRoleIdEqualTo(roleId).andTypeEqualTo(type).andGetTimeIsNull();
			return dao.selectByExample(exp);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new LinkedList<PlayerActiveRewards>();
	}
	@SuppressWarnings("unchecked")
	public static List<PlayerActiveRewards> queryPlayerReward(Long id){
		PlayerActiveRewardsDAO dao=(PlayerActiveRewardsDAOImpl)getDao(PlayerActiveRewardsDAOImpl.class);
		try {
			PlayerActiveRewardsExample exp = new PlayerActiveRewardsExample();
			exp.createCriteria().andIdEqualTo(id).andGetTimeIsNull();
			return dao.selectByExample(exp);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new LinkedList<PlayerActiveRewards>();
	}
	
	// ---------------------------新手帮助(begin)------------------------------
	@SuppressWarnings("unchecked")
	public static List<QuestionType> loadQuestionTypes() {
		QuestionTypeDAO dao = (QuestionTypeDAO) getDataDao(QuestionTypeDAOImpl.class);
		try {
			return dao.selectByExample(null);
		} catch (SQLException e) {
			log.error("load all question Type failure", e);
			return new ArrayList<QuestionType>();
		}
	} 
	
	@SuppressWarnings("unchecked")
	public static List<HelpQa> loadHelpQas() {
		HelpQaDAO dao = (HelpQaDAO) getDataDao(HelpQaDAOImpl.class);
		try {
			return dao.selectByExample(null);
		} catch (SQLException e) {
			log.error("load all helpQa failure", e);
			return new ArrayList<HelpQa>();
		}
	}
	
	/************************************ 新加资源加载 ***************************************************/
	
	@SuppressWarnings("unchecked")
	public static void loadSpecBuff(Map<String, SpecBuff> specBuffMap){
		SpecBuffDAO dao = (SpecBuffDAO)getDataDao(SpecBuffDAOImpl.class);
		try {
			List<SpecBuff> sb = dao.selectByExample(null);
			if (sb != null && sb.size() > 0) {
				for (SpecBuff s : sb) {
					specBuffMap.put(getSpecBuffKey(s), s);
				}
			}
		} catch (SQLException e) {
		}
	}
	
	private static String getSpecBuffKey(SpecBuff s){
		return s.getSectId() + "_" + s.getLevel() + "_" + s.getSpecTypeId();
	}
	
	@SuppressWarnings("unchecked")
	public static List<ItemType> loadItemTpye(){
		ItemTypeDAO dao = (ItemTypeDAO) getDataDao(ItemTypeDAOImpl.class);
		List<ItemType> list = new ArrayList<ItemType>();
		try {
			ItemTypeExample example = new ItemTypeExample();
			example.setOrderByClause("id");
			list = dao.selectByExample(example);
		} catch (SQLException e) {
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public static void loadBuffRand(Map<Integer, TreeMap<Integer, BuffRand>> buffRandMap,Map<Integer, BuffType> buffTypeMap){
		BuffTypeDAO dao = (BuffTypeDAO)getDataDao(BuffTypeDAOImpl.class);
		BuffRandDAO dataDao = (BuffRandDAO)getDataDao(BuffRandDAOImpl.class);
		try {
			List<BuffType> buffTypes = dao.selectByExample(null);
			for (BuffType bt : buffTypes) {
				buffTypeMap.put(bt.getId(), bt);
			}
			List<BuffRand> buffRands = dataDao.selectByExample(null);
			TreeMap<Integer, BuffRand> tmp = null;
			BuffType bt = null;
			for (BuffRand br : buffRands) {
				tmp = buffRandMap.get(br.getBuffTypeId());
				if (tmp == null) {
					tmp = new TreeMap<Integer, BuffRand>();
					buffRandMap.put(br.getBuffTypeId(), tmp);
				}
				bt = buffTypeMap.get(br.getBuffTypeId());
				br.setBuffType(bt);
				tmp.put(br.getId(), br);
			}
		} catch (SQLException e) {
		}
	}
	
//	public static void loadImploveBuff(Map<Integer, ImploveBuff> imploveMap){
//		ImploveBuffDAO dao = (ImploveBuffDAO)getDataDao(ImploveBuffDAOImpl.class);
//		try {
//			List<ImploveBuff> list = dao.selectByExample(null);
//			if (list != null && list.size() > 0) {
//				for (ImploveBuff ib : list) {
//					imploveMap.put(ib.getLevel(), ib);
//				}
//			}
//		} catch (SQLException e) {
//		}
//	}
	
	@SuppressWarnings("unchecked")
	public static void  loadComposeItem(Map<Integer, ComposeItem> composeItem) {
		ComposeItemDAO dao = (ComposeItemDAO)getDataDao(ComposeItemDAOImpl.class);
		try {
			List<ComposeItem> list = dao.selectByExample(null);
			Item item = null;
			String items[] = null;//需要所有道具
			String nums[] = null;//需要道具以及数量
			Compose compose = null;
			if (list != null && list.size() > 0) {
				for (ComposeItem ci : list) {
					item = ItemMG.instance.getItem(ci.getItemId());
					if (item == null) {
						continue;
					}
					ci.setItemName(item.getName());
					items = ci.getNeedItems().split("\\|");
					for (String s : items) {
						nums = s.split("_");
						item = ItemMG.instance.getItem(Integer.parseInt(nums[0]));
						if (item == null) {
							continue;
						}
						compose = new Compose();
						compose.setNeedNum(Integer.parseInt(nums[1]));
						compose.setItemId(Integer.parseInt(nums[0]));
						compose.setName(item.getName());
						compose.setAmount(0);
						ci.getCompose().add(compose);
					}
					composeItem.put(ci.getId(), ci);
				}
			}
		} catch (SQLException e) {
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public static void loadItemSuit(Map<Integer, ItemSuit> itemSuits){
		ItemSuitDAO dao = (ItemSuitDAO)getDataDao(ItemSuitDAOImpl.class);
		try {
			List<ItemSuit> list = dao.selectByExample(null);
			for (ItemSuit is : list) {
				itemSuits.put(is.getId(), is);
			}
		} catch (SQLException e) {
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<ResellPrice> queryAllResellPrice(){
		try {
			ResellPriceDAO dao = (ResellPriceDAO)getDataDao(ResellPriceDAOImpl.class);
			ResellPriceExample example = new ResellPriceExample();
			example.setOrderByClause("id");
			return dao.selectByExample(null);
		} catch (SQLException e) {
			log.error("exception", e);

		}
		return new LinkedList<ResellPrice>();
	}
	
	/**
	 * 上线操作时调用
	 * @param player
	 */
	public static void loadPlayerResell(Player player){
		PlayerResellDAO dao = (PlayerResellDAO) getDao(PlayerResellDAOImpl.class);
		PlayerResellExample example = new PlayerResellExample();
		example.createCriteria().andPlayerIdEqualTo(player.getId());
		try {
			List<PlayerResell> list = dao.selectByExampleWithBLOBs(example);
			if (list != null && list.size() > 0) {
				PlayerResell pDart = list.get(0);
				player.setPlayerResell(pDart);
			}
		} catch (SQLException e) {
			log.error("exception", e);
		}
	}
	
	public static void loadItemImprove(Map<Integer, ItemImprove> improves){
		ItemImproveDAO dao = (ItemImproveDAO) getDataDao(ItemImproveDAOImpl.class);
		try {
			List<ItemImprove> list = dao.selectByExample(null);
			String items[] = null;
			String nums[] = null;
			ItemVo itemVo = null;
			if (list != null && list.size() > 0) {
				for (ItemImprove ii : list) {
					if (ii.getImproveLevel() > ItemService.MAX_IMPROVE_LEVEL) {
						ItemService.MAX_IMPROVE_LEVEL = ii.getImproveLevel();
					}
					if (ii.getNeedItems()  != null && ii.getNeedItems().length() > 0) {
						items = ii.getNeedItems().split("\\|");
						for (int i = 0; i < items.length; i++) {
							nums = items[i].split("_");
							itemVo = createItemVo(Integer.parseInt(nums[0]), Integer.parseInt(nums[1]));
							if (itemVo == null) {
								continue;
							}
							ii.getNeedItemVos().add(itemVo);
						}
					}
					if (ii.getSpecItems() != null && ii.getSpecItems().length() > 0) {
						items = ii.getSpecItems().split("\\|");
						for (int i = 0; i < items.length; i++) {
							nums = items[i].split("_");
							itemVo = createItemVo(Integer.parseInt(nums[0]), Integer.parseInt(nums[1]));
							if (itemVo == null) {
								continue;
							}
							ii.getSpecItemVos().add(itemVo);
						}
					}
					improves.put(ii.getImproveLevel(), ii);
				}
			}
		} catch (SQLException e) {
			log.error("exception :" + e.getMessage() , e);
		}
	}
	
	private static ItemVo createItemVo(int itemId,int num){
		Item item  = ItemMG.instance.getItem(itemId);
		if (item == null) {
			return null;
		}
		ItemVo itemVo = new ItemVo();
		itemVo.setId(item.getId());
		itemVo.setName(item.getName());
		itemVo.setNum(num);
		itemVo.setDesception(item.getDescription());
		return itemVo;
	}
	
	/**
	 * 加载玩家所以道具资源
	 **/
	@SuppressWarnings("unchecked")
	public static List<PlayerItemAppend> queryPlayerItemAppends(int pid){
		try {
			PlayerItemAppendDAO dao = (PlayerItemAppendDAO) getDao(PlayerItemAppendDAOImpl.class);
			PlayerItemAppendExample exp = new PlayerItemAppendExample();
			exp.createCriteria().andPlayerIdEqualTo(Integer.valueOf(pid));
			return dao.selectByExample(exp);
		} catch (SQLException e) {
			log.error("exception", e);
		}
		return new LinkedList<PlayerItemAppend>();		
	}
	
	/**
	 * 加载押镖资源
	 **/
	@SuppressWarnings("unchecked")
	public static void loadDart(Map<Integer, Dart> darts){
		DartDAO dao = (DartDAO) getDataDao(DartDAOImpl.class);
		try {
			DartAward da = null;
			List<Dart> list = dao.selectByExample(null);
			if (list != null && list.size() > 0) {
				for (Dart d : list) {
					da = getAward(d.getAward());
					d.setDartAwards(da);
					da = getAward(d.getExtra());
					d.setExtraAwards(da);
					darts.put(d.getId(), d);
				}
			}
		} catch (Exception e) {
			log.error("exception", e);
		}
	}
	
	private static DartAward getAward(String award){
		DartAward dAward = new DartAward();
		if (award != null) {
			String values[] = null;
			values = award.split("\\|");
			String tmpCop[] = null;
			String items[] = null;
			String num[] = null;
			Item item = null;
			DartItem di = null;
			if (values != null && values.length > 0) {
				for (int i = 0; i < values.length; i++) {
					tmpCop = values[i].split("_");
					if (tmpCop != null && tmpCop.length == 2) {
						if ("0".equals(tmpCop[0])) {//游戏币
							dAward.setCopper(Integer.parseInt(tmpCop[1]));
						}
						if ("1".equals(tmpCop[0])) {//rmb
							dAward.setGold(Integer.parseInt(tmpCop[1]));
						}
						if ("2".equals(tmpCop[0])) {//道具
							items = tmpCop[1].split(",");
							if (items != null && items.length > 0) {
								for (int j = 0; j < items.length; j++) {
									num = items[j].split(":");
									item = ItemMG.instance.getItem(Integer.parseInt(num[0]));
									if (item != null) {
										di = dAward.new DartItem();
										di.setId(item.getId());
										di.setName(item.getName());
										di.setAmount(Integer.parseInt(num[1]));
										if (dAward.getDartItems() == null) {
											List<DartItem> list = new ArrayList<DartAward.DartItem>();
											dAward.setDartItems(list);
										}
										dAward.getDartItems().add(di);
									}
									
								}
							}
						}
					}
				}
			}
			
			return dAward;
		}else {
			return null;
		}
	}
	
	/**
	 * 加载玩家押镖
	 * @param player
	 */
	public static void loadPlayerDart(Player player){
		PlayerDartDAO dao = (PlayerDartDAO) getDao(PlayerDartDAOImpl.class);
		PlayerDartExample example = new PlayerDartExample();
		example.createCriteria().andPlayerIdEqualTo(player.getId());
		try {
			List<PlayerDart> list = dao.selectByExample(example);
			if (list != null && list.size() > 0) {
				PlayerDart pDart = list.get(0);
				player.setPlayerDart(pDart);
			}
		} catch (SQLException e) {
			log.error("exception", e);
		}
	}
	
	public static void loadGetOnce(Player player){
		GetOnceDAO dao = (GetOnceDAO) getDao(GetOnceDAOImpl.class);
		GetOnceExample example = new GetOnceExample();
		example.createCriteria().andPlayerIdEqualTo(player.getId());
		try {
			List<GetOnce> list = dao.selectByExample(example);
			if (list != null && list.size() > 0) {
				String keys[] = null;
				for (GetOnce go : list) {
					keys = go.getGetDay().split("_");
					player.getGetOnce().put(keys[0], go);
				}
			}
		} catch (SQLException e) {
			log.error("exception", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void loadPlayerGetOnce(Player player){
		GetOnceDAO dao = (GetOnceDAO)getDao(GetOnceDAOImpl.class);
		GetOnceExample example = new GetOnceExample();
		example.createCriteria().andPlayerIdEqualTo(player.getId());
		try {
			List<GetOnce> list = dao.selectByExample(example);
			if (list != null && list.size() > 0) {
				Map<String, GetOnce> onceMap = player.getGetOnce();
				String key = "";
				for (GetOnce go : list) {
					key = go.getGetDay().split("_")[0];
					if (!onceMap.containsKey(key)) {
						onceMap.put(key, go);
					}
				}
			}
			
		} catch (SQLException e) {
			log.error("exception", e);
		}
		
	}
	@SuppressWarnings("unchecked")
	public static void loadPlayerDartPrize(Player player){
		PlayerDartPrizeDAO dao = (PlayerDartPrizeDAO)getDao(PlayerDartPrizeDAOImpl.class);
		PlayerDartPrizeExample example = new PlayerDartPrizeExample();
		example.createCriteria().andPlayerIdEqualTo(player.getId());
		try {
			List<PlayerDartPrize> list = dao.selectByExample(example);
			if (list != null && list.size() > 0) {
				for (PlayerDartPrize pdp : list) {
					pdp.setDartAward(getAward(pdp.getPrizes()));
					player.getPlayerDartPrize().put(pdp.getId(), pdp);
				}
			}
		} catch (SQLException e) {
			log.error("exception", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void loadSkill(Map<Integer, Map<Integer, Skill>> skills){
		SkillDAO dao = (SkillDAO)getDataDao(SkillDAOImpl.class);
		try {
			List<Skill> list = dao.selectByExample(null);
			Map<Integer, Skill> map = null;
			for (Skill sk : list) {
				map = skills.get(sk.getSkillType());
				if (map == null) {
					map = new HashMap<Integer, Skill>();
					skills.put(sk.getSkillType(), map);
				}
				map.put(sk.getId(), sk);
			}
		} catch (SQLException e) {
			log.error("exception", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void loadPlayerSkill(Player player){
		PlayerSkillDAO dao = (PlayerSkillDAO) getDao(PlayerSkillDAOImpl.class);
		PlayerSkillExample example = new PlayerSkillExample();
		example.createCriteria().andPlayerIdEqualTo(player.getId());
		try {
			List<PlayerSkill> list = dao.selectByExample(example);
			if (list != null && list.size() > 0) {
				Skill skill = null;
				for (PlayerSkill ps : list) {
					skill = SkillService.getSkill(ps.getSkillId());
					ps.setSkill(skill);
					player.getPlayerSkill().put(skill.getSkillType(), ps);
				}
			}
		} catch (SQLException e) {
			log.error("exception", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void loadFarmPre(Map<Integer, Map<Integer, FarmPre>> farmPars){
		FarmPreDAO dao = (FarmPreDAO)getDataDao(FarmPreDAOImpl.class);
		try {
			List<FarmPre> list = dao.selectByExample(null);
			if (list != null && list.size() > 0) {
				Map<Integer, FarmPre> map = null;
				for (FarmPre fp : list) {
					map = farmPars.get(fp.getFarmStatus());
					if (map == null) {
						map = new HashMap<Integer, FarmPre>();
						farmPars.put(fp.getFarmStatus(), map);
					}
					map.put(fp.getId(), fp);
				}
			}
		} catch (SQLException e) {
			log.error("exception", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void loadPlayerFarm(Player player){
		PlayerFarmDAO dao = (PlayerFarmDAO)getDao(PlayerFarmDAOImpl.class);
		PlayerFarmExample example = new PlayerFarmExample();
		example.createCriteria().andPlayerIdEqualTo(player.getId());
		try {
			List<PlayerFarm> list = dao.selectByExample(example);
			if (list != null && list.size() > 0) {
				for (PlayerFarm pf : list) {
					player.getPlayerFarm().put(pf.getId(), pf);
				}
			}
		} catch (SQLException e) {
			log.error("exception", e);
		}
	}
}


package com.ppsea.ds.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ppsea.ds.util.Side;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.GlobalGenerator;
import com.ppsea.ds.data.Path;
import com.ppsea.ds.data.model.Area;
import com.ppsea.ds.data.model.City;
import com.ppsea.ds.data.model.CityFacility;
import com.ppsea.ds.data.model.CityTong;
import com.ppsea.ds.data.model.Event;
import com.ppsea.ds.data.model.EventRoute;
import com.ppsea.ds.data.model.Facility;
import com.ppsea.ds.data.model.PlayerMission;
import com.ppsea.ds.data.model.Route;
import com.ppsea.ds.data.model.TempRoute;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.DBService;
import com.ppsea.ds.util.Dijkstra;


/**
 * 地图加载器，初始化area, city, facility
 * 
 * */

public class MapMG {
	private static final Logger log = Logger.getLogger(MapMG.class);
	public static MapMG instance = new MapMG();
	public static CityFacility BORN_LOCATION  = null;
	
	
	private List<Area> areaList = new LinkedList<Area>();
	private Map<Integer, Area> areaCache = new HashMap<Integer, Area>();
	private Map<Integer, City> cityCache = new HashMap<Integer, City>();
	private Map<Integer, CityTong> cityTongCache = new HashMap<Integer, CityTong>();
	private Map<Integer, Facility> facilitiyCache = new HashMap<Integer, Facility>();
	private Map<Integer, CityFacility> cityFacilityCache = new HashMap<Integer, CityFacility>();
	private Map<Integer, Integer> warArea = new HashMap<Integer,Integer>();
	
	//全部航线
	private Map<String, Path> pathCache = new HashMap<String, Path>();

	//直航
	private Map<String, Route> routeCache = new HashMap<String, Route>() ;


	public void init() throws PpseaException{
		loadArea();
		loadFacility();
		loadCity();
		loadRoute();
		
		BORN_LOCATION = MapMG.instance.getSpecialCityFacility(Constants.BORN_CITY_ID, Constants.BORN_FACILITY_ID);
		if(BORN_LOCATION == null){
			throw new PpseaException("BORN_LOCATION=null");
		}
		
//		for(Area a:areaList){
//			CityFacility cf = a.getMainCity().getSpecialFacilityMap().get(Constants.WARAREA_ID);
//			if(cf != null){
//				warArea.put(a.getSectId(), cf.getId());
//			}
//		}
	}
	
	
	/**
	 * 加载地区
	 */
	private void loadArea(){
		areaList = DBManager.queryAllArea();		
		for(Area area:areaList){
			areaCache.put(area.getId(), area);
		}
	}

	/**
	 * 加载城市
	 */
	private void loadCity(){
		//加载所有城市设施
		Map<Integer, Map<Integer, CityFacility>> map = loadCityFacility();
		
		List<City> ls = DBManager.queryAllCity();	
		for (City city:ls) {
//			log.info("Loading " + city.getName() + " ...");
			cityCache.put(city.getId(), city);
			
			//设置area的城市列表和主城
			Area area = areaCache.get(city.getAreaId());
			if(area != null){
				area.getCityList().add(city);
				if (city.getLevel() == 2) {
					area.setMainCity(city);
				}		
			}

			//加载该城市设施
			city.setCityFacilityMap(map.get(city.getId()));
			if (city.getCityFacilityMap() != null) {
				city.getFacilityList().addAll(city.getCityFacilityMap().values());
				Collections.sort(city.getFacilityList());
			}

			//确定设施间位置关系
//			log.info(city.getName() + " 布局图：");
			if (city.getCityFacilityMap() == null) {
				log.info("无设施");
				continue;
			}
			
			//初始化城内设施
			for (CityFacility cf : city.getCityFacilityMap().values()) {
				//设置设施的所属城市
				cf.setCity(city);

				//是否特殊设施
				if (cf.getFacility().getType()){
					city.getSpecialFacilityMap().put(cf.getFacilityId(), cf);
				}

				cf.getDestinations().add(city.getCityFacilityMap().get(cf.getEastCityFacilityId()));
				cf.getDestinations().add(city.getCityFacilityMap().get(cf.getWestCityFacilityId()));
				cf.getDestinations().add(city.getCityFacilityMap().get(cf.getSouthCityFacilityId()));
				cf.getDestinations().add(city.getCityFacilityMap().get(cf.getNorthCityFacilityId()));

				CityFacility cf1 = city.getCityFacilityMap().get(cf.getEastCityFacilityId());
				CityFacility cf2 = city.getCityFacilityMap().get(cf.getWestCityFacilityId());
				CityFacility cf3 = city.getCityFacilityMap().get(cf.getSouthCityFacilityId());
				CityFacility cf4 = city.getCityFacilityMap().get(cf.getNorthCityFacilityId());
				String msg = cf.getFacility().getName() + "=>";
				if (cf1 == null)
					msg += "东：null";
				else
					msg += "东:" + cf1.getFacility().getName();
				msg += ",";
				if (cf2 == null)
					msg += "西：null";
				else
					msg += "西:" + cf2.getFacility().getName();
				msg += ",";
				if (cf3 == null)
					msg += "南：null";
				else
					msg += "南:" + cf3.getFacility().getName();
				msg += ",";
				if (cf4 == null)
					msg += "北：null";
				else
					msg += "北:" + cf4.getFacility().getName();
				msg += ",";
//				log.info(msg);
			}
		}
	}
	
	private void loadFacility(){
		List<Facility> ls = DBManager.queryAllFacilty();
		for(Facility f:ls){
			facilitiyCache.put(f.getId(), f);
		}
	}
	
	/**
	 * 加载城市帮会
	 */
	private void loadCityTong(){
		List<CityTong> ls = DBManager.queryAllCityTong();
		for(CityTong cityTong: ls){
			cityTongCache.put(cityTong.getCityId(), cityTong);
		}
		// 新增城市校验
		for(City city :cityCache.values()){
			if(!cityTongCache.containsKey(city.getId())){
				CityTong cityTong = new CityTong();
				cityTong.setId(GlobalGenerator.instance.getIdForNewObj(cityTong));
				cityTong.setCityId(city.getId());
				cityTong.setTongId(0);
				cityTong.setLastAktTime(0);
				DBService.commitNoCacheUpdate(cityTong);
				cityTongCache.put(city.getId(), cityTong);
			}
		}
	}
	
	public CityTong getCityTong(int cityId){
		return cityTongCache.get(cityId);
	}
	
	public List<CityTong> getCityTongs(){
		return new ArrayList<CityTong>(cityTongCache.values());
	}
	
	/**
	 * 加载城市设施
	 * @return
	 */
	@SuppressWarnings("unused")
	private Map<Integer, Map<Integer, CityFacility>> loadCityFacility() {
		Map<Integer, Map<Integer, CityFacility>> map = new HashMap<Integer, Map<Integer, CityFacility>>();
		
		List<CityFacility> c = DBManager.queryAllCityFacility();
		for (CityFacility cf : c) {
			Facility facility = facilitiyCache.get(cf.getFacilityId());
			//设置设施别名
			if (cf.getAlias() == null || cf.getAlias().equals("")) {
				cf.setAlias(facility.getName());
			}
			if (cf.getDescription() != null && !cf.getDescription().equals("")){
				facility.setDescription(cf.getDescription());
			}

			cf.setFacility(facility);
			Map<Integer, CityFacility> cfMap = map.get(cf.getCityId());
			if (cfMap == null) {
				cfMap = new HashMap<Integer, CityFacility>();
				map.put(cf.getCityId(), cfMap);
			}
			
			EventMG.instance.loadCityFacilityEvents(cf);

			cfMap.put(cf.getId(), cf);
			
			cityFacilityCache.put(cf.getId(), cf);
		}
		
		return map;
	}
	
	public CityFacility getCityFacility(int cityFacilityId){
		return cityFacilityCache.get(cityFacilityId);
	}
	
	/**
	 * 找到城内的特殊设施
	 * @param cityId
	 * @param facilityId
	 * @return
	 */
	public  CityFacility getSpecialCityFacility(int cityId, int facilityId){
		City city = cityCache.get(cityId);
		if(city == null){
			return null;
		}
		
		return city.getSpecialFacilityMap().get(facilityId);
	}
	
	
	public Area getArea(int areaId){
		return areaCache.get(areaId);
	}


	public List<Area> getAreaList() {
		return areaList;
	}


	public void setAreaList(List<Area> areaList) {
		this.areaList = areaList;
	}
	
	public City getCity(int cityId){
		return cityCache.get(cityId);
	}
	
	private void loadRoute() throws PpseaException {
		//初始化直达路线
		List<Route> routeList = DBManager.queryAllRoute();
		if(routeList == null || routeList.size() == 0){
			//重新初始化直达表
			routeList = buildRoute();
		}
		else{
			for (Route route : routeList ) {
				routeCache.put(getKey(route.getCity1(), route.getCity2()), route);
				//初始化city的直达城
				City city1 = cityCache.get(route.getCity1());
				City city2 = cityCache.get(route.getCity2());
				city1.addNearCity(city2);
				city2.addNearCity(city1);
				
				Collection<EventRoute> c = EventMG.instance.getEventRoutes().get(route.getId());

				if (c != null) {
					for (EventRoute re : c) {
						Event event = EventMG.instance.getRandomEvent(re.getEventId());
						route.getEvents().add(event);
						
						EventMG.instance.addRouteEvent(event.getCityfacilityId(), event.getId());
					}
				}				
			}
		}
		
		//如果以前已经有计算好路径，则直接使用
		List<TempRoute> tempRoutes = DBManager.queryAllTempRoute();
		if (tempRoutes != null && tempRoutes.size() > 0) {
			for (TempRoute tempRoute : tempRoutes) {
				Path path = new Path();
				path.setDistance(tempRoute.getDistance());
				String[] n = tempRoute.getPath().split(",");
				if (n.length < 2) {
					throw new PpseaException("route error :" + n.length + ":" + tempRoute.getPath());
				}
				List<Integer> nodes = new ArrayList<Integer>();
				for (int i = 0; i < n.length; i++) {
					if (n[i].length() == 0) {
						continue;
					}
					nodes.add(Integer.parseInt(n[i]));
				}
				path.setNodes(nodes);
				addPathToMap(path);
			}
			return;
		}

		//重新计算路径
		log.info("重新计算route！");
		ArrayList<Side> sides = new ArrayList<Side>();
		for (Route route : routeList ) {
			//初始化有向边，dijkstra算法
			sides.add(new Side(route.getCity1(), route.getCity2(), route.getDistance()));
			sides.add(new Side(route.getCity2(), route.getCity1(), route.getDistance()));
		}
		Dijkstra djs = new Dijkstra();
		int[] cityIds = getCityIds();
		djs.init(sides, cityIds);

		for (int i = 0; i < cityIds.length; i++) {
			List<Path> paths = djs.search(i);
			for (Path path : paths) {
				addPathToMap(path);
			}
		}
		DBManager.buildTempRoute(pathCache);
	}
	
	/**
	 * 增加路径到routeMap, A-B, B-A只需要保留一条路（A<B）
	 * */
	private void addPathToMap(Path path) {
		if (path.getDistance() > 0) {
			int start = path.getNodes().get(0);
			int end = path.getNodes().get(path.getNodes().size() - 1);

			if(start < end ){
				String key = getKey(start, end);
				if (pathCache.get(key) == null) {
					pathCache.put(key, path);
				}
			}	
		}
	}
	
	public String getKey(int start, int end) {
		if (start < end) {
			return start + "-" + end;
		} else {
			return end + "-" + start;
		}
	}
	
	private int[] getCityIds() {
		int[] cityIds = new int[cityCache.size()];
		int i=0;
		for(City city:cityCache.values()){
			cityIds[i] = city.getId();
			i++;
		}
		return cityIds;
	}
	
	public Path getPath(int fromId, int toId) {
		Path path = pathCache.get(getKey(fromId, toId));
		if (path == null) {
			log.error("no path:" + fromId + "-" + toId);
		}
		return path;
	}
	
	/**
	 * 生成直达表，规则如下：
	 * 区域间主城可直达
	 * 区域内各城可直达
	 */
	private List<Route> buildRoute(){
		Map<Integer, List<City>> cMap = new HashMap<Integer, List<City>>();
		List<City> mainCities = new ArrayList<City>();
		for(City city:cityCache.values()){
			if(city.getLevel() == 1 || city.getLevel() == 2){
				List<City> l = cMap.get(city.getAreaId());
				if( l == null ){
					l = new ArrayList<City>();
					cMap.put(city.getAreaId(), l);
				}
				l.add(city);
			}
			if(city.getLevel() == 2){
				mainCities.add(city);
			}
		}
		routeCache = getRouteMap(mainCities);
		for(List<City> cities: cMap.values()){
			routeCache.putAll(getRouteMap(cities));
		}
		
		DBManager.insertRoute(routeCache);
		List<Route> rList = new LinkedList<Route>();
		for(Route r:routeCache.values()){
			rList.add(r);
		}
		return rList;
	}
	
	private Map<String, Route> getRouteMap(List<City> cities){
		Map<String, Route> rMap = new HashMap<String, Route>();
		for(int i=0; i<cities.size(); i++){
			for(int j=i+1; j<cities.size(); j++){
				Route r = new Route();
				City c1 = cities.get(i);
				City c2 = cities.get(j);
				if(c1.getId() < c2.getId()){
					r.setCity1(c1.getId());
					r.setCity2(c2.getId());
				}
				else{
					r.setCity1(c2.getId());
					r.setCity2(c1.getId());
				}
				r.setDistance(Math.abs(c1.getX()-c2.getX())+Math.abs(c1.getY()-c2.getY()));
				rMap.put(getKey(r.getCity1(), r.getCity2()), r);
			}
		}
		return rMap;
	}


	public Map<Integer, CityFacility> getCityFacilityCache() {
		return cityFacilityCache;
	}


	public void setCityFacilityCache(Map<Integer, CityFacility> cityFacilityCache) {
		this.cityFacilityCache = cityFacilityCache;
	}
	
	public int getWarAreaId(int sectId){
		return warArea.get(sectId);
	}
	
	public Route getRoute(String key){
		return routeCache.get(key);
	}
}

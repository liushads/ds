package com.ppsea.ds.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.log4j.Logger;

import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.ItemBetterProty;
import com.ppsea.ds.data.model.ItemDecompose;
import com.ppsea.ds.data.model.ItemForge;
import com.ppsea.ds.data.model.ItemGroup;
import com.ppsea.ds.data.model.ItemImprove;
import com.ppsea.ds.data.model.ItemPosition;
import com.ppsea.ds.data.model.ItemProperty;
import com.ppsea.ds.data.model.ItemSuit;
import com.ppsea.ds.data.model.NpcItem;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.data.model.PlayerItemUsing;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.util.PropertyBuilder;

/**
 * 道具加载器,负责加载游戏中所有道具资源
 **/
public class ItemMG {
	
	// --------------------------------------------------------------------------- 静态成员
	
	/**
	 * Log
	 **/
	private static final Logger log = Logger.getLogger(ItemMG.class);
	
	/**
	 * 静态实例对象 
	 **/
	public static ItemMG instance = new ItemMG();
	
	// 特殊道具名称 可以在以后程序中会不断添加	
	public static Item ITEM_BOX = null;//百宝箱	
	public static Item ITEM_BIGBOX = null;//行囊.
	public static Item ITEM_SMALL_PACKET = null;//小行囊.
	public static Item ITEM_EMERY = null;//精钢钻 
	public static int BOX_VALUE = 20; //小行囊增加负重值
	public static int ITEM_BIGBOX_VALUE = 50; //大行囊增加负重值
	public static int ITEM_BIGBOX_MAX_NUM = 10; //乾坤袋最多个数
//	public static Item ITEM_HYLOCEREUS_UNDATUS = null;//火龙果 
//	public static Item ITEM_FORGET_STONE = null;//遗忘石
//	public static Item ITEM_TALENT_FORGET = null;//宠物天赋遗忘
//	public static Item ENEMY_ITEM = null;//发布通缉时必须的缉令符
//	public static Item ITEM_MARRY_HAO_HUA =null; //豪华婚礼礼包
//	public static Item ITEM_MARRY_SHE_HUA =null; //奢华婚礼礼包
//	public static Item ITEM_MARRY_CHUAN_SONG =null; //传送卷
	
//	public static Item ITEM_IPVE_JEWEL = null; //强化宝石
//	public static Item ITEM_IPVE_STONE = null; //强化石
//	public static Item ITEM_STAR_JEWEL = null; //升星宝石
//	public static Item ITEM_STAR_STONE = null; //升星石
	public static Item ITEM_HOLE_JEWEL = null; //精钢宝钻
	public static Item ITEM_HOLE_STONE = null; //精钢钻
	public static Item ITEM_CITY_CHUANSONG = null;//传送令
//	public static Item ITEM_QI_XI_GIFT = null;	//七夕活动礼品
//	public static Item IDENTIFY_SCROLL = null;	//鉴定卷轴
//	public static Item UNKOWN_ITEM = null;		//未知道具
//	public static Item ITEM_CASH_BOX = null;	//钱箱
//	public static Item ITEM_ORE_STONE = null;	//银矿石
//	public static Item ITEM_SMALL_PRIZE = null;	//小礼包
//	public static Item ITEM_MID_PRIZE = null;	//中礼包
//	public static Item ITEM_BIG_PRIZE = null;	//大礼包
	
//	public static Item ITEM_SUPER_PET_FOOD; 		//宠物食粮
//	public static Item ITEM_SUPER_PET_FOOD_USING; 	//使用中的宠物食粮
//	public static Item SLIVER_STONE = null;   //银矿石
	
//	public static Item ITEM_AUTO_REPAIR_ARM = null;   //修复神水
//	public static Item ITEM_AUTO_REPAIR_ARM_USING = null;//使用中的修复神水
//	
//	public static Item ITEM_BIG_AUTO_REPAIR_ARM = null;   //大修复神水
//	public static Item ITEM_BIG_AUTO_REPAIR_ARM_USING = null;//使用中的大 修复神水
	
	
	// --------------------------------------------------------------------------- 构造方法
	
	/**
	 * 默认构造 
	 **/
	public ItemMG(){
		
	}
	
	// --------------------------------------------------------------------------- 成员变量
	
	/**
	 * 游戏中所有道具资源集合 
	 * key=id,value=item
	 **/	
	private Map<Integer, Item> items = new HashMap<Integer, Item>();
	
	/**
	 * 宠物物品 
	 **/
	private List<Item> petItems = new LinkedList<Item>();
	
	
	//按级别分的可掉落装备
	private Map<Integer, ArrayList<Integer>> levelLegacyItems = new HashMap<Integer, ArrayList<Integer>>();
	
	/**
	 * 游戏中NPC道具集合 key=npcId value=item
	 **/	
	private Map<Integer,Map<Integer,List<Item>>> npcItems = new HashMap<Integer,Map<Integer,List<Item>>>();
	
	/**
	 * 游戏中的特殊道具资源 
	 **/
	private Map<String,Item> specialItems = new HashMap<String,Item>();
	
	/**
	 * 装备位置 
	 **/
	private TreeMap<Integer, ItemPosition> itemPositions = new TreeMap<Integer,ItemPosition>();
	
	/**
	 * 装备打造材料列表 
	 **/
	private Map<Integer,ItemForge> itemForges = new HashMap<Integer,ItemForge>();
	
	/**
	 * 装备强化材料列表
	 **/
	private List<ItemImprove> itemImproves = new ArrayList<ItemImprove>();
	
	/**
	 * 装备分解材料 
	 **/
	private ArrayList<ItemDecompose> itemDecomposes;
	//key=id,value=property
	private Map<Integer,ItemProperty> itemPropertyMap = new HashMap<Integer,ItemProperty>();
	
	/** 爆出特殊装备概率 */
	private Map<Integer,Map<Integer,ItemBetterProty>> holeOutMap = new HashMap<Integer,Map<Integer,ItemBetterProty>>();
	private Map<Integer,Map<Integer,ItemBetterProty>> ipveOutMap = new HashMap<Integer,Map<Integer,ItemBetterProty>>();
	private Map<Integer,Map<Integer,ItemBetterProty>> starOutMap = new HashMap<Integer,Map<Integer,ItemBetterProty>>();
	
	// --------------------------------------------------------------------------- 公共方法
	
	/**
	 * 初始化并加载所有资源 
	 **/
	public void init()throws PpseaException {
		//加载道具资源
		log.info("[ItemMG]{resources began to load the game props.....}");
		loadItems();
		log.info("[ItemMG]{start load npc itmes .....}");
		//加载NPC道具
		loadNpcItems();
		//加载游戏中特殊道具资源
		log.info("[ItemMG]{start load special itmes .....}");
		loadSpecialItems();	
		//加载装备位置
		log.info("[ItemMG]{start load item positions .....}");
		loadItemPositions();
	}
	
	/**
	 * 根据给定的ItemId返回道具资源,如果给定道具Id不存在则返回NULL 
	 **/
	public Item getItem(int itemId){
		return (items.get(itemId));
	}
	
	/**
	 * 返回所有的道具资源 
	 **/
	public List<Item> getAllItems(){
		return (Arrays.asList(items.values().toArray(new Item[0])));
	}

	/**
	 * 返回某个基本的所有道具id
	 */
	public ArrayList<Integer> getAllLegacyItemByLevel(Integer level) {
		return levelLegacyItems.get(level);
	}
	
	/**
	 * 返回指定NPC的所有道具资源 
	 * @param npcId 
	 * @param type 类型
	 **/
	public List<Item> getNpcItems(int npcId,int type){
		Map<Integer,List<Item>> itemMap = npcItems.get(Integer.valueOf(npcId));
		return (itemMap != null?itemMap.get(type):null);
	}
	
	/**
	 * 返回给定名称的特殊道具 
	 **/
	public Item getSpecialItem(String uniqueName){
		if(uniqueName == null || "".equals(uniqueName)) return null;
		return (specialItems.get(uniqueName));
	}
	
	/**
	 * 返回所有特殊道具资源 
	 **/
	public Map<String,Item> getAllSpecialItems(){
		return (specialItems);
	}
	
	/**
	 * 返回指定装备位置 
	 **/
	public ItemPosition getItemPostion(int id) {
		return itemPositions.get(id);
	}
	
	/**
	 * 返回打造道具材料 
	 **/
	public ItemForge getItemForge(int itemId){
		return itemForges.get(Integer.valueOf(itemId));
	}
	
	public Map<Integer,ItemForge> getAllItemForges() {
		return itemForges;
	}
	
	/**
	 * 返回强化资源
	 * @param level 准备强化的等级
	 **/
	public ItemImprove getItemImprove(int level){
		return (itemImproves.get(level-1));
	}
	
	/**
	 * 返回所有类型的装备强化资源 
	 **/
	public List<ItemImprove> getAllItemImprove(){
		return itemImproves;
	}
	
	/**
	 * 返回装备分解图
	 * @param level 装备等级
	 **/
//	public ItemDecompose getItemDecompose(PlayerItem pi){
//		ItemDecompose result = null;
//		if (pi == null) {
//			return null;
//		}
//		if (pi.getItem().getPosition() == null || pi.getItem().getPosition() == 0) {
//			return null;
//		}
//		int position = pi.getItem().getPosition();
//		int type = 0;
//		if (position == 1) {
//			type = 1;
//		} else if (position == 3 || position == 9 || position == 5) {
//			type = 3;
//		} else {
//			type =2;
//		}
//		int level = pi.getItem().getLevel();
//		int starLevel = pi.getStarLevel();
//		for(ItemDecompose itemDecompose : itemDecomposes){
//			if (level >= itemDecompose.getStartLevel().intValue()
//			        && level <= itemDecompose.getEndLevel().intValue()
//			        && starLevel == itemDecompose.getStarLevel()
//			        && type == itemDecompose.getType()) {
//				result = itemDecompose;
//				break;
//			}
//		}
//		return result;
//	}
	
	/**
	 * 返回爆出特殊属性概率
	 * @param itemType 装备类型 1-武器 2-防具
	 * @param level 装备等级
	 * @param type 类型 1-孔 2-强化 3-升星
	 **/
	public ItemBetterProty getItemBetterProty(int itemType,int level,int type){
		Map<Integer,Map<Integer,ItemBetterProty>> parentMap = null;
		if(type == ItemBetterProty.TYPE_OUT_HOLE) parentMap = holeOutMap;
		if(type == ItemBetterProty.TYPE_OUT_IPVE) parentMap = ipveOutMap;
		if(type == ItemBetterProty.TYPE_OUT_STAR) parentMap = starOutMap;
		if(parentMap == null)return null;
		Map<Integer,ItemBetterProty> map = parentMap.get(itemType);
		if(map == null)return null;
		return (map.get(level));
	}
	
	/**
	 * 返回宠物食品列表 
	 **/
	public List<Item> getPetItems(){
		return petItems;
	}
	
	/**
	 * 检查是否存在正在使用中的同组道具.
	 * @param pid
	 * @param itemId
	 * @return
	 */
	public boolean isGroupMember(Player player, int itemId) {
		ItemGroup itemGroup = ItemGroupMG.instance.getItemGroupByItemId(itemId);
		if (itemGroup == null) return false;
		
		//直接从内存读取
		List<PlayerItemUsing> usingItemList = player.getUsingItemList();
		
		//判断
		for (PlayerItemUsing usingItem : usingItemList) {
			ItemGroup usingItemGroup = ItemGroupMG.instance.getItemGroupByItemId(usingItem.getItemId());
			if (usingItemGroup == null)continue;
			else if (itemGroup.getGroupid().intValue() == usingItemGroup.getGroupid().intValue() ) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 返回给定的套装属性,如果没有该属性则返回NULL
	 * @param id 套装属性ID
	 **/
	public ItemProperty getItemSuitProperty(int id){
		return itemPropertyMap.get(id);
	}
	
	// --------------------------------------------------------------------------- 私有方法
	
	/**
	 * 从数据库加载道具资源
	 * @throws PpseaException 
	 **/
	private void loadItems() throws PpseaException{
		List<Item> itemList = DBManager.queryAllItems();
		for (Item item : itemList) {
			items.put(item.getId(), item);
			if (item.getLegacy()) {
				if (levelLegacyItems.containsKey(item.getLevel())) {
					levelLegacyItems.get(item.getLevel()).add(item.getId());
				} else {
					ArrayList<Integer> ls = new ArrayList<Integer>();
					ls.add(item.getId());
					levelLegacyItems.put(item.getLevel(), ls);
				}
			}
			//宠物食品
			if(item.getType().intValue() == Item.TYPE_PET_FOOD){
				petItems.add(item);
				log.info("{load pet food "+item.getName()+"...}");
			}
			Integer itemSuitId = item.getItemSuitId();
			ItemSuit itemSuit = ItemSuitMG.instance.getItemSuit(itemSuitId);
			if (itemSuit != null) {
				itemSuit.setItemNum(itemSuit.getItemNum() + 1);
			}
		}
		
		//特殊道具
//		ITEM_HYLOCEREUS_UNDATUS = getItem(Constants.getIntValue("item.hyus.id"));	
//		ITEM_FORGET_STONE = getItem(Constants.getIntValue("item.forget.id"));
//		ITEM_TALENT_FORGET = getItem(Constants.getIntValue("item.forget.stone"));
//		ENEMY_ITEM = getItem(Constants.getIntValue("item.enemy.id"));
//		SLIVER_STONE = getItem(10414);//银矿石ID
//		ITEM_MARRY_HAO_HUA= getItem(Constants.getIntValue("item.marry.haohua.id"));
//		ITEM_MARRY_SHE_HUA = getItem(Constants.getIntValue("item.marry.shehua.id"));
//		ITEM_MARRY_CHUAN_SONG = getItem(Constants.getIntValue("item.marry.chuansong.id"));
	}
	
	/**
	 * 加载游戏中NPC道具集合
	 **/
	private void loadNpcItems(){
		List<NpcItem>  npcItemsTmp = DBManager.queryAllNpcItems();
		Iterator<NpcItem> it = npcItemsTmp.iterator();
		while(it.hasNext()){
			NpcItem npcItem = it.next();
			Map<Integer,List<Item>> itemMap = npcItems.get(npcItem.getNpcId()); 
			if(itemMap  == null) {
				itemMap = new HashMap<Integer,List<Item>>();
				npcItems.put(npcItem.getNpcId(), itemMap);
			}
			List<Item> nits = itemMap.get(npcItem.getType());
			if(nits == null){
				nits = new LinkedList<Item>();
				itemMap.put(npcItem.getType(), nits);
			}
			Item item = items.get(npcItem.getItemId());
			if(item != null) nits.add(item);
			it.remove();
		}
	}
	
	/**
	 * 加载游戏中特殊道具资源 
	 * @throws PpseaException 
	 **/
	private void loadSpecialItems() throws PpseaException{
		ITEM_EMERY = getItem(31);
		ITEM_BOX = getItem(39);
		ITEM_BIGBOX = getItem(10282);
		ITEM_SMALL_PACKET = getItem(10333);//小行囊
//		ITEM_AUTO_REPAIR_ARM = getItem(10457);//修复神水
//		ITEM_AUTO_REPAIR_ARM_USING = getItem(10458);//使用中的修复神水
//		ITEM_BIG_AUTO_REPAIR_ARM = getItem(10532);//大修复神水
//		ITEM_BIG_AUTO_REPAIR_ARM_USING = getItem(10533);//大修复神水
		
		//装备强化石头等
//		ITEM_IPVE_JEWEL = getItem(Constants.getIntValue("item.ipve.jewel")); //强化宝石
//		ITEM_IPVE_STONE = getItem(Constants.getIntValue("item.ipve.stone")); //强化石
//		ITEM_STAR_JEWEL = getItem(Constants.getIntValue("item.star.jewel")); //升星宝石
//		ITEM_STAR_STONE = getItem(Constants.getIntValue("item.star.stone")); //升星石
		ITEM_HOLE_JEWEL = getItem(Constants.getIntValue("item.hole.jewel")); //精钢宝钻
		ITEM_HOLE_STONE = getItem(Constants.getIntValue("item.hole.stone")); //精钢钻 
//		ITEM_QI_XI_GIFT = getItem(Constants.getIntValue("item.qixi.gift")); //精钢钻
		
		//宠物
//		ITEM_SUPER_PET_FOOD = getItem(Constants.getIntValue("item.pet_food.id")); //宠物粮食
//		ITEM_SUPER_PET_FOOD_USING = getItem(Constants.getIntValue("item.pet_food.using.id")); //使用中的宠物粮食
//		IDENTIFY_SCROLL = getItem(Constants.getIntValue("item.scroll.id")); 			//鉴定卷轴
//		UNKOWN_ITEM = getItem(Constants.getIntValue("item.unknown.id")); 				//未知道具
//		ITEM_CASH_BOX = getItem(Constants.getIntValue("item.cash.box")); 				//钱箱
//		ITEM_ORE_STONE = getItem(Constants.getIntValue("item.ore.id")); 				//银矿石
//		ITEM_SMALL_PRIZE = getItem(Constants.getIntValue("item.small.prize")); 			//小礼包
//		ITEM_MID_PRIZE = getItem(Constants.getIntValue("item.mid.prize")); 				//中礼包
//		ITEM_BIG_PRIZE = getItem(Constants.getIntValue("item.big.prize")); 				//大礼包
		ITEM_CITY_CHUANSONG = getItem(Constants.getIntValue("item.city.chuansong"));	//传送令O
	}
	
	/**
	 * 加载装备位置 
	 **/
	private void loadItemPositions(){
		List<ItemPosition>  ipTemp = DBManager.queryAllItemPositions();
		Iterator<ItemPosition> it = ipTemp.iterator();
		while(it.hasNext()){
			ItemPosition itemPosition = it.next();	
			if (itemPosition.getId() != 0) {
				itemPositions.put(itemPosition.getId(), itemPosition);
			}
			it.remove();
		}
		ipTemp = null;
	}
	
	/**
	 * 加载游戏中装备铸造材料列表 
	 **/
	private void loadItemForges(){
		List<ItemForge>  forges = DBManager.queryAllItemForges();
		for(ItemForge itemForge : forges){
			itemForges.put(itemForge.getItemId(), itemForge);
		}
	}
	
	/**
	 * 加载游戏中装备强化方案
	 **/
	private void loadItemImproves(){
		itemImproves.clear();
		List<ItemImprove>  improves = DBManager.queryAllItemImproves();
		//初始化
		for(int i=0;i<improves.size();i++){
			itemImproves.add(i, null);
		}
		//添加
		for(ItemImprove improve : improves){
			itemImproves.add(improve.getImproveLevel()-1, improve);
		}
	}
	
	/** 特殊装备爆出概率 */
	private void loadItemBetterProty(){
		List<ItemBetterProty> allLst = DBManager.queryAllItemBetterProty();
		int type = ItemBetterProty.TYPE_OUT_HOLE;
		Map<Integer,ItemBetterProty> map = null;
		Map<Integer,Map<Integer,ItemBetterProty>> parent = null;
		for(ItemBetterProty proty : allLst){
			type = proty.getType().intValue();
			if(type == ItemBetterProty.TYPE_OUT_HOLE){
				map = holeOutMap.get(proty.getUseType());
				parent = holeOutMap;
			}
			else if(type == ItemBetterProty.TYPE_OUT_IPVE){
				map = ipveOutMap.get(proty.getUseType());
				parent = ipveOutMap;
			}
			else if(type == ItemBetterProty.TYPE_OUT_STAR){
				map = starOutMap.get(proty.getUseType());
				parent = starOutMap;
			}
			if(map == null) {
				map = new HashMap<Integer,ItemBetterProty>();
				parent.put(proty.getUseType(), map);
			}
			map.put(proty.getItemLevel(), proty);
			{
				//装备概率数组
				String prbty = proty.getProabilityArray();
				if(prbty == null || prbty.length() == 0)continue;
				String[] prbtyStrArray = prbty.split("[,]");
				int[] prbtyArray = new int[prbtyStrArray.length]; 
				for(int i=0;i<prbtyStrArray.length;i++){
					prbtyArray[i] = Integer.parseInt(prbtyStrArray[i]);
				}
				proty.setPrbtyArray(prbtyArray);
			}
		}
	}
	
	/**
	 * 加载套装使用属性集合 
	 **/
	private void loadItemProperty(){
		List<ItemProperty>  propertyLst = DBManager.queryAllItemSuitProperty();
		for(ItemProperty property : propertyLst){
			if(property.getUseType().intValue() == ItemProperty.USE_TYPE_SUIT){
				itemPropertyMap.put(property.getId(), property);
			}
		}
	}
	
	/**
	 * 强化辅助类对象
	 **/
	class ItemImproveWrapper implements java.io.Serializable {
		private static final int MAX_IMPROVE_LEVEL = 12;//最大强化等级
		private static final long serialVersionUID = 1L;
		Item item;//强化的装备
		List<ItemImprove> improves = new ArrayList<ItemImprove>(MAX_IMPROVE_LEVEL);
		ItemImproveWrapper(Item item){
			this.item = item;
		}
		public int addItemImprove(ItemImprove improve){
			if(improve == null) return -1;
			int index = improve.getImproveLevel().intValue() - 1;
			improves.add(index, improve);
			return index;
		}		
		public ItemImprove getItemImprove(int level){
			ItemImprove improve = null;
			try{
				improve = improves.get(--level);
			}
			catch(IndexOutOfBoundsException e){
				improve = null;
				log.error("[ItemMG]{can not found improve source.}", e);
			}
			return improve;
		}
		public int hashCode(){
			HashCodeBuilder hashBuilder = new HashCodeBuilder();
			hashBuilder.append(improves);
			return hashBuilder.toHashCode();
		}		
	}

	public TreeMap<Integer, ItemPosition> getItemPositions() {
		return itemPositions;
	}	
}

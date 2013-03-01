package com.ppsea.ds.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.CityFacilityNpc;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.ItemForge;
import com.ppsea.ds.data.model.ItemImprove;
import com.ppsea.ds.data.model.ItemProperty;
import com.ppsea.ds.data.model.Npc;
import com.ppsea.ds.data.model.NpcItem;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.manager.NpcMG;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;

/**
 * 游戏道具辅助类
 **/
public class ItemUtil {
	
	/**
	 * 随机对象 
	 **/
	private static Random RAND = new Random();
	
	/**
	 * 获取给的NPC可以进行铸造装备的列表,如果该NPC没有可以进行铸造列表
	 * 则返回大小为0的集合 
	 * @param npcId NPC
	 **/
	public static List<Item> getNpcForgeList(int npcId){
		List<Item> items = ItemMG.instance.getNpcItems(npcId,NpcItem.NPC_ITEM_TYPE_FORGE);
		if(items == null) return new LinkedList<Item>();
		List<Item> forgeList = new LinkedList<Item>();
		for(Item item : items){
			if(item != null)forgeList.add(item);
		}
		return forgeList;
		
	}
	
	/**
	 * 获取给的NPC可以进行合成宝石的列表,如果该NPC没有可以进行合成列表
	 * 则返回大小为0的集合 
	 * @param npcId NPC
	 **/
	public static List<Item> getNpcComposeList(int npcId , int type){
		List<Item> items = ItemMG.instance.getNpcItems(npcId,NpcItem.NPC_ITEM_TYPE_COMPOSE);
		if(items == null) return new LinkedList<Item>();
		List<Item> forgeList = new LinkedList<Item>();
		for(Item item : items){
			ItemForge itemForge = ItemMG.instance.getItemForge(item.getId());
			if(itemForge == null)continue;
			if(item != null && item.getType() == Item.GEM_TYPE &&
				itemForge.getSecondType().intValue() == type)
				forgeList.add(item);
		}
		return forgeList;
		
	}
	
	/**
	 * 获取给定道具铸造材料列表
	 * @param item 准备铸造的道具
	 * @param copper 铸造装备需要金钱
	 **/
	public static MaterialRequirement getForgeMaterialList(Item item){
		if(item == null)return null;//返回空
		ItemForge itemForge = ItemMG.instance.getItemForge(item.getId());
		if(itemForge == null) return null;//返回空
		MaterialRequirement material = new MaterialRequirement();
		String[] materialArray = itemForge.getMaterial().split("[|]");
		for(String i2:materialArray){
			Item item2 = ItemMG.instance.getItem(Integer.parseInt(i2.split("[+]")[0]));
			material.addMaterial(item2, Integer.parseInt(i2.split("[+]")[1]));
		}
		material.setCopper(itemForge.getCopper().intValue());
		return material;
	}
	
	/**
	 * 获取给定道具升星材料列表
	 * @param item 准备升星的道具
	 * @param nextLevel 升星下一下等级
	 * @param cooper 升星需要金钱
	 **/
	public static MaterialRequirement getPromoteMaterialList(Item item , int nextLevel){
		if(item == null || nextLevel <= 0 || nextLevel > ItemService.MAX_STAR_LEVEL)
		return null;//返回空
		MaterialRequirement material = new MaterialRequirement();	
//		material.addMaterial(ItemMG.ITEM_STAR_STONE, 1);
//		//金钱
//		material.setCopper(ItemUtil.getStarCopper(nextLevel));		 
		return material;
	}
	
	/**
	 * 获取给定道具强化材料列表
	 * @param item 准备升星的道具
	 * @param nextLevel 升星下一下等级
	 * @param cooper 升星需要金钱
	 **/
	public static MaterialRequirement getImproveMaterialList(Item item , int nextLevel){
//		if(item == null || nextLevel <= 0 || nextLevel > ItemService.MAX_IMPROVE_LEVEL)
//		return null;//返回空
//		ItemImprove itemImprove = ItemMG.instance.getItemImprove(nextLevel);
//		if(itemImprove == null) return null;//返回空
		MaterialRequirement material = new MaterialRequirement();	
//		material.addMaterial(ItemMG.ITEM_IPVE_STONE, ItemUtil.getMaterialNum(nextLevel));
//		//金钱
//		material.setCopper(itemImprove.getCopper());		 
		return material;
	}
	
	/**
	 * 验证是否可以进行升星
	 **/
	public static ErrorMsg checkPromote(Player player,PlayerItem playerItem){
		//等级升星等级验证
		if(playerItem == null || playerItem.item.getType() != Item.ARM_TYPE){
			return new ErrorMsg(ErrorCode.ERR_SYS,"没有改装备或者该装备不能升星");
		}
		int currentLevel = playerItem.getStarLevel().intValue();
		if(currentLevel <= 0 )currentLevel = 0;
		if(currentLevel+1 > ItemService.MAX_STAR_LEVEL){//已经升到4星了
			return new ErrorMsg(ErrorCode.ERR_STAR_LIMIT,"装备最多只能升"+ItemService.MAX_STAR_LEVEL+"星");
		}
		return Constants.SUCC;
	}
	
	/**
	 * 验证是否可以进行强化
	 * @param player 使用者
	 * @param item 要进行强化的装备
	 **/
//	public static ErrorMsg checkImprove(Player player,PlayerItem playerItem){
//		//强化等级验证
//		if(playerItem == null || playerItem.item.getType() != Item.ARM_TYPE){
//			return new ErrorMsg(ErrorCode.ERR_SYS,"没有该装备或者该装备为非强化类装备");
//		}
//		int currentLevel = playerItem.getImproveLevel().intValue();
//		if(currentLevel <= 0 )currentLevel = 0;
//		if(currentLevel+1 > ItemService.MAX_IMPROVE_LEVEL){//强化到最高等级
//			return new ErrorMsg(ErrorCode.ERR_IMPROVE_LIMIT,"最多只能强化到"+ItemService.MAX_IMPROVE_LEVEL+"级");
//		}
//		return Constants.SUCC;
//	}
	
	/**
	 * 验证分解装备是否合法 
	 **/
	public static ErrorMsg checkDecompose(Player player,PlayerItem item){
		if(item == null) return new ErrorMsg(ErrorCode.ERR_NO_ITEM,"物品不存在");
		//是否为自己的装备
		if(item.checkBindOther(player.getId())){
			return new ErrorMsg(ErrorCode.ERR_BIND,"不能分解别人的装备");
		}
		//是否为装备类
		if(item.getItem().getType().intValue() != Item.ARM_TYPE){
			return new ErrorMsg(ErrorCode.ERR_NOT_SPECIFY_TYPE,"只能分解装备");
		}
		//是否正在使用
		if(item.getIsUse().intValue() == 1){
			return new ErrorMsg(ErrorCode.ERR_ITEM_IN_USE,"装备正在使用不能进行分解");
		}
		return Constants.SUCC;
	}
	
	/**
	 * 
	 * @param player
	 *            玩家对象实例.
	 * @param type
	 *            10：采集 11: 分解 12: 锻造 13: 制药.
	 * @param level
	 *            玩家处理的装备水平级别.
	 * @return true: 玩家的技能水平大于当前装备需求的级别 false：玩家技能不足，不能操作该级别的装备.
	 */
//	public static boolean checkPlayerSkillLevel(Player player, int type, int level) {
//		Map<Integer, List<PlayerSkill>> map = player.getSkills();
//		if (map == null || map.size() == 0) {
//			return false;
//		}
//		List<PlayerSkill> list = map.get(type);
//		if (list == null || list.size() == 0) {
//			return false;
//		}
//		PlayerSkill skill = list.get(0);
//		SectZs zs = (SectZs) skill.getSectSkill();
//		int ownedLevel = zs.getMp();
//		if (ownedLevel >= level) {
//			return true;
//		}
//		return false;
//	}
	
	/**
	 * 计算附加属性字符串
	 * @param oldValue 旧字符串
	 * @param propertyId 新属性ID
	 **/
	public static String computeProperty(String oldValue,int propertyId){
		String newProperty = "";
		if(oldValue == null || "".equals(oldValue)){
			newProperty = String.valueOf(propertyId);
		}
		else{
			newProperty = oldValue.trim()+","+propertyId;
		}			
		return newProperty;
	}
	
	/**
	 * 验证玩家材料是否齐全
	 * @param  
	 **/
	public static MaterialRequirement checkMaterialRequirement(Player player,String[] materialArray,List<PlayerItem> playerItems,int[] amounts){
		int index = 0; 
		boolean keep = true; 
		ItemUtil.MaterialRequirement requireMaterial = new ItemUtil.MaterialRequirement();
		for(String itemAmount : materialArray){		
			Item temp = ItemMG.instance.getItem(Integer.parseInt(itemAmount.split("[+]")[0]));
			int amount = Integer.parseInt(itemAmount.split("[+]")[1]);
			PlayerItem playerItem = ItemService.findPlayerItem(player, temp);
			if(playerItem == null){
				requireMaterial.addMaterial(temp, amount);
				keep = false;
			}
			if(keep && playerItem.getAmount() < amount){				
				requireMaterial.addMaterial(temp, amount - playerItem.getAmount());
				keep = false;
			}			
			playerItems.add(index, playerItem);
			amounts[index] = amount;
			index++;			
		}	
		return (keep?null:requireMaterial);
	}
	
	/**
	 * 验证玩家材料是否齐全
	 * @param  
	 **/
	public static MaterialRequirement checkMaterialRequirement(PlayerItem stoneMaterial,PlayerItem jewelMaterial,
			int num,List<PlayerItem> playerItems,int[] amounts,Item needItem){
		int hasNum = 0;
		if(stoneMaterial != null && (stoneMaterial.getBindId() ==null || stoneMaterial.getBindId() ==0) && !stoneMaterial.getInExchange()) {
			hasNum += stoneMaterial.getAmount();			
		}
		if(jewelMaterial != null  && (jewelMaterial.getBindId() ==null || jewelMaterial.getBindId() ==0) && !jewelMaterial.getInExchange()) {
			hasNum += jewelMaterial.getAmount();
		}
		if(hasNum < num){//材料不够
			ItemUtil.MaterialRequirement requireMaterial = new ItemUtil.MaterialRequirement();
			requireMaterial.addMaterial(needItem, num - hasNum);
			return requireMaterial;
		}

		int idx = 0,usedNum = 0;
		
		
		if(stoneMaterial != null && stoneMaterial.getAmount() - num >= 0 && ((stoneMaterial.getBindId() ==null || stoneMaterial.getBindId() ==0) && !stoneMaterial.getInExchange())){
			playerItems.add(stoneMaterial);
			amounts[idx++] = num;
			usedNum += num;
		}
		else if(stoneMaterial != null && usedNum < num && num -  stoneMaterial.getAmount() > 0 && ((stoneMaterial.getBindId() ==null || stoneMaterial.getBindId() ==0) && !stoneMaterial.getInExchange())){
			playerItems.add(stoneMaterial);
			amounts[idx++] = stoneMaterial.getAmount();
			usedNum += stoneMaterial.getAmount();
		}
		if(jewelMaterial != null && usedNum < num && jewelMaterial.getAmount() - num >= 0 && ((jewelMaterial.getBindId() ==null || jewelMaterial.getBindId() ==0) && !jewelMaterial.getInExchange())){
			playerItems.add(jewelMaterial);
			amounts[idx++] = num;
			usedNum += num;
		}
		else if(jewelMaterial != null && usedNum < num && num -  jewelMaterial.getAmount() > 0 && ((jewelMaterial.getBindId() ==null || jewelMaterial.getBindId() ==0) && !jewelMaterial.getInExchange())){
			playerItems.add(jewelMaterial);
			amounts[idx++] = jewelMaterial.getAmount();
		}
		return null;
	}
	
	
	/**
	 * 材料需求
	 **/
	public static class MaterialRequirement implements java.io.Serializable {
		public static final int INIT_CAPACITY = 6;
		private static final long serialVersionUID = 1L;
		private List<Item> items = new ArrayList<Item>(INIT_CAPACITY);	//材料列表
		private List<Integer> amounts = new ArrayList<Integer>(INIT_CAPACITY);//材料数量
		private int copper = 0;			//金钱
		private int gold = 0;			//金
		private transient int index = 0;
		private int degree = 0;
		/**
         * @return the degree
         */
        public int getDegree() {
        	return degree;
        }
		/**
         * @param degree the degree to set
         */
        public void setDegree(int degree) {
        	this.degree = degree;
        }
		public void addMaterial(Item item,int amount){
			items.add(index, item);
			amounts.add(index, amount);
			index ++;
		}
		public void setCopper(int copper){ this.copper = copper; };
		public int getCopper(){ return copper; }	
		public void setGold(int gold){ this.gold = gold; }
		public int getGold(){ return gold; }
		public List<Item> getItems(){ return items; }
		public List<Integer> getAmounts(){ return amounts; }
	}
	
	/**
	 * 增加给的对象给的属性的值 目前支持数值整数
	 * @param obj 要设置属性值的对象
	 * @param property 属性名
	 * @param 设置的数值
	 * @param flag 标志
	 * @param isPercent 是否为百分比
	 **/
	public static void appendProperty(Object obj,String property,int value,int flag){
		if(obj == null || property == null || "".equals(property)) return;
		try{
			Class<?> clazz = obj.getClass();
			Field field = clazz.getDeclaredField(property);
			boolean accessible = field.isAccessible();
			if(!accessible)field.setAccessible(true);
			int oldValue = field.getInt(obj);		
			field.setInt(obj, oldValue + value* flag);						
			field.setAccessible(accessible);
		}
		catch(Exception e){
			
		}
	}
	
	/**
	 * 提取装备升星附加属性集合
	 * @param playerItem 装备 
	 **/
	public static List<ItemProperty> extraItemProperties(PlayerItem playerItem){
		if(playerItem == null || playerItem.getStarLevel().intValue() <= 0) 
			return new LinkedList<ItemProperty>();
		List<ItemProperty> properties = new LinkedList<ItemProperty>();
		String promoteProperty = playerItem.getPromoteProperty();
		String[] propertyArray = ((promoteProperty == null || "".equals(promoteProperty))?new String[0]:promoteProperty.split("[,]"));
		int starLevel = playerItem.getStarLevel().intValue();
		for(int i=0;i<propertyArray.length && i<starLevel;i++){
			Integer propertyId = -1;
			try {
				propertyId = Integer.parseInt(propertyArray[i]);
			}catch(Exception e){					
			}
			ItemProperty itemProperty = PropertyBuilder.instance.getItemProperty(propertyId);
			if(itemProperty != null)properties.add(itemProperty);
		}
		return properties;
	}
	
	/**
	 * 提取装备强化附加属性集合
	 * @param playerItem 装备 
	 **/
	public static List<ItemProperty> extraItemImproves(PlayerItem playerItem){
		return null;
		/*
		if(playerItem == null || playerItem.getImproveLevel().intValue() <= 0) 
			return new LinkedList<ItemProperty>();
		List<ItemProperty> improves = new LinkedList<ItemProperty>();
		String improveProperty = playerItem.getImproveProperty();
		String[] propertyArray = ((improveProperty == null || "".equals(improveProperty))?new String[0]:improveProperty.split("[,]"));		
		int improveLevel = playerItem.getImproveLevel().intValue();
		for(int i=0;i<propertyArray.length && i<improveLevel;i++){
			int propertyId = Integer.valueOf(propertyArray[i]);
			ItemProperty itemProperty = PropertyBuilder.instance.getItemProperty(propertyId);
			if(itemProperty != null)improves.add(itemProperty);
		}
		return improves;
	*/}
	
    /**
     * 重新计算属性字符串
     * @param propertyList 属性列表
     **/
    public static String remomputeProperty(List<ItemProperty> propertyList){
    	String propertyLst = "";
    	int index = 0;
    	for(ItemProperty property : propertyList){
    		if(property != null){
    			if(index == 0)propertyLst += property.getId();
    			else {
    				propertyLst = propertyLst + ","+property.getId();    				
    			}
    			index ++;
    		}
    	}
    	return propertyLst;
    }
    
    /**
     * 验证两件装备是否可以相互镶嵌,如果可以镶嵌则返回true 
     **/
    public static boolean isMergeable(Item master,Item slaver){
    	if(master == null || slaver == null) return false;
    	String appendPos = slaver.getAppendPosition();
    	if(master.getType().intValue() != Item.ARM_TYPE) return false;
    	if(appendPos == null || "".equals(appendPos))return true;
    	String[] posArray = appendPos.split("[,]");
    	boolean found = false;
    	for(int i=0;i<posArray.length;i++){
    		if(posArray[i] != null && posArray[i].equals(master.getPositionStr())){
    			found = true;
    			break;
    		}
    	}
    	return found;
    }
    
    /**
     * 检查给定的物品是否可以进行交易,如果该物品可以进行交易则返回true,否则返回false
     * @param player 道具所属玩家
     * @param playerItem 要卖出的道具 
     **/
    public static boolean sellCheck(Player player , PlayerItem playerItem){
    	return (playerItem.item.getIsExchange());
    }
    
    /**
     * 验证玩家和NPC是否在相同的设施中 
     **/
    public static boolean bothInFacility(Player player,int npcId){
    	return bothInFacility(player,NpcMG.instance.getNpc(npcId));
    }
    
    /**
     * 验证玩家和NPC是否在相同的设施中 
     **/
    public static boolean bothInFacility(Player player,Npc npc){
    	if(player == null || npc == null) return false;
    	int cityFacilityId = player.getCityFacilityId();
		Map<Integer, CityFacilityNpc> facilityNpcs = NpcMG.instance.getNpcInFacility(cityFacilityId);
		Iterator<CityFacilityNpc> itr = facilityNpcs.values().iterator();
		boolean isIn = false;
		while(itr.hasNext()){
			if(itr.next().getNpcId().intValue() == npc.getId().intValue()){
				isIn = true;break;
			}
		}		
    	return isIn;
    }
    
    /**
     * 返回玩家身上指定类型的道具列表,如果玩家身上没有给定类的道具返回NULL
     **/
    public static List<PlayerItem> getNonArmsByType(Player player,int type){
    	Map<String,PlayerItem> itemMap = player.getNonArms().get(String.valueOf(type));
    	if(itemMap == null || itemMap.size() == 0)return null;
    	return Arrays.asList(itemMap.values().toArray(new PlayerItem[itemMap.size()]));
    }
    
    /**
     * 源材料是否可以合成目标材料,如果可以合成目标材料返回true
     * @param src 源材料
     * @param dst 目标材料
     **/
    public static boolean canComposeFrom(Item src,Item dst){
    	ItemForge itemForge = ItemMG.instance.getItemForge(dst.getId());
    	if(itemForge == null) return false;
    	String materialStr = itemForge.getMaterial();
    	if(materialStr == null || materialStr.length() <= 0)return false;
    	boolean isCan = false;
    	String[] materialArray = materialStr.split("[|]");
    	for(int i=0;i<materialArray.length;i++){
    		if(materialArray[i].indexOf(src.getId().toString()) >= 0){
    			isCan = true;
    			break;
    		}
    	}
    	return isCan;
    } 
    
    /**
     * 抽取材料对象
     * @param itemForge
     **/
    public static MaterialRequirement extractMaterialRequirement(ItemForge itemForge){
    	if(itemForge == null) return null;
    	MaterialRequirement material = new MaterialRequirement();
    	String materialStr = itemForge.getMaterial();
    	String[] materialArray = materialStr.split("[|]");
    	for(int i=0;i<materialArray.length;i++){
    		String[] array = materialArray[i].split("[+]");
    		int itemId = 0,amount = 0;
    		try { itemId = Integer.parseInt(array[0]);
    		}catch(Exception e){}
    		try { amount = Integer.parseInt(array[1]);
    		}catch(Exception e){}
    		Item item = ItemMG.instance.getItem(itemId);
    		if(item == null) continue;
    		material.addMaterial(item, amount);
    	}
		material.setCopper(itemForge.getCopper());
		material.setGold(itemForge.getGold());
		material.setDegree(itemForge.getDegree());
		return material;
    }
    
    /**
     * 返回装备强化材料个数
     * @param 强化等级
     **/
    public static int getMaterialNum(int level){
    	if(level >= 0 && level <= 4 )return 1;
    	else if(level > 4 && level <= 8)return 2;
    	else return 3;
    }   
    
    /**
     * 返回装备强化材料个数
     * @param 强化等级
     **/
    public static int getStarCopper(int level){
    	if(level == 1)return 10;
    	if(level == 2)return 20;
    	if(level == 3)return 30;
    	return 100;
    }  
    
    
    /**
     * 转换为使用类型
     * @param item 要转换的道具
     **/
    public static int convertUseType(Item item){
    	int pos = item.getPosition().intValue();
    	if(pos == 1)return Item.USE_TYPE_WQ;
    	else if(pos > 1 && pos <= 10) return Item.USE_TYPE_FJ;
    	return 11;
    }
    
    /**
     * 爆出特殊属性个数
     * @param probyArray 概率数组
     * @param prob 概率
     **/
    public static int bost(int[] probyArray){
    	if(probyArray == null || probyArray.length <= 1)return 0;
    	int bost = 0,prob = 0;
    	for(int i=0;i<probyArray.length;i++){
    		prob = (int)(Math.random()*100);
    		if(prob <= probyArray[i]){
    			bost = i;
    			break;
    		}
    	}
    	return bost;
    }
	
	/**
	 * 设置返回错误消息 
	 **/
	public static void setResult(CommandResult result,String status,int errorCode,String msg){
		result.setStatus(status);
		ErrorMsg errMsg = new ErrorMsg(errorCode, msg);
		result.setVO(Command.TAG_ERR_MSG, errMsg);
	}
	
	/**
	 * 开箱子抽取银 
	 **/
	public static int extractCopper(){
		int n = RAND.nextInt(10000);
		int copper = 3000;
		if( n < 72) copper = 20000;			//20银 万分之72
		else if( n < 145) copper = 10000;	//10银 万分之145
		else if( n < 360) copper = 6000;	//6银 万分之360	
		else if( n < 800) copper = 5000;	//5银 万分之800
		else if( n < 6000) copper =4000;	//4银 万分之800
		else copper = 3000;					//3银 默认
		return copper;
	}
	
	/**
	 * 提炼金钱
	 **/
	public static int epurateCopper(){
//		int n = RAND.nextInt(10000);
//		int copper = 30000;
//		if( n < 30) copper = 200000;		//200银 万分之20
//		else if( n < 130) copper = 150000;	//150银 万分之50
//		else if( n < 327) copper = 100000;	//100银 万分之100
//		else if( n < 907) copper = 90000;	//90银 万分之200
//		else if( n < 1817) copper = 80000;	//80银 万分之300	
//		else if( n < 3453) copper = 40000;	//40银 万分之6000		
//		else if( n < 6072) copper = 50000;	//50银 万分之3500				
//		else if( n < 7643) copper = 70000;	//70银 万分之400
//		else if( n < 9057) copper = 60000;	//60银 万分之700		
//		else copper = 30000;				//20银 默认
		return 100000;
	}
	
	public static void main(String[] args) throws Exception{		
		int[] array = new int[]{70,25,5};
		System.out.println("bost:"+bost(array));
	} 
	
	/**
	 * 检查道具对装备影响,如果道具是不可交易的则对装备进行镶嵌和强化等操作
	 * 以后,装备也变成不可以交易的. 
	 **/
	public static RedirectResult checkBindRedirect(Item item){
		if(item != null && item.getIsExchange().booleanValue() == false)
			return new RedirectResult(item);
		return null;
	}
	
	/**
	 * 检查道具对装备影响,如果道具是不可交易的则对装备进行镶嵌和强化等操作
	 * 以后,装备也变成不可以交易的. 
	 **/
	public static RedirectResult checkBindRedirect(List<PlayerItem> items){
		RedirectResult redirectResult = null;
		for(PlayerItem item : items){
			redirectResult = checkBindRedirect(item.item);
			if(redirectResult != null)return redirectResult;
		}
		return redirectResult;
	}
	
	/**
	 * 结果 
	 **/
	public static class RedirectResult implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		private String 	ctnueCmd;
		private String 	backCmd;
		private String 	param;
		private Item	item;		
		public RedirectResult (Item item){
			this.item = item;
		}
		public String getCtnueCmd() {
			return ctnueCmd;
		}
		public void setCtnueCmd(String ctnueCmd) {
			this.ctnueCmd = ctnueCmd;
		}
		public String getBackCmd() {
			return backCmd;
		}
		public void setBackCmd(String backCmd) {
			this.backCmd = backCmd;
		}
		public String getParam() {
			return param;
		}
		public void setParam(String param) {
			this.param = param;
		}
		public Item getItem() {
			return item;
		}
		public void setItem(Item item) {
			this.item = item;
		}		
	}
}
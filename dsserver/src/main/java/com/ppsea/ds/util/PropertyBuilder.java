package com.ppsea.ds.util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import com.ppsea.ds.data.model.ItemProperty;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.DBManager;
import com.ppsea.ds.util.item.PropertyHandler;

/**
 * 升星成功附加属性产生类
 **/
public class PropertyBuilder implements PropertyHandler {
	
	// ------------------------------------------------------------------------------------- 静态常量
	
	/**
	 * 铸造装备升星几率
	 **/
	public static final int NORMAL_PROBABILITY = 90;//普通为90
	public static final int ONE_STAR_PROBABILITY = 9;//一星为9
	public static final int TWO_STAR_PROBABILITY = 1;//二星为9
	
	public static final int PROPERTY_TYPE_PROMOTE = 0;//升星属性
	public static final int PROPERTY_TYPE_IMPROVE = 1;//强化属性
	
	/**
	 * Log 
	 **/
	private static final Logger log = Logger.getLogger(PropertyBuilder.class);
	
	/**
	 * 包名前缀 
	 **//*
	private static final String PACKAGE_NAME = "com.ppsea.ds.util"; 
	
	*//**
	 * 类模板文件 
	 **//*
	private static final String TEMPLATE = "package $packageName;" +
											"import com.ppsea.ds.data.model.Player;"+
											"import com.ppsea.ds.data.model.PlayerItem;"+
											"import com.ppsea.ds.util.item.PpseaCompilerException;"+
											"import com.ppsea.ds.exception.PpseaException;"+
											"import static com.ppsea.ds.util.ItemUtil.appendProperty;"+
											"public class $className implements com.ppsea.ds.util.item.PropertyHandler {"+
											"public void handlePromoteProperty(Player player,PlayerItem item,int flag)throws PpseaException {"+
											"try{$body}catch(Exception e){ throw new PpseaException(e.getMessage());}}"+
											"public void handleImproveProperty(Player player,PlayerItem item,int flag)throws PpseaException {"+
											"try{$body}catch(Exception e){ throw new PpseaException(e.getMessage());}}}";
	
	 private static final String TARGET_VERSION = "1.6"; */
	   
	/* *//**
	  * 编译器 
	  **//*
	 private final SimpleCompiler<PropertyHandler> compiler = new SimpleCompiler<PropertyHandler>(getClass()   
	            	.getClassLoader(), Arrays.asList(new String[] { "-target", TARGET_VERSION, "-encoding",Utils.ENCODING }));   */
	
	static PropertyBuilder instance = null;
	boolean isInit = false;
	ItemProperty DEFAULT_PROPERTY = null;
	Random RAND = new Random(System.currentTimeMillis());//随机
	Map<Integer, ItemProperty> ITEM_PROPERTY_MAP = new HashMap<Integer, ItemProperty>();
	List<ItemProperty> RANDOM_PROPERTY_POOL = new ArrayList<ItemProperty>(100);//随机池	
	
	//装备铸造
	int[] starArray = new int[100];
	
	/**
	 * 产生随机数 
	 **/
	public int random(int n){
		return (RAND.nextInt(n)); 
	}
	
	public boolean isInit(){
		return isInit;
	}
	
	/**
	 * 初始化 
	 **/
	public void initialize(){
		//升星
		log.info("[PropertyBuilder]{PropertyBuilder start initialization ...}");
		ITEM_PROPERTY_MAP.clear();
		List<ItemProperty> itemProperties = DBManager.queryAllItemStarProperty();
		int count = 0,index = 0;
		for(ItemProperty property : itemProperties){
			if(count + 1 == itemProperties.size())DEFAULT_PROPERTY = property;//最后一个属性
			ITEM_PROPERTY_MAP.put(property.getId(), property);
			count ++;
			int probability = property.getProbability().intValue();
			for(int i=0;i<probability;i++){
				RANDOM_PROPERTY_POOL.add(index++, property);
			}
			//升星属性产生器
			//promoteHandlers.put(property.getId(), newPropertyHandler(String.valueOf(property.getId()),property.getScriptBody()));
		}
		log.info("[PropertyBuilder]{PropertyBuilder improve initialization ...}");
		
		//铸造
		index = 89;
		for(int i=0;i < ONE_STAR_PROBABILITY;i++){//一星
			starArray[index++] = 1;
		}
		for(int i=0;i < TWO_STAR_PROBABILITY;i++){//二星
			starArray[index++] = 2;
		}
		isInit = true;
		log.info("[PropertyBuilder]{PropertyBuilder complete ...}");
	}
	
	/**
	 * 返回指定ID的附加属性 
	 **/
	public ItemProperty getItemProperty(int propertyId){
		return ITEM_PROPERTY_MAP.get(Integer.valueOf(propertyId));
	}
	
	/**
	 * 设置玩家附加属性
	 * @param 使用者
	 * @param item 装备 
	 **/
	public void handlePromoteProperty(Player player,PlayerItem item,int flag)throws PpseaException {
		
	}
	
	/**
	 * 设置玩家强化附加属性
	 * @param 使用者
	 * @param item 装备 
	 **/
	public void handleImproveProperty(Player player,PlayerItem item,int flag)throws PpseaException{
		
	}
	
	/**
	 * 产生装备随机升星个数 
	 **/
	public int randomPromote(){
		int index = RAND.nextInt(100);
		return starArray[index];
	}
	
	/**
	 * 随机产生一个属性 属性可以重复
	 **/
	public ItemProperty build(){
		int index = RAND.nextInt(RANDOM_PROPERTY_POOL.size());
		return (RANDOM_PROPERTY_POOL.get(index));
	}
	
	// ------------------------------------------------------------------------------------- 静态方法
	
	//静态方法初始化
	public static PropertyBuilder getInstance(){
		if(instance == null) instance = new PropertyBuilder();
		if(!instance.isInit)instance.initialize();
		return instance;
	}	

}

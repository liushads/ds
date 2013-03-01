package com.ppsea.ds.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.ItemBurst;
import com.ppsea.ds.data.model.ItemIdentify;
/**
 * 道具鉴定 
 **/
public class IdentifyCenter {
	/**
	 * 静态实例对象 
	 **/
	public static IdentifyCenter instance = new IdentifyCenter();
	public static final String SMALL_PRIZE = "small_prize";	//小礼包
	public static final String MID_PRIZE = "mid_prize";		//中礼包
	public static final String BIG_PRIZE = "big_prize";		//打礼包
	public static final Random RAND = new Random();
	public static Map<Integer, ItemIdentify> ITEM_VALUE_MAP = new HashMap<Integer, ItemIdentify>();
	private static int probArry[];
	private static List<Item> normalItem = new ArrayList<Item>();	
	//key=item,value=
	private static Map<Integer,ItemBurst> itemBurst = new HashMap<Integer,ItemBurst>();
	/**
	 * 升序排列
	 **/
	private static Comparator<ItemBurst.RateItem> compAesc = new Comparator<ItemBurst.RateItem>() {
			public int compare(ItemBurst.RateItem a, ItemBurst.RateItem b) {
				return (int)(a.getRate() - b.getRate());
			}
	};
	
	/**
	 * 默认构造 
	 **/
	private IdentifyCenter(){
		
	}
	
	/**
	 * 初始化 
	 **/
	public void init(){
		int idx = 0;
		probArry = new int[10000];
		normalItem.clear();
		ITEM_VALUE_MAP.clear();
		
		List<ItemIdentify> ls = DBManager.queryAllItemIdentify();
		for(ItemIdentify ii:ls){
			ITEM_VALUE_MAP.put(ii.getItemId(), ii);
			if(ii.getProbability() > 0 ){
				for(int i=0; i<ii.getProbability(); i++){					
					probArry[idx] = ii.getItemId();
					idx++;
				}
			}
			else{
				normalItem.add(ItemMG.instance.getItem(ii.getItemId()));
			}
		}
		
		//初始化道具
		initBurst();
	}
	
	/**
	 * 鉴定道具
	 **/
	public Item fetchItem(){
		 Random RAND = new Random();
		int n = RAND.nextInt(10000);
		if(probArry[n] == 0){
			int idx = RAND.nextInt(normalItem.size());
			return normalItem.get(idx);
		}
		else{
			return ItemMG.instance.getItem(probArry[n]);
		}
	}
	
	/**
	 * 开出物品 
	 **/
	public Item burst(Item item){
		ItemBurst burst = itemBurst.get(item.getId());
		List<ItemBurst.RateItem> rateLst = null;
		if(burst == null || (rateLst = burst.getRateLst()) == null || rateLst.size() == 0) return null;
		Collections.sort(rateLst, compAesc);	
		Item burstItem = null;
		int n = RAND.nextInt(10000);
		for(int i=0;i<rateLst.size();i++){
			ItemBurst.RateItem rateItem = rateLst.get(i);
			if(rateItem == null) continue;
			if(n <=  rateItem.getRate()){
				burstItem = rateItem.getItem();
				break;
			}
		}
		return burstItem;
	}
	
	/**
	 * 初始化中秋节日礼包 
	 **/
	private void initBurst(){
		List<ItemBurst> lst = DBManager.queryAllItemBurst();
		Date now = new Date();
		ItemMG IM = ItemMG.instance;
		for(ItemBurst burst : lst){
			if(burst != null){
				Date expire = burst.getExpireDate();
				//重启后会导致数据加载失败，取消。。。
				//if(expire == null || (now.getTime() - expire.getTime() >= 0) ) continue;
				//添加
				ItemBurst oldBurst = itemBurst.get(burst.getItemId());
				if(oldBurst != null) oldBurst = null;
				itemBurst.put(burst.getItemId(),burst);
				burst.setItem(IM.getItem(burst.getItemId()));
				
				//添加爆出物品
				String rateItemStr = burst.getBurst();
				if(rateItemStr == null || rateItemStr.length() == 0) continue;
				String[] rateItemArray = rateItemStr.split("[,]");
				for(int i=0;i<rateItemArray.length;i++){
					String[] rateItem = rateItemArray[i].split("[|]");
					List<ItemBurst.RateItem> rateLst = burst.getRateLst();
					if(rateLst == null) {
						rateLst = new ArrayList<ItemBurst.RateItem>();
						burst.setRateLst(rateLst);
					}
					try {
						int burstItmeId = Integer.parseInt(rateItem[0]);
						int rate = Integer.parseInt(rateItem[1]);
						rateLst.add(new ItemBurst.RateItem(IM.getItem(burstItmeId),rate));
					}
					catch(Exception e){
						
					}					
				}
			}
		}
	}

}

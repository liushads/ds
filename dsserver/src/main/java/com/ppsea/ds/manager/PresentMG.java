package com.ppsea.ds.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ppsea.ds.data.model.Item;
/**
 * 送礼
 **/
public class PresentMG {
	
	/**
	 * 静态 
	 **/
	public static PresentMG instance = new PresentMG();
	private static Map<String, String> presentMessages = new HashMap<String, String>();
	private static List<Item> presents = new ArrayList<Item>();

	/**
	 * 初始化礼品
	 **/
	public void init(){
		presentMessages.put("0_1", "M送给F一束茶香清，关系更加亲密。");
		presentMessages.put("0_2", "M送给F一束春风细，关系更加亲密。");
		presentMessages.put("0_3", "M送给F一个龙呈祥，关系更加亲密。");
		presentMessages.put("0_4", "M送给F一个凤来仪，关系更加亲密。");
		presentMessages.put("0_5", "M送给F一朵鲜花，关系更加亲密。");
		presentMessages.put("0_6", "M送给F一个铜镜，关系更加亲密。");
		presentMessages.put("0_7", "M送给F一副字画，关系更加亲密。");
		presentMessages.put("0_8", "M送给F一包绿罗裙茶，关系更加亲密。");
		presentMessages.put("0_9", "M送给F一朵玫瑰，关系更加亲密。");
		presentMessages.put("0_10598", "M送给F一朵浓情玫瑰，关系更加亲密。");
		presentMessages.put("0_10599", "M送给F一朵巧克力玫瑰，关系更加亲密。");
		presentMessages.put("1_1", "M送给F一束茶香清，关系更加亲密。");
		presentMessages.put("1_2", "M送给F一束春风细，关系更加亲密。");
		presentMessages.put("1_3", "M送给F一个龙呈祥，关系更加亲密。");
		presentMessages.put("1_4", "M送给F一个凤来仪，关系更加亲密。");
		presentMessages.put("1_5", "M送给F一朵鲜花，关系更加亲密。");
		presentMessages.put("1_6", "M送给F一个铜镜，关系更加亲密。");
		presentMessages.put("1_7", "M送给F一副字画，关系更加亲密。");
		presentMessages.put("1_8", "M送给F一包绿罗裙茶，关系更加亲密。");
		presentMessages.put("1_9", "M送给F一朵玫瑰，关系更加亲密。");
		presentMessages.put("1_10598", "M送给F一朵浓情玫瑰，关系更加亲密。");
		presentMessages.put("1_10599", "M送给F一朵巧克力玫瑰，关系更加亲密。");

		//礼品
		Item item = new Item();
		item.setId(1);
		item.setHp(0);
		item.setLevel(10);
		item.setGold(0);
		item.setName("茶香清");
		item.setPrice(50*1000);
		item.setDescription("对你痴心一片，热爱着你。");
		item.setIntimateScore(10);
		presents.add(item);

		item = new Item();
		item.setId(2);
		item.setHp(0);
		item.setLevel(15);
		item.setGold(0);
		item.setName("春风细");
		item.setPrice(50*1000);
		item.setDescription("对你痴心一片，铭记于心。");
		item.setIntimateScore(10);
		presents.add(item);

		item = new Item();
		item.setId(3);
		item.setHp(0);
		item.setLevel(20);
		item.setGold(0);
		item.setName("龙呈祥");
		item.setPrice(100*1000);
		item.setDescription("对你痴心一片，代表纯洁的爱。");
		item.setIntimateScore(25);
		presents.add(item);

		item = new Item();
		item.setId(4);
		item.setHp(0);
		item.setLevel(25);
		item.setGold(0);
		item.setName("凤来仪");
		item.setPrice(200*1000);
		item.setDescription("对你一心一意，热爱着你。");
		item.setIntimateScore(55);
		presents.add(item);

		//=========================================================================
		
		item = new Item();
		item.setId(5);
		item.setHp(1);
		item.setLevel(30);
		item.setGold(100);
		item.setName("鲜花");
		item.setDescription("对你一心一意，铭记于心。");
		item.setIntimateScore(25);
		presents.add(item);		

		item = new Item();
		item.setId(6);
		item.setHp(1);
		item.setLevel(35);
		item.setGold(100);
		item.setName("铜镜");
		item.setDescription("对你对你一心一意，代表纯洁的爱。");
		item.setIntimateScore(25);
		presents.add(item);

		item = new Item();
		item.setId(7);
		item.setHp(1);
		item.setLevel(150);
		item.setGold(200);
		item.setName("字画");
		item.setDescription("天长地久，热爱着你。");
		item.setIntimateScore(60);
		presents.add(item);

		item = new Item();
		item.setId(9);
		item.setHp(1);
		item.setLevel(400);
		item.setGold(200);
		item.setName("玫瑰花");
		item.setPrice(0);
		item.setDescription("送人玫瑰，手留余香");
		item.setIntimateScore(200);
		presents.add(item);
		
		item = new Item();
		item.setId(8);
		item.setHp(1);
		item.setLevel(400);
		item.setGold(500);
		item.setName("绿罗裙茶");
		item.setPrice(0);
		item.setDescription("天长地久，铭记于心。");
		item.setIntimateScore(200);
		presents.add(item);	
		
		item = new Item();
		item.setId(10598);
		item.setHp(0);
		item.setLevel(1);
		item.setGold(0);
		item.setName("浓情玫瑰");
		item.setPrice(100*1000);
		item.setDescription("情人节玫瑰");
		item.setIntimateScore(0);
		presents.add(item);
		
		item = new Item();
		item.setId(10599);
		item.setHp(0);
		item.setLevel(1);
		item.setGold(0);
		item.setName("巧克力玫瑰");
		item.setPrice(100*1000);
		item.setDescription("情人节玫瑰");
		item.setIntimateScore(0);
		presents.add(item);
		
	}
	
	/**
	 * 返回特定类型的商品
	 * @param type
	 * @return
	 */
	public List<Item> getPresents(int type) {
		List<Item> results = new ArrayList<Item>();
		for (Item present : presents) {
			if (present.getHp().intValue() == type) {
				results.add(present);
			}
		}
		return results;
	}
	
	public Item getPresent(int id) {
		for (Item present : presents) {
			if (present.getId().intValue() == id) {
				return present;
			}
		}
		return null;
	}

	public Map<String, String> getPresentMessages() {
		return presentMessages;
	}

	public void setPresentMessages(Map<String, String> presentMessages) {
		PresentMG.presentMessages = presentMessages;
	}

	public List<Item> getPresents() {
		return presents;
	}

	public void setPresents(List<Item> presents) {
		PresentMG.presents = presents;
	}
	
}

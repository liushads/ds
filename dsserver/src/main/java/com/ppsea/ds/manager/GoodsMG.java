package com.ppsea.ds.manager;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.data.model.Goods;
import com.ppsea.ds.data.model.GoodsItem;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.manager.DBManager;


/**
 * 商城道具加载器
 * 
 * */

public class GoodsMG {
	private static final Logger log = Logger.getLogger(GoodsMG.class);
	public static GoodsMG instance = new GoodsMG();
	
	private Map<Integer, Goods> goodsMap = new HashMap<Integer, Goods>();
	private Map<Integer, List<Goods>> typeGoods = new HashMap<Integer, List<Goods>>();

	public void init() throws PpseaException{
		loadGoods();
	}
	
	/**
	 * 根据类型取得商品列表
	 * @param type
	 * @return
	 */
	public List<Goods> getMallGoods(int type) {
		if (typeGoods.containsKey(type)) {
			return typeGoods.get(type);
		} else {
			return null;
		}
	}

	public Goods getGoods(int id) {
		return goodsMap.get(id);
	}
	
	/**
	 * 加载商品信息
	 */
	public void loadGoods() {

		goodsMap.clear();
		List<Goods> goodsList = DBManager.queryAllGoods();
		for (Goods goods : goodsList) {
			if (typeGoods.containsKey(goods.getType().intValue())) {
				typeGoods.get(goods.getType().intValue()).add(goods);
			} else {
				List<Goods> newGoodsList = new LinkedList<Goods>();
				newGoodsList.add(goods);
				typeGoods.put(goods.getType().intValue(), newGoodsList);
			}

			int room = 0;
			goodsMap.put(goods.getId(), goods);
			//计算每个pakage的总负
			for (GoodsItem goodsItem : goods.getGoodsItems()) {
				Item item = ItemMG.instance.getItem(goodsItem.getItemId());
				room += item.getRoom() * goodsItem.getAmount();
			}
			goods.setRoom(goods.getRoom() + room);
		}
	}
	
}

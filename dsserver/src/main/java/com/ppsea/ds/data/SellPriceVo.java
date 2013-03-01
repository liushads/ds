package com.ppsea.ds.data;
public class SellPriceVo extends BaseObject {
	
	/**
	 * 使用情况：用于玩家买进时展示货物的价格列表(id name price sign isBuy)
	 * 一个记录就是一个对象，不用设计number字段标志数量
	 */
	private static final long serialVersionUID = 1L;
	int id;
	String name;
	int price;   //货物卖价
	int sign;   //rise or go down price  (value:1,0,-1)  刷新时判定
	int isBuy;  //1,true  0,false  isBuy in the page  (value:0,1)  刷新时判定
	
	public SellPriceVo(int id, String name, int price, int sign, int isBuy) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.sign = sign;
		this.isBuy = isBuy;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getSign() {
		return sign;
	}
	public void setSign(int sign) {
		this.sign = sign;
	}
	public int getIsBuy() {
		return isBuy;
	}
	public void setIsBuy(int isBuy) {
		this.isBuy = isBuy;
	}
	
			
}
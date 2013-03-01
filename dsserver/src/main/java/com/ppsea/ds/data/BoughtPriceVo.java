package com.ppsea.ds.data;
/**
 * @author xl
 *
 */
public class BoughtPriceVo extends BaseObject {
	
	/**
	 * 使用情况： 用于展示已买货物，用于卖出(id name price )
	 * 一个记录就是一个对象，不用设计number字段标志数量
	 */
	private static final long serialVersionUID = 1L;
	int id;
	String name;//用于页面展示
	int price;   //货物的买价
	int randomCode;//随机码，用于防刷新
	
	public BoughtPriceVo(int id, String name, int price, int randomCode) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.randomCode = randomCode;
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
	public int getRandomCode() {
		return randomCode;
	}
	public void setRandomCode(int randomCode) {
		this.randomCode = randomCode;
	}
	
}
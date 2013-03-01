package com.ppsea.ds.data.model;

import java.io.Serializable;
import java.util.List;

public class DartAward implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int copper;
	
	private int gold;
	
	private List<DartItem> dartItems;

	public int getCopper() {
		return copper;
	}


	public void setCopper(int copper) {
		this.copper = copper;
	}


	public int getGold() {
		return gold;
	}


	public void setGold(int gold) {
		this.gold = gold;
	}

	
	public List<DartItem> getDartItems() {
		return dartItems;
	}


	public void setDartItems(List<DartItem> dartItems) {
		this.dartItems = dartItems;
	}


	public class DartItem implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public int id;
		public String name;
		public int amount;
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
		public int getAmount() {
			return amount;
		}
		public void setAmount(int amount) {
			this.amount = amount;
		}
	}
}

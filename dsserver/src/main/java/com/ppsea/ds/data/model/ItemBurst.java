package com.ppsea.ds.data.model;

import java.util.Date;
import java.util.List;

import com.ppsea.ds.data.BaseObject;

public class ItemBurst extends BaseObject {

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column item_burst.id
	 * @ibatorgenerated  Tue Aug 31 12:57:33 CST 2010
	 */
	private Integer id;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column item_burst.item_id
	 * @ibatorgenerated  Tue Aug 31 12:57:33 CST 2010
	 */
	private Integer itemId;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column item_burst.burst
	 * @ibatorgenerated  Tue Aug 31 12:57:33 CST 2010
	 */
	private String burst;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column item_burst.expire_date
	 * @ibatorgenerated  Tue Aug 31 12:57:33 CST 2010
	 */
	private Date expireDate;

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column item_burst.id
	 * @return  the value of item_burst.id
	 * @ibatorgenerated  Tue Aug 31 12:57:33 CST 2010
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column item_burst.id
	 * @param id  the value for item_burst.id
	 * @ibatorgenerated  Tue Aug 31 12:57:33 CST 2010
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column item_burst.item_id
	 * @return  the value of item_burst.item_id
	 * @ibatorgenerated  Tue Aug 31 12:57:33 CST 2010
	 */
	public Integer getItemId() {
		return itemId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column item_burst.item_id
	 * @param itemId  the value for item_burst.item_id
	 * @ibatorgenerated  Tue Aug 31 12:57:33 CST 2010
	 */
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column item_burst.burst
	 * @return  the value of item_burst.burst
	 * @ibatorgenerated  Tue Aug 31 12:57:33 CST 2010
	 */
	public String getBurst() {
		return burst;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column item_burst.burst
	 * @param burst  the value for item_burst.burst
	 * @ibatorgenerated  Tue Aug 31 12:57:33 CST 2010
	 */
	public void setBurst(String burst) {
		this.burst = burst;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column item_burst.expire_date
	 * @return  the value of item_burst.expire_date
	 * @ibatorgenerated  Tue Aug 31 12:57:33 CST 2010
	 */
	public Date getExpireDate() {
		return expireDate;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column item_burst.expire_date
	 * @param expireDate  the value for item_burst.expire_date
	 * @ibatorgenerated  Tue Aug 31 12:57:33 CST 2010
	 */
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	
	private transient Item item;
	private transient List<RateItem> rateLst;
		
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public List<RateItem> getRateLst() {
		return rateLst;
	}

	public void setRateLst(List<RateItem> rateLst) {
		this.rateLst = rateLst;
	}


	/**
	 * rate item
	 **/
	static public class RateItem implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		private Item item;
		private int rate;
		public RateItem(Item item,int rate){
			this.item = item;
			this.rate = rate;
		}
		public Item getItem() {
			return item;
		}
		public void setItem(Item item) {
			this.item = item;
		}
		public int getRate() {
			return rate;
		}
		public void setRate(int rate) {
			this.rate = rate;
		}		
	}
}
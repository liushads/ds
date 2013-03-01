package com.ppsea.ds.data.model;

import com.ppsea.ds.data.BaseObject;

public class ItemBetterProty extends BaseObject {

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column item_better_proty.id
	 * @ibatorgenerated  Thu Jul 08 11:27:42 CST 2010
	 */
	private Integer id;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column item_better_proty.item_level
	 * @ibatorgenerated  Thu Jul 08 11:27:42 CST 2010
	 */
	private Integer itemLevel;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column item_better_proty.use_type
	 * @ibatorgenerated  Thu Jul 08 11:27:42 CST 2010
	 */
	private Integer useType;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column item_better_proty.proability_array
	 * @ibatorgenerated  Thu Jul 08 11:27:42 CST 2010
	 */
	private String proabilityArray;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column item_better_proty.type
	 * @ibatorgenerated  Thu Jul 08 11:27:42 CST 2010
	 */
	private Integer type;

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column item_better_proty.id
	 * @return  the value of item_better_proty.id
	 * @ibatorgenerated  Thu Jul 08 11:27:42 CST 2010
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column item_better_proty.id
	 * @param id  the value for item_better_proty.id
	 * @ibatorgenerated  Thu Jul 08 11:27:42 CST 2010
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column item_better_proty.item_level
	 * @return  the value of item_better_proty.item_level
	 * @ibatorgenerated  Thu Jul 08 11:27:42 CST 2010
	 */
	public Integer getItemLevel() {
		return itemLevel;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column item_better_proty.item_level
	 * @param itemLevel  the value for item_better_proty.item_level
	 * @ibatorgenerated  Thu Jul 08 11:27:42 CST 2010
	 */
	public void setItemLevel(Integer itemLevel) {
		this.itemLevel = itemLevel;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column item_better_proty.use_type
	 * @return  the value of item_better_proty.use_type
	 * @ibatorgenerated  Thu Jul 08 11:27:42 CST 2010
	 */
	public Integer getUseType() {
		return useType;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column item_better_proty.use_type
	 * @param useType  the value for item_better_proty.use_type
	 * @ibatorgenerated  Thu Jul 08 11:27:42 CST 2010
	 */
	public void setUseType(Integer useType) {
		this.useType = useType;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column item_better_proty.proability_array
	 * @return  the value of item_better_proty.proability_array
	 * @ibatorgenerated  Thu Jul 08 11:27:42 CST 2010
	 */
	public String getProabilityArray() {
		return proabilityArray;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column item_better_proty.proability_array
	 * @param proabilityArray  the value for item_better_proty.proability_array
	 * @ibatorgenerated  Thu Jul 08 11:27:42 CST 2010
	 */
	public void setProabilityArray(String proabilityArray) {
		this.proabilityArray = proabilityArray;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column item_better_proty.type
	 * @return  the value of item_better_proty.type
	 * @ibatorgenerated  Thu Jul 08 11:27:42 CST 2010
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column item_better_proty.type
	 * @param type  the value for item_better_proty.type
	 * @ibatorgenerated  Thu Jul 08 11:27:42 CST 2010
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**********************************************************
	 * custom code
	 **/
	
	public static final int TYPE_OUT_HOLE = 1;//out hole number
	public static final int TYPE_OUT_IPVE = 2;//out improve level
	public static final int TYPE_OUT_STAR = 3;//out star number
	
	private int[] prbtyArray = null;

	public int[] getPrbtyArray() {
		return prbtyArray;
	}

	public void setPrbtyArray(int[] prbtyArray) {
		this.prbtyArray = prbtyArray;
	} 	
}
package com.ppsea.ds.data.dao;

import com.ppsea.ds.data.model.ItemSuit;
import com.ppsea.ds.data.model.ItemSuitExample;
import java.sql.SQLException;
import java.util.List;

public interface ItemSuitDAO {

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table item_suit
	 * @ibatorgenerated  Tue Jan 15 22:07:23 CST 2013
	 */
	int countByExample(ItemSuitExample example) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table item_suit
	 * @ibatorgenerated  Tue Jan 15 22:07:23 CST 2013
	 */
	int deleteByExample(ItemSuitExample example) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table item_suit
	 * @ibatorgenerated  Tue Jan 15 22:07:23 CST 2013
	 */
	int deleteByPrimaryKey(Integer id) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table item_suit
	 * @ibatorgenerated  Tue Jan 15 22:07:23 CST 2013
	 */
	Integer insert(ItemSuit record) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table item_suit
	 * @ibatorgenerated  Tue Jan 15 22:07:23 CST 2013
	 */
	Integer insertSelective(ItemSuit record) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table item_suit
	 * @ibatorgenerated  Tue Jan 15 22:07:23 CST 2013
	 */
	List selectByExample(ItemSuitExample example) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table item_suit
	 * @ibatorgenerated  Tue Jan 15 22:07:23 CST 2013
	 */
	ItemSuit selectByPrimaryKey(Integer id) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table item_suit
	 * @ibatorgenerated  Tue Jan 15 22:07:23 CST 2013
	 */
	int updateByExampleSelective(ItemSuit record, ItemSuitExample example)
			throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table item_suit
	 * @ibatorgenerated  Tue Jan 15 22:07:23 CST 2013
	 */
	int updateByExample(ItemSuit record, ItemSuitExample example)
			throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table item_suit
	 * @ibatorgenerated  Tue Jan 15 22:07:23 CST 2013
	 */
	int updateByPrimaryKeySelective(ItemSuit record) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table item_suit
	 * @ibatorgenerated  Tue Jan 15 22:07:23 CST 2013
	 */
	int updateByPrimaryKey(ItemSuit record) throws SQLException;
}
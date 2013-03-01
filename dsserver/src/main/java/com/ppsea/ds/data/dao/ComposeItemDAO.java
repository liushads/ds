package com.ppsea.ds.data.dao;

import com.ppsea.ds.data.model.ComposeItem;
import com.ppsea.ds.data.model.ComposeItemExample;
import java.sql.SQLException;
import java.util.List;

public interface ComposeItemDAO {

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table compose_item
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	int countByExample(ComposeItemExample example) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table compose_item
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	int deleteByExample(ComposeItemExample example) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table compose_item
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	int deleteByPrimaryKey(Integer id) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table compose_item
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	Integer insert(ComposeItem record) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table compose_item
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	Integer insertSelective(ComposeItem record) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table compose_item
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	List selectByExample(ComposeItemExample example) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table compose_item
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	ComposeItem selectByPrimaryKey(Integer id) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table compose_item
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	int updateByExampleSelective(ComposeItem record, ComposeItemExample example)
			throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table compose_item
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	int updateByExample(ComposeItem record, ComposeItemExample example)
			throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table compose_item
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	int updateByPrimaryKeySelective(ComposeItem record) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table compose_item
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	int updateByPrimaryKey(ComposeItem record) throws SQLException;
}
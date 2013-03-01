package com.ppsea.ds.data.dao;

import com.ppsea.ds.data.model.PointsItem;
import com.ppsea.ds.data.model.PointsItemExample;
import java.sql.SQLException;
import java.util.List;

public interface PointsItemDAO {

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table points_item
     * @ibatorgenerated  Tue Dec 14 11:41:37 CST 2010
     */
    int countByExample(PointsItemExample example) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table points_item
     * @ibatorgenerated  Tue Dec 14 11:41:37 CST 2010
     */
    int deleteByExample(PointsItemExample example) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table points_item
     * @ibatorgenerated  Tue Dec 14 11:41:37 CST 2010
     */
    int deleteByPrimaryKey(Integer id) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table points_item
     * @ibatorgenerated  Tue Dec 14 11:41:37 CST 2010
     */
    Integer insert(PointsItem record) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table points_item
     * @ibatorgenerated  Tue Dec 14 11:41:37 CST 2010
     */
    Integer insertSelective(PointsItem record) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table points_item
     * @ibatorgenerated  Tue Dec 14 11:41:37 CST 2010
     */
    List selectByExample(PointsItemExample example) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table points_item
     * @ibatorgenerated  Tue Dec 14 11:41:37 CST 2010
     */
    PointsItem selectByPrimaryKey(Integer id) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table points_item
     * @ibatorgenerated  Tue Dec 14 11:41:37 CST 2010
     */
    int updateByExampleSelective(PointsItem record, PointsItemExample example)
            throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table points_item
     * @ibatorgenerated  Tue Dec 14 11:41:37 CST 2010
     */
    int updateByExample(PointsItem record, PointsItemExample example)
            throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table points_item
     * @ibatorgenerated  Tue Dec 14 11:41:37 CST 2010
     */
    int updateByPrimaryKeySelective(PointsItem record) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table points_item
     * @ibatorgenerated  Tue Dec 14 11:41:37 CST 2010
     */
    int updateByPrimaryKey(PointsItem record) throws SQLException;
}
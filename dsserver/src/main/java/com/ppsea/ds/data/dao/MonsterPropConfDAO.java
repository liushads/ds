package com.ppsea.ds.data.dao;

import com.ppsea.ds.data.model.MonsterPropConf;
import com.ppsea.ds.data.model.MonsterPropConfExample;
import java.sql.SQLException;
import java.util.List;

public interface MonsterPropConfDAO {

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table monster_prop_conf
     * @ibatorgenerated  Tue Sep 14 11:29:44 CST 2010
     */
    int countByExample(MonsterPropConfExample example) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table monster_prop_conf
     * @ibatorgenerated  Tue Sep 14 11:29:44 CST 2010
     */
    int deleteByExample(MonsterPropConfExample example) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table monster_prop_conf
     * @ibatorgenerated  Tue Sep 14 11:29:44 CST 2010
     */
    int deleteByPrimaryKey(Integer id) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table monster_prop_conf
     * @ibatorgenerated  Tue Sep 14 11:29:44 CST 2010
     */
    Integer insert(MonsterPropConf record) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table monster_prop_conf
     * @ibatorgenerated  Tue Sep 14 11:29:44 CST 2010
     */
    Integer insertSelective(MonsterPropConf record) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table monster_prop_conf
     * @ibatorgenerated  Tue Sep 14 11:29:44 CST 2010
     */
    List selectByExample(MonsterPropConfExample example) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table monster_prop_conf
     * @ibatorgenerated  Tue Sep 14 11:29:44 CST 2010
     */
    MonsterPropConf selectByPrimaryKey(Integer id) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table monster_prop_conf
     * @ibatorgenerated  Tue Sep 14 11:29:44 CST 2010
     */
    int updateByExampleSelective(MonsterPropConf record,
            MonsterPropConfExample example) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table monster_prop_conf
     * @ibatorgenerated  Tue Sep 14 11:29:44 CST 2010
     */
    int updateByExample(MonsterPropConf record, MonsterPropConfExample example)
            throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table monster_prop_conf
     * @ibatorgenerated  Tue Sep 14 11:29:44 CST 2010
     */
    int updateByPrimaryKeySelective(MonsterPropConf record) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table monster_prop_conf
     * @ibatorgenerated  Tue Sep 14 11:29:44 CST 2010
     */
    int updateByPrimaryKey(MonsterPropConf record) throws SQLException;
}
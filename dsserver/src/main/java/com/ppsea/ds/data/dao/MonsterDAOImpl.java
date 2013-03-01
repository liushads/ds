package com.ppsea.ds.data.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ppsea.ds.data.model.Monster;
import com.ppsea.ds.data.model.MonsterExample;
import java.sql.SQLException;
import java.util.List;

public class MonsterDAOImpl implements MonsterDAO {

	/**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database table monster
     * @ibatorgenerated  Mon Sep 13 11:18:04 CST 2010
     */
    private SqlMapClient sqlMapClient;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table monster
     * @ibatorgenerated  Mon Sep 13 11:18:04 CST 2010
     */
    public MonsterDAOImpl(SqlMapClient sqlMapClient) {
	    super();
	    this.sqlMapClient = sqlMapClient;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table monster
     * @ibatorgenerated  Mon Sep 13 11:18:04 CST 2010
     */
    public int countByExample(MonsterExample example) throws SQLException {
	    Integer count = (Integer) sqlMapClient.queryForObject(
	            "monster.ibatorgenerated_countByExample", example);
	    return count.intValue();
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table monster
     * @ibatorgenerated  Mon Sep 13 11:18:04 CST 2010
     */
    public int deleteByExample(MonsterExample example) throws SQLException {
	    int rows = sqlMapClient.delete(
	            "monster.ibatorgenerated_deleteByExample", example);
	    return rows;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table monster
     * @ibatorgenerated  Mon Sep 13 11:18:04 CST 2010
     */
    public int deleteByPrimaryKey(Integer id) throws SQLException {
	    Monster key = new Monster();
	    key.setId(id);
	    int rows = sqlMapClient.delete(
	            "monster.ibatorgenerated_deleteByPrimaryKey", key);
	    return rows;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table monster
     * @ibatorgenerated  Mon Sep 13 11:18:04 CST 2010
     */
    public Integer insert(Monster record) throws SQLException {
	    Object newKey = sqlMapClient.insert("monster.ibatorgenerated_insert",
	            record);
	    return (Integer) newKey;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table monster
     * @ibatorgenerated  Mon Sep 13 11:18:04 CST 2010
     */
    public Integer insertSelective(Monster record) throws SQLException {
	    Object newKey = sqlMapClient.insert(
	            "monster.ibatorgenerated_insertSelective", record);
	    return (Integer) newKey;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table monster
     * @ibatorgenerated  Mon Sep 13 11:18:04 CST 2010
     */
    public List selectByExample(MonsterExample example) throws SQLException {
	    List list = sqlMapClient.queryForList(
	            "monster.ibatorgenerated_selectByExample", example);
	    return list;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table monster
     * @ibatorgenerated  Mon Sep 13 11:18:04 CST 2010
     */
    public Monster selectByPrimaryKey(Integer id) throws SQLException {
	    Monster key = new Monster();
	    key.setId(id);
	    Monster record = (Monster) sqlMapClient.queryForObject(
	            "monster.ibatorgenerated_selectByPrimaryKey", key);
	    return record;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table monster
     * @ibatorgenerated  Mon Sep 13 11:18:04 CST 2010
     */
    public int updateByExampleSelective(Monster record, MonsterExample example)
            throws SQLException {
	    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
	    int rows = sqlMapClient.update(
	            "monster.ibatorgenerated_updateByExampleSelective", parms);
	    return rows;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table monster
     * @ibatorgenerated  Mon Sep 13 11:18:04 CST 2010
     */
    public int updateByExample(Monster record, MonsterExample example)
            throws SQLException {
	    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
	    int rows = sqlMapClient.update(
	            "monster.ibatorgenerated_updateByExample", parms);
	    return rows;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table monster
     * @ibatorgenerated  Mon Sep 13 11:18:04 CST 2010
     */
    public int updateByPrimaryKeySelective(Monster record) throws SQLException {
	    int rows = sqlMapClient.update(
	            "monster.ibatorgenerated_updateByPrimaryKeySelective", record);
	    return rows;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table monster
     * @ibatorgenerated  Mon Sep 13 11:18:04 CST 2010
     */
    public int updateByPrimaryKey(Monster record) throws SQLException {
	    int rows = sqlMapClient.update(
	            "monster.ibatorgenerated_updateByPrimaryKey", record);
	    return rows;
    }

	/**
     * This class was generated by Apache iBATIS ibator. This class corresponds to the database table monster
     * @ibatorgenerated  Mon Sep 13 11:18:04 CST 2010
     */
    private static class UpdateByExampleParms extends MonsterExample {
	    private Object record;

	    public UpdateByExampleParms(Object record, MonsterExample example) {
		    super(example);
		    this.record = record;
	    }

	    public Object getRecord() {
		    return record;
	    }
    }
}
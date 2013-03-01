package com.ppsea.ds.data.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ppsea.ds.data.model.PlayerPasswordSafty;
import com.ppsea.ds.data.model.PlayerPasswordSaftyExample;
import java.sql.SQLException;
import java.util.List;

public class PlayerPasswordSaftyDAOImpl implements PlayerPasswordSaftyDAO {

	/**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database table player_password_safty
     * @ibatorgenerated  Fri Oct 29 17:05:18 CST 2010
     */
    private SqlMapClient sqlMapClient;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_password_safty
     * @ibatorgenerated  Fri Oct 29 17:05:18 CST 2010
     */
    public PlayerPasswordSaftyDAOImpl(SqlMapClient sqlMapClient) {
	    super();
	    this.sqlMapClient = sqlMapClient;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_password_safty
     * @ibatorgenerated  Fri Oct 29 17:05:18 CST 2010
     */
    public int countByExample(PlayerPasswordSaftyExample example)
            throws SQLException {
	    Integer count = (Integer) sqlMapClient
	            .queryForObject(
	                    "player_password_safty.ibatorgenerated_countByExample",
	                    example);
	    return count.intValue();
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_password_safty
     * @ibatorgenerated  Fri Oct 29 17:05:18 CST 2010
     */
    public int deleteByExample(PlayerPasswordSaftyExample example)
            throws SQLException {
	    int rows = sqlMapClient.delete(
	            "player_password_safty.ibatorgenerated_deleteByExample",
	            example);
	    return rows;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_password_safty
     * @ibatorgenerated  Fri Oct 29 17:05:18 CST 2010
     */
    public int deleteByPrimaryKey(Integer id) throws SQLException {
	    PlayerPasswordSafty key = new PlayerPasswordSafty();
	    key.setId(id);
	    int rows = sqlMapClient
	            .delete(
	                    "player_password_safty.ibatorgenerated_deleteByPrimaryKey",
	                    key);
	    return rows;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_password_safty
     * @ibatorgenerated  Fri Oct 29 17:05:18 CST 2010
     */
    public Integer insert(PlayerPasswordSafty record) throws SQLException {
	    Object newKey = sqlMapClient.insert(
	            "player_password_safty.ibatorgenerated_insert", record);
	    return (Integer) newKey;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_password_safty
     * @ibatorgenerated  Fri Oct 29 17:05:18 CST 2010
     */
    public Integer insertSelective(PlayerPasswordSafty record)
            throws SQLException {
	    Object newKey = sqlMapClient
	            .insert(
	                    "player_password_safty.ibatorgenerated_insertSelective",
	                    record);
	    return (Integer) newKey;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_password_safty
     * @ibatorgenerated  Fri Oct 29 17:05:18 CST 2010
     */
    public List selectByExample(PlayerPasswordSaftyExample example)
            throws SQLException {
	    List list = sqlMapClient.queryForList(
	            "player_password_safty.ibatorgenerated_selectByExample",
	            example);
	    return list;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_password_safty
     * @ibatorgenerated  Fri Oct 29 17:05:18 CST 2010
     */
    public PlayerPasswordSafty selectByPrimaryKey(Integer id)
            throws SQLException {
	    PlayerPasswordSafty key = new PlayerPasswordSafty();
	    key.setId(id);
	    PlayerPasswordSafty record = (PlayerPasswordSafty) sqlMapClient
	            .queryForObject(
	                    "player_password_safty.ibatorgenerated_selectByPrimaryKey",
	                    key);
	    return record;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_password_safty
     * @ibatorgenerated  Fri Oct 29 17:05:18 CST 2010
     */
    public int updateByExampleSelective(PlayerPasswordSafty record,
            PlayerPasswordSaftyExample example) throws SQLException {
	    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
	    int rows = sqlMapClient
	            .update(
	                    "player_password_safty.ibatorgenerated_updateByExampleSelective",
	                    parms);
	    return rows;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_password_safty
     * @ibatorgenerated  Fri Oct 29 17:05:18 CST 2010
     */
    public int updateByExample(PlayerPasswordSafty record,
            PlayerPasswordSaftyExample example) throws SQLException {
	    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
	    int rows = sqlMapClient.update(
	            "player_password_safty.ibatorgenerated_updateByExample", parms);
	    return rows;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_password_safty
     * @ibatorgenerated  Fri Oct 29 17:05:18 CST 2010
     */
    public int updateByPrimaryKeySelective(PlayerPasswordSafty record)
            throws SQLException {
	    int rows = sqlMapClient
	            .update(
	                    "player_password_safty.ibatorgenerated_updateByPrimaryKeySelective",
	                    record);
	    return rows;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_password_safty
     * @ibatorgenerated  Fri Oct 29 17:05:18 CST 2010
     */
    public int updateByPrimaryKey(PlayerPasswordSafty record)
            throws SQLException {
	    int rows = sqlMapClient.update(
	            "player_password_safty.ibatorgenerated_updateByPrimaryKey",
	            record);
	    return rows;
    }

	/**
     * This class was generated by Apache iBATIS ibator. This class corresponds to the database table player_password_safty
     * @ibatorgenerated  Fri Oct 29 17:05:18 CST 2010
     */
    private static class UpdateByExampleParms extends
            PlayerPasswordSaftyExample {
	    private Object record;

	    public UpdateByExampleParms(Object record,
	            PlayerPasswordSaftyExample example) {
		    super(example);
		    this.record = record;
	    }

	    public Object getRecord() {
		    return record;
	    }
    }
}
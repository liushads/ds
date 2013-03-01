package com.ppsea.ds.data.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ppsea.ds.data.model.PlayerKf;
import com.ppsea.ds.data.model.PlayerKfExample;
import java.sql.SQLException;
import java.util.List;

public class PlayerKfDAOImpl implements PlayerKfDAO {

	/**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database table player_kf
     * @ibatorgenerated  Wed Aug 25 17:39:10 CST 2010
     */
    private SqlMapClient sqlMapClient;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_kf
     * @ibatorgenerated  Wed Aug 25 17:39:10 CST 2010
     */
    public PlayerKfDAOImpl(SqlMapClient sqlMapClient) {
	    super();
	    this.sqlMapClient = sqlMapClient;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_kf
     * @ibatorgenerated  Wed Aug 25 17:39:10 CST 2010
     */
    public int countByExample(PlayerKfExample example) throws SQLException {
	    Integer count = (Integer) sqlMapClient.queryForObject(
	            "player_kf.ibatorgenerated_countByExample", example);
	    return count.intValue();
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_kf
     * @ibatorgenerated  Wed Aug 25 17:39:10 CST 2010
     */
    public int deleteByExample(PlayerKfExample example) throws SQLException {
	    int rows = sqlMapClient.delete(
	            "player_kf.ibatorgenerated_deleteByExample", example);
	    return rows;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_kf
     * @ibatorgenerated  Wed Aug 25 17:39:10 CST 2010
     */
    public int deleteByPrimaryKey(Integer id) throws SQLException {
	    PlayerKf key = new PlayerKf();
	    key.setId(id);
	    int rows = sqlMapClient.delete(
	            "player_kf.ibatorgenerated_deleteByPrimaryKey", key);
	    return rows;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_kf
     * @ibatorgenerated  Wed Aug 25 17:39:10 CST 2010
     */
    public Integer insert(PlayerKf record) throws SQLException {
	    Object newKey = sqlMapClient.insert("player_kf.ibatorgenerated_insert",
	            record);
	    return (Integer) newKey;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_kf
     * @ibatorgenerated  Wed Aug 25 17:39:10 CST 2010
     */
    public Integer insertSelective(PlayerKf record) throws SQLException {
	    Object newKey = sqlMapClient.insert(
	            "player_kf.ibatorgenerated_insertSelective", record);
	    return (Integer) newKey;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_kf
     * @ibatorgenerated  Wed Aug 25 17:39:10 CST 2010
     */
    public List selectByExample(PlayerKfExample example) throws SQLException {
	    List list = sqlMapClient.queryForList(
	            "player_kf.ibatorgenerated_selectByExample", example);
	    return list;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_kf
     * @ibatorgenerated  Wed Aug 25 17:39:10 CST 2010
     */
    public PlayerKf selectByPrimaryKey(Integer id) throws SQLException {
	    PlayerKf key = new PlayerKf();
	    key.setId(id);
	    PlayerKf record = (PlayerKf) sqlMapClient.queryForObject(
	            "player_kf.ibatorgenerated_selectByPrimaryKey", key);
	    return record;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_kf
     * @ibatorgenerated  Wed Aug 25 17:39:10 CST 2010
     */
    public int updateByExampleSelective(PlayerKf record, PlayerKfExample example)
            throws SQLException {
	    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
	    int rows = sqlMapClient.update(
	            "player_kf.ibatorgenerated_updateByExampleSelective", parms);
	    return rows;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_kf
     * @ibatorgenerated  Wed Aug 25 17:39:10 CST 2010
     */
    public int updateByExample(PlayerKf record, PlayerKfExample example)
            throws SQLException {
	    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
	    int rows = sqlMapClient.update(
	            "player_kf.ibatorgenerated_updateByExample", parms);
	    return rows;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_kf
     * @ibatorgenerated  Wed Aug 25 17:39:10 CST 2010
     */
    public int updateByPrimaryKeySelective(PlayerKf record) throws SQLException {
	    int rows = sqlMapClient
	            .update(
	                    "player_kf.ibatorgenerated_updateByPrimaryKeySelective",
	                    record);
	    return rows;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_kf
     * @ibatorgenerated  Wed Aug 25 17:39:10 CST 2010
     */
    public int updateByPrimaryKey(PlayerKf record) throws SQLException {
	    int rows = sqlMapClient.update(
	            "player_kf.ibatorgenerated_updateByPrimaryKey", record);
	    return rows;
    }

	/**
     * This class was generated by Apache iBATIS ibator. This class corresponds to the database table player_kf
     * @ibatorgenerated  Wed Aug 25 17:39:10 CST 2010
     */
    private static class UpdateByExampleParms extends PlayerKfExample {
	    private Object record;

	    public UpdateByExampleParms(Object record, PlayerKfExample example) {
		    super(example);
		    this.record = record;
	    }

	    public Object getRecord() {
		    return record;
	    }
    }
}
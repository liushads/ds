package com.ppsea.ds.data.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ppsea.ds.data.model.PlayerItemAppend;
import com.ppsea.ds.data.model.PlayerItemAppendExample;
import java.sql.SQLException;
import java.util.List;

public class PlayerItemAppendDAOImpl implements PlayerItemAppendDAO {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table player_item_append
     *
     * @ibatorgenerated Wed Apr 07 17:01:41 CST 2010
     */
    private SqlMapClient sqlMapClient;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_item_append
     *
     * @ibatorgenerated Wed Apr 07 17:01:41 CST 2010
     */
    public PlayerItemAppendDAOImpl(SqlMapClient sqlMapClient) {
        super();
        this.sqlMapClient = sqlMapClient;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_item_append
     *
     * @ibatorgenerated Wed Apr 07 17:01:41 CST 2010
     */
    public int countByExample(PlayerItemAppendExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("player_item_append.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_item_append
     *
     * @ibatorgenerated Wed Apr 07 17:01:41 CST 2010
     */
    public int deleteByExample(PlayerItemAppendExample example) throws SQLException {
        int rows = sqlMapClient.delete("player_item_append.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_item_append
     *
     * @ibatorgenerated Wed Apr 07 17:01:41 CST 2010
     */
    public int deleteByPrimaryKey(Integer id) throws SQLException {
        PlayerItemAppend key = new PlayerItemAppend();
        key.setId(id);
        int rows = sqlMapClient.delete("player_item_append.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_item_append
     *
     * @ibatorgenerated Wed Apr 07 17:01:41 CST 2010
     */
    public Integer insert(PlayerItemAppend record) throws SQLException {
        Object newKey = sqlMapClient.insert("player_item_append.ibatorgenerated_insert", record);
        return (Integer) newKey;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_item_append
     *
     * @ibatorgenerated Wed Apr 07 17:01:41 CST 2010
     */
    public Integer insertSelective(PlayerItemAppend record) throws SQLException {
        Object newKey = sqlMapClient.insert("player_item_append.ibatorgenerated_insertSelective", record);
        return (Integer) newKey;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_item_append
     *
     * @ibatorgenerated Wed Apr 07 17:01:41 CST 2010
     */
    public List selectByExample(PlayerItemAppendExample example) throws SQLException {
        List list = sqlMapClient.queryForList("player_item_append.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_item_append
     *
     * @ibatorgenerated Wed Apr 07 17:01:41 CST 2010
     */
    public PlayerItemAppend selectByPrimaryKey(Integer id) throws SQLException {
        PlayerItemAppend key = new PlayerItemAppend();
        key.setId(id);
        PlayerItemAppend record = (PlayerItemAppend) sqlMapClient.queryForObject("player_item_append.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_item_append
     *
     * @ibatorgenerated Wed Apr 07 17:01:41 CST 2010
     */
    public int updateByExampleSelective(PlayerItemAppend record, PlayerItemAppendExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("player_item_append.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_item_append
     *
     * @ibatorgenerated Wed Apr 07 17:01:41 CST 2010
     */
    public int updateByExample(PlayerItemAppend record, PlayerItemAppendExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("player_item_append.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_item_append
     *
     * @ibatorgenerated Wed Apr 07 17:01:41 CST 2010
     */
    public int updateByPrimaryKeySelective(PlayerItemAppend record) throws SQLException {
        int rows = sqlMapClient.update("player_item_append.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_item_append
     *
     * @ibatorgenerated Wed Apr 07 17:01:41 CST 2010
     */
    public int updateByPrimaryKey(PlayerItemAppend record) throws SQLException {
        int rows = sqlMapClient.update("player_item_append.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table player_item_append
     *
     * @ibatorgenerated Wed Apr 07 17:01:41 CST 2010
     */
    private static class UpdateByExampleParms extends PlayerItemAppendExample {
        private Object record;

        public UpdateByExampleParms(Object record, PlayerItemAppendExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}
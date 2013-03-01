package com.ppsea.ds.data.dao;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ppsea.ds.data.model.EventExtraDesc;
import com.ppsea.ds.data.model.EventExtraDescExample;

public class EventExtraDescDAOImpl implements EventExtraDescDAO {
    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database table event_extra_desc
     *
     * @abatorgenerated Thu May 14 08:54:55 CST 2009
     */
    private SqlMapClient sqlMapClient;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table event_extra_desc
     *
     * @abatorgenerated Thu May 14 08:54:55 CST 2009
     */
    public EventExtraDescDAOImpl(SqlMapClient sqlMapClient) {
        super();
        this.sqlMapClient = sqlMapClient;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table event_extra_desc
     *
     * @abatorgenerated Thu May 14 08:54:55 CST 2009
     */
    public Integer insert(EventExtraDesc record) throws SQLException {
        Object newKey = sqlMapClient.insert("event_extra_desc.abatorgenerated_insert", record);
        return (Integer) newKey;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table event_extra_desc
     *
     * @abatorgenerated Thu May 14 08:54:55 CST 2009
     */
    public int updateByPrimaryKey(EventExtraDesc record) throws SQLException {
        int rows = sqlMapClient.update("event_extra_desc.abatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table event_extra_desc
     *
     * @abatorgenerated Thu May 14 08:54:55 CST 2009
     */
    public int updateByPrimaryKeySelective(EventExtraDesc record) throws SQLException {
        int rows = sqlMapClient.update("event_extra_desc.abatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table event_extra_desc
     *
     * @abatorgenerated Thu May 14 08:54:55 CST 2009
     */
    @SuppressWarnings("unchecked")
    public List<EventExtraDesc> selectByExample(EventExtraDescExample example) throws SQLException {
        List<EventExtraDesc> list = (List<EventExtraDesc>) sqlMapClient.queryForList("event_extra_desc.abatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table event_extra_desc
     *
     * @abatorgenerated Thu May 14 08:54:55 CST 2009
     */
    public EventExtraDesc selectByPrimaryKey(Integer id) throws SQLException {
        EventExtraDesc key = new EventExtraDesc();
        key.setId(id);
        EventExtraDesc record = (EventExtraDesc) sqlMapClient.queryForObject("event_extra_desc.abatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table event_extra_desc
     *
     * @abatorgenerated Thu May 14 08:54:55 CST 2009
     */
    public int deleteByExample(EventExtraDescExample example) throws SQLException {
        int rows = sqlMapClient.delete("event_extra_desc.abatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table event_extra_desc
     *
     * @abatorgenerated Thu May 14 08:54:55 CST 2009
     */
    public int deleteByPrimaryKey(Integer id) throws SQLException {
        EventExtraDesc key = new EventExtraDesc();
        key.setId(id);
        int rows = sqlMapClient.delete("event_extra_desc.abatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table event_extra_desc
     *
     * @abatorgenerated Thu May 14 08:54:55 CST 2009
     */
    public int countByExample(EventExtraDescExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("event_extra_desc.abatorgenerated_countByExample", example);
        return count;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table event_extra_desc
     *
     * @abatorgenerated Thu May 14 08:54:55 CST 2009
     */
    public int updateByExampleSelective(EventExtraDesc record, EventExtraDescExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("event_extra_desc.abatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table event_extra_desc
     *
     * @abatorgenerated Thu May 14 08:54:55 CST 2009
     */
    public int updateByExample(EventExtraDesc record, EventExtraDescExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("event_extra_desc.abatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This class was generated by Abator for iBATIS.
     * This class corresponds to the database table event_extra_desc
     *
     * @abatorgenerated Thu May 14 08:54:55 CST 2009
     */
    private static class UpdateByExampleParms extends EventExtraDescExample {
        private Object record;

        public UpdateByExampleParms(Object record, EventExtraDescExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}
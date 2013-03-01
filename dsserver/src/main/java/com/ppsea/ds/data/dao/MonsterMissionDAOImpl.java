package com.ppsea.ds.data.dao;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ppsea.ds.data.model.MonsterMission;
import com.ppsea.ds.data.model.MonsterMissionExample;

public class MonsterMissionDAOImpl implements MonsterMissionDAO {
    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database table monster_mission
     *
     * @abatorgenerated Mon Feb 16 16:02:11 CST 2009
     */
    private SqlMapClient sqlMapClient;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table monster_mission
     *
     * @abatorgenerated Mon Feb 16 16:02:11 CST 2009
     */
    public MonsterMissionDAOImpl(SqlMapClient sqlMapClient) {
        super();
        this.sqlMapClient = sqlMapClient;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table monster_mission
     *
     * @abatorgenerated Mon Feb 16 16:02:11 CST 2009
     */
    public Integer insert(MonsterMission record) throws SQLException {
        Object newKey = sqlMapClient.insert("monster_mission.abatorgenerated_insert", record);
        return (Integer) newKey;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table monster_mission
     *
     * @abatorgenerated Mon Feb 16 16:02:11 CST 2009
     */
    public int updateByPrimaryKey(MonsterMission record) throws SQLException {
        int rows = sqlMapClient.update("monster_mission.abatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table monster_mission
     *
     * @abatorgenerated Mon Feb 16 16:02:11 CST 2009
     */
    public int updateByPrimaryKeySelective(MonsterMission record) throws SQLException {
        int rows = sqlMapClient.update("monster_mission.abatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table monster_mission
     *
     * @abatorgenerated Mon Feb 16 16:02:11 CST 2009
     */
    @SuppressWarnings("unchecked")
    public List<MonsterMission> selectByExample(MonsterMissionExample example) throws SQLException {
        List<MonsterMission> list = (List<MonsterMission>) sqlMapClient.queryForList("monster_mission.abatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table monster_mission
     *
     * @abatorgenerated Mon Feb 16 16:02:11 CST 2009
     */
    public MonsterMission selectByPrimaryKey(Integer id) throws SQLException {
        MonsterMission key = new MonsterMission();
        key.setId(id);
        MonsterMission record = (MonsterMission) sqlMapClient.queryForObject("monster_mission.abatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table monster_mission
     *
     * @abatorgenerated Mon Feb 16 16:02:11 CST 2009
     */
    public int deleteByExample(MonsterMissionExample example) throws SQLException {
        int rows = sqlMapClient.delete("monster_mission.abatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table monster_mission
     *
     * @abatorgenerated Mon Feb 16 16:02:11 CST 2009
     */
    public int deleteByPrimaryKey(Integer id) throws SQLException {
        MonsterMission key = new MonsterMission();
        key.setId(id);
        int rows = sqlMapClient.delete("monster_mission.abatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table monster_mission
     *
     * @abatorgenerated Mon Feb 16 16:02:11 CST 2009
     */
    public int countByExample(MonsterMissionExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("monster_mission.abatorgenerated_countByExample", example);
        return count;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table monster_mission
     *
     * @abatorgenerated Mon Feb 16 16:02:11 CST 2009
     */
    public int updateByExampleSelective(MonsterMission record, MonsterMissionExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("monster_mission.abatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table monster_mission
     *
     * @abatorgenerated Mon Feb 16 16:02:11 CST 2009
     */
    public int updateByExample(MonsterMission record, MonsterMissionExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("monster_mission.abatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This class was generated by Abator for iBATIS.
     * This class corresponds to the database table monster_mission
     *
     * @abatorgenerated Mon Feb 16 16:02:11 CST 2009
     */
    private static class UpdateByExampleParms extends MonsterMissionExample {
        private Object record;

        public UpdateByExampleParms(Object record, MonsterMissionExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}
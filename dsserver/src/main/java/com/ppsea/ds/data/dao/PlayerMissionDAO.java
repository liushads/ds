package com.ppsea.ds.data.dao;

import com.ppsea.ds.data.model.PlayerMission;
import com.ppsea.ds.data.model.PlayerMissionExample;
import java.sql.SQLException;
import java.util.List;

public interface PlayerMissionDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_mission
     *
     * @ibatorgenerated Fri May 28 13:21:21 CST 2010
     */
    int countByExample(PlayerMissionExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_mission
     *
     * @ibatorgenerated Fri May 28 13:21:21 CST 2010
     */
    int deleteByExample(PlayerMissionExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_mission
     *
     * @ibatorgenerated Fri May 28 13:21:21 CST 2010
     */
    int deleteByPrimaryKey(Integer id) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_mission
     *
     * @ibatorgenerated Fri May 28 13:21:21 CST 2010
     */
    Integer insert(PlayerMission record) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_mission
     *
     * @ibatorgenerated Fri May 28 13:21:21 CST 2010
     */
    Integer insertSelective(PlayerMission record) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_mission
     *
     * @ibatorgenerated Fri May 28 13:21:21 CST 2010
     */
    List selectByExample(PlayerMissionExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_mission
     *
     * @ibatorgenerated Fri May 28 13:21:21 CST 2010
     */
    PlayerMission selectByPrimaryKey(Integer id) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_mission
     *
     * @ibatorgenerated Fri May 28 13:21:21 CST 2010
     */
    int updateByExampleSelective(PlayerMission record, PlayerMissionExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_mission
     *
     * @ibatorgenerated Fri May 28 13:21:21 CST 2010
     */
    int updateByExample(PlayerMission record, PlayerMissionExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_mission
     *
     * @ibatorgenerated Fri May 28 13:21:21 CST 2010
     */
    int updateByPrimaryKeySelective(PlayerMission record) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_mission
     *
     * @ibatorgenerated Fri May 28 13:21:21 CST 2010
     */
    int updateByPrimaryKey(PlayerMission record) throws SQLException;
}
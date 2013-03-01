package com.ppsea.ds.data.dao;

import com.ppsea.ds.data.model.RawMission;
import com.ppsea.ds.data.model.RawMissionExample;
import java.sql.SQLException;
import java.util.List;

public interface RawMissionDAO {

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table raw_mission
     * @ibatorgenerated  Wed Jan 19 14:22:45 CST 2011
     */
    int countByExample(RawMissionExample example) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table raw_mission
     * @ibatorgenerated  Wed Jan 19 14:22:45 CST 2011
     */
    int deleteByExample(RawMissionExample example) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table raw_mission
     * @ibatorgenerated  Wed Jan 19 14:22:45 CST 2011
     */
    int deleteByPrimaryKey(Integer id) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table raw_mission
     * @ibatorgenerated  Wed Jan 19 14:22:45 CST 2011
     */
    Integer insert(RawMission record) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table raw_mission
     * @ibatorgenerated  Wed Jan 19 14:22:45 CST 2011
     */
    Integer insertSelective(RawMission record) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table raw_mission
     * @ibatorgenerated  Wed Jan 19 14:22:45 CST 2011
     */
    List selectByExample(RawMissionExample example) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table raw_mission
     * @ibatorgenerated  Wed Jan 19 14:22:45 CST 2011
     */
    RawMission selectByPrimaryKey(Integer id) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table raw_mission
     * @ibatorgenerated  Wed Jan 19 14:22:45 CST 2011
     */
    int updateByExampleSelective(RawMission record, RawMissionExample example)
            throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table raw_mission
     * @ibatorgenerated  Wed Jan 19 14:22:45 CST 2011
     */
    int updateByExample(RawMission record, RawMissionExample example)
            throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table raw_mission
     * @ibatorgenerated  Wed Jan 19 14:22:45 CST 2011
     */
    int updateByPrimaryKeySelective(RawMission record) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table raw_mission
     * @ibatorgenerated  Wed Jan 19 14:22:45 CST 2011
     */
    int updateByPrimaryKey(RawMission record) throws SQLException;
}
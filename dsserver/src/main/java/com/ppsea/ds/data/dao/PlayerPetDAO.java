package com.ppsea.ds.data.dao;

import com.ppsea.ds.data.model.PlayerPet;
import com.ppsea.ds.data.model.PlayerPetExample;
import java.sql.SQLException;
import java.util.List;

public interface PlayerPetDAO {

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_pet
     * @ibatorgenerated  Tue Dec 21 10:46:52 CST 2010
     */
    int countByExample(PlayerPetExample example) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_pet
     * @ibatorgenerated  Tue Dec 21 10:46:52 CST 2010
     */
    int deleteByExample(PlayerPetExample example) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_pet
     * @ibatorgenerated  Tue Dec 21 10:46:52 CST 2010
     */
    int deleteByPrimaryKey(Integer id) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_pet
     * @ibatorgenerated  Tue Dec 21 10:46:52 CST 2010
     */
    Integer insert(PlayerPet record) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_pet
     * @ibatorgenerated  Tue Dec 21 10:46:52 CST 2010
     */
    Integer insertSelective(PlayerPet record) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_pet
     * @ibatorgenerated  Tue Dec 21 10:46:52 CST 2010
     */
    List selectByExample(PlayerPetExample example) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_pet
     * @ibatorgenerated  Tue Dec 21 10:46:52 CST 2010
     */
    PlayerPet selectByPrimaryKey(Integer id) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_pet
     * @ibatorgenerated  Tue Dec 21 10:46:52 CST 2010
     */
    int updateByExampleSelective(PlayerPet record, PlayerPetExample example)
            throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_pet
     * @ibatorgenerated  Tue Dec 21 10:46:52 CST 2010
     */
    int updateByExample(PlayerPet record, PlayerPetExample example)
            throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_pet
     * @ibatorgenerated  Tue Dec 21 10:46:52 CST 2010
     */
    int updateByPrimaryKeySelective(PlayerPet record) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_pet
     * @ibatorgenerated  Tue Dec 21 10:46:52 CST 2010
     */
    int updateByPrimaryKey(PlayerPet record) throws SQLException;
}
package com.ppsea.ds.data.dao;

import com.ppsea.ds.data.model.PlayerEnemy;
import com.ppsea.ds.data.model.PlayerEnemyExample;
import java.sql.SQLException;
import java.util.List;

public interface PlayerEnemyDAO {

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_enemy
	 * @ibatorgenerated  Thu May 27 15:07:51 CST 2010
	 */
	int countByExample(PlayerEnemyExample example) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_enemy
	 * @ibatorgenerated  Thu May 27 15:07:51 CST 2010
	 */
	int deleteByExample(PlayerEnemyExample example) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_enemy
	 * @ibatorgenerated  Thu May 27 15:07:51 CST 2010
	 */
	int deleteByPrimaryKey(Integer id) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_enemy
	 * @ibatorgenerated  Thu May 27 15:07:51 CST 2010
	 */
	Integer insert(PlayerEnemy record) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_enemy
	 * @ibatorgenerated  Thu May 27 15:07:51 CST 2010
	 */
	Integer insertSelective(PlayerEnemy record) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_enemy
	 * @ibatorgenerated  Thu May 27 15:07:51 CST 2010
	 */
	List selectByExample(PlayerEnemyExample example) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_enemy
	 * @ibatorgenerated  Thu May 27 15:07:51 CST 2010
	 */
	PlayerEnemy selectByPrimaryKey(Integer id) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_enemy
	 * @ibatorgenerated  Thu May 27 15:07:51 CST 2010
	 */
	int updateByExampleSelective(PlayerEnemy record, PlayerEnemyExample example)
			throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_enemy
	 * @ibatorgenerated  Thu May 27 15:07:51 CST 2010
	 */
	int updateByExample(PlayerEnemy record, PlayerEnemyExample example)
			throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_enemy
	 * @ibatorgenerated  Thu May 27 15:07:51 CST 2010
	 */
	int updateByPrimaryKeySelective(PlayerEnemy record) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_enemy
	 * @ibatorgenerated  Thu May 27 15:07:51 CST 2010
	 */
	int updateByPrimaryKey(PlayerEnemy record) throws SQLException;
}
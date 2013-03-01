package com.ppsea.ds.data.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ppsea.ds.data.model.PlayerLevel;
import com.ppsea.ds.data.model.PlayerLevelExample;
import java.sql.SQLException;
import java.util.List;

public class PlayerLevelDAOImpl implements PlayerLevelDAO {

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table player_level
	 * @ibatorgenerated  Thu Dec 27 01:05:56 CST 2012
	 */
	private SqlMapClient sqlMapClient;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_level
	 * @ibatorgenerated  Thu Dec 27 01:05:56 CST 2012
	 */
	public PlayerLevelDAOImpl(SqlMapClient sqlMapClient) {
		super();
		this.sqlMapClient = sqlMapClient;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_level
	 * @ibatorgenerated  Thu Dec 27 01:05:56 CST 2012
	 */
	public int countByExample(PlayerLevelExample example) throws SQLException {
		Integer count = (Integer) sqlMapClient.queryForObject(
				"player_level.ibatorgenerated_countByExample", example);
		return count.intValue();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_level
	 * @ibatorgenerated  Thu Dec 27 01:05:56 CST 2012
	 */
	public int deleteByExample(PlayerLevelExample example) throws SQLException {
		int rows = sqlMapClient.delete(
				"player_level.ibatorgenerated_deleteByExample", example);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_level
	 * @ibatorgenerated  Thu Dec 27 01:05:56 CST 2012
	 */
	public int deleteByPrimaryKey(Integer id) throws SQLException {
		PlayerLevel key = new PlayerLevel();
		key.setId(id);
		int rows = sqlMapClient.delete(
				"player_level.ibatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_level
	 * @ibatorgenerated  Thu Dec 27 01:05:56 CST 2012
	 */
	public Integer insert(PlayerLevel record) throws SQLException {
		Object newKey = sqlMapClient.insert(
				"player_level.ibatorgenerated_insert", record);
		return (Integer) newKey;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_level
	 * @ibatorgenerated  Thu Dec 27 01:05:56 CST 2012
	 */
	public Integer insertSelective(PlayerLevel record) throws SQLException {
		Object newKey = sqlMapClient.insert(
				"player_level.ibatorgenerated_insertSelective", record);
		return (Integer) newKey;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_level
	 * @ibatorgenerated  Thu Dec 27 01:05:56 CST 2012
	 */
	public List selectByExample(PlayerLevelExample example) throws SQLException {
		List list = sqlMapClient.queryForList(
				"player_level.ibatorgenerated_selectByExample", example);
		return list;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_level
	 * @ibatorgenerated  Thu Dec 27 01:05:56 CST 2012
	 */
	public PlayerLevel selectByPrimaryKey(Integer id) throws SQLException {
		PlayerLevel key = new PlayerLevel();
		key.setId(id);
		PlayerLevel record = (PlayerLevel) sqlMapClient.queryForObject(
				"player_level.ibatorgenerated_selectByPrimaryKey", key);
		return record;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_level
	 * @ibatorgenerated  Thu Dec 27 01:05:56 CST 2012
	 */
	public int updateByExampleSelective(PlayerLevel record,
			PlayerLevelExample example) throws SQLException {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = sqlMapClient.update(
				"player_level.ibatorgenerated_updateByExampleSelective", parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_level
	 * @ibatorgenerated  Thu Dec 27 01:05:56 CST 2012
	 */
	public int updateByExample(PlayerLevel record, PlayerLevelExample example)
			throws SQLException {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = sqlMapClient.update(
				"player_level.ibatorgenerated_updateByExample", parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_level
	 * @ibatorgenerated  Thu Dec 27 01:05:56 CST 2012
	 */
	public int updateByPrimaryKeySelective(PlayerLevel record)
			throws SQLException {
		int rows = sqlMapClient.update(
				"player_level.ibatorgenerated_updateByPrimaryKeySelective",
				record);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_level
	 * @ibatorgenerated  Thu Dec 27 01:05:56 CST 2012
	 */
	public int updateByPrimaryKey(PlayerLevel record) throws SQLException {
		int rows = sqlMapClient.update(
				"player_level.ibatorgenerated_updateByPrimaryKey", record);
		return rows;
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds to the database table player_level
	 * @ibatorgenerated  Thu Dec 27 01:05:56 CST 2012
	 */
	private static class UpdateByExampleParms extends PlayerLevelExample {
		private Object record;

		public UpdateByExampleParms(Object record, PlayerLevelExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}
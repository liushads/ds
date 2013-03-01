package com.ppsea.ds.data.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ppsea.ds.data.model.PlayerPasswdHistory;
import com.ppsea.ds.data.model.PlayerPasswdHistoryExample;
import java.sql.SQLException;
import java.util.List;

public class PlayerPasswdHistoryDAOImpl implements PlayerPasswdHistoryDAO {

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table player_passwd_history
	 * @ibatorgenerated  Mon Jun 07 15:26:45 CST 2010
	 */
	private SqlMapClient sqlMapClient;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_passwd_history
	 * @ibatorgenerated  Mon Jun 07 15:26:45 CST 2010
	 */
	public PlayerPasswdHistoryDAOImpl(SqlMapClient sqlMapClient) {
		super();
		this.sqlMapClient = sqlMapClient;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_passwd_history
	 * @ibatorgenerated  Mon Jun 07 15:26:45 CST 2010
	 */
	public int countByExample(PlayerPasswdHistoryExample example)
			throws SQLException {
		Integer count = (Integer) sqlMapClient
				.queryForObject(
						"player_passwd_history.ibatorgenerated_countByExample",
						example);
		return count.intValue();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_passwd_history
	 * @ibatorgenerated  Mon Jun 07 15:26:45 CST 2010
	 */
	public int deleteByExample(PlayerPasswdHistoryExample example)
			throws SQLException {
		int rows = sqlMapClient.delete(
				"player_passwd_history.ibatorgenerated_deleteByExample",
				example);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_passwd_history
	 * @ibatorgenerated  Mon Jun 07 15:26:45 CST 2010
	 */
	public int deleteByPrimaryKey(Integer id) throws SQLException {
		PlayerPasswdHistory key = new PlayerPasswdHistory();
		key.setId(id);
		int rows = sqlMapClient
				.delete(
						"player_passwd_history.ibatorgenerated_deleteByPrimaryKey",
						key);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_passwd_history
	 * @ibatorgenerated  Mon Jun 07 15:26:45 CST 2010
	 */
	public Integer insert(PlayerPasswdHistory record) throws SQLException {
		Object newKey = sqlMapClient.insert(
				"player_passwd_history.ibatorgenerated_insert", record);
		return (Integer) newKey;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_passwd_history
	 * @ibatorgenerated  Mon Jun 07 15:26:45 CST 2010
	 */
	public Integer insertSelective(PlayerPasswdHistory record)
			throws SQLException {
		Object newKey = sqlMapClient
				.insert(
						"player_passwd_history.ibatorgenerated_insertSelective",
						record);
		return (Integer) newKey;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_passwd_history
	 * @ibatorgenerated  Mon Jun 07 15:26:45 CST 2010
	 */
	public List selectByExample(PlayerPasswdHistoryExample example)
			throws SQLException {
		List list = sqlMapClient.queryForList(
				"player_passwd_history.ibatorgenerated_selectByExample",
				example);
		return list;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_passwd_history
	 * @ibatorgenerated  Mon Jun 07 15:26:45 CST 2010
	 */
	public PlayerPasswdHistory selectByPrimaryKey(Integer id)
			throws SQLException {
		PlayerPasswdHistory key = new PlayerPasswdHistory();
		key.setId(id);
		PlayerPasswdHistory record = (PlayerPasswdHistory) sqlMapClient
				.queryForObject(
						"player_passwd_history.ibatorgenerated_selectByPrimaryKey",
						key);
		return record;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_passwd_history
	 * @ibatorgenerated  Mon Jun 07 15:26:45 CST 2010
	 */
	public int updateByExampleSelective(PlayerPasswdHistory record,
			PlayerPasswdHistoryExample example) throws SQLException {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = sqlMapClient
				.update(
						"player_passwd_history.ibatorgenerated_updateByExampleSelective",
						parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_passwd_history
	 * @ibatorgenerated  Mon Jun 07 15:26:45 CST 2010
	 */
	public int updateByExample(PlayerPasswdHistory record,
			PlayerPasswdHistoryExample example) throws SQLException {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = sqlMapClient.update(
				"player_passwd_history.ibatorgenerated_updateByExample", parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_passwd_history
	 * @ibatorgenerated  Mon Jun 07 15:26:45 CST 2010
	 */
	public int updateByPrimaryKeySelective(PlayerPasswdHistory record)
			throws SQLException {
		int rows = sqlMapClient
				.update(
						"player_passwd_history.ibatorgenerated_updateByPrimaryKeySelective",
						record);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_passwd_history
	 * @ibatorgenerated  Mon Jun 07 15:26:45 CST 2010
	 */
	public int updateByPrimaryKey(PlayerPasswdHistory record)
			throws SQLException {
		int rows = sqlMapClient.update(
				"player_passwd_history.ibatorgenerated_updateByPrimaryKey",
				record);
		return rows;
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds to the database table player_passwd_history
	 * @ibatorgenerated  Mon Jun 07 15:26:45 CST 2010
	 */
	private static class UpdateByExampleParms extends
			PlayerPasswdHistoryExample {
		private Object record;

		public UpdateByExampleParms(Object record,
				PlayerPasswdHistoryExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}
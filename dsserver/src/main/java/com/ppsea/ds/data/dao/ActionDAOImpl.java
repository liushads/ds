package com.ppsea.ds.data.dao;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ppsea.ds.data.model.Action;
import com.ppsea.ds.data.model.ActionExample;

public class ActionDAOImpl implements ActionDAO {

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table action
	 * @abatorgenerated  Tue Jan 20 12:51:55 CST 2009
	 */
	private SqlMapClient sqlMapClient;

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table action
	 * @abatorgenerated  Tue Jan 20 12:51:55 CST 2009
	 */
	public ActionDAOImpl(SqlMapClient sqlMapClient) {
		super();
		this.sqlMapClient = sqlMapClient;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table action
	 * @abatorgenerated  Tue Jan 20 12:51:55 CST 2009
	 */
	public Integer insert(Action record) throws SQLException {
		Object newKey = sqlMapClient.insert("action.abatorgenerated_insert",
				record);
		return (Integer) newKey;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table action
	 * @abatorgenerated  Tue Jan 20 12:51:55 CST 2009
	 */
	public int updateByPrimaryKey(Action record) throws SQLException {
		int rows = sqlMapClient.update(
				"action.abatorgenerated_updateByPrimaryKey", record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table action
	 * @abatorgenerated  Tue Jan 20 12:51:55 CST 2009
	 */
	public int updateByPrimaryKeySelective(Action record) throws SQLException {
		int rows = sqlMapClient.update(
				"action.abatorgenerated_updateByPrimaryKeySelective", record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table action
	 * @abatorgenerated  Tue Jan 20 12:51:55 CST 2009
	 */
	@SuppressWarnings("unchecked")
	public List<Action> selectByExample(ActionExample example)
			throws SQLException {
		List<Action> list = (List<Action>) sqlMapClient.queryForList(
				"action.abatorgenerated_selectByExample", example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table action
	 * @abatorgenerated  Tue Jan 20 12:51:55 CST 2009
	 */
	public Action selectByPrimaryKey(Integer id) throws SQLException {
		Action key = new Action();
		key.setId(id);
		Action record = (Action) sqlMapClient.queryForObject(
				"action.abatorgenerated_selectByPrimaryKey", key);
		return record;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table action
	 * @abatorgenerated  Tue Jan 20 12:51:55 CST 2009
	 */
	public int deleteByExample(ActionExample example) throws SQLException {
		int rows = sqlMapClient.delete(
				"action.abatorgenerated_deleteByExample", example);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table action
	 * @abatorgenerated  Tue Jan 20 12:51:55 CST 2009
	 */
	public int deleteByPrimaryKey(Integer id) throws SQLException {
		Action key = new Action();
		key.setId(id);
		int rows = sqlMapClient.delete(
				"action.abatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table action
	 * @abatorgenerated  Tue Jan 20 12:51:55 CST 2009
	 */
	public int countByExample(ActionExample example) throws SQLException {
		Integer count = (Integer) sqlMapClient.queryForObject(
				"action.abatorgenerated_countByExample", example);
		return count;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table action
	 * @abatorgenerated  Tue Jan 20 12:51:55 CST 2009
	 */
	public int updateByExampleSelective(Action record, ActionExample example)
			throws SQLException {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = sqlMapClient.update(
				"action.abatorgenerated_updateByExampleSelective", parms);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table action
	 * @abatorgenerated  Tue Jan 20 12:51:55 CST 2009
	 */
	public int updateByExample(Action record, ActionExample example)
			throws SQLException {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = sqlMapClient.update(
				"action.abatorgenerated_updateByExample", parms);
		return rows;
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to the database table action
	 * @abatorgenerated  Tue Jan 20 12:51:55 CST 2009
	 */
	private static class UpdateByExampleParms extends ActionExample {
		private Object record;

		public UpdateByExampleParms(Object record, ActionExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}
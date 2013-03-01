package com.ppsea.ds.data.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ppsea.ds.data.model.ComposeItem;
import com.ppsea.ds.data.model.ComposeItemExample;
import java.sql.SQLException;
import java.util.List;

public class ComposeItemDAOImpl implements ComposeItemDAO {

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table compose_item
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	private SqlMapClient sqlMapClient;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table compose_item
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	public ComposeItemDAOImpl(SqlMapClient sqlMapClient) {
		super();
		this.sqlMapClient = sqlMapClient;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table compose_item
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	public int countByExample(ComposeItemExample example) throws SQLException {
		Integer count = (Integer) sqlMapClient.queryForObject(
				"compose_item.ibatorgenerated_countByExample", example);
		return count.intValue();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table compose_item
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	public int deleteByExample(ComposeItemExample example) throws SQLException {
		int rows = sqlMapClient.delete(
				"compose_item.ibatorgenerated_deleteByExample", example);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table compose_item
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	public int deleteByPrimaryKey(Integer id) throws SQLException {
		ComposeItem key = new ComposeItem();
		key.setId(id);
		int rows = sqlMapClient.delete(
				"compose_item.ibatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table compose_item
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	public Integer insert(ComposeItem record) throws SQLException {
		Object newKey = sqlMapClient.insert(
				"compose_item.ibatorgenerated_insert", record);
		return (Integer) newKey;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table compose_item
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	public Integer insertSelective(ComposeItem record) throws SQLException {
		Object newKey = sqlMapClient.insert(
				"compose_item.ibatorgenerated_insertSelective", record);
		return (Integer) newKey;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table compose_item
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	public List selectByExample(ComposeItemExample example) throws SQLException {
		List list = sqlMapClient.queryForList(
				"compose_item.ibatorgenerated_selectByExample", example);
		return list;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table compose_item
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	public ComposeItem selectByPrimaryKey(Integer id) throws SQLException {
		ComposeItem key = new ComposeItem();
		key.setId(id);
		ComposeItem record = (ComposeItem) sqlMapClient.queryForObject(
				"compose_item.ibatorgenerated_selectByPrimaryKey", key);
		return record;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table compose_item
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	public int updateByExampleSelective(ComposeItem record,
			ComposeItemExample example) throws SQLException {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = sqlMapClient.update(
				"compose_item.ibatorgenerated_updateByExampleSelective", parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table compose_item
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	public int updateByExample(ComposeItem record, ComposeItemExample example)
			throws SQLException {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = sqlMapClient.update(
				"compose_item.ibatorgenerated_updateByExample", parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table compose_item
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	public int updateByPrimaryKeySelective(ComposeItem record)
			throws SQLException {
		int rows = sqlMapClient.update(
				"compose_item.ibatorgenerated_updateByPrimaryKeySelective",
				record);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table compose_item
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	public int updateByPrimaryKey(ComposeItem record) throws SQLException {
		int rows = sqlMapClient.update(
				"compose_item.ibatorgenerated_updateByPrimaryKey", record);
		return rows;
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds to the database table compose_item
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	private static class UpdateByExampleParms extends ComposeItemExample {
		private Object record;

		public UpdateByExampleParms(Object record, ComposeItemExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}
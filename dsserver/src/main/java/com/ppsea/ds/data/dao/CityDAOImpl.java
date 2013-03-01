package com.ppsea.ds.data.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ppsea.ds.data.model.City;
import com.ppsea.ds.data.model.CityExample;
import java.sql.SQLException;
import java.util.List;

public class CityDAOImpl implements CityDAO {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table city
     *
     * @ibatorgenerated Thu Apr 01 13:57:44 CST 2010
     */
    private SqlMapClient sqlMapClient;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table city
     *
     * @ibatorgenerated Thu Apr 01 13:57:44 CST 2010
     */
    public CityDAOImpl(SqlMapClient sqlMapClient) {
        super();
        this.sqlMapClient = sqlMapClient;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table city
     *
     * @ibatorgenerated Thu Apr 01 13:57:44 CST 2010
     */
    public int countByExample(CityExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("city.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table city
     *
     * @ibatorgenerated Thu Apr 01 13:57:44 CST 2010
     */
    public int deleteByExample(CityExample example) throws SQLException {
        int rows = sqlMapClient.delete("city.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table city
     *
     * @ibatorgenerated Thu Apr 01 13:57:44 CST 2010
     */
    public int deleteByPrimaryKey(Integer id) throws SQLException {
        City key = new City();
        key.setId(id);
        int rows = sqlMapClient.delete("city.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table city
     *
     * @ibatorgenerated Thu Apr 01 13:57:44 CST 2010
     */
    public Integer insert(City record) throws SQLException {
        Object newKey = sqlMapClient.insert("city.ibatorgenerated_insert", record);
        return (Integer) newKey;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table city
     *
     * @ibatorgenerated Thu Apr 01 13:57:44 CST 2010
     */
    public Integer insertSelective(City record) throws SQLException {
        Object newKey = sqlMapClient.insert("city.ibatorgenerated_insertSelective", record);
        return (Integer) newKey;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table city
     *
     * @ibatorgenerated Thu Apr 01 13:57:44 CST 2010
     */
    public List selectByExample(CityExample example) throws SQLException {
        List list = sqlMapClient.queryForList("city.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table city
     *
     * @ibatorgenerated Thu Apr 01 13:57:44 CST 2010
     */
    public City selectByPrimaryKey(Integer id) throws SQLException {
        City key = new City();
        key.setId(id);
        City record = (City) sqlMapClient.queryForObject("city.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table city
     *
     * @ibatorgenerated Thu Apr 01 13:57:44 CST 2010
     */
    public int updateByExampleSelective(City record, CityExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("city.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table city
     *
     * @ibatorgenerated Thu Apr 01 13:57:44 CST 2010
     */
    public int updateByExample(City record, CityExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("city.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table city
     *
     * @ibatorgenerated Thu Apr 01 13:57:44 CST 2010
     */
    public int updateByPrimaryKeySelective(City record) throws SQLException {
        int rows = sqlMapClient.update("city.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table city
     *
     * @ibatorgenerated Thu Apr 01 13:57:44 CST 2010
     */
    public int updateByPrimaryKey(City record) throws SQLException {
        int rows = sqlMapClient.update("city.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table city
     *
     * @ibatorgenerated Thu Apr 01 13:57:44 CST 2010
     */
    private static class UpdateByExampleParms extends CityExample {
        private Object record;

        public UpdateByExampleParms(Object record, CityExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}
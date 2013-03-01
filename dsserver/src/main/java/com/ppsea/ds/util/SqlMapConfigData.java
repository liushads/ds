package com.ppsea.ds.util;

import java.io.Reader;

import org.apache.log4j.Logger;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

/**
 * ClassName:SqlMapConfig 
 * 
 * @author Daniel.Hao
 * @version
 * @since Ver 1.0
 * @Date 2008 Dec 3, 2008 9:16:37 PM
 * 
 * @see
 */
public class SqlMapConfigData {
	private static final Logger log = Logger.getLogger(SqlMapConfigData.class);
	
	private static SqlMapClient sqlMap;

	public static SqlMapClient getSqlMapInstance() {
		return sqlMap;
	}
	
	public static void init(){
		try {
			String resource = "com/ppsea/ds/data/maps/SqlMapConfig_Data.xml";
			Reader reader = Resources.getResourceAsReader(resource);
			sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
		} catch (Exception e) {
			// If you get an error at this point, it doesn’t matter what it was.
			// It is going to be
			// unrecoverable and we will want the app to blow up hard so we are
			// aware of the
			// problem. You should always log such errors and re-throw them in
			// such a way that
			// you can be made immediately aware of the problem.
			log.error("exception", e);
			throw new RuntimeException("Error initializing: " + e);
		}
	}
}

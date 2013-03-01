package com.ppsea.ds.data.dao;

import com.ppsea.ds.data.model.MemberPriviliges;
import com.ppsea.ds.data.model.MemberPriviligesExample;
import java.sql.SQLException;
import java.util.List;

public interface MemberPriviligesDAO {

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table member_priviliges
     * @ibatorgenerated  Wed Dec 15 13:49:33 CST 2010
     */
    int countByExample(MemberPriviligesExample example) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table member_priviliges
     * @ibatorgenerated  Wed Dec 15 13:49:33 CST 2010
     */
    int deleteByExample(MemberPriviligesExample example) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table member_priviliges
     * @ibatorgenerated  Wed Dec 15 13:49:33 CST 2010
     */
    int deleteByPrimaryKey(Integer id) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table member_priviliges
     * @ibatorgenerated  Wed Dec 15 13:49:33 CST 2010
     */
    Integer insert(MemberPriviliges record) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table member_priviliges
     * @ibatorgenerated  Wed Dec 15 13:49:33 CST 2010
     */
    Integer insertSelective(MemberPriviliges record) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table member_priviliges
     * @ibatorgenerated  Wed Dec 15 13:49:33 CST 2010
     */
    List selectByExample(MemberPriviligesExample example) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table member_priviliges
     * @ibatorgenerated  Wed Dec 15 13:49:33 CST 2010
     */
    MemberPriviliges selectByPrimaryKey(Integer id) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table member_priviliges
     * @ibatorgenerated  Wed Dec 15 13:49:33 CST 2010
     */
    int updateByExampleSelective(MemberPriviliges record,
            MemberPriviligesExample example) throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table member_priviliges
     * @ibatorgenerated  Wed Dec 15 13:49:33 CST 2010
     */
    int updateByExample(MemberPriviliges record, MemberPriviligesExample example)
            throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table member_priviliges
     * @ibatorgenerated  Wed Dec 15 13:49:33 CST 2010
     */
    int updateByPrimaryKeySelective(MemberPriviliges record)
            throws SQLException;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table member_priviliges
     * @ibatorgenerated  Wed Dec 15 13:49:33 CST 2010
     */
    int updateByPrimaryKey(MemberPriviliges record) throws SQLException;
}
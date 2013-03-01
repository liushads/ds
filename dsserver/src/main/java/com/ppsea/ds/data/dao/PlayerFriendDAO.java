package com.ppsea.ds.data.dao;

import com.ppsea.ds.data.model.PlayerFriend;
import com.ppsea.ds.data.model.PlayerFriendExample;
import java.sql.SQLException;
import java.util.List;

public interface PlayerFriendDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_friend
     *
     * @ibatorgenerated Fri Apr 23 12:45:25 CST 2010
     */
    int countByExample(PlayerFriendExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_friend
     *
     * @ibatorgenerated Fri Apr 23 12:45:25 CST 2010
     */
    int deleteByExample(PlayerFriendExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_friend
     *
     * @ibatorgenerated Fri Apr 23 12:45:25 CST 2010
     */
    int deleteByPrimaryKey(Integer id) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_friend
     *
     * @ibatorgenerated Fri Apr 23 12:45:25 CST 2010
     */
    void insert(PlayerFriend record) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_friend
     *
     * @ibatorgenerated Fri Apr 23 12:45:25 CST 2010
     */
    void insertSelective(PlayerFriend record) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_friend
     *
     * @ibatorgenerated Fri Apr 23 12:45:25 CST 2010
     */
    List<PlayerFriend> selectByExample(PlayerFriendExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_friend
     *
     * @ibatorgenerated Fri Apr 23 12:45:25 CST 2010
     */
    PlayerFriend selectByPrimaryKey(Integer id) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_friend
     *
     * @ibatorgenerated Fri Apr 23 12:45:25 CST 2010
     */
    int updateByExampleSelective(PlayerFriend record, PlayerFriendExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_friend
     *
     * @ibatorgenerated Fri Apr 23 12:45:25 CST 2010
     */
    int updateByExample(PlayerFriend record, PlayerFriendExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_friend
     *
     * @ibatorgenerated Fri Apr 23 12:45:25 CST 2010
     */
    int updateByPrimaryKeySelective(PlayerFriend record) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table player_friend
     *
     * @ibatorgenerated Fri Apr 23 12:45:25 CST 2010
     */
    int updateByPrimaryKey(PlayerFriend record) throws SQLException;
}
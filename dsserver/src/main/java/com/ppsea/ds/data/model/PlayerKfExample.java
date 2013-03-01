package com.ppsea.ds.data.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerKfExample {

	/**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database table player_kf
     * @ibatorgenerated  Wed Aug 25 17:39:10 CST 2010
     */
    protected String orderByClause;
	/**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database table player_kf
     * @ibatorgenerated  Wed Aug 25 17:39:10 CST 2010
     */
    protected List oredCriteria;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_kf
     * @ibatorgenerated  Wed Aug 25 17:39:10 CST 2010
     */
    public PlayerKfExample() {
	    oredCriteria = new ArrayList();
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_kf
     * @ibatorgenerated  Wed Aug 25 17:39:10 CST 2010
     */
    protected PlayerKfExample(PlayerKfExample example) {
	    this.orderByClause = example.orderByClause;
	    this.oredCriteria = example.oredCriteria;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_kf
     * @ibatorgenerated  Wed Aug 25 17:39:10 CST 2010
     */
    public void setOrderByClause(String orderByClause) {
	    this.orderByClause = orderByClause;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_kf
     * @ibatorgenerated  Wed Aug 25 17:39:10 CST 2010
     */
    public String getOrderByClause() {
	    return orderByClause;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_kf
     * @ibatorgenerated  Wed Aug 25 17:39:10 CST 2010
     */
    public List getOredCriteria() {
	    return oredCriteria;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_kf
     * @ibatorgenerated  Wed Aug 25 17:39:10 CST 2010
     */
    public void or(Criteria criteria) {
	    oredCriteria.add(criteria);
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_kf
     * @ibatorgenerated  Wed Aug 25 17:39:10 CST 2010
     */
    public Criteria createCriteria() {
	    Criteria criteria = createCriteriaInternal();
	    if (oredCriteria.size() == 0) {
		    oredCriteria.add(criteria);
	    }
	    return criteria;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_kf
     * @ibatorgenerated  Wed Aug 25 17:39:10 CST 2010
     */
    protected Criteria createCriteriaInternal() {
	    Criteria criteria = new Criteria();
	    return criteria;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_kf
     * @ibatorgenerated  Wed Aug 25 17:39:10 CST 2010
     */
    public void clear() {
	    oredCriteria.clear();
    }

	/**
     * This class was generated by Apache iBATIS ibator. This class corresponds to the database table player_kf
     * @ibatorgenerated  Wed Aug 25 17:39:10 CST 2010
     */
    public static class Criteria {
	    protected List criteriaWithoutValue;
	    protected List criteriaWithSingleValue;
	    protected List criteriaWithListValue;
	    protected List criteriaWithBetweenValue;

	    protected Criteria() {
		    super();
		    criteriaWithoutValue = new ArrayList();
		    criteriaWithSingleValue = new ArrayList();
		    criteriaWithListValue = new ArrayList();
		    criteriaWithBetweenValue = new ArrayList();
	    }

	    public boolean isValid() {
		    return criteriaWithoutValue.size() > 0
		            || criteriaWithSingleValue.size() > 0
		            || criteriaWithListValue.size() > 0
		            || criteriaWithBetweenValue.size() > 0;
	    }

	    public List getCriteriaWithoutValue() {
		    return criteriaWithoutValue;
	    }

	    public List getCriteriaWithSingleValue() {
		    return criteriaWithSingleValue;
	    }

	    public List getCriteriaWithListValue() {
		    return criteriaWithListValue;
	    }

	    public List getCriteriaWithBetweenValue() {
		    return criteriaWithBetweenValue;
	    }

	    protected void addCriterion(String condition) {
		    if (condition == null) {
			    throw new RuntimeException("Value for condition cannot be null");
		    }
		    criteriaWithoutValue.add(condition);
	    }

	    protected void addCriterion(String condition, Object value,
	            String property) {
		    if (value == null) {
			    throw new RuntimeException("Value for " + property
			            + " cannot be null");
		    }
		    Map map = new HashMap();
		    map.put("condition", condition);
		    map.put("value", value);
		    criteriaWithSingleValue.add(map);
	    }

	    protected void addCriterion(String condition, List values,
	            String property) {
		    if (values == null || values.size() == 0) {
			    throw new RuntimeException("Value list for " + property
			            + " cannot be null or empty");
		    }
		    Map map = new HashMap();
		    map.put("condition", condition);
		    map.put("values", values);
		    criteriaWithListValue.add(map);
	    }

	    protected void addCriterion(String condition, Object value1,
	            Object value2, String property) {
		    if (value1 == null || value2 == null) {
			    throw new RuntimeException("Between values for " + property
			            + " cannot be null");
		    }
		    List list = new ArrayList();
		    list.add(value1);
		    list.add(value2);
		    Map map = new HashMap();
		    map.put("condition", condition);
		    map.put("values", list);
		    criteriaWithBetweenValue.add(map);
	    }

	    public Criteria andIdIsNull() {
		    addCriterion("id is null");
		    return this;
	    }

	    public Criteria andIdIsNotNull() {
		    addCriterion("id is not null");
		    return this;
	    }

	    public Criteria andIdEqualTo(Integer value) {
		    addCriterion("id =", value, "id");
		    return this;
	    }

	    public Criteria andIdNotEqualTo(Integer value) {
		    addCriterion("id <>", value, "id");
		    return this;
	    }

	    public Criteria andIdGreaterThan(Integer value) {
		    addCriterion("id >", value, "id");
		    return this;
	    }

	    public Criteria andIdGreaterThanOrEqualTo(Integer value) {
		    addCriterion("id >=", value, "id");
		    return this;
	    }

	    public Criteria andIdLessThan(Integer value) {
		    addCriterion("id <", value, "id");
		    return this;
	    }

	    public Criteria andIdLessThanOrEqualTo(Integer value) {
		    addCriterion("id <=", value, "id");
		    return this;
	    }

	    public Criteria andIdIn(List values) {
		    addCriterion("id in", values, "id");
		    return this;
	    }

	    public Criteria andIdNotIn(List values) {
		    addCriterion("id not in", values, "id");
		    return this;
	    }

	    public Criteria andIdBetween(Integer value1, Integer value2) {
		    addCriterion("id between", value1, value2, "id");
		    return this;
	    }

	    public Criteria andIdNotBetween(Integer value1, Integer value2) {
		    addCriterion("id not between", value1, value2, "id");
		    return this;
	    }

	    public Criteria andPlayerIdIsNull() {
		    addCriterion("player_id is null");
		    return this;
	    }

	    public Criteria andPlayerIdIsNotNull() {
		    addCriterion("player_id is not null");
		    return this;
	    }

	    public Criteria andPlayerIdEqualTo(Integer value) {
		    addCriterion("player_id =", value, "playerId");
		    return this;
	    }

	    public Criteria andPlayerIdNotEqualTo(Integer value) {
		    addCriterion("player_id <>", value, "playerId");
		    return this;
	    }

	    public Criteria andPlayerIdGreaterThan(Integer value) {
		    addCriterion("player_id >", value, "playerId");
		    return this;
	    }

	    public Criteria andPlayerIdGreaterThanOrEqualTo(Integer value) {
		    addCriterion("player_id >=", value, "playerId");
		    return this;
	    }

	    public Criteria andPlayerIdLessThan(Integer value) {
		    addCriterion("player_id <", value, "playerId");
		    return this;
	    }

	    public Criteria andPlayerIdLessThanOrEqualTo(Integer value) {
		    addCriterion("player_id <=", value, "playerId");
		    return this;
	    }

	    public Criteria andPlayerIdIn(List values) {
		    addCriterion("player_id in", values, "playerId");
		    return this;
	    }

	    public Criteria andPlayerIdNotIn(List values) {
		    addCriterion("player_id not in", values, "playerId");
		    return this;
	    }

	    public Criteria andPlayerIdBetween(Integer value1, Integer value2) {
		    addCriterion("player_id between", value1, value2, "playerId");
		    return this;
	    }

	    public Criteria andPlayerIdNotBetween(Integer value1, Integer value2) {
		    addCriterion("player_id not between", value1, value2, "playerId");
		    return this;
	    }

	    public Criteria andPlayerNameIsNull() {
		    addCriterion("player_name is null");
		    return this;
	    }

	    public Criteria andPlayerNameIsNotNull() {
		    addCriterion("player_name is not null");
		    return this;
	    }

	    public Criteria andPlayerNameEqualTo(String value) {
		    addCriterion("player_name =", value, "playerName");
		    return this;
	    }

	    public Criteria andPlayerNameNotEqualTo(String value) {
		    addCriterion("player_name <>", value, "playerName");
		    return this;
	    }

	    public Criteria andPlayerNameGreaterThan(String value) {
		    addCriterion("player_name >", value, "playerName");
		    return this;
	    }

	    public Criteria andPlayerNameGreaterThanOrEqualTo(String value) {
		    addCriterion("player_name >=", value, "playerName");
		    return this;
	    }

	    public Criteria andPlayerNameLessThan(String value) {
		    addCriterion("player_name <", value, "playerName");
		    return this;
	    }

	    public Criteria andPlayerNameLessThanOrEqualTo(String value) {
		    addCriterion("player_name <=", value, "playerName");
		    return this;
	    }

	    public Criteria andPlayerNameLike(String value) {
		    addCriterion("player_name like", value, "playerName");
		    return this;
	    }

	    public Criteria andPlayerNameNotLike(String value) {
		    addCriterion("player_name not like", value, "playerName");
		    return this;
	    }

	    public Criteria andPlayerNameIn(List values) {
		    addCriterion("player_name in", values, "playerName");
		    return this;
	    }

	    public Criteria andPlayerNameNotIn(List values) {
		    addCriterion("player_name not in", values, "playerName");
		    return this;
	    }

	    public Criteria andPlayerNameBetween(String value1, String value2) {
		    addCriterion("player_name between", value1, value2, "playerName");
		    return this;
	    }

	    public Criteria andPlayerNameNotBetween(String value1, String value2) {
		    addCriterion("player_name not between", value1, value2,
		            "playerName");
		    return this;
	    }

	    public Criteria andCreateDateIsNull() {
		    addCriterion("create_date is null");
		    return this;
	    }

	    public Criteria andCreateDateIsNotNull() {
		    addCriterion("create_date is not null");
		    return this;
	    }

	    public Criteria andCreateDateEqualTo(Date value) {
		    addCriterion("create_date =", value, "createDate");
		    return this;
	    }

	    public Criteria andCreateDateNotEqualTo(Date value) {
		    addCriterion("create_date <>", value, "createDate");
		    return this;
	    }

	    public Criteria andCreateDateGreaterThan(Date value) {
		    addCriterion("create_date >", value, "createDate");
		    return this;
	    }

	    public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
		    addCriterion("create_date >=", value, "createDate");
		    return this;
	    }

	    public Criteria andCreateDateLessThan(Date value) {
		    addCriterion("create_date <", value, "createDate");
		    return this;
	    }

	    public Criteria andCreateDateLessThanOrEqualTo(Date value) {
		    addCriterion("create_date <=", value, "createDate");
		    return this;
	    }

	    public Criteria andCreateDateIn(List values) {
		    addCriterion("create_date in", values, "createDate");
		    return this;
	    }

	    public Criteria andCreateDateNotIn(List values) {
		    addCriterion("create_date not in", values, "createDate");
		    return this;
	    }

	    public Criteria andCreateDateBetween(Date value1, Date value2) {
		    addCriterion("create_date between", value1, value2, "createDate");
		    return this;
	    }

	    public Criteria andCreateDateNotBetween(Date value1, Date value2) {
		    addCriterion("create_date not between", value1, value2,
		            "createDate");
		    return this;
	    }

	    public Criteria andLastServerDateIsNull() {
		    addCriterion("last_server_date is null");
		    return this;
	    }

	    public Criteria andLastServerDateIsNotNull() {
		    addCriterion("last_server_date is not null");
		    return this;
	    }

	    public Criteria andLastServerDateEqualTo(Date value) {
		    addCriterion("last_server_date =", value, "lastServerDate");
		    return this;
	    }

	    public Criteria andLastServerDateNotEqualTo(Date value) {
		    addCriterion("last_server_date <>", value, "lastServerDate");
		    return this;
	    }

	    public Criteria andLastServerDateGreaterThan(Date value) {
		    addCriterion("last_server_date >", value, "lastServerDate");
		    return this;
	    }

	    public Criteria andLastServerDateGreaterThanOrEqualTo(Date value) {
		    addCriterion("last_server_date >=", value, "lastServerDate");
		    return this;
	    }

	    public Criteria andLastServerDateLessThan(Date value) {
		    addCriterion("last_server_date <", value, "lastServerDate");
		    return this;
	    }

	    public Criteria andLastServerDateLessThanOrEqualTo(Date value) {
		    addCriterion("last_server_date <=", value, "lastServerDate");
		    return this;
	    }

	    public Criteria andLastServerDateIn(List values) {
		    addCriterion("last_server_date in", values, "lastServerDate");
		    return this;
	    }

	    public Criteria andLastServerDateNotIn(List values) {
		    addCriterion("last_server_date not in", values, "lastServerDate");
		    return this;
	    }

	    public Criteria andLastServerDateBetween(Date value1, Date value2) {
		    addCriterion("last_server_date between", value1, value2,
		            "lastServerDate");
		    return this;
	    }

	    public Criteria andLastServerDateNotBetween(Date value1, Date value2) {
		    addCriterion("last_server_date not between", value1, value2,
		            "lastServerDate");
		    return this;
	    }

	    public Criteria andHonorValueIsNull() {
		    addCriterion("honor_value is null");
		    return this;
	    }

	    public Criteria andHonorValueIsNotNull() {
		    addCriterion("honor_value is not null");
		    return this;
	    }

	    public Criteria andHonorValueEqualTo(Integer value) {
		    addCriterion("honor_value =", value, "honorValue");
		    return this;
	    }

	    public Criteria andHonorValueNotEqualTo(Integer value) {
		    addCriterion("honor_value <>", value, "honorValue");
		    return this;
	    }

	    public Criteria andHonorValueGreaterThan(Integer value) {
		    addCriterion("honor_value >", value, "honorValue");
		    return this;
	    }

	    public Criteria andHonorValueGreaterThanOrEqualTo(Integer value) {
		    addCriterion("honor_value >=", value, "honorValue");
		    return this;
	    }

	    public Criteria andHonorValueLessThan(Integer value) {
		    addCriterion("honor_value <", value, "honorValue");
		    return this;
	    }

	    public Criteria andHonorValueLessThanOrEqualTo(Integer value) {
		    addCriterion("honor_value <=", value, "honorValue");
		    return this;
	    }

	    public Criteria andHonorValueIn(List values) {
		    addCriterion("honor_value in", values, "honorValue");
		    return this;
	    }

	    public Criteria andHonorValueNotIn(List values) {
		    addCriterion("honor_value not in", values, "honorValue");
		    return this;
	    }

	    public Criteria andHonorValueBetween(Integer value1, Integer value2) {
		    addCriterion("honor_value between", value1, value2, "honorValue");
		    return this;
	    }

	    public Criteria andHonorValueNotBetween(Integer value1, Integer value2) {
		    addCriterion("honor_value not between", value1, value2,
		            "honorValue");
		    return this;
	    }

	    public Criteria andTotalSeverTimesIsNull() {
		    addCriterion("total_sever_times is null");
		    return this;
	    }

	    public Criteria andTotalSeverTimesIsNotNull() {
		    addCriterion("total_sever_times is not null");
		    return this;
	    }

	    public Criteria andTotalSeverTimesEqualTo(Integer value) {
		    addCriterion("total_sever_times =", value, "totalSeverTimes");
		    return this;
	    }

	    public Criteria andTotalSeverTimesNotEqualTo(Integer value) {
		    addCriterion("total_sever_times <>", value, "totalSeverTimes");
		    return this;
	    }

	    public Criteria andTotalSeverTimesGreaterThan(Integer value) {
		    addCriterion("total_sever_times >", value, "totalSeverTimes");
		    return this;
	    }

	    public Criteria andTotalSeverTimesGreaterThanOrEqualTo(Integer value) {
		    addCriterion("total_sever_times >=", value, "totalSeverTimes");
		    return this;
	    }

	    public Criteria andTotalSeverTimesLessThan(Integer value) {
		    addCriterion("total_sever_times <", value, "totalSeverTimes");
		    return this;
	    }

	    public Criteria andTotalSeverTimesLessThanOrEqualTo(Integer value) {
		    addCriterion("total_sever_times <=", value, "totalSeverTimes");
		    return this;
	    }

	    public Criteria andTotalSeverTimesIn(List values) {
		    addCriterion("total_sever_times in", values, "totalSeverTimes");
		    return this;
	    }

	    public Criteria andTotalSeverTimesNotIn(List values) {
		    addCriterion("total_sever_times not in", values, "totalSeverTimes");
		    return this;
	    }

	    public Criteria andTotalSeverTimesBetween(Integer value1, Integer value2) {
		    addCriterion("total_sever_times between", value1, value2,
		            "totalSeverTimes");
		    return this;
	    }

	    public Criteria andTotalSeverTimesNotBetween(Integer value1,
	            Integer value2) {
		    addCriterion("total_sever_times not between", value1, value2,
		            "totalSeverTimes");
		    return this;
	    }
    }
}
package com.ppsea.ds.data.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerPayExample {

	/**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database table player_pay
     * @ibatorgenerated  Thu Nov 25 13:40:20 CST 2010
     */
    protected String orderByClause;
	/**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database table player_pay
     * @ibatorgenerated  Thu Nov 25 13:40:20 CST 2010
     */
    protected List oredCriteria;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_pay
     * @ibatorgenerated  Thu Nov 25 13:40:20 CST 2010
     */
    public PlayerPayExample() {
	    oredCriteria = new ArrayList();
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_pay
     * @ibatorgenerated  Thu Nov 25 13:40:20 CST 2010
     */
    protected PlayerPayExample(PlayerPayExample example) {
	    this.orderByClause = example.orderByClause;
	    this.oredCriteria = example.oredCriteria;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_pay
     * @ibatorgenerated  Thu Nov 25 13:40:20 CST 2010
     */
    public void setOrderByClause(String orderByClause) {
	    this.orderByClause = orderByClause;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_pay
     * @ibatorgenerated  Thu Nov 25 13:40:20 CST 2010
     */
    public String getOrderByClause() {
	    return orderByClause;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_pay
     * @ibatorgenerated  Thu Nov 25 13:40:20 CST 2010
     */
    public List getOredCriteria() {
	    return oredCriteria;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_pay
     * @ibatorgenerated  Thu Nov 25 13:40:20 CST 2010
     */
    public void or(Criteria criteria) {
	    oredCriteria.add(criteria);
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_pay
     * @ibatorgenerated  Thu Nov 25 13:40:20 CST 2010
     */
    public Criteria createCriteria() {
	    Criteria criteria = createCriteriaInternal();
	    if (oredCriteria.size() == 0) {
		    oredCriteria.add(criteria);
	    }
	    return criteria;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_pay
     * @ibatorgenerated  Thu Nov 25 13:40:20 CST 2010
     */
    protected Criteria createCriteriaInternal() {
	    Criteria criteria = new Criteria();
	    return criteria;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_pay
     * @ibatorgenerated  Thu Nov 25 13:40:20 CST 2010
     */
    public void clear() {
	    oredCriteria.clear();
    }

	/**
     * This class was generated by Apache iBATIS ibator. This class corresponds to the database table player_pay
     * @ibatorgenerated  Thu Nov 25 13:40:20 CST 2010
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

	    public Criteria andStateIsNull() {
		    addCriterion("state is null");
		    return this;
	    }

	    public Criteria andStateIsNotNull() {
		    addCriterion("state is not null");
		    return this;
	    }

	    public Criteria andStateEqualTo(Integer value) {
		    addCriterion("state =", value, "state");
		    return this;
	    }

	    public Criteria andStateNotEqualTo(Integer value) {
		    addCriterion("state <>", value, "state");
		    return this;
	    }

	    public Criteria andStateGreaterThan(Integer value) {
		    addCriterion("state >", value, "state");
		    return this;
	    }

	    public Criteria andStateGreaterThanOrEqualTo(Integer value) {
		    addCriterion("state >=", value, "state");
		    return this;
	    }

	    public Criteria andStateLessThan(Integer value) {
		    addCriterion("state <", value, "state");
		    return this;
	    }

	    public Criteria andStateLessThanOrEqualTo(Integer value) {
		    addCriterion("state <=", value, "state");
		    return this;
	    }

	    public Criteria andStateIn(List values) {
		    addCriterion("state in", values, "state");
		    return this;
	    }

	    public Criteria andStateNotIn(List values) {
		    addCriterion("state not in", values, "state");
		    return this;
	    }

	    public Criteria andStateBetween(Integer value1, Integer value2) {
		    addCriterion("state between", value1, value2, "state");
		    return this;
	    }

	    public Criteria andStateNotBetween(Integer value1, Integer value2) {
		    addCriterion("state not between", value1, value2, "state");
		    return this;
	    }

	    public Criteria andCreateTimeIsNull() {
		    addCriterion("create_time is null");
		    return this;
	    }

	    public Criteria andCreateTimeIsNotNull() {
		    addCriterion("create_time is not null");
		    return this;
	    }

	    public Criteria andCreateTimeEqualTo(Date value) {
		    addCriterion("create_time =", value, "createTime");
		    return this;
	    }

	    public Criteria andCreateTimeNotEqualTo(Date value) {
		    addCriterion("create_time <>", value, "createTime");
		    return this;
	    }

	    public Criteria andCreateTimeGreaterThan(Date value) {
		    addCriterion("create_time >", value, "createTime");
		    return this;
	    }

	    public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
		    addCriterion("create_time >=", value, "createTime");
		    return this;
	    }

	    public Criteria andCreateTimeLessThan(Date value) {
		    addCriterion("create_time <", value, "createTime");
		    return this;
	    }

	    public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
		    addCriterion("create_time <=", value, "createTime");
		    return this;
	    }

	    public Criteria andCreateTimeIn(List values) {
		    addCriterion("create_time in", values, "createTime");
		    return this;
	    }

	    public Criteria andCreateTimeNotIn(List values) {
		    addCriterion("create_time not in", values, "createTime");
		    return this;
	    }

	    public Criteria andCreateTimeBetween(Date value1, Date value2) {
		    addCriterion("create_time between", value1, value2, "createTime");
		    return this;
	    }

	    public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
		    addCriterion("create_time not between", value1, value2,
		            "createTime");
		    return this;
	    }
    }
}
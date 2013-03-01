package com.ppsea.ds.data.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerStoreRoomExample {

	/**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database table player_store_room
     * @ibatorgenerated  Fri Feb 25 12:15:41 CST 2011
     */
    protected String orderByClause;
	/**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database table player_store_room
     * @ibatorgenerated  Fri Feb 25 12:15:41 CST 2011
     */
    protected List oredCriteria;

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_store_room
     * @ibatorgenerated  Fri Feb 25 12:15:41 CST 2011
     */
    public PlayerStoreRoomExample() {
	    oredCriteria = new ArrayList();
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_store_room
     * @ibatorgenerated  Fri Feb 25 12:15:41 CST 2011
     */
    protected PlayerStoreRoomExample(PlayerStoreRoomExample example) {
	    this.orderByClause = example.orderByClause;
	    this.oredCriteria = example.oredCriteria;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_store_room
     * @ibatorgenerated  Fri Feb 25 12:15:41 CST 2011
     */
    public void setOrderByClause(String orderByClause) {
	    this.orderByClause = orderByClause;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_store_room
     * @ibatorgenerated  Fri Feb 25 12:15:41 CST 2011
     */
    public String getOrderByClause() {
	    return orderByClause;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_store_room
     * @ibatorgenerated  Fri Feb 25 12:15:41 CST 2011
     */
    public List getOredCriteria() {
	    return oredCriteria;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_store_room
     * @ibatorgenerated  Fri Feb 25 12:15:41 CST 2011
     */
    public void or(Criteria criteria) {
	    oredCriteria.add(criteria);
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_store_room
     * @ibatorgenerated  Fri Feb 25 12:15:41 CST 2011
     */
    public Criteria createCriteria() {
	    Criteria criteria = createCriteriaInternal();
	    if (oredCriteria.size() == 0) {
		    oredCriteria.add(criteria);
	    }
	    return criteria;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_store_room
     * @ibatorgenerated  Fri Feb 25 12:15:41 CST 2011
     */
    protected Criteria createCriteriaInternal() {
	    Criteria criteria = new Criteria();
	    return criteria;
    }

	/**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_store_room
     * @ibatorgenerated  Fri Feb 25 12:15:41 CST 2011
     */
    public void clear() {
	    oredCriteria.clear();
    }

	/**
     * This class was generated by Apache iBATIS ibator. This class corresponds to the database table player_store_room
     * @ibatorgenerated  Fri Feb 25 12:15:41 CST 2011
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

	    public Criteria andItemIdIsNull() {
		    addCriterion("item_id is null");
		    return this;
	    }

	    public Criteria andItemIdIsNotNull() {
		    addCriterion("item_id is not null");
		    return this;
	    }

	    public Criteria andItemIdEqualTo(Integer value) {
		    addCriterion("item_id =", value, "itemId");
		    return this;
	    }

	    public Criteria andItemIdNotEqualTo(Integer value) {
		    addCriterion("item_id <>", value, "itemId");
		    return this;
	    }

	    public Criteria andItemIdGreaterThan(Integer value) {
		    addCriterion("item_id >", value, "itemId");
		    return this;
	    }

	    public Criteria andItemIdGreaterThanOrEqualTo(Integer value) {
		    addCriterion("item_id >=", value, "itemId");
		    return this;
	    }

	    public Criteria andItemIdLessThan(Integer value) {
		    addCriterion("item_id <", value, "itemId");
		    return this;
	    }

	    public Criteria andItemIdLessThanOrEqualTo(Integer value) {
		    addCriterion("item_id <=", value, "itemId");
		    return this;
	    }

	    public Criteria andItemIdIn(List values) {
		    addCriterion("item_id in", values, "itemId");
		    return this;
	    }

	    public Criteria andItemIdNotIn(List values) {
		    addCriterion("item_id not in", values, "itemId");
		    return this;
	    }

	    public Criteria andItemIdBetween(Integer value1, Integer value2) {
		    addCriterion("item_id between", value1, value2, "itemId");
		    return this;
	    }

	    public Criteria andItemIdNotBetween(Integer value1, Integer value2) {
		    addCriterion("item_id not between", value1, value2, "itemId");
		    return this;
	    }

	    public Criteria andCurrEndureIsNull() {
		    addCriterion("curr_endure is null");
		    return this;
	    }

	    public Criteria andCurrEndureIsNotNull() {
		    addCriterion("curr_endure is not null");
		    return this;
	    }

	    public Criteria andCurrEndureEqualTo(Integer value) {
		    addCriterion("curr_endure =", value, "currEndure");
		    return this;
	    }

	    public Criteria andCurrEndureNotEqualTo(Integer value) {
		    addCriterion("curr_endure <>", value, "currEndure");
		    return this;
	    }

	    public Criteria andCurrEndureGreaterThan(Integer value) {
		    addCriterion("curr_endure >", value, "currEndure");
		    return this;
	    }

	    public Criteria andCurrEndureGreaterThanOrEqualTo(Integer value) {
		    addCriterion("curr_endure >=", value, "currEndure");
		    return this;
	    }

	    public Criteria andCurrEndureLessThan(Integer value) {
		    addCriterion("curr_endure <", value, "currEndure");
		    return this;
	    }

	    public Criteria andCurrEndureLessThanOrEqualTo(Integer value) {
		    addCriterion("curr_endure <=", value, "currEndure");
		    return this;
	    }

	    public Criteria andCurrEndureIn(List values) {
		    addCriterion("curr_endure in", values, "currEndure");
		    return this;
	    }

	    public Criteria andCurrEndureNotIn(List values) {
		    addCriterion("curr_endure not in", values, "currEndure");
		    return this;
	    }

	    public Criteria andCurrEndureBetween(Integer value1, Integer value2) {
		    addCriterion("curr_endure between", value1, value2, "currEndure");
		    return this;
	    }

	    public Criteria andCurrEndureNotBetween(Integer value1, Integer value2) {
		    addCriterion("curr_endure not between", value1, value2,
		            "currEndure");
		    return this;
	    }

	    public Criteria andAmountIsNull() {
		    addCriterion("amount is null");
		    return this;
	    }

	    public Criteria andAmountIsNotNull() {
		    addCriterion("amount is not null");
		    return this;
	    }

	    public Criteria andAmountEqualTo(Integer value) {
		    addCriterion("amount =", value, "amount");
		    return this;
	    }

	    public Criteria andAmountNotEqualTo(Integer value) {
		    addCriterion("amount <>", value, "amount");
		    return this;
	    }

	    public Criteria andAmountGreaterThan(Integer value) {
		    addCriterion("amount >", value, "amount");
		    return this;
	    }

	    public Criteria andAmountGreaterThanOrEqualTo(Integer value) {
		    addCriterion("amount >=", value, "amount");
		    return this;
	    }

	    public Criteria andAmountLessThan(Integer value) {
		    addCriterion("amount <", value, "amount");
		    return this;
	    }

	    public Criteria andAmountLessThanOrEqualTo(Integer value) {
		    addCriterion("amount <=", value, "amount");
		    return this;
	    }

	    public Criteria andAmountIn(List values) {
		    addCriterion("amount in", values, "amount");
		    return this;
	    }

	    public Criteria andAmountNotIn(List values) {
		    addCriterion("amount not in", values, "amount");
		    return this;
	    }

	    public Criteria andAmountBetween(Integer value1, Integer value2) {
		    addCriterion("amount between", value1, value2, "amount");
		    return this;
	    }

	    public Criteria andAmountNotBetween(Integer value1, Integer value2) {
		    addCriterion("amount not between", value1, value2, "amount");
		    return this;
	    }

	    public Criteria andRoomIdIsNull() {
		    addCriterion("room_id is null");
		    return this;
	    }

	    public Criteria andRoomIdIsNotNull() {
		    addCriterion("room_id is not null");
		    return this;
	    }

	    public Criteria andRoomIdEqualTo(Integer value) {
		    addCriterion("room_id =", value, "roomId");
		    return this;
	    }

	    public Criteria andRoomIdNotEqualTo(Integer value) {
		    addCriterion("room_id <>", value, "roomId");
		    return this;
	    }

	    public Criteria andRoomIdGreaterThan(Integer value) {
		    addCriterion("room_id >", value, "roomId");
		    return this;
	    }

	    public Criteria andRoomIdGreaterThanOrEqualTo(Integer value) {
		    addCriterion("room_id >=", value, "roomId");
		    return this;
	    }

	    public Criteria andRoomIdLessThan(Integer value) {
		    addCriterion("room_id <", value, "roomId");
		    return this;
	    }

	    public Criteria andRoomIdLessThanOrEqualTo(Integer value) {
		    addCriterion("room_id <=", value, "roomId");
		    return this;
	    }

	    public Criteria andRoomIdIn(List values) {
		    addCriterion("room_id in", values, "roomId");
		    return this;
	    }

	    public Criteria andRoomIdNotIn(List values) {
		    addCriterion("room_id not in", values, "roomId");
		    return this;
	    }

	    public Criteria andRoomIdBetween(Integer value1, Integer value2) {
		    addCriterion("room_id between", value1, value2, "roomId");
		    return this;
	    }

	    public Criteria andRoomIdNotBetween(Integer value1, Integer value2) {
		    addCriterion("room_id not between", value1, value2, "roomId");
		    return this;
	    }

	    public Criteria andImproveLevelIsNull() {
		    addCriterion("improve_level is null");
		    return this;
	    }

	    public Criteria andImproveLevelIsNotNull() {
		    addCriterion("improve_level is not null");
		    return this;
	    }

	    public Criteria andImproveLevelEqualTo(Integer value) {
		    addCriterion("improve_level =", value, "improveLevel");
		    return this;
	    }

	    public Criteria andImproveLevelNotEqualTo(Integer value) {
		    addCriterion("improve_level <>", value, "improveLevel");
		    return this;
	    }

	    public Criteria andImproveLevelGreaterThan(Integer value) {
		    addCriterion("improve_level >", value, "improveLevel");
		    return this;
	    }

	    public Criteria andImproveLevelGreaterThanOrEqualTo(Integer value) {
		    addCriterion("improve_level >=", value, "improveLevel");
		    return this;
	    }

	    public Criteria andImproveLevelLessThan(Integer value) {
		    addCriterion("improve_level <", value, "improveLevel");
		    return this;
	    }

	    public Criteria andImproveLevelLessThanOrEqualTo(Integer value) {
		    addCriterion("improve_level <=", value, "improveLevel");
		    return this;
	    }

	    public Criteria andImproveLevelIn(List values) {
		    addCriterion("improve_level in", values, "improveLevel");
		    return this;
	    }

	    public Criteria andImproveLevelNotIn(List values) {
		    addCriterion("improve_level not in", values, "improveLevel");
		    return this;
	    }

	    public Criteria andImproveLevelBetween(Integer value1, Integer value2) {
		    addCriterion("improve_level between", value1, value2,
		            "improveLevel");
		    return this;
	    }

	    public Criteria andImproveLevelNotBetween(Integer value1, Integer value2) {
		    addCriterion("improve_level not between", value1, value2,
		            "improveLevel");
		    return this;
	    }

	    public Criteria andCurrHoleAmountIsNull() {
		    addCriterion("curr_hole_amount is null");
		    return this;
	    }

	    public Criteria andCurrHoleAmountIsNotNull() {
		    addCriterion("curr_hole_amount is not null");
		    return this;
	    }

	    public Criteria andCurrHoleAmountEqualTo(Integer value) {
		    addCriterion("curr_hole_amount =", value, "currHoleAmount");
		    return this;
	    }

	    public Criteria andCurrHoleAmountNotEqualTo(Integer value) {
		    addCriterion("curr_hole_amount <>", value, "currHoleAmount");
		    return this;
	    }

	    public Criteria andCurrHoleAmountGreaterThan(Integer value) {
		    addCriterion("curr_hole_amount >", value, "currHoleAmount");
		    return this;
	    }

	    public Criteria andCurrHoleAmountGreaterThanOrEqualTo(Integer value) {
		    addCriterion("curr_hole_amount >=", value, "currHoleAmount");
		    return this;
	    }

	    public Criteria andCurrHoleAmountLessThan(Integer value) {
		    addCriterion("curr_hole_amount <", value, "currHoleAmount");
		    return this;
	    }

	    public Criteria andCurrHoleAmountLessThanOrEqualTo(Integer value) {
		    addCriterion("curr_hole_amount <=", value, "currHoleAmount");
		    return this;
	    }

	    public Criteria andCurrHoleAmountIn(List values) {
		    addCriterion("curr_hole_amount in", values, "currHoleAmount");
		    return this;
	    }

	    public Criteria andCurrHoleAmountNotIn(List values) {
		    addCriterion("curr_hole_amount not in", values, "currHoleAmount");
		    return this;
	    }

	    public Criteria andCurrHoleAmountBetween(Integer value1, Integer value2) {
		    addCriterion("curr_hole_amount between", value1, value2,
		            "currHoleAmount");
		    return this;
	    }

	    public Criteria andCurrHoleAmountNotBetween(Integer value1,
	            Integer value2) {
		    addCriterion("curr_hole_amount not between", value1, value2,
		            "currHoleAmount");
		    return this;
	    }

	    public Criteria andStarLevelIsNull() {
		    addCriterion("star_level is null");
		    return this;
	    }

	    public Criteria andStarLevelIsNotNull() {
		    addCriterion("star_level is not null");
		    return this;
	    }

	    public Criteria andStarLevelEqualTo(Integer value) {
		    addCriterion("star_level =", value, "starLevel");
		    return this;
	    }

	    public Criteria andStarLevelNotEqualTo(Integer value) {
		    addCriterion("star_level <>", value, "starLevel");
		    return this;
	    }

	    public Criteria andStarLevelGreaterThan(Integer value) {
		    addCriterion("star_level >", value, "starLevel");
		    return this;
	    }

	    public Criteria andStarLevelGreaterThanOrEqualTo(Integer value) {
		    addCriterion("star_level >=", value, "starLevel");
		    return this;
	    }

	    public Criteria andStarLevelLessThan(Integer value) {
		    addCriterion("star_level <", value, "starLevel");
		    return this;
	    }

	    public Criteria andStarLevelLessThanOrEqualTo(Integer value) {
		    addCriterion("star_level <=", value, "starLevel");
		    return this;
	    }

	    public Criteria andStarLevelIn(List values) {
		    addCriterion("star_level in", values, "starLevel");
		    return this;
	    }

	    public Criteria andStarLevelNotIn(List values) {
		    addCriterion("star_level not in", values, "starLevel");
		    return this;
	    }

	    public Criteria andStarLevelBetween(Integer value1, Integer value2) {
		    addCriterion("star_level between", value1, value2, "starLevel");
		    return this;
	    }

	    public Criteria andStarLevelNotBetween(Integer value1, Integer value2) {
		    addCriterion("star_level not between", value1, value2, "starLevel");
		    return this;
	    }

	    public Criteria andPromotePropertyIsNull() {
		    addCriterion("promote_property is null");
		    return this;
	    }

	    public Criteria andPromotePropertyIsNotNull() {
		    addCriterion("promote_property is not null");
		    return this;
	    }

	    public Criteria andPromotePropertyEqualTo(String value) {
		    addCriterion("promote_property =", value, "promoteProperty");
		    return this;
	    }

	    public Criteria andPromotePropertyNotEqualTo(String value) {
		    addCriterion("promote_property <>", value, "promoteProperty");
		    return this;
	    }

	    public Criteria andPromotePropertyGreaterThan(String value) {
		    addCriterion("promote_property >", value, "promoteProperty");
		    return this;
	    }

	    public Criteria andPromotePropertyGreaterThanOrEqualTo(String value) {
		    addCriterion("promote_property >=", value, "promoteProperty");
		    return this;
	    }

	    public Criteria andPromotePropertyLessThan(String value) {
		    addCriterion("promote_property <", value, "promoteProperty");
		    return this;
	    }

	    public Criteria andPromotePropertyLessThanOrEqualTo(String value) {
		    addCriterion("promote_property <=", value, "promoteProperty");
		    return this;
	    }

	    public Criteria andPromotePropertyLike(String value) {
		    addCriterion("promote_property like", value, "promoteProperty");
		    return this;
	    }

	    public Criteria andPromotePropertyNotLike(String value) {
		    addCriterion("promote_property not like", value, "promoteProperty");
		    return this;
	    }

	    public Criteria andPromotePropertyIn(List values) {
		    addCriterion("promote_property in", values, "promoteProperty");
		    return this;
	    }

	    public Criteria andPromotePropertyNotIn(List values) {
		    addCriterion("promote_property not in", values, "promoteProperty");
		    return this;
	    }

	    public Criteria andPromotePropertyBetween(String value1, String value2) {
		    addCriterion("promote_property between", value1, value2,
		            "promoteProperty");
		    return this;
	    }

	    public Criteria andPromotePropertyNotBetween(String value1,
	            String value2) {
		    addCriterion("promote_property not between", value1, value2,
		            "promoteProperty");
		    return this;
	    }

	    public Criteria andCreatedTimeIsNull() {
		    addCriterion("created_time is null");
		    return this;
	    }

	    public Criteria andCreatedTimeIsNotNull() {
		    addCriterion("created_time is not null");
		    return this;
	    }

	    public Criteria andCreatedTimeEqualTo(Date value) {
		    addCriterion("created_time =", value, "createdTime");
		    return this;
	    }

	    public Criteria andCreatedTimeNotEqualTo(Date value) {
		    addCriterion("created_time <>", value, "createdTime");
		    return this;
	    }

	    public Criteria andCreatedTimeGreaterThan(Date value) {
		    addCriterion("created_time >", value, "createdTime");
		    return this;
	    }

	    public Criteria andCreatedTimeGreaterThanOrEqualTo(Date value) {
		    addCriterion("created_time >=", value, "createdTime");
		    return this;
	    }

	    public Criteria andCreatedTimeLessThan(Date value) {
		    addCriterion("created_time <", value, "createdTime");
		    return this;
	    }

	    public Criteria andCreatedTimeLessThanOrEqualTo(Date value) {
		    addCriterion("created_time <=", value, "createdTime");
		    return this;
	    }

	    public Criteria andCreatedTimeIn(List values) {
		    addCriterion("created_time in", values, "createdTime");
		    return this;
	    }

	    public Criteria andCreatedTimeNotIn(List values) {
		    addCriterion("created_time not in", values, "createdTime");
		    return this;
	    }

	    public Criteria andCreatedTimeBetween(Date value1, Date value2) {
		    addCriterion("created_time between", value1, value2, "createdTime");
		    return this;
	    }

	    public Criteria andCreatedTimeNotBetween(Date value1, Date value2) {
		    addCriterion("created_time not between", value1, value2,
		            "createdTime");
		    return this;
	    }

	    public Criteria andIsExchangeIsNull() {
		    addCriterion("is_exchange is null");
		    return this;
	    }

	    public Criteria andIsExchangeIsNotNull() {
		    addCriterion("is_exchange is not null");
		    return this;
	    }

	    public Criteria andIsExchangeEqualTo(Boolean value) {
		    addCriterion("is_exchange =", value, "isExchange");
		    return this;
	    }

	    public Criteria andIsExchangeNotEqualTo(Boolean value) {
		    addCriterion("is_exchange <>", value, "isExchange");
		    return this;
	    }

	    public Criteria andIsExchangeGreaterThan(Boolean value) {
		    addCriterion("is_exchange >", value, "isExchange");
		    return this;
	    }

	    public Criteria andIsExchangeGreaterThanOrEqualTo(Boolean value) {
		    addCriterion("is_exchange >=", value, "isExchange");
		    return this;
	    }

	    public Criteria andIsExchangeLessThan(Boolean value) {
		    addCriterion("is_exchange <", value, "isExchange");
		    return this;
	    }

	    public Criteria andIsExchangeLessThanOrEqualTo(Boolean value) {
		    addCriterion("is_exchange <=", value, "isExchange");
		    return this;
	    }

	    public Criteria andIsExchangeIn(List values) {
		    addCriterion("is_exchange in", values, "isExchange");
		    return this;
	    }

	    public Criteria andIsExchangeNotIn(List values) {
		    addCriterion("is_exchange not in", values, "isExchange");
		    return this;
	    }

	    public Criteria andIsExchangeBetween(Boolean value1, Boolean value2) {
		    addCriterion("is_exchange between", value1, value2, "isExchange");
		    return this;
	    }

	    public Criteria andIsExchangeNotBetween(Boolean value1, Boolean value2) {
		    addCriterion("is_exchange not between", value1, value2,
		            "isExchange");
		    return this;
	    }
    }
}
package com.ppsea.ds.data.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerFarmExample {

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table player_farm
	 * @ibatorgenerated  Fri Mar 01 14:19:48 CST 2013
	 */
	protected String orderByClause;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table player_farm
	 * @ibatorgenerated  Fri Mar 01 14:19:48 CST 2013
	 */
	protected List oredCriteria;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_farm
	 * @ibatorgenerated  Fri Mar 01 14:19:48 CST 2013
	 */
	public PlayerFarmExample() {
		oredCriteria = new ArrayList();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_farm
	 * @ibatorgenerated  Fri Mar 01 14:19:48 CST 2013
	 */
	protected PlayerFarmExample(PlayerFarmExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_farm
	 * @ibatorgenerated  Fri Mar 01 14:19:48 CST 2013
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_farm
	 * @ibatorgenerated  Fri Mar 01 14:19:48 CST 2013
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_farm
	 * @ibatorgenerated  Fri Mar 01 14:19:48 CST 2013
	 */
	public List getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_farm
	 * @ibatorgenerated  Fri Mar 01 14:19:48 CST 2013
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_farm
	 * @ibatorgenerated  Fri Mar 01 14:19:48 CST 2013
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_farm
	 * @ibatorgenerated  Fri Mar 01 14:19:48 CST 2013
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_farm
	 * @ibatorgenerated  Fri Mar 01 14:19:48 CST 2013
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds to the database table player_farm
	 * @ibatorgenerated  Fri Mar 01 14:19:48 CST 2013
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

		public Criteria andRewardStatusIsNull() {
			addCriterion("reward_status is null");
			return this;
		}

		public Criteria andRewardStatusIsNotNull() {
			addCriterion("reward_status is not null");
			return this;
		}

		public Criteria andRewardStatusEqualTo(Integer value) {
			addCriterion("reward_status =", value, "rewardStatus");
			return this;
		}

		public Criteria andRewardStatusNotEqualTo(Integer value) {
			addCriterion("reward_status <>", value, "rewardStatus");
			return this;
		}

		public Criteria andRewardStatusGreaterThan(Integer value) {
			addCriterion("reward_status >", value, "rewardStatus");
			return this;
		}

		public Criteria andRewardStatusGreaterThanOrEqualTo(Integer value) {
			addCriterion("reward_status >=", value, "rewardStatus");
			return this;
		}

		public Criteria andRewardStatusLessThan(Integer value) {
			addCriterion("reward_status <", value, "rewardStatus");
			return this;
		}

		public Criteria andRewardStatusLessThanOrEqualTo(Integer value) {
			addCriterion("reward_status <=", value, "rewardStatus");
			return this;
		}

		public Criteria andRewardStatusIn(List values) {
			addCriterion("reward_status in", values, "rewardStatus");
			return this;
		}

		public Criteria andRewardStatusNotIn(List values) {
			addCriterion("reward_status not in", values, "rewardStatus");
			return this;
		}

		public Criteria andRewardStatusBetween(Integer value1, Integer value2) {
			addCriterion("reward_status between", value1, value2,
					"rewardStatus");
			return this;
		}

		public Criteria andRewardStatusNotBetween(Integer value1, Integer value2) {
			addCriterion("reward_status not between", value1, value2,
					"rewardStatus");
			return this;
		}

		public Criteria andFarmStatusIsNull() {
			addCriterion("farm_status is null");
			return this;
		}

		public Criteria andFarmStatusIsNotNull() {
			addCriterion("farm_status is not null");
			return this;
		}

		public Criteria andFarmStatusEqualTo(Integer value) {
			addCriterion("farm_status =", value, "farmStatus");
			return this;
		}

		public Criteria andFarmStatusNotEqualTo(Integer value) {
			addCriterion("farm_status <>", value, "farmStatus");
			return this;
		}

		public Criteria andFarmStatusGreaterThan(Integer value) {
			addCriterion("farm_status >", value, "farmStatus");
			return this;
		}

		public Criteria andFarmStatusGreaterThanOrEqualTo(Integer value) {
			addCriterion("farm_status >=", value, "farmStatus");
			return this;
		}

		public Criteria andFarmStatusLessThan(Integer value) {
			addCriterion("farm_status <", value, "farmStatus");
			return this;
		}

		public Criteria andFarmStatusLessThanOrEqualTo(Integer value) {
			addCriterion("farm_status <=", value, "farmStatus");
			return this;
		}

		public Criteria andFarmStatusIn(List values) {
			addCriterion("farm_status in", values, "farmStatus");
			return this;
		}

		public Criteria andFarmStatusNotIn(List values) {
			addCriterion("farm_status not in", values, "farmStatus");
			return this;
		}

		public Criteria andFarmStatusBetween(Integer value1, Integer value2) {
			addCriterion("farm_status between", value1, value2, "farmStatus");
			return this;
		}

		public Criteria andFarmStatusNotBetween(Integer value1, Integer value2) {
			addCriterion("farm_status not between", value1, value2,
					"farmStatus");
			return this;
		}

		public Criteria andLastUpgradeTimeIsNull() {
			addCriterion("last_upgrade_time is null");
			return this;
		}

		public Criteria andLastUpgradeTimeIsNotNull() {
			addCriterion("last_upgrade_time is not null");
			return this;
		}

		public Criteria andLastUpgradeTimeEqualTo(Date value) {
			addCriterion("last_upgrade_time =", value, "lastUpgradeTime");
			return this;
		}

		public Criteria andLastUpgradeTimeNotEqualTo(Date value) {
			addCriterion("last_upgrade_time <>", value, "lastUpgradeTime");
			return this;
		}

		public Criteria andLastUpgradeTimeGreaterThan(Date value) {
			addCriterion("last_upgrade_time >", value, "lastUpgradeTime");
			return this;
		}

		public Criteria andLastUpgradeTimeGreaterThanOrEqualTo(Date value) {
			addCriterion("last_upgrade_time >=", value, "lastUpgradeTime");
			return this;
		}

		public Criteria andLastUpgradeTimeLessThan(Date value) {
			addCriterion("last_upgrade_time <", value, "lastUpgradeTime");
			return this;
		}

		public Criteria andLastUpgradeTimeLessThanOrEqualTo(Date value) {
			addCriterion("last_upgrade_time <=", value, "lastUpgradeTime");
			return this;
		}

		public Criteria andLastUpgradeTimeIn(List values) {
			addCriterion("last_upgrade_time in", values, "lastUpgradeTime");
			return this;
		}

		public Criteria andLastUpgradeTimeNotIn(List values) {
			addCriterion("last_upgrade_time not in", values, "lastUpgradeTime");
			return this;
		}

		public Criteria andLastUpgradeTimeBetween(Date value1, Date value2) {
			addCriterion("last_upgrade_time between", value1, value2,
					"lastUpgradeTime");
			return this;
		}

		public Criteria andLastUpgradeTimeNotBetween(Date value1, Date value2) {
			addCriterion("last_upgrade_time not between", value1, value2,
					"lastUpgradeTime");
			return this;
		}

		public Criteria andFruitTypeIsNull() {
			addCriterion("fruit_type is null");
			return this;
		}

		public Criteria andFruitTypeIsNotNull() {
			addCriterion("fruit_type is not null");
			return this;
		}

		public Criteria andFruitTypeEqualTo(Integer value) {
			addCriterion("fruit_type =", value, "fruitType");
			return this;
		}

		public Criteria andFruitTypeNotEqualTo(Integer value) {
			addCriterion("fruit_type <>", value, "fruitType");
			return this;
		}

		public Criteria andFruitTypeGreaterThan(Integer value) {
			addCriterion("fruit_type >", value, "fruitType");
			return this;
		}

		public Criteria andFruitTypeGreaterThanOrEqualTo(Integer value) {
			addCriterion("fruit_type >=", value, "fruitType");
			return this;
		}

		public Criteria andFruitTypeLessThan(Integer value) {
			addCriterion("fruit_type <", value, "fruitType");
			return this;
		}

		public Criteria andFruitTypeLessThanOrEqualTo(Integer value) {
			addCriterion("fruit_type <=", value, "fruitType");
			return this;
		}

		public Criteria andFruitTypeIn(List values) {
			addCriterion("fruit_type in", values, "fruitType");
			return this;
		}

		public Criteria andFruitTypeNotIn(List values) {
			addCriterion("fruit_type not in", values, "fruitType");
			return this;
		}

		public Criteria andFruitTypeBetween(Integer value1, Integer value2) {
			addCriterion("fruit_type between", value1, value2, "fruitType");
			return this;
		}

		public Criteria andFruitTypeNotBetween(Integer value1, Integer value2) {
			addCriterion("fruit_type not between", value1, value2, "fruitType");
			return this;
		}

		public Criteria andFruitNumIsNull() {
			addCriterion("fruit_num is null");
			return this;
		}

		public Criteria andFruitNumIsNotNull() {
			addCriterion("fruit_num is not null");
			return this;
		}

		public Criteria andFruitNumEqualTo(Integer value) {
			addCriterion("fruit_num =", value, "fruitNum");
			return this;
		}

		public Criteria andFruitNumNotEqualTo(Integer value) {
			addCriterion("fruit_num <>", value, "fruitNum");
			return this;
		}

		public Criteria andFruitNumGreaterThan(Integer value) {
			addCriterion("fruit_num >", value, "fruitNum");
			return this;
		}

		public Criteria andFruitNumGreaterThanOrEqualTo(Integer value) {
			addCriterion("fruit_num >=", value, "fruitNum");
			return this;
		}

		public Criteria andFruitNumLessThan(Integer value) {
			addCriterion("fruit_num <", value, "fruitNum");
			return this;
		}

		public Criteria andFruitNumLessThanOrEqualTo(Integer value) {
			addCriterion("fruit_num <=", value, "fruitNum");
			return this;
		}

		public Criteria andFruitNumIn(List values) {
			addCriterion("fruit_num in", values, "fruitNum");
			return this;
		}

		public Criteria andFruitNumNotIn(List values) {
			addCriterion("fruit_num not in", values, "fruitNum");
			return this;
		}

		public Criteria andFruitNumBetween(Integer value1, Integer value2) {
			addCriterion("fruit_num between", value1, value2, "fruitNum");
			return this;
		}

		public Criteria andFruitNumNotBetween(Integer value1, Integer value2) {
			addCriterion("fruit_num not between", value1, value2, "fruitNum");
			return this;
		}

		public Criteria andEventIsNull() {
			addCriterion("event is null");
			return this;
		}

		public Criteria andEventIsNotNull() {
			addCriterion("event is not null");
			return this;
		}

		public Criteria andEventEqualTo(Integer value) {
			addCriterion("event =", value, "event");
			return this;
		}

		public Criteria andEventNotEqualTo(Integer value) {
			addCriterion("event <>", value, "event");
			return this;
		}

		public Criteria andEventGreaterThan(Integer value) {
			addCriterion("event >", value, "event");
			return this;
		}

		public Criteria andEventGreaterThanOrEqualTo(Integer value) {
			addCriterion("event >=", value, "event");
			return this;
		}

		public Criteria andEventLessThan(Integer value) {
			addCriterion("event <", value, "event");
			return this;
		}

		public Criteria andEventLessThanOrEqualTo(Integer value) {
			addCriterion("event <=", value, "event");
			return this;
		}

		public Criteria andEventIn(List values) {
			addCriterion("event in", values, "event");
			return this;
		}

		public Criteria andEventNotIn(List values) {
			addCriterion("event not in", values, "event");
			return this;
		}

		public Criteria andEventBetween(Integer value1, Integer value2) {
			addCriterion("event between", value1, value2, "event");
			return this;
		}

		public Criteria andEventNotBetween(Integer value1, Integer value2) {
			addCriterion("event not between", value1, value2, "event");
			return this;
		}
	}
}
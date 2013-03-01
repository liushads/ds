package com.ppsea.ds.data.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

public class PlayerItemExample {

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table player_item
	 * @ibatorgenerated  Thu Jan 31 23:07:14 CST 2013
	 */
	protected String orderByClause;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table player_item
	 * @ibatorgenerated  Thu Jan 31 23:07:14 CST 2013
	 */
	protected List oredCriteria;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_item
	 * @ibatorgenerated  Thu Jan 31 23:07:14 CST 2013
	 */
	public PlayerItemExample() {
		oredCriteria = new ArrayList();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_item
	 * @ibatorgenerated  Thu Jan 31 23:07:14 CST 2013
	 */
	protected PlayerItemExample(PlayerItemExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_item
	 * @ibatorgenerated  Thu Jan 31 23:07:14 CST 2013
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_item
	 * @ibatorgenerated  Thu Jan 31 23:07:14 CST 2013
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_item
	 * @ibatorgenerated  Thu Jan 31 23:07:14 CST 2013
	 */
	public List getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_item
	 * @ibatorgenerated  Thu Jan 31 23:07:14 CST 2013
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_item
	 * @ibatorgenerated  Thu Jan 31 23:07:14 CST 2013
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_item
	 * @ibatorgenerated  Thu Jan 31 23:07:14 CST 2013
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table player_item
	 * @ibatorgenerated  Thu Jan 31 23:07:14 CST 2013
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds to the database table player_item
	 * @ibatorgenerated  Thu Jan 31 23:07:14 CST 2013
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

		public Criteria andCurrHpIsNull() {
			addCriterion("curr_hp is null");
			return this;
		}

		public Criteria andCurrHpIsNotNull() {
			addCriterion("curr_hp is not null");
			return this;
		}

		public Criteria andCurrHpEqualTo(Integer value) {
			addCriterion("curr_hp =", value, "currHp");
			return this;
		}

		public Criteria andCurrHpNotEqualTo(Integer value) {
			addCriterion("curr_hp <>", value, "currHp");
			return this;
		}

		public Criteria andCurrHpGreaterThan(Integer value) {
			addCriterion("curr_hp >", value, "currHp");
			return this;
		}

		public Criteria andCurrHpGreaterThanOrEqualTo(Integer value) {
			addCriterion("curr_hp >=", value, "currHp");
			return this;
		}

		public Criteria andCurrHpLessThan(Integer value) {
			addCriterion("curr_hp <", value, "currHp");
			return this;
		}

		public Criteria andCurrHpLessThanOrEqualTo(Integer value) {
			addCriterion("curr_hp <=", value, "currHp");
			return this;
		}

		public Criteria andCurrHpIn(List values) {
			addCriterion("curr_hp in", values, "currHp");
			return this;
		}

		public Criteria andCurrHpNotIn(List values) {
			addCriterion("curr_hp not in", values, "currHp");
			return this;
		}

		public Criteria andCurrHpBetween(Integer value1, Integer value2) {
			addCriterion("curr_hp between", value1, value2, "currHp");
			return this;
		}

		public Criteria andCurrHpNotBetween(Integer value1, Integer value2) {
			addCriterion("curr_hp not between", value1, value2, "currHp");
			return this;
		}

		public Criteria andIsUseIsNull() {
			addCriterion("is_use is null");
			return this;
		}

		public Criteria andIsUseIsNotNull() {
			addCriterion("is_use is not null");
			return this;
		}

		public Criteria andIsUseEqualTo(Integer value) {
			addCriterion("is_use =", value, "isUse");
			return this;
		}

		public Criteria andIsUseNotEqualTo(Integer value) {
			addCriterion("is_use <>", value, "isUse");
			return this;
		}

		public Criteria andIsUseGreaterThan(Integer value) {
			addCriterion("is_use >", value, "isUse");
			return this;
		}

		public Criteria andIsUseGreaterThanOrEqualTo(Integer value) {
			addCriterion("is_use >=", value, "isUse");
			return this;
		}

		public Criteria andIsUseLessThan(Integer value) {
			addCriterion("is_use <", value, "isUse");
			return this;
		}

		public Criteria andIsUseLessThanOrEqualTo(Integer value) {
			addCriterion("is_use <=", value, "isUse");
			return this;
		}

		public Criteria andIsUseIn(List values) {
			addCriterion("is_use in", values, "isUse");
			return this;
		}

		public Criteria andIsUseNotIn(List values) {
			addCriterion("is_use not in", values, "isUse");
			return this;
		}

		public Criteria andIsUseBetween(Integer value1, Integer value2) {
			addCriterion("is_use between", value1, value2, "isUse");
			return this;
		}

		public Criteria andIsUseNotBetween(Integer value1, Integer value2) {
			addCriterion("is_use not between", value1, value2, "isUse");
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

		public Criteria andBindIdIsNull() {
			addCriterion("bind_id is null");
			return this;
		}

		public Criteria andBindIdIsNotNull() {
			addCriterion("bind_id is not null");
			return this;
		}

		public Criteria andBindIdEqualTo(Integer value) {
			addCriterion("bind_id =", value, "bindId");
			return this;
		}

		public Criteria andBindIdNotEqualTo(Integer value) {
			addCriterion("bind_id <>", value, "bindId");
			return this;
		}

		public Criteria andBindIdGreaterThan(Integer value) {
			addCriterion("bind_id >", value, "bindId");
			return this;
		}

		public Criteria andBindIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("bind_id >=", value, "bindId");
			return this;
		}

		public Criteria andBindIdLessThan(Integer value) {
			addCriterion("bind_id <", value, "bindId");
			return this;
		}

		public Criteria andBindIdLessThanOrEqualTo(Integer value) {
			addCriterion("bind_id <=", value, "bindId");
			return this;
		}

		public Criteria andBindIdIn(List values) {
			addCriterion("bind_id in", values, "bindId");
			return this;
		}

		public Criteria andBindIdNotIn(List values) {
			addCriterion("bind_id not in", values, "bindId");
			return this;
		}

		public Criteria andBindIdBetween(Integer value1, Integer value2) {
			addCriterion("bind_id between", value1, value2, "bindId");
			return this;
		}

		public Criteria andBindIdNotBetween(Integer value1, Integer value2) {
			addCriterion("bind_id not between", value1, value2, "bindId");
			return this;
		}

		public Criteria andTongIdIsNull() {
			addCriterion("tong_id is null");
			return this;
		}

		public Criteria andTongIdIsNotNull() {
			addCriterion("tong_id is not null");
			return this;
		}

		public Criteria andTongIdEqualTo(Integer value) {
			addCriterion("tong_id =", value, "tongId");
			return this;
		}

		public Criteria andTongIdNotEqualTo(Integer value) {
			addCriterion("tong_id <>", value, "tongId");
			return this;
		}

		public Criteria andTongIdGreaterThan(Integer value) {
			addCriterion("tong_id >", value, "tongId");
			return this;
		}

		public Criteria andTongIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("tong_id >=", value, "tongId");
			return this;
		}

		public Criteria andTongIdLessThan(Integer value) {
			addCriterion("tong_id <", value, "tongId");
			return this;
		}

		public Criteria andTongIdLessThanOrEqualTo(Integer value) {
			addCriterion("tong_id <=", value, "tongId");
			return this;
		}

		public Criteria andTongIdIn(List values) {
			addCriterion("tong_id in", values, "tongId");
			return this;
		}

		public Criteria andTongIdNotIn(List values) {
			addCriterion("tong_id not in", values, "tongId");
			return this;
		}

		public Criteria andTongIdBetween(Integer value1, Integer value2) {
			addCriterion("tong_id between", value1, value2, "tongId");
			return this;
		}

		public Criteria andTongIdNotBetween(Integer value1, Integer value2) {
			addCriterion("tong_id not between", value1, value2, "tongId");
			return this;
		}

		public Criteria andShortcutIdIsNull() {
			addCriterion("shortcut_id is null");
			return this;
		}

		public Criteria andShortcutIdIsNotNull() {
			addCriterion("shortcut_id is not null");
			return this;
		}

		public Criteria andShortcutIdEqualTo(Integer value) {
			addCriterion("shortcut_id =", value, "shortcutId");
			return this;
		}

		public Criteria andShortcutIdNotEqualTo(Integer value) {
			addCriterion("shortcut_id <>", value, "shortcutId");
			return this;
		}

		public Criteria andShortcutIdGreaterThan(Integer value) {
			addCriterion("shortcut_id >", value, "shortcutId");
			return this;
		}

		public Criteria andShortcutIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("shortcut_id >=", value, "shortcutId");
			return this;
		}

		public Criteria andShortcutIdLessThan(Integer value) {
			addCriterion("shortcut_id <", value, "shortcutId");
			return this;
		}

		public Criteria andShortcutIdLessThanOrEqualTo(Integer value) {
			addCriterion("shortcut_id <=", value, "shortcutId");
			return this;
		}

		public Criteria andShortcutIdIn(List values) {
			addCriterion("shortcut_id in", values, "shortcutId");
			return this;
		}

		public Criteria andShortcutIdNotIn(List values) {
			addCriterion("shortcut_id not in", values, "shortcutId");
			return this;
		}

		public Criteria andShortcutIdBetween(Integer value1, Integer value2) {
			addCriterion("shortcut_id between", value1, value2, "shortcutId");
			return this;
		}

		public Criteria andShortcutIdNotBetween(Integer value1, Integer value2) {
			addCriterion("shortcut_id not between", value1, value2,
					"shortcutId");
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

		public Criteria andInExchangeIsNull() {
			addCriterion("in_exchange is null");
			return this;
		}

		public Criteria andInExchangeIsNotNull() {
			addCriterion("in_exchange is not null");
			return this;
		}

		public Criteria andInExchangeEqualTo(Boolean value) {
			addCriterion("in_exchange =", value, "inExchange");
			return this;
		}

		public Criteria andInExchangeNotEqualTo(Boolean value) {
			addCriterion("in_exchange <>", value, "inExchange");
			return this;
		}

		public Criteria andInExchangeGreaterThan(Boolean value) {
			addCriterion("in_exchange >", value, "inExchange");
			return this;
		}

		public Criteria andInExchangeGreaterThanOrEqualTo(Boolean value) {
			addCriterion("in_exchange >=", value, "inExchange");
			return this;
		}

		public Criteria andInExchangeLessThan(Boolean value) {
			addCriterion("in_exchange <", value, "inExchange");
			return this;
		}

		public Criteria andInExchangeLessThanOrEqualTo(Boolean value) {
			addCriterion("in_exchange <=", value, "inExchange");
			return this;
		}

		public Criteria andInExchangeIn(List values) {
			addCriterion("in_exchange in", values, "inExchange");
			return this;
		}

		public Criteria andInExchangeNotIn(List values) {
			addCriterion("in_exchange not in", values, "inExchange");
			return this;
		}

		public Criteria andInExchangeBetween(Boolean value1, Boolean value2) {
			addCriterion("in_exchange between", value1, value2, "inExchange");
			return this;
		}

		public Criteria andInExchangeNotBetween(Boolean value1, Boolean value2) {
			addCriterion("in_exchange not between", value1, value2,
					"inExchange");
			return this;
		}

		public Criteria andExchangePriceIsNull() {
			addCriterion("exchange_price is null");
			return this;
		}

		public Criteria andExchangePriceIsNotNull() {
			addCriterion("exchange_price is not null");
			return this;
		}

		public Criteria andExchangePriceEqualTo(Integer value) {
			addCriterion("exchange_price =", value, "exchangePrice");
			return this;
		}

		public Criteria andExchangePriceNotEqualTo(Integer value) {
			addCriterion("exchange_price <>", value, "exchangePrice");
			return this;
		}

		public Criteria andExchangePriceGreaterThan(Integer value) {
			addCriterion("exchange_price >", value, "exchangePrice");
			return this;
		}

		public Criteria andExchangePriceGreaterThanOrEqualTo(Integer value) {
			addCriterion("exchange_price >=", value, "exchangePrice");
			return this;
		}

		public Criteria andExchangePriceLessThan(Integer value) {
			addCriterion("exchange_price <", value, "exchangePrice");
			return this;
		}

		public Criteria andExchangePriceLessThanOrEqualTo(Integer value) {
			addCriterion("exchange_price <=", value, "exchangePrice");
			return this;
		}

		public Criteria andExchangePriceIn(List values) {
			addCriterion("exchange_price in", values, "exchangePrice");
			return this;
		}

		public Criteria andExchangePriceNotIn(List values) {
			addCriterion("exchange_price not in", values, "exchangePrice");
			return this;
		}

		public Criteria andExchangePriceBetween(Integer value1, Integer value2) {
			addCriterion("exchange_price between", value1, value2,
					"exchangePrice");
			return this;
		}

		public Criteria andExchangePriceNotBetween(Integer value1,
				Integer value2) {
			addCriterion("exchange_price not between", value1, value2,
					"exchangePrice");
			return this;
		}

		public Criteria andExchangeAmountIsNull() {
			addCriterion("exchange_amount is null");
			return this;
		}

		public Criteria andExchangeAmountIsNotNull() {
			addCriterion("exchange_amount is not null");
			return this;
		}

		public Criteria andExchangeAmountEqualTo(Integer value) {
			addCriterion("exchange_amount =", value, "exchangeAmount");
			return this;
		}

		public Criteria andExchangeAmountNotEqualTo(Integer value) {
			addCriterion("exchange_amount <>", value, "exchangeAmount");
			return this;
		}

		public Criteria andExchangeAmountGreaterThan(Integer value) {
			addCriterion("exchange_amount >", value, "exchangeAmount");
			return this;
		}

		public Criteria andExchangeAmountGreaterThanOrEqualTo(Integer value) {
			addCriterion("exchange_amount >=", value, "exchangeAmount");
			return this;
		}

		public Criteria andExchangeAmountLessThan(Integer value) {
			addCriterion("exchange_amount <", value, "exchangeAmount");
			return this;
		}

		public Criteria andExchangeAmountLessThanOrEqualTo(Integer value) {
			addCriterion("exchange_amount <=", value, "exchangeAmount");
			return this;
		}

		public Criteria andExchangeAmountIn(List values) {
			addCriterion("exchange_amount in", values, "exchangeAmount");
			return this;
		}

		public Criteria andExchangeAmountNotIn(List values) {
			addCriterion("exchange_amount not in", values, "exchangeAmount");
			return this;
		}

		public Criteria andExchangeAmountBetween(Integer value1, Integer value2) {
			addCriterion("exchange_amount between", value1, value2,
					"exchangeAmount");
			return this;
		}

		public Criteria andExchangeAmountNotBetween(Integer value1,
				Integer value2) {
			addCriterion("exchange_amount not between", value1, value2,
					"exchangeAmount");
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

		public Criteria andUpdatedTimeIsNull() {
			addCriterion("updated_time is null");
			return this;
		}

		public Criteria andUpdatedTimeIsNotNull() {
			addCriterion("updated_time is not null");
			return this;
		}

		public Criteria andUpdatedTimeEqualTo(Date value) {
			addCriterion("updated_time =", value, "updatedTime");
			return this;
		}

		public Criteria andUpdatedTimeNotEqualTo(Date value) {
			addCriterion("updated_time <>", value, "updatedTime");
			return this;
		}

		public Criteria andUpdatedTimeGreaterThan(Date value) {
			addCriterion("updated_time >", value, "updatedTime");
			return this;
		}

		public Criteria andUpdatedTimeGreaterThanOrEqualTo(Date value) {
			addCriterion("updated_time >=", value, "updatedTime");
			return this;
		}

		public Criteria andUpdatedTimeLessThan(Date value) {
			addCriterion("updated_time <", value, "updatedTime");
			return this;
		}

		public Criteria andUpdatedTimeLessThanOrEqualTo(Date value) {
			addCriterion("updated_time <=", value, "updatedTime");
			return this;
		}

		public Criteria andUpdatedTimeIn(List values) {
			addCriterion("updated_time in", values, "updatedTime");
			return this;
		}

		public Criteria andUpdatedTimeNotIn(List values) {
			addCriterion("updated_time not in", values, "updatedTime");
			return this;
		}

		public Criteria andUpdatedTimeBetween(Date value1, Date value2) {
			addCriterion("updated_time between", value1, value2, "updatedTime");
			return this;
		}

		public Criteria andUpdatedTimeNotBetween(Date value1, Date value2) {
			addCriterion("updated_time not between", value1, value2,
					"updatedTime");
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

		public Criteria andBuffrandidsIsNull() {
			addCriterion("buffRandIds is null");
			return this;
		}

		public Criteria andBuffrandidsIsNotNull() {
			addCriterion("buffRandIds is not null");
			return this;
		}

		public Criteria andBuffrandidsEqualTo(String value) {
			addCriterion("buffRandIds =", value, "buffrandids");
			return this;
		}

		public Criteria andBuffrandidsNotEqualTo(String value) {
			addCriterion("buffRandIds <>", value, "buffrandids");
			return this;
		}

		public Criteria andBuffrandidsGreaterThan(String value) {
			addCriterion("buffRandIds >", value, "buffrandids");
			return this;
		}

		public Criteria andBuffrandidsGreaterThanOrEqualTo(String value) {
			addCriterion("buffRandIds >=", value, "buffrandids");
			return this;
		}

		public Criteria andBuffrandidsLessThan(String value) {
			addCriterion("buffRandIds <", value, "buffrandids");
			return this;
		}

		public Criteria andBuffrandidsLessThanOrEqualTo(String value) {
			addCriterion("buffRandIds <=", value, "buffrandids");
			return this;
		}

		public Criteria andBuffrandidsLike(String value) {
			addCriterion("buffRandIds like", value, "buffrandids");
			return this;
		}

		public Criteria andBuffrandidsNotLike(String value) {
			addCriterion("buffRandIds not like", value, "buffrandids");
			return this;
		}

		public Criteria andBuffrandidsIn(List values) {
			addCriterion("buffRandIds in", values, "buffrandids");
			return this;
		}

		public Criteria andBuffrandidsNotIn(List values) {
			addCriterion("buffRandIds not in", values, "buffrandids");
			return this;
		}

		public Criteria andBuffrandidsBetween(String value1, String value2) {
			addCriterion("buffRandIds between", value1, value2, "buffrandids");
			return this;
		}

		public Criteria andBuffrandidsNotBetween(String value1, String value2) {
			addCriterion("buffRandIds not between", value1, value2,
					"buffrandids");
			return this;
		}
	}
}
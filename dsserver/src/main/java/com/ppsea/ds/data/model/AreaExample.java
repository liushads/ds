package com.ppsea.ds.data.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AreaExample {

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table area
	 * @ibatorgenerated  Thu Aug 12 11:51:13 CST 2010
	 */
	protected String orderByClause;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table area
	 * @ibatorgenerated  Thu Aug 12 11:51:13 CST 2010
	 */
	protected List oredCriteria;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table area
	 * @ibatorgenerated  Thu Aug 12 11:51:13 CST 2010
	 */
	public AreaExample() {
		oredCriteria = new ArrayList();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table area
	 * @ibatorgenerated  Thu Aug 12 11:51:13 CST 2010
	 */
	protected AreaExample(AreaExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table area
	 * @ibatorgenerated  Thu Aug 12 11:51:13 CST 2010
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table area
	 * @ibatorgenerated  Thu Aug 12 11:51:13 CST 2010
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table area
	 * @ibatorgenerated  Thu Aug 12 11:51:13 CST 2010
	 */
	public List getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table area
	 * @ibatorgenerated  Thu Aug 12 11:51:13 CST 2010
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table area
	 * @ibatorgenerated  Thu Aug 12 11:51:13 CST 2010
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table area
	 * @ibatorgenerated  Thu Aug 12 11:51:13 CST 2010
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table area
	 * @ibatorgenerated  Thu Aug 12 11:51:13 CST 2010
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds to the database table area
	 * @ibatorgenerated  Thu Aug 12 11:51:13 CST 2010
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

		public Criteria andNameIsNull() {
			addCriterion("name is null");
			return this;
		}

		public Criteria andNameIsNotNull() {
			addCriterion("name is not null");
			return this;
		}

		public Criteria andNameEqualTo(String value) {
			addCriterion("name =", value, "name");
			return this;
		}

		public Criteria andNameNotEqualTo(String value) {
			addCriterion("name <>", value, "name");
			return this;
		}

		public Criteria andNameGreaterThan(String value) {
			addCriterion("name >", value, "name");
			return this;
		}

		public Criteria andNameGreaterThanOrEqualTo(String value) {
			addCriterion("name >=", value, "name");
			return this;
		}

		public Criteria andNameLessThan(String value) {
			addCriterion("name <", value, "name");
			return this;
		}

		public Criteria andNameLessThanOrEqualTo(String value) {
			addCriterion("name <=", value, "name");
			return this;
		}

		public Criteria andNameLike(String value) {
			addCriterion("name like", value, "name");
			return this;
		}

		public Criteria andNameNotLike(String value) {
			addCriterion("name not like", value, "name");
			return this;
		}

		public Criteria andNameIn(List values) {
			addCriterion("name in", values, "name");
			return this;
		}

		public Criteria andNameNotIn(List values) {
			addCriterion("name not in", values, "name");
			return this;
		}

		public Criteria andNameBetween(String value1, String value2) {
			addCriterion("name between", value1, value2, "name");
			return this;
		}

		public Criteria andNameNotBetween(String value1, String value2) {
			addCriterion("name not between", value1, value2, "name");
			return this;
		}

		public Criteria andSectIdIsNull() {
			addCriterion("sect_id is null");
			return this;
		}

		public Criteria andSectIdIsNotNull() {
			addCriterion("sect_id is not null");
			return this;
		}

		public Criteria andSectIdEqualTo(Integer value) {
			addCriterion("sect_id =", value, "sectId");
			return this;
		}

		public Criteria andSectIdNotEqualTo(Integer value) {
			addCriterion("sect_id <>", value, "sectId");
			return this;
		}

		public Criteria andSectIdGreaterThan(Integer value) {
			addCriterion("sect_id >", value, "sectId");
			return this;
		}

		public Criteria andSectIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("sect_id >=", value, "sectId");
			return this;
		}

		public Criteria andSectIdLessThan(Integer value) {
			addCriterion("sect_id <", value, "sectId");
			return this;
		}

		public Criteria andSectIdLessThanOrEqualTo(Integer value) {
			addCriterion("sect_id <=", value, "sectId");
			return this;
		}

		public Criteria andSectIdIn(List values) {
			addCriterion("sect_id in", values, "sectId");
			return this;
		}

		public Criteria andSectIdNotIn(List values) {
			addCriterion("sect_id not in", values, "sectId");
			return this;
		}

		public Criteria andSectIdBetween(Integer value1, Integer value2) {
			addCriterion("sect_id between", value1, value2, "sectId");
			return this;
		}

		public Criteria andSectIdNotBetween(Integer value1, Integer value2) {
			addCriterion("sect_id not between", value1, value2, "sectId");
			return this;
		}

		public Criteria andPlayerLevelIsNull() {
			addCriterion("player_level is null");
			return this;
		}

		public Criteria andPlayerLevelIsNotNull() {
			addCriterion("player_level is not null");
			return this;
		}

		public Criteria andPlayerLevelEqualTo(Integer value) {
			addCriterion("player_level =", value, "playerLevel");
			return this;
		}

		public Criteria andPlayerLevelNotEqualTo(Integer value) {
			addCriterion("player_level <>", value, "playerLevel");
			return this;
		}

		public Criteria andPlayerLevelGreaterThan(Integer value) {
			addCriterion("player_level >", value, "playerLevel");
			return this;
		}

		public Criteria andPlayerLevelGreaterThanOrEqualTo(Integer value) {
			addCriterion("player_level >=", value, "playerLevel");
			return this;
		}

		public Criteria andPlayerLevelLessThan(Integer value) {
			addCriterion("player_level <", value, "playerLevel");
			return this;
		}

		public Criteria andPlayerLevelLessThanOrEqualTo(Integer value) {
			addCriterion("player_level <=", value, "playerLevel");
			return this;
		}

		public Criteria andPlayerLevelIn(List values) {
			addCriterion("player_level in", values, "playerLevel");
			return this;
		}

		public Criteria andPlayerLevelNotIn(List values) {
			addCriterion("player_level not in", values, "playerLevel");
			return this;
		}

		public Criteria andPlayerLevelBetween(Integer value1, Integer value2) {
			addCriterion("player_level between", value1, value2, "playerLevel");
			return this;
		}

		public Criteria andPlayerLevelNotBetween(Integer value1, Integer value2) {
			addCriterion("player_level not between", value1, value2,
					"playerLevel");
			return this;
		}
	}
}
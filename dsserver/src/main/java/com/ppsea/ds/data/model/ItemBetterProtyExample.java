package com.ppsea.ds.data.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemBetterProtyExample {

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table item_better_proty
	 * @ibatorgenerated  Thu Jul 08 11:27:42 CST 2010
	 */
	protected String orderByClause;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table item_better_proty
	 * @ibatorgenerated  Thu Jul 08 11:27:42 CST 2010
	 */
	protected List oredCriteria;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table item_better_proty
	 * @ibatorgenerated  Thu Jul 08 11:27:42 CST 2010
	 */
	public ItemBetterProtyExample() {
		oredCriteria = new ArrayList();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table item_better_proty
	 * @ibatorgenerated  Thu Jul 08 11:27:42 CST 2010
	 */
	protected ItemBetterProtyExample(ItemBetterProtyExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table item_better_proty
	 * @ibatorgenerated  Thu Jul 08 11:27:42 CST 2010
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table item_better_proty
	 * @ibatorgenerated  Thu Jul 08 11:27:42 CST 2010
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table item_better_proty
	 * @ibatorgenerated  Thu Jul 08 11:27:42 CST 2010
	 */
	public List getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table item_better_proty
	 * @ibatorgenerated  Thu Jul 08 11:27:42 CST 2010
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table item_better_proty
	 * @ibatorgenerated  Thu Jul 08 11:27:42 CST 2010
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table item_better_proty
	 * @ibatorgenerated  Thu Jul 08 11:27:42 CST 2010
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table item_better_proty
	 * @ibatorgenerated  Thu Jul 08 11:27:42 CST 2010
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds to the database table item_better_proty
	 * @ibatorgenerated  Thu Jul 08 11:27:42 CST 2010
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

		public Criteria andItemLevelIsNull() {
			addCriterion("item_level is null");
			return this;
		}

		public Criteria andItemLevelIsNotNull() {
			addCriterion("item_level is not null");
			return this;
		}

		public Criteria andItemLevelEqualTo(Integer value) {
			addCriterion("item_level =", value, "itemLevel");
			return this;
		}

		public Criteria andItemLevelNotEqualTo(Integer value) {
			addCriterion("item_level <>", value, "itemLevel");
			return this;
		}

		public Criteria andItemLevelGreaterThan(Integer value) {
			addCriterion("item_level >", value, "itemLevel");
			return this;
		}

		public Criteria andItemLevelGreaterThanOrEqualTo(Integer value) {
			addCriterion("item_level >=", value, "itemLevel");
			return this;
		}

		public Criteria andItemLevelLessThan(Integer value) {
			addCriterion("item_level <", value, "itemLevel");
			return this;
		}

		public Criteria andItemLevelLessThanOrEqualTo(Integer value) {
			addCriterion("item_level <=", value, "itemLevel");
			return this;
		}

		public Criteria andItemLevelIn(List values) {
			addCriterion("item_level in", values, "itemLevel");
			return this;
		}

		public Criteria andItemLevelNotIn(List values) {
			addCriterion("item_level not in", values, "itemLevel");
			return this;
		}

		public Criteria andItemLevelBetween(Integer value1, Integer value2) {
			addCriterion("item_level between", value1, value2, "itemLevel");
			return this;
		}

		public Criteria andItemLevelNotBetween(Integer value1, Integer value2) {
			addCriterion("item_level not between", value1, value2, "itemLevel");
			return this;
		}

		public Criteria andUseTypeIsNull() {
			addCriterion("use_type is null");
			return this;
		}

		public Criteria andUseTypeIsNotNull() {
			addCriterion("use_type is not null");
			return this;
		}

		public Criteria andUseTypeEqualTo(Integer value) {
			addCriterion("use_type =", value, "useType");
			return this;
		}

		public Criteria andUseTypeNotEqualTo(Integer value) {
			addCriterion("use_type <>", value, "useType");
			return this;
		}

		public Criteria andUseTypeGreaterThan(Integer value) {
			addCriterion("use_type >", value, "useType");
			return this;
		}

		public Criteria andUseTypeGreaterThanOrEqualTo(Integer value) {
			addCriterion("use_type >=", value, "useType");
			return this;
		}

		public Criteria andUseTypeLessThan(Integer value) {
			addCriterion("use_type <", value, "useType");
			return this;
		}

		public Criteria andUseTypeLessThanOrEqualTo(Integer value) {
			addCriterion("use_type <=", value, "useType");
			return this;
		}

		public Criteria andUseTypeIn(List values) {
			addCriterion("use_type in", values, "useType");
			return this;
		}

		public Criteria andUseTypeNotIn(List values) {
			addCriterion("use_type not in", values, "useType");
			return this;
		}

		public Criteria andUseTypeBetween(Integer value1, Integer value2) {
			addCriterion("use_type between", value1, value2, "useType");
			return this;
		}

		public Criteria andUseTypeNotBetween(Integer value1, Integer value2) {
			addCriterion("use_type not between", value1, value2, "useType");
			return this;
		}

		public Criteria andProabilityArrayIsNull() {
			addCriterion("proability_array is null");
			return this;
		}

		public Criteria andProabilityArrayIsNotNull() {
			addCriterion("proability_array is not null");
			return this;
		}

		public Criteria andProabilityArrayEqualTo(String value) {
			addCriterion("proability_array =", value, "proabilityArray");
			return this;
		}

		public Criteria andProabilityArrayNotEqualTo(String value) {
			addCriterion("proability_array <>", value, "proabilityArray");
			return this;
		}

		public Criteria andProabilityArrayGreaterThan(String value) {
			addCriterion("proability_array >", value, "proabilityArray");
			return this;
		}

		public Criteria andProabilityArrayGreaterThanOrEqualTo(String value) {
			addCriterion("proability_array >=", value, "proabilityArray");
			return this;
		}

		public Criteria andProabilityArrayLessThan(String value) {
			addCriterion("proability_array <", value, "proabilityArray");
			return this;
		}

		public Criteria andProabilityArrayLessThanOrEqualTo(String value) {
			addCriterion("proability_array <=", value, "proabilityArray");
			return this;
		}

		public Criteria andProabilityArrayLike(String value) {
			addCriterion("proability_array like", value, "proabilityArray");
			return this;
		}

		public Criteria andProabilityArrayNotLike(String value) {
			addCriterion("proability_array not like", value, "proabilityArray");
			return this;
		}

		public Criteria andProabilityArrayIn(List values) {
			addCriterion("proability_array in", values, "proabilityArray");
			return this;
		}

		public Criteria andProabilityArrayNotIn(List values) {
			addCriterion("proability_array not in", values, "proabilityArray");
			return this;
		}

		public Criteria andProabilityArrayBetween(String value1, String value2) {
			addCriterion("proability_array between", value1, value2,
					"proabilityArray");
			return this;
		}

		public Criteria andProabilityArrayNotBetween(String value1,
				String value2) {
			addCriterion("proability_array not between", value1, value2,
					"proabilityArray");
			return this;
		}

		public Criteria andTypeIsNull() {
			addCriterion("type is null");
			return this;
		}

		public Criteria andTypeIsNotNull() {
			addCriterion("type is not null");
			return this;
		}

		public Criteria andTypeEqualTo(Integer value) {
			addCriterion("type =", value, "type");
			return this;
		}

		public Criteria andTypeNotEqualTo(Integer value) {
			addCriterion("type <>", value, "type");
			return this;
		}

		public Criteria andTypeGreaterThan(Integer value) {
			addCriterion("type >", value, "type");
			return this;
		}

		public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
			addCriterion("type >=", value, "type");
			return this;
		}

		public Criteria andTypeLessThan(Integer value) {
			addCriterion("type <", value, "type");
			return this;
		}

		public Criteria andTypeLessThanOrEqualTo(Integer value) {
			addCriterion("type <=", value, "type");
			return this;
		}

		public Criteria andTypeIn(List values) {
			addCriterion("type in", values, "type");
			return this;
		}

		public Criteria andTypeNotIn(List values) {
			addCriterion("type not in", values, "type");
			return this;
		}

		public Criteria andTypeBetween(Integer value1, Integer value2) {
			addCriterion("type between", value1, value2, "type");
			return this;
		}

		public Criteria andTypeNotBetween(Integer value1, Integer value2) {
			addCriterion("type not between", value1, value2, "type");
			return this;
		}
	}
}
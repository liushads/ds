package com.ppsea.ds.data.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemIdentifyExample {

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table item_identify
	 * @ibatorgenerated  Thu Aug 19 12:17:17 CST 2010
	 */
	protected String orderByClause;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table item_identify
	 * @ibatorgenerated  Thu Aug 19 12:17:17 CST 2010
	 */
	protected List oredCriteria;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table item_identify
	 * @ibatorgenerated  Thu Aug 19 12:17:17 CST 2010
	 */
	public ItemIdentifyExample() {
		oredCriteria = new ArrayList();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table item_identify
	 * @ibatorgenerated  Thu Aug 19 12:17:17 CST 2010
	 */
	protected ItemIdentifyExample(ItemIdentifyExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table item_identify
	 * @ibatorgenerated  Thu Aug 19 12:17:17 CST 2010
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table item_identify
	 * @ibatorgenerated  Thu Aug 19 12:17:17 CST 2010
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table item_identify
	 * @ibatorgenerated  Thu Aug 19 12:17:17 CST 2010
	 */
	public List getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table item_identify
	 * @ibatorgenerated  Thu Aug 19 12:17:17 CST 2010
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table item_identify
	 * @ibatorgenerated  Thu Aug 19 12:17:17 CST 2010
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table item_identify
	 * @ibatorgenerated  Thu Aug 19 12:17:17 CST 2010
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table item_identify
	 * @ibatorgenerated  Thu Aug 19 12:17:17 CST 2010
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds to the database table item_identify
	 * @ibatorgenerated  Thu Aug 19 12:17:17 CST 2010
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

		public Criteria andProbabilityIsNull() {
			addCriterion("probability is null");
			return this;
		}

		public Criteria andProbabilityIsNotNull() {
			addCriterion("probability is not null");
			return this;
		}

		public Criteria andProbabilityEqualTo(Integer value) {
			addCriterion("probability =", value, "probability");
			return this;
		}

		public Criteria andProbabilityNotEqualTo(Integer value) {
			addCriterion("probability <>", value, "probability");
			return this;
		}

		public Criteria andProbabilityGreaterThan(Integer value) {
			addCriterion("probability >", value, "probability");
			return this;
		}

		public Criteria andProbabilityGreaterThanOrEqualTo(Integer value) {
			addCriterion("probability >=", value, "probability");
			return this;
		}

		public Criteria andProbabilityLessThan(Integer value) {
			addCriterion("probability <", value, "probability");
			return this;
		}

		public Criteria andProbabilityLessThanOrEqualTo(Integer value) {
			addCriterion("probability <=", value, "probability");
			return this;
		}

		public Criteria andProbabilityIn(List values) {
			addCriterion("probability in", values, "probability");
			return this;
		}

		public Criteria andProbabilityNotIn(List values) {
			addCriterion("probability not in", values, "probability");
			return this;
		}

		public Criteria andProbabilityBetween(Integer value1, Integer value2) {
			addCriterion("probability between", value1, value2, "probability");
			return this;
		}

		public Criteria andProbabilityNotBetween(Integer value1, Integer value2) {
			addCriterion("probability not between", value1, value2,
					"probability");
			return this;
		}

		public Criteria andPriceIsNull() {
			addCriterion("price is null");
			return this;
		}

		public Criteria andPriceIsNotNull() {
			addCriterion("price is not null");
			return this;
		}

		public Criteria andPriceEqualTo(Integer value) {
			addCriterion("price =", value, "price");
			return this;
		}

		public Criteria andPriceNotEqualTo(Integer value) {
			addCriterion("price <>", value, "price");
			return this;
		}

		public Criteria andPriceGreaterThan(Integer value) {
			addCriterion("price >", value, "price");
			return this;
		}

		public Criteria andPriceGreaterThanOrEqualTo(Integer value) {
			addCriterion("price >=", value, "price");
			return this;
		}

		public Criteria andPriceLessThan(Integer value) {
			addCriterion("price <", value, "price");
			return this;
		}

		public Criteria andPriceLessThanOrEqualTo(Integer value) {
			addCriterion("price <=", value, "price");
			return this;
		}

		public Criteria andPriceIn(List values) {
			addCriterion("price in", values, "price");
			return this;
		}

		public Criteria andPriceNotIn(List values) {
			addCriterion("price not in", values, "price");
			return this;
		}

		public Criteria andPriceBetween(Integer value1, Integer value2) {
			addCriterion("price between", value1, value2, "price");
			return this;
		}

		public Criteria andPriceNotBetween(Integer value1, Integer value2) {
			addCriterion("price not between", value1, value2, "price");
			return this;
		}

		public Criteria andNoticeIsNull() {
			addCriterion("notice is null");
			return this;
		}

		public Criteria andNoticeIsNotNull() {
			addCriterion("notice is not null");
			return this;
		}

		public Criteria andNoticeEqualTo(Short value) {
			addCriterion("notice =", value, "notice");
			return this;
		}

		public Criteria andNoticeNotEqualTo(Short value) {
			addCriterion("notice <>", value, "notice");
			return this;
		}

		public Criteria andNoticeGreaterThan(Short value) {
			addCriterion("notice >", value, "notice");
			return this;
		}

		public Criteria andNoticeGreaterThanOrEqualTo(Short value) {
			addCriterion("notice >=", value, "notice");
			return this;
		}

		public Criteria andNoticeLessThan(Short value) {
			addCriterion("notice <", value, "notice");
			return this;
		}

		public Criteria andNoticeLessThanOrEqualTo(Short value) {
			addCriterion("notice <=", value, "notice");
			return this;
		}

		public Criteria andNoticeIn(List values) {
			addCriterion("notice in", values, "notice");
			return this;
		}

		public Criteria andNoticeNotIn(List values) {
			addCriterion("notice not in", values, "notice");
			return this;
		}

		public Criteria andNoticeBetween(Short value1, Short value2) {
			addCriterion("notice between", value1, value2, "notice");
			return this;
		}

		public Criteria andNoticeNotBetween(Short value1, Short value2) {
			addCriterion("notice not between", value1, value2, "notice");
			return this;
		}
	}
}
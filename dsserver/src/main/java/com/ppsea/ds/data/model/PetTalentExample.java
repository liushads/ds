package com.ppsea.ds.data.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PetTalentExample {

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table pet_talent
	 * @ibatorgenerated  Thu May 20 14:46:15 CST 2010
	 */
	protected String orderByClause;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table pet_talent
	 * @ibatorgenerated  Thu May 20 14:46:15 CST 2010
	 */
	protected List oredCriteria;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table pet_talent
	 * @ibatorgenerated  Thu May 20 14:46:15 CST 2010
	 */
	public PetTalentExample() {
		oredCriteria = new ArrayList();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table pet_talent
	 * @ibatorgenerated  Thu May 20 14:46:15 CST 2010
	 */
	protected PetTalentExample(PetTalentExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table pet_talent
	 * @ibatorgenerated  Thu May 20 14:46:15 CST 2010
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table pet_talent
	 * @ibatorgenerated  Thu May 20 14:46:15 CST 2010
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table pet_talent
	 * @ibatorgenerated  Thu May 20 14:46:15 CST 2010
	 */
	public List getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table pet_talent
	 * @ibatorgenerated  Thu May 20 14:46:15 CST 2010
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table pet_talent
	 * @ibatorgenerated  Thu May 20 14:46:15 CST 2010
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table pet_talent
	 * @ibatorgenerated  Thu May 20 14:46:15 CST 2010
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table pet_talent
	 * @ibatorgenerated  Thu May 20 14:46:15 CST 2010
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds to the database table pet_talent
	 * @ibatorgenerated  Thu May 20 14:46:15 CST 2010
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

		public Criteria andDescriptionIsNull() {
			addCriterion("description is null");
			return this;
		}

		public Criteria andDescriptionIsNotNull() {
			addCriterion("description is not null");
			return this;
		}

		public Criteria andDescriptionEqualTo(String value) {
			addCriterion("description =", value, "description");
			return this;
		}

		public Criteria andDescriptionNotEqualTo(String value) {
			addCriterion("description <>", value, "description");
			return this;
		}

		public Criteria andDescriptionGreaterThan(String value) {
			addCriterion("description >", value, "description");
			return this;
		}

		public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
			addCriterion("description >=", value, "description");
			return this;
		}

		public Criteria andDescriptionLessThan(String value) {
			addCriterion("description <", value, "description");
			return this;
		}

		public Criteria andDescriptionLessThanOrEqualTo(String value) {
			addCriterion("description <=", value, "description");
			return this;
		}

		public Criteria andDescriptionLike(String value) {
			addCriterion("description like", value, "description");
			return this;
		}

		public Criteria andDescriptionNotLike(String value) {
			addCriterion("description not like", value, "description");
			return this;
		}

		public Criteria andDescriptionIn(List values) {
			addCriterion("description in", values, "description");
			return this;
		}

		public Criteria andDescriptionNotIn(List values) {
			addCriterion("description not in", values, "description");
			return this;
		}

		public Criteria andDescriptionBetween(String value1, String value2) {
			addCriterion("description between", value1, value2, "description");
			return this;
		}

		public Criteria andDescriptionNotBetween(String value1, String value2) {
			addCriterion("description not between", value1, value2,
					"description");
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

		public Criteria andSkillNameIsNull() {
			addCriterion("skill_name is null");
			return this;
		}

		public Criteria andSkillNameIsNotNull() {
			addCriterion("skill_name is not null");
			return this;
		}

		public Criteria andSkillNameEqualTo(String value) {
			addCriterion("skill_name =", value, "skillName");
			return this;
		}

		public Criteria andSkillNameNotEqualTo(String value) {
			addCriterion("skill_name <>", value, "skillName");
			return this;
		}

		public Criteria andSkillNameGreaterThan(String value) {
			addCriterion("skill_name >", value, "skillName");
			return this;
		}

		public Criteria andSkillNameGreaterThanOrEqualTo(String value) {
			addCriterion("skill_name >=", value, "skillName");
			return this;
		}

		public Criteria andSkillNameLessThan(String value) {
			addCriterion("skill_name <", value, "skillName");
			return this;
		}

		public Criteria andSkillNameLessThanOrEqualTo(String value) {
			addCriterion("skill_name <=", value, "skillName");
			return this;
		}

		public Criteria andSkillNameLike(String value) {
			addCriterion("skill_name like", value, "skillName");
			return this;
		}

		public Criteria andSkillNameNotLike(String value) {
			addCriterion("skill_name not like", value, "skillName");
			return this;
		}

		public Criteria andSkillNameIn(List values) {
			addCriterion("skill_name in", values, "skillName");
			return this;
		}

		public Criteria andSkillNameNotIn(List values) {
			addCriterion("skill_name not in", values, "skillName");
			return this;
		}

		public Criteria andSkillNameBetween(String value1, String value2) {
			addCriterion("skill_name between", value1, value2, "skillName");
			return this;
		}

		public Criteria andSkillNameNotBetween(String value1, String value2) {
			addCriterion("skill_name not between", value1, value2, "skillName");
			return this;
		}

		public Criteria andSkillTypeIsNull() {
			addCriterion("skill_type is null");
			return this;
		}

		public Criteria andSkillTypeIsNotNull() {
			addCriterion("skill_type is not null");
			return this;
		}

		public Criteria andSkillTypeEqualTo(Integer value) {
			addCriterion("skill_type =", value, "skillType");
			return this;
		}

		public Criteria andSkillTypeNotEqualTo(Integer value) {
			addCriterion("skill_type <>", value, "skillType");
			return this;
		}

		public Criteria andSkillTypeGreaterThan(Integer value) {
			addCriterion("skill_type >", value, "skillType");
			return this;
		}

		public Criteria andSkillTypeGreaterThanOrEqualTo(Integer value) {
			addCriterion("skill_type >=", value, "skillType");
			return this;
		}

		public Criteria andSkillTypeLessThan(Integer value) {
			addCriterion("skill_type <", value, "skillType");
			return this;
		}

		public Criteria andSkillTypeLessThanOrEqualTo(Integer value) {
			addCriterion("skill_type <=", value, "skillType");
			return this;
		}

		public Criteria andSkillTypeIn(List values) {
			addCriterion("skill_type in", values, "skillType");
			return this;
		}

		public Criteria andSkillTypeNotIn(List values) {
			addCriterion("skill_type not in", values, "skillType");
			return this;
		}

		public Criteria andSkillTypeBetween(Integer value1, Integer value2) {
			addCriterion("skill_type between", value1, value2, "skillType");
			return this;
		}

		public Criteria andSkillTypeNotBetween(Integer value1, Integer value2) {
			addCriterion("skill_type not between", value1, value2, "skillType");
			return this;
		}

		public Criteria andPropertyValueIsNull() {
			addCriterion("property_value is null");
			return this;
		}

		public Criteria andPropertyValueIsNotNull() {
			addCriterion("property_value is not null");
			return this;
		}

		public Criteria andPropertyValueEqualTo(Integer value) {
			addCriterion("property_value =", value, "propertyValue");
			return this;
		}

		public Criteria andPropertyValueNotEqualTo(Integer value) {
			addCriterion("property_value <>", value, "propertyValue");
			return this;
		}

		public Criteria andPropertyValueGreaterThan(Integer value) {
			addCriterion("property_value >", value, "propertyValue");
			return this;
		}

		public Criteria andPropertyValueGreaterThanOrEqualTo(Integer value) {
			addCriterion("property_value >=", value, "propertyValue");
			return this;
		}

		public Criteria andPropertyValueLessThan(Integer value) {
			addCriterion("property_value <", value, "propertyValue");
			return this;
		}

		public Criteria andPropertyValueLessThanOrEqualTo(Integer value) {
			addCriterion("property_value <=", value, "propertyValue");
			return this;
		}

		public Criteria andPropertyValueIn(List values) {
			addCriterion("property_value in", values, "propertyValue");
			return this;
		}

		public Criteria andPropertyValueNotIn(List values) {
			addCriterion("property_value not in", values, "propertyValue");
			return this;
		}

		public Criteria andPropertyValueBetween(Integer value1, Integer value2) {
			addCriterion("property_value between", value1, value2,
					"propertyValue");
			return this;
		}

		public Criteria andPropertyValueNotBetween(Integer value1,
				Integer value2) {
			addCriterion("property_value not between", value1, value2,
					"propertyValue");
			return this;
		}

		public Criteria andOutProbabilityIsNull() {
			addCriterion("out_probability is null");
			return this;
		}

		public Criteria andOutProbabilityIsNotNull() {
			addCriterion("out_probability is not null");
			return this;
		}

		public Criteria andOutProbabilityEqualTo(Integer value) {
			addCriterion("out_probability =", value, "outProbability");
			return this;
		}

		public Criteria andOutProbabilityNotEqualTo(Integer value) {
			addCriterion("out_probability <>", value, "outProbability");
			return this;
		}

		public Criteria andOutProbabilityGreaterThan(Integer value) {
			addCriterion("out_probability >", value, "outProbability");
			return this;
		}

		public Criteria andOutProbabilityGreaterThanOrEqualTo(Integer value) {
			addCriterion("out_probability >=", value, "outProbability");
			return this;
		}

		public Criteria andOutProbabilityLessThan(Integer value) {
			addCriterion("out_probability <", value, "outProbability");
			return this;
		}

		public Criteria andOutProbabilityLessThanOrEqualTo(Integer value) {
			addCriterion("out_probability <=", value, "outProbability");
			return this;
		}

		public Criteria andOutProbabilityIn(List values) {
			addCriterion("out_probability in", values, "outProbability");
			return this;
		}

		public Criteria andOutProbabilityNotIn(List values) {
			addCriterion("out_probability not in", values, "outProbability");
			return this;
		}

		public Criteria andOutProbabilityBetween(Integer value1, Integer value2) {
			addCriterion("out_probability between", value1, value2,
					"outProbability");
			return this;
		}

		public Criteria andOutProbabilityNotBetween(Integer value1,
				Integer value2) {
			addCriterion("out_probability not between", value1, value2,
					"outProbability");
			return this;
		}

		public Criteria andDecreaseMpIsNull() {
			addCriterion("decrease_mp is null");
			return this;
		}

		public Criteria andDecreaseMpIsNotNull() {
			addCriterion("decrease_mp is not null");
			return this;
		}

		public Criteria andDecreaseMpEqualTo(Integer value) {
			addCriterion("decrease_mp =", value, "decreaseMp");
			return this;
		}

		public Criteria andDecreaseMpNotEqualTo(Integer value) {
			addCriterion("decrease_mp <>", value, "decreaseMp");
			return this;
		}

		public Criteria andDecreaseMpGreaterThan(Integer value) {
			addCriterion("decrease_mp >", value, "decreaseMp");
			return this;
		}

		public Criteria andDecreaseMpGreaterThanOrEqualTo(Integer value) {
			addCriterion("decrease_mp >=", value, "decreaseMp");
			return this;
		}

		public Criteria andDecreaseMpLessThan(Integer value) {
			addCriterion("decrease_mp <", value, "decreaseMp");
			return this;
		}

		public Criteria andDecreaseMpLessThanOrEqualTo(Integer value) {
			addCriterion("decrease_mp <=", value, "decreaseMp");
			return this;
		}

		public Criteria andDecreaseMpIn(List values) {
			addCriterion("decrease_mp in", values, "decreaseMp");
			return this;
		}

		public Criteria andDecreaseMpNotIn(List values) {
			addCriterion("decrease_mp not in", values, "decreaseMp");
			return this;
		}

		public Criteria andDecreaseMpBetween(Integer value1, Integer value2) {
			addCriterion("decrease_mp between", value1, value2, "decreaseMp");
			return this;
		}

		public Criteria andDecreaseMpNotBetween(Integer value1, Integer value2) {
			addCriterion("decrease_mp not between", value1, value2,
					"decreaseMp");
			return this;
		}

		public Criteria andIsPercentIsNull() {
			addCriterion("is_percent is null");
			return this;
		}

		public Criteria andIsPercentIsNotNull() {
			addCriterion("is_percent is not null");
			return this;
		}

		public Criteria andIsPercentEqualTo(Boolean value) {
			addCriterion("is_percent =", value, "isPercent");
			return this;
		}

		public Criteria andIsPercentNotEqualTo(Boolean value) {
			addCriterion("is_percent <>", value, "isPercent");
			return this;
		}

		public Criteria andIsPercentGreaterThan(Boolean value) {
			addCriterion("is_percent >", value, "isPercent");
			return this;
		}

		public Criteria andIsPercentGreaterThanOrEqualTo(Boolean value) {
			addCriterion("is_percent >=", value, "isPercent");
			return this;
		}

		public Criteria andIsPercentLessThan(Boolean value) {
			addCriterion("is_percent <", value, "isPercent");
			return this;
		}

		public Criteria andIsPercentLessThanOrEqualTo(Boolean value) {
			addCriterion("is_percent <=", value, "isPercent");
			return this;
		}

		public Criteria andIsPercentIn(List values) {
			addCriterion("is_percent in", values, "isPercent");
			return this;
		}

		public Criteria andIsPercentNotIn(List values) {
			addCriterion("is_percent not in", values, "isPercent");
			return this;
		}

		public Criteria andIsPercentBetween(Boolean value1, Boolean value2) {
			addCriterion("is_percent between", value1, value2, "isPercent");
			return this;
		}

		public Criteria andIsPercentNotBetween(Boolean value1, Boolean value2) {
			addCriterion("is_percent not between", value1, value2, "isPercent");
			return this;
		}
	}
}
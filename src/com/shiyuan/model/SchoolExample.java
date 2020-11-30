package com.shiyuan.model;

import java.util.ArrayList;
import java.util.List;

public class SchoolExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SchoolExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andSchoolIdIsNull() {
            addCriterion("school_id is null");
            return (Criteria) this;
        }

        public Criteria andSchoolIdIsNotNull() {
            addCriterion("school_id is not null");
            return (Criteria) this;
        }

        public Criteria andSchoolIdEqualTo(Long value) {
            addCriterion("school_id =", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdNotEqualTo(Long value) {
            addCriterion("school_id <>", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdGreaterThan(Long value) {
            addCriterion("school_id >", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdGreaterThanOrEqualTo(Long value) {
            addCriterion("school_id >=", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdLessThan(Long value) {
            addCriterion("school_id <", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdLessThanOrEqualTo(Long value) {
            addCriterion("school_id <=", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdIn(List<Long> values) {
            addCriterion("school_id in", values, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdNotIn(List<Long> values) {
            addCriterion("school_id not in", values, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdBetween(Long value1, Long value2) {
            addCriterion("school_id between", value1, value2, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdNotBetween(Long value1, Long value2) {
            addCriterion("school_id not between", value1, value2, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolProvincesIsNull() {
            addCriterion("school_provinces is null");
            return (Criteria) this;
        }

        public Criteria andSchoolProvincesIsNotNull() {
            addCriterion("school_provinces is not null");
            return (Criteria) this;
        }

        public Criteria andSchoolProvincesEqualTo(String value) {
            addCriterion("school_provinces =", value, "schoolProvinces");
            return (Criteria) this;
        }

        public Criteria andSchoolProvincesNotEqualTo(String value) {
            addCriterion("school_provinces <>", value, "schoolProvinces");
            return (Criteria) this;
        }

        public Criteria andSchoolProvincesGreaterThan(String value) {
            addCriterion("school_provinces >", value, "schoolProvinces");
            return (Criteria) this;
        }

        public Criteria andSchoolProvincesGreaterThanOrEqualTo(String value) {
            addCriterion("school_provinces >=", value, "schoolProvinces");
            return (Criteria) this;
        }

        public Criteria andSchoolProvincesLessThan(String value) {
            addCriterion("school_provinces <", value, "schoolProvinces");
            return (Criteria) this;
        }

        public Criteria andSchoolProvincesLessThanOrEqualTo(String value) {
            addCriterion("school_provinces <=", value, "schoolProvinces");
            return (Criteria) this;
        }

        public Criteria andSchoolProvincesLike(String value) {
            addCriterion("school_provinces like", value, "schoolProvinces");
            return (Criteria) this;
        }

        public Criteria andSchoolProvincesNotLike(String value) {
            addCriterion("school_provinces not like", value, "schoolProvinces");
            return (Criteria) this;
        }

        public Criteria andSchoolProvincesIn(List<String> values) {
            addCriterion("school_provinces in", values, "schoolProvinces");
            return (Criteria) this;
        }

        public Criteria andSchoolProvincesNotIn(List<String> values) {
            addCriterion("school_provinces not in", values, "schoolProvinces");
            return (Criteria) this;
        }

        public Criteria andSchoolProvincesBetween(String value1, String value2) {
            addCriterion("school_provinces between", value1, value2, "schoolProvinces");
            return (Criteria) this;
        }

        public Criteria andSchoolProvincesNotBetween(String value1, String value2) {
            addCriterion("school_provinces not between", value1, value2, "schoolProvinces");
            return (Criteria) this;
        }

        public Criteria andSchoolCityIsNull() {
            addCriterion("school_city is null");
            return (Criteria) this;
        }

        public Criteria andSchoolCityIsNotNull() {
            addCriterion("school_city is not null");
            return (Criteria) this;
        }

        public Criteria andSchoolCityEqualTo(String value) {
            addCriterion("school_city =", value, "schoolCity");
            return (Criteria) this;
        }

        public Criteria andSchoolCityNotEqualTo(String value) {
            addCriterion("school_city <>", value, "schoolCity");
            return (Criteria) this;
        }

        public Criteria andSchoolCityGreaterThan(String value) {
            addCriterion("school_city >", value, "schoolCity");
            return (Criteria) this;
        }

        public Criteria andSchoolCityGreaterThanOrEqualTo(String value) {
            addCriterion("school_city >=", value, "schoolCity");
            return (Criteria) this;
        }

        public Criteria andSchoolCityLessThan(String value) {
            addCriterion("school_city <", value, "schoolCity");
            return (Criteria) this;
        }

        public Criteria andSchoolCityLessThanOrEqualTo(String value) {
            addCriterion("school_city <=", value, "schoolCity");
            return (Criteria) this;
        }

        public Criteria andSchoolCityLike(String value) {
            addCriterion("school_city like", value, "schoolCity");
            return (Criteria) this;
        }

        public Criteria andSchoolCityNotLike(String value) {
            addCriterion("school_city not like", value, "schoolCity");
            return (Criteria) this;
        }

        public Criteria andSchoolCityIn(List<String> values) {
            addCriterion("school_city in", values, "schoolCity");
            return (Criteria) this;
        }

        public Criteria andSchoolCityNotIn(List<String> values) {
            addCriterion("school_city not in", values, "schoolCity");
            return (Criteria) this;
        }

        public Criteria andSchoolCityBetween(String value1, String value2) {
            addCriterion("school_city between", value1, value2, "schoolCity");
            return (Criteria) this;
        }

        public Criteria andSchoolCityNotBetween(String value1, String value2) {
            addCriterion("school_city not between", value1, value2, "schoolCity");
            return (Criteria) this;
        }

        public Criteria andSchoolNameIsNull() {
            addCriterion("school_name is null");
            return (Criteria) this;
        }

        public Criteria andSchoolNameIsNotNull() {
            addCriterion("school_name is not null");
            return (Criteria) this;
        }

        public Criteria andSchoolNameEqualTo(String value) {
            addCriterion("school_name =", value, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameNotEqualTo(String value) {
            addCriterion("school_name <>", value, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameGreaterThan(String value) {
            addCriterion("school_name >", value, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameGreaterThanOrEqualTo(String value) {
            addCriterion("school_name >=", value, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameLessThan(String value) {
            addCriterion("school_name <", value, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameLessThanOrEqualTo(String value) {
            addCriterion("school_name <=", value, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameLike(String value) {
            addCriterion("school_name like", value, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameNotLike(String value) {
            addCriterion("school_name not like", value, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameIn(List<String> values) {
            addCriterion("school_name in", values, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameNotIn(List<String> values) {
            addCriterion("school_name not in", values, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameBetween(String value1, String value2) {
            addCriterion("school_name between", value1, value2, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameNotBetween(String value1, String value2) {
            addCriterion("school_name not between", value1, value2, "schoolName");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}
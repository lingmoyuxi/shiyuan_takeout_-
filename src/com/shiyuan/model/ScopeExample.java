package com.shiyuan.model;

import java.util.ArrayList;
import java.util.List;

public class ScopeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ScopeExample() {
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

        public Criteria andScopeIdIsNull() {
            addCriterion("scope_id is null");
            return (Criteria) this;
        }

        public Criteria andScopeIdIsNotNull() {
            addCriterion("scope_id is not null");
            return (Criteria) this;
        }

        public Criteria andScopeIdEqualTo(Long value) {
            addCriterion("scope_id =", value, "scopeId");
            return (Criteria) this;
        }

        public Criteria andScopeIdNotEqualTo(Long value) {
            addCriterion("scope_id <>", value, "scopeId");
            return (Criteria) this;
        }

        public Criteria andScopeIdGreaterThan(Long value) {
            addCriterion("scope_id >", value, "scopeId");
            return (Criteria) this;
        }

        public Criteria andScopeIdGreaterThanOrEqualTo(Long value) {
            addCriterion("scope_id >=", value, "scopeId");
            return (Criteria) this;
        }

        public Criteria andScopeIdLessThan(Long value) {
            addCriterion("scope_id <", value, "scopeId");
            return (Criteria) this;
        }

        public Criteria andScopeIdLessThanOrEqualTo(Long value) {
            addCriterion("scope_id <=", value, "scopeId");
            return (Criteria) this;
        }

        public Criteria andScopeIdIn(List<Long> values) {
            addCriterion("scope_id in", values, "scopeId");
            return (Criteria) this;
        }

        public Criteria andScopeIdNotIn(List<Long> values) {
            addCriterion("scope_id not in", values, "scopeId");
            return (Criteria) this;
        }

        public Criteria andScopeIdBetween(Long value1, Long value2) {
            addCriterion("scope_id between", value1, value2, "scopeId");
            return (Criteria) this;
        }

        public Criteria andScopeIdNotBetween(Long value1, Long value2) {
            addCriterion("scope_id not between", value1, value2, "scopeId");
            return (Criteria) this;
        }

        public Criteria andScopeNameIsNull() {
            addCriterion("scope_name is null");
            return (Criteria) this;
        }

        public Criteria andScopeNameIsNotNull() {
            addCriterion("scope_name is not null");
            return (Criteria) this;
        }

        public Criteria andScopeNameEqualTo(String value) {
            addCriterion("scope_name =", value, "scopeName");
            return (Criteria) this;
        }

        public Criteria andScopeNameNotEqualTo(String value) {
            addCriterion("scope_name <>", value, "scopeName");
            return (Criteria) this;
        }

        public Criteria andScopeNameGreaterThan(String value) {
            addCriterion("scope_name >", value, "scopeName");
            return (Criteria) this;
        }

        public Criteria andScopeNameGreaterThanOrEqualTo(String value) {
            addCriterion("scope_name >=", value, "scopeName");
            return (Criteria) this;
        }

        public Criteria andScopeNameLessThan(String value) {
            addCriterion("scope_name <", value, "scopeName");
            return (Criteria) this;
        }

        public Criteria andScopeNameLessThanOrEqualTo(String value) {
            addCriterion("scope_name <=", value, "scopeName");
            return (Criteria) this;
        }

        public Criteria andScopeNameLike(String value) {
            addCriterion("scope_name like", value, "scopeName");
            return (Criteria) this;
        }

        public Criteria andScopeNameNotLike(String value) {
            addCriterion("scope_name not like", value, "scopeName");
            return (Criteria) this;
        }

        public Criteria andScopeNameIn(List<String> values) {
            addCriterion("scope_name in", values, "scopeName");
            return (Criteria) this;
        }

        public Criteria andScopeNameNotIn(List<String> values) {
            addCriterion("scope_name not in", values, "scopeName");
            return (Criteria) this;
        }

        public Criteria andScopeNameBetween(String value1, String value2) {
            addCriterion("scope_name between", value1, value2, "scopeName");
            return (Criteria) this;
        }

        public Criteria andScopeNameNotBetween(String value1, String value2) {
            addCriterion("scope_name not between", value1, value2, "scopeName");
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
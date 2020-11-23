package com.shiyuan.model;

import java.util.ArrayList;
import java.util.List;

public class ThirdloginExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ThirdloginExample() {
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

        public Criteria andThirdloginIdIsNull() {
            addCriterion("thirdlogin_id is null");
            return (Criteria) this;
        }

        public Criteria andThirdloginIdIsNotNull() {
            addCriterion("thirdlogin_id is not null");
            return (Criteria) this;
        }

        public Criteria andThirdloginIdEqualTo(Long value) {
            addCriterion("thirdlogin_id =", value, "thirdloginId");
            return (Criteria) this;
        }

        public Criteria andThirdloginIdNotEqualTo(Long value) {
            addCriterion("thirdlogin_id <>", value, "thirdloginId");
            return (Criteria) this;
        }

        public Criteria andThirdloginIdGreaterThan(Long value) {
            addCriterion("thirdlogin_id >", value, "thirdloginId");
            return (Criteria) this;
        }

        public Criteria andThirdloginIdGreaterThanOrEqualTo(Long value) {
            addCriterion("thirdlogin_id >=", value, "thirdloginId");
            return (Criteria) this;
        }

        public Criteria andThirdloginIdLessThan(Long value) {
            addCriterion("thirdlogin_id <", value, "thirdloginId");
            return (Criteria) this;
        }

        public Criteria andThirdloginIdLessThanOrEqualTo(Long value) {
            addCriterion("thirdlogin_id <=", value, "thirdloginId");
            return (Criteria) this;
        }

        public Criteria andThirdloginIdIn(List<Long> values) {
            addCriterion("thirdlogin_id in", values, "thirdloginId");
            return (Criteria) this;
        }

        public Criteria andThirdloginIdNotIn(List<Long> values) {
            addCriterion("thirdlogin_id not in", values, "thirdloginId");
            return (Criteria) this;
        }

        public Criteria andThirdloginIdBetween(Long value1, Long value2) {
            addCriterion("thirdlogin_id between", value1, value2, "thirdloginId");
            return (Criteria) this;
        }

        public Criteria andThirdloginIdNotBetween(Long value1, Long value2) {
            addCriterion("thirdlogin_id not between", value1, value2, "thirdloginId");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Long value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andThirdloginKeyIsNull() {
            addCriterion("thirdlogin_key is null");
            return (Criteria) this;
        }

        public Criteria andThirdloginKeyIsNotNull() {
            addCriterion("thirdlogin_key is not null");
            return (Criteria) this;
        }

        public Criteria andThirdloginKeyEqualTo(String value) {
            addCriterion("thirdlogin_key =", value, "thirdloginKey");
            return (Criteria) this;
        }

        public Criteria andThirdloginKeyNotEqualTo(String value) {
            addCriterion("thirdlogin_key <>", value, "thirdloginKey");
            return (Criteria) this;
        }

        public Criteria andThirdloginKeyGreaterThan(String value) {
            addCriterion("thirdlogin_key >", value, "thirdloginKey");
            return (Criteria) this;
        }

        public Criteria andThirdloginKeyGreaterThanOrEqualTo(String value) {
            addCriterion("thirdlogin_key >=", value, "thirdloginKey");
            return (Criteria) this;
        }

        public Criteria andThirdloginKeyLessThan(String value) {
            addCriterion("thirdlogin_key <", value, "thirdloginKey");
            return (Criteria) this;
        }

        public Criteria andThirdloginKeyLessThanOrEqualTo(String value) {
            addCriterion("thirdlogin_key <=", value, "thirdloginKey");
            return (Criteria) this;
        }

        public Criteria andThirdloginKeyLike(String value) {
            addCriterion("thirdlogin_key like", value, "thirdloginKey");
            return (Criteria) this;
        }

        public Criteria andThirdloginKeyNotLike(String value) {
            addCriterion("thirdlogin_key not like", value, "thirdloginKey");
            return (Criteria) this;
        }

        public Criteria andThirdloginKeyIn(List<String> values) {
            addCriterion("thirdlogin_key in", values, "thirdloginKey");
            return (Criteria) this;
        }

        public Criteria andThirdloginKeyNotIn(List<String> values) {
            addCriterion("thirdlogin_key not in", values, "thirdloginKey");
            return (Criteria) this;
        }

        public Criteria andThirdloginKeyBetween(String value1, String value2) {
            addCriterion("thirdlogin_key between", value1, value2, "thirdloginKey");
            return (Criteria) this;
        }

        public Criteria andThirdloginKeyNotBetween(String value1, String value2) {
            addCriterion("thirdlogin_key not between", value1, value2, "thirdloginKey");
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
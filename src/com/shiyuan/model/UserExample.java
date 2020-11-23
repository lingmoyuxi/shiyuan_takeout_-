package com.shiyuan.model;

import java.util.ArrayList;
import java.util.List;

public class UserExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserExample() {
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

        public Criteria andUserNameIsNull() {
            addCriterion("user_name is null");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNotNull() {
            addCriterion("user_name is not null");
            return (Criteria) this;
        }

        public Criteria andUserNameEqualTo(String value) {
            addCriterion("user_name =", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotEqualTo(String value) {
            addCriterion("user_name <>", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThan(String value) {
            addCriterion("user_name >", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("user_name >=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThan(String value) {
            addCriterion("user_name <", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThanOrEqualTo(String value) {
            addCriterion("user_name <=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLike(String value) {
            addCriterion("user_name like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotLike(String value) {
            addCriterion("user_name not like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameIn(List<String> values) {
            addCriterion("user_name in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotIn(List<String> values) {
            addCriterion("user_name not in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameBetween(String value1, String value2) {
            addCriterion("user_name between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotBetween(String value1, String value2) {
            addCriterion("user_name not between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserAccountIsNull() {
            addCriterion("user_account is null");
            return (Criteria) this;
        }

        public Criteria andUserAccountIsNotNull() {
            addCriterion("user_account is not null");
            return (Criteria) this;
        }

        public Criteria andUserAccountEqualTo(Long value) {
            addCriterion("user_account =", value, "userAccount");
            return (Criteria) this;
        }

        public Criteria andUserAccountNotEqualTo(Long value) {
            addCriterion("user_account <>", value, "userAccount");
            return (Criteria) this;
        }

        public Criteria andUserAccountGreaterThan(Long value) {
            addCriterion("user_account >", value, "userAccount");
            return (Criteria) this;
        }

        public Criteria andUserAccountGreaterThanOrEqualTo(Long value) {
            addCriterion("user_account >=", value, "userAccount");
            return (Criteria) this;
        }

        public Criteria andUserAccountLessThan(Long value) {
            addCriterion("user_account <", value, "userAccount");
            return (Criteria) this;
        }

        public Criteria andUserAccountLessThanOrEqualTo(Long value) {
            addCriterion("user_account <=", value, "userAccount");
            return (Criteria) this;
        }

        public Criteria andUserAccountIn(List<Long> values) {
            addCriterion("user_account in", values, "userAccount");
            return (Criteria) this;
        }

        public Criteria andUserAccountNotIn(List<Long> values) {
            addCriterion("user_account not in", values, "userAccount");
            return (Criteria) this;
        }

        public Criteria andUserAccountBetween(Long value1, Long value2) {
            addCriterion("user_account between", value1, value2, "userAccount");
            return (Criteria) this;
        }

        public Criteria andUserAccountNotBetween(Long value1, Long value2) {
            addCriterion("user_account not between", value1, value2, "userAccount");
            return (Criteria) this;
        }

        public Criteria andUserPasswordIsNull() {
            addCriterion("user_password is null");
            return (Criteria) this;
        }

        public Criteria andUserPasswordIsNotNull() {
            addCriterion("user_password is not null");
            return (Criteria) this;
        }

        public Criteria andUserPasswordEqualTo(String value) {
            addCriterion("user_password =", value, "userPassword");
            return (Criteria) this;
        }

        public Criteria andUserPasswordNotEqualTo(String value) {
            addCriterion("user_password <>", value, "userPassword");
            return (Criteria) this;
        }

        public Criteria andUserPasswordGreaterThan(String value) {
            addCriterion("user_password >", value, "userPassword");
            return (Criteria) this;
        }

        public Criteria andUserPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("user_password >=", value, "userPassword");
            return (Criteria) this;
        }

        public Criteria andUserPasswordLessThan(String value) {
            addCriterion("user_password <", value, "userPassword");
            return (Criteria) this;
        }

        public Criteria andUserPasswordLessThanOrEqualTo(String value) {
            addCriterion("user_password <=", value, "userPassword");
            return (Criteria) this;
        }

        public Criteria andUserPasswordLike(String value) {
            addCriterion("user_password like", value, "userPassword");
            return (Criteria) this;
        }

        public Criteria andUserPasswordNotLike(String value) {
            addCriterion("user_password not like", value, "userPassword");
            return (Criteria) this;
        }

        public Criteria andUserPasswordIn(List<String> values) {
            addCriterion("user_password in", values, "userPassword");
            return (Criteria) this;
        }

        public Criteria andUserPasswordNotIn(List<String> values) {
            addCriterion("user_password not in", values, "userPassword");
            return (Criteria) this;
        }

        public Criteria andUserPasswordBetween(String value1, String value2) {
            addCriterion("user_password between", value1, value2, "userPassword");
            return (Criteria) this;
        }

        public Criteria andUserPasswordNotBetween(String value1, String value2) {
            addCriterion("user_password not between", value1, value2, "userPassword");
            return (Criteria) this;
        }

        public Criteria andUserIconIsNull() {
            addCriterion("user_icon is null");
            return (Criteria) this;
        }

        public Criteria andUserIconIsNotNull() {
            addCriterion("user_icon is not null");
            return (Criteria) this;
        }

        public Criteria andUserIconEqualTo(String value) {
            addCriterion("user_icon =", value, "userIcon");
            return (Criteria) this;
        }

        public Criteria andUserIconNotEqualTo(String value) {
            addCriterion("user_icon <>", value, "userIcon");
            return (Criteria) this;
        }

        public Criteria andUserIconGreaterThan(String value) {
            addCriterion("user_icon >", value, "userIcon");
            return (Criteria) this;
        }

        public Criteria andUserIconGreaterThanOrEqualTo(String value) {
            addCriterion("user_icon >=", value, "userIcon");
            return (Criteria) this;
        }

        public Criteria andUserIconLessThan(String value) {
            addCriterion("user_icon <", value, "userIcon");
            return (Criteria) this;
        }

        public Criteria andUserIconLessThanOrEqualTo(String value) {
            addCriterion("user_icon <=", value, "userIcon");
            return (Criteria) this;
        }

        public Criteria andUserIconLike(String value) {
            addCriterion("user_icon like", value, "userIcon");
            return (Criteria) this;
        }

        public Criteria andUserIconNotLike(String value) {
            addCriterion("user_icon not like", value, "userIcon");
            return (Criteria) this;
        }

        public Criteria andUserIconIn(List<String> values) {
            addCriterion("user_icon in", values, "userIcon");
            return (Criteria) this;
        }

        public Criteria andUserIconNotIn(List<String> values) {
            addCriterion("user_icon not in", values, "userIcon");
            return (Criteria) this;
        }

        public Criteria andUserIconBetween(String value1, String value2) {
            addCriterion("user_icon between", value1, value2, "userIcon");
            return (Criteria) this;
        }

        public Criteria andUserIconNotBetween(String value1, String value2) {
            addCriterion("user_icon not between", value1, value2, "userIcon");
            return (Criteria) this;
        }

        public Criteria andUserPhoneKeyIsNull() {
            addCriterion("user_phone_key is null");
            return (Criteria) this;
        }

        public Criteria andUserPhoneKeyIsNotNull() {
            addCriterion("user_phone_key is not null");
            return (Criteria) this;
        }

        public Criteria andUserPhoneKeyEqualTo(Long value) {
            addCriterion("user_phone_key =", value, "userPhoneKey");
            return (Criteria) this;
        }

        public Criteria andUserPhoneKeyNotEqualTo(Long value) {
            addCriterion("user_phone_key <>", value, "userPhoneKey");
            return (Criteria) this;
        }

        public Criteria andUserPhoneKeyGreaterThan(Long value) {
            addCriterion("user_phone_key >", value, "userPhoneKey");
            return (Criteria) this;
        }

        public Criteria andUserPhoneKeyGreaterThanOrEqualTo(Long value) {
            addCriterion("user_phone_key >=", value, "userPhoneKey");
            return (Criteria) this;
        }

        public Criteria andUserPhoneKeyLessThan(Long value) {
            addCriterion("user_phone_key <", value, "userPhoneKey");
            return (Criteria) this;
        }

        public Criteria andUserPhoneKeyLessThanOrEqualTo(Long value) {
            addCriterion("user_phone_key <=", value, "userPhoneKey");
            return (Criteria) this;
        }

        public Criteria andUserPhoneKeyIn(List<Long> values) {
            addCriterion("user_phone_key in", values, "userPhoneKey");
            return (Criteria) this;
        }

        public Criteria andUserPhoneKeyNotIn(List<Long> values) {
            addCriterion("user_phone_key not in", values, "userPhoneKey");
            return (Criteria) this;
        }

        public Criteria andUserPhoneKeyBetween(Long value1, Long value2) {
            addCriterion("user_phone_key between", value1, value2, "userPhoneKey");
            return (Criteria) this;
        }

        public Criteria andUserPhoneKeyNotBetween(Long value1, Long value2) {
            addCriterion("user_phone_key not between", value1, value2, "userPhoneKey");
            return (Criteria) this;
        }

        public Criteria andUserQqKeyIsNull() {
            addCriterion("user_qq_key is null");
            return (Criteria) this;
        }

        public Criteria andUserQqKeyIsNotNull() {
            addCriterion("user_qq_key is not null");
            return (Criteria) this;
        }

        public Criteria andUserQqKeyEqualTo(String value) {
            addCriterion("user_qq_key =", value, "userQqKey");
            return (Criteria) this;
        }

        public Criteria andUserQqKeyNotEqualTo(String value) {
            addCriterion("user_qq_key <>", value, "userQqKey");
            return (Criteria) this;
        }

        public Criteria andUserQqKeyGreaterThan(String value) {
            addCriterion("user_qq_key >", value, "userQqKey");
            return (Criteria) this;
        }

        public Criteria andUserQqKeyGreaterThanOrEqualTo(String value) {
            addCriterion("user_qq_key >=", value, "userQqKey");
            return (Criteria) this;
        }

        public Criteria andUserQqKeyLessThan(String value) {
            addCriterion("user_qq_key <", value, "userQqKey");
            return (Criteria) this;
        }

        public Criteria andUserQqKeyLessThanOrEqualTo(String value) {
            addCriterion("user_qq_key <=", value, "userQqKey");
            return (Criteria) this;
        }

        public Criteria andUserQqKeyLike(String value) {
            addCriterion("user_qq_key like", value, "userQqKey");
            return (Criteria) this;
        }

        public Criteria andUserQqKeyNotLike(String value) {
            addCriterion("user_qq_key not like", value, "userQqKey");
            return (Criteria) this;
        }

        public Criteria andUserQqKeyIn(List<String> values) {
            addCriterion("user_qq_key in", values, "userQqKey");
            return (Criteria) this;
        }

        public Criteria andUserQqKeyNotIn(List<String> values) {
            addCriterion("user_qq_key not in", values, "userQqKey");
            return (Criteria) this;
        }

        public Criteria andUserQqKeyBetween(String value1, String value2) {
            addCriterion("user_qq_key between", value1, value2, "userQqKey");
            return (Criteria) this;
        }

        public Criteria andUserQqKeyNotBetween(String value1, String value2) {
            addCriterion("user_qq_key not between", value1, value2, "userQqKey");
            return (Criteria) this;
        }

        public Criteria andUserWeixinKeyIsNull() {
            addCriterion("user_weixin_key is null");
            return (Criteria) this;
        }

        public Criteria andUserWeixinKeyIsNotNull() {
            addCriterion("user_weixin_key is not null");
            return (Criteria) this;
        }

        public Criteria andUserWeixinKeyEqualTo(String value) {
            addCriterion("user_weixin_key =", value, "userWeixinKey");
            return (Criteria) this;
        }

        public Criteria andUserWeixinKeyNotEqualTo(String value) {
            addCriterion("user_weixin_key <>", value, "userWeixinKey");
            return (Criteria) this;
        }

        public Criteria andUserWeixinKeyGreaterThan(String value) {
            addCriterion("user_weixin_key >", value, "userWeixinKey");
            return (Criteria) this;
        }

        public Criteria andUserWeixinKeyGreaterThanOrEqualTo(String value) {
            addCriterion("user_weixin_key >=", value, "userWeixinKey");
            return (Criteria) this;
        }

        public Criteria andUserWeixinKeyLessThan(String value) {
            addCriterion("user_weixin_key <", value, "userWeixinKey");
            return (Criteria) this;
        }

        public Criteria andUserWeixinKeyLessThanOrEqualTo(String value) {
            addCriterion("user_weixin_key <=", value, "userWeixinKey");
            return (Criteria) this;
        }

        public Criteria andUserWeixinKeyLike(String value) {
            addCriterion("user_weixin_key like", value, "userWeixinKey");
            return (Criteria) this;
        }

        public Criteria andUserWeixinKeyNotLike(String value) {
            addCriterion("user_weixin_key not like", value, "userWeixinKey");
            return (Criteria) this;
        }

        public Criteria andUserWeixinKeyIn(List<String> values) {
            addCriterion("user_weixin_key in", values, "userWeixinKey");
            return (Criteria) this;
        }

        public Criteria andUserWeixinKeyNotIn(List<String> values) {
            addCriterion("user_weixin_key not in", values, "userWeixinKey");
            return (Criteria) this;
        }

        public Criteria andUserWeixinKeyBetween(String value1, String value2) {
            addCriterion("user_weixin_key between", value1, value2, "userWeixinKey");
            return (Criteria) this;
        }

        public Criteria andUserWeixinKeyNotBetween(String value1, String value2) {
            addCriterion("user_weixin_key not between", value1, value2, "userWeixinKey");
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
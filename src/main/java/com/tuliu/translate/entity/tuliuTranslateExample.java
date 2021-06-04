package com.tuliu.translate.entity;

import java.util.ArrayList;
import java.util.List;

public class tuliuTranslateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public tuliuTranslateExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andHashKeyIsNull() {
            addCriterion("hash_key is null");
            return (Criteria) this;
        }

        public Criteria andHashKeyIsNotNull() {
            addCriterion("hash_key is not null");
            return (Criteria) this;
        }

        public Criteria andHashKeyEqualTo(String value) {
            addCriterion("hash_key =", value, "hashKey");
            return (Criteria) this;
        }

        public Criteria andHashKeyNotEqualTo(String value) {
            addCriterion("hash_key <>", value, "hashKey");
            return (Criteria) this;
        }

        public Criteria andHashKeyGreaterThan(String value) {
            addCriterion("hash_key >", value, "hashKey");
            return (Criteria) this;
        }

        public Criteria andHashKeyGreaterThanOrEqualTo(String value) {
            addCriterion("hash_key >=", value, "hashKey");
            return (Criteria) this;
        }

        public Criteria andHashKeyLessThan(String value) {
            addCriterion("hash_key <", value, "hashKey");
            return (Criteria) this;
        }

        public Criteria andHashKeyLessThanOrEqualTo(String value) {
            addCriterion("hash_key <=", value, "hashKey");
            return (Criteria) this;
        }

        public Criteria andHashKeyLike(String value) {
            addCriterion("hash_key like", value, "hashKey");
            return (Criteria) this;
        }

        public Criteria andHashKeyNotLike(String value) {
            addCriterion("hash_key not like", value, "hashKey");
            return (Criteria) this;
        }

        public Criteria andHashKeyIn(List<String> values) {
            addCriterion("hash_key in", values, "hashKey");
            return (Criteria) this;
        }

        public Criteria andHashKeyNotIn(List<String> values) {
            addCriterion("hash_key not in", values, "hashKey");
            return (Criteria) this;
        }

        public Criteria andHashKeyBetween(String value1, String value2) {
            addCriterion("hash_key between", value1, value2, "hashKey");
            return (Criteria) this;
        }

        public Criteria andHashKeyNotBetween(String value1, String value2) {
            addCriterion("hash_key not between", value1, value2, "hashKey");
            return (Criteria) this;
        }

        public Criteria andZhIsNull() {
            addCriterion("zh is null");
            return (Criteria) this;
        }

        public Criteria andZhIsNotNull() {
            addCriterion("zh is not null");
            return (Criteria) this;
        }

        public Criteria andZhEqualTo(String value) {
            addCriterion("zh =", value, "zh");
            return (Criteria) this;
        }

        public Criteria andZhNotEqualTo(String value) {
            addCriterion("zh <>", value, "zh");
            return (Criteria) this;
        }

        public Criteria andZhGreaterThan(String value) {
            addCriterion("zh >", value, "zh");
            return (Criteria) this;
        }

        public Criteria andZhGreaterThanOrEqualTo(String value) {
            addCriterion("zh >=", value, "zh");
            return (Criteria) this;
        }

        public Criteria andZhLessThan(String value) {
            addCriterion("zh <", value, "zh");
            return (Criteria) this;
        }

        public Criteria andZhLessThanOrEqualTo(String value) {
            addCriterion("zh <=", value, "zh");
            return (Criteria) this;
        }

        public Criteria andZhLike(String value) {
            addCriterion("zh like", value, "zh");
            return (Criteria) this;
        }

        public Criteria andZhNotLike(String value) {
            addCriterion("zh not like", value, "zh");
            return (Criteria) this;
        }

        public Criteria andZhIn(List<String> values) {
            addCriterion("zh in", values, "zh");
            return (Criteria) this;
        }

        public Criteria andZhNotIn(List<String> values) {
            addCriterion("zh not in", values, "zh");
            return (Criteria) this;
        }

        public Criteria andZhBetween(String value1, String value2) {
            addCriterion("zh between", value1, value2, "zh");
            return (Criteria) this;
        }

        public Criteria andZhNotBetween(String value1, String value2) {
            addCriterion("zh not between", value1, value2, "zh");
            return (Criteria) this;
        }

        public Criteria andEnIsNull() {
            addCriterion("en is null");
            return (Criteria) this;
        }

        public Criteria andEnIsNotNull() {
            addCriterion("en is not null");
            return (Criteria) this;
        }

        public Criteria andEnEqualTo(String value) {
            addCriterion("en =", value, "en");
            return (Criteria) this;
        }

        public Criteria andEnNotEqualTo(String value) {
            addCriterion("en <>", value, "en");
            return (Criteria) this;
        }

        public Criteria andEnGreaterThan(String value) {
            addCriterion("en >", value, "en");
            return (Criteria) this;
        }

        public Criteria andEnGreaterThanOrEqualTo(String value) {
            addCriterion("en >=", value, "en");
            return (Criteria) this;
        }

        public Criteria andEnLessThan(String value) {
            addCriterion("en <", value, "en");
            return (Criteria) this;
        }

        public Criteria andEnLessThanOrEqualTo(String value) {
            addCriterion("en <=", value, "en");
            return (Criteria) this;
        }

        public Criteria andEnLike(String value) {
            addCriterion("en like", value, "en");
            return (Criteria) this;
        }

        public Criteria andEnNotLike(String value) {
            addCriterion("en not like", value, "en");
            return (Criteria) this;
        }

        public Criteria andEnIn(List<String> values) {
            addCriterion("en in", values, "en");
            return (Criteria) this;
        }

        public Criteria andEnNotIn(List<String> values) {
            addCriterion("en not in", values, "en");
            return (Criteria) this;
        }

        public Criteria andEnBetween(String value1, String value2) {
            addCriterion("en between", value1, value2, "en");
            return (Criteria) this;
        }

        public Criteria andEnNotBetween(String value1, String value2) {
            addCriterion("en not between", value1, value2, "en");
            return (Criteria) this;
        }

        public Criteria andRuIsNull() {
            addCriterion("ru is null");
            return (Criteria) this;
        }

        public Criteria andRuIsNotNull() {
            addCriterion("ru is not null");
            return (Criteria) this;
        }

        public Criteria andRuEqualTo(String value) {
            addCriterion("ru =", value, "ru");
            return (Criteria) this;
        }

        public Criteria andRuNotEqualTo(String value) {
            addCriterion("ru <>", value, "ru");
            return (Criteria) this;
        }

        public Criteria andRuGreaterThan(String value) {
            addCriterion("ru >", value, "ru");
            return (Criteria) this;
        }

        public Criteria andRuGreaterThanOrEqualTo(String value) {
            addCriterion("ru >=", value, "ru");
            return (Criteria) this;
        }

        public Criteria andRuLessThan(String value) {
            addCriterion("ru <", value, "ru");
            return (Criteria) this;
        }

        public Criteria andRuLessThanOrEqualTo(String value) {
            addCriterion("ru <=", value, "ru");
            return (Criteria) this;
        }

        public Criteria andRuLike(String value) {
            addCriterion("ru like", value, "ru");
            return (Criteria) this;
        }

        public Criteria andRuNotLike(String value) {
            addCriterion("ru not like", value, "ru");
            return (Criteria) this;
        }

        public Criteria andRuIn(List<String> values) {
            addCriterion("ru in", values, "ru");
            return (Criteria) this;
        }

        public Criteria andRuNotIn(List<String> values) {
            addCriterion("ru not in", values, "ru");
            return (Criteria) this;
        }

      public Criteria andRuBetween(String value1, String value2) {
        addCriterion("ru between", value1, value2, "ru");
        return (Criteria) this;
      }

      public Criteria andRuNotBetween(String value1, String value2) {
        addCriterion("ru not between", value1, value2, "ru");
        return (Criteria) this;
      }

      public Criteria andAddzhIsNull() {
        addCriterion("addzh is null");
        return (Criteria) this;
      }

      public Criteria andAddzhIsNotNull() {
        addCriterion("addzh is not null");
        return (Criteria) this;
      }

      public Criteria andAddzhEqualTo(String value) {
        addCriterion("addzh =", value, "addzh");
        return (Criteria) this;
      }

      public Criteria andAddzhNotEqualTo(String value) {
        addCriterion("addzh <>", value, "addzh");
        return (Criteria) this;
      }

      public Criteria andAddzhGreaterThan(String value) {
        addCriterion("addzh >", value, "addzh");
        return (Criteria) this;
      }

      public Criteria andAddzhGreaterThanOrEqualTo(String value) {
        addCriterion("addzh >=", value, "addzh");
        return (Criteria) this;
      }

      public Criteria andAddzhLessThan(String value) {
        addCriterion("addzh <", value, "addzh");
        return (Criteria) this;
      }

      public Criteria andAddzhLessThanOrEqualTo(String value) {
        addCriterion("addzh <=", value, "addzh");
        return (Criteria) this;
      }

      public Criteria andAddzhLike(String value) {
        addCriterion("addzh like", value, "addzh");
        return (Criteria) this;
      }

      public Criteria andAddzhNotLike(String value) {
        addCriterion("addzh not like", value, "addzh");
        return (Criteria) this;
      }

      public Criteria andAddzhIn(List<String> values) {
        addCriterion("addzh in", values, "addzh");
        return (Criteria) this;
      }

      public Criteria andAddzhNotIn(List<String> values) {
        addCriterion("addzh not in", values, "addzh");
        return (Criteria) this;
      }

      public Criteria andAddzhBetween(String value1, String value2) {
        addCriterion("addzh between", value1, value2, "addzh");
        return (Criteria) this;
      }

      public Criteria andAddzhNotBetween(String value1, String value2) {
        addCriterion("addzh not between", value1, value2, "addzh");
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

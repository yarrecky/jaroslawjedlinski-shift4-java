package com.shift4.request;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shift4.util.AmountSerializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an amount for a product.
 * <p>
 * For simple products: set only the value field.
 * For donations: set options (predefined values) and/or custom (custom range).
 */
@JsonInclude(Include.NON_NULL)
@JsonSerialize(using = AmountSerializer.class)
public class AmountRequest {

    private Integer value;
    private List<Integer> options;
    private CustomAmount custom;

    @JsonIgnore
    private final Map<String, Object> other = new HashMap<>();

    public AmountRequest() {
    }

    public AmountRequest(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public List<Integer> getOptions() {
        return options;
    }

    public CustomAmount getCustom() {
        return custom;
    }

    public AmountRequest value(Integer value) {
        this.value = value;
        return this;
    }

    public AmountRequest options(List<Integer> options) {
        this.options = options;
        return this;
    }

    public AmountRequest custom(CustomAmount custom) {
        this.custom = custom;
        return this;
    }

    public AmountRequest custom(Integer min, Integer max) {
        this.custom = new CustomAmount(min, max);
        return this;
    }

    @JsonAnyGetter
    private Map<String, Object> getOtherMap() {
        return other;
    }

    @JsonAnySetter
    public AmountRequest set(String name, Object value) {
        other.put(name, value);
        return this;
    }

    @JsonInclude(Include.NON_NULL)
    public static class CustomAmount {

        private Integer min;
        private Integer max;

        @JsonIgnore
        private final Map<String, Object> other = new HashMap<>();

        public CustomAmount() {
        }

        public CustomAmount(Integer min, Integer max) {
            this.min = min;
            this.max = max;
        }

        public Integer getMin() {
            return min;
        }

        public Integer getMax() {
            return max;
        }

        public CustomAmount min(Integer min) {
            this.min = min;
            return this;
        }

        public CustomAmount max(Integer max) {
            this.max = max;
            return this;
        }

        @JsonAnyGetter
        private Map<String, Object> getOtherMap() {
            return other;
        }

        @JsonAnySetter
        public CustomAmount set(String name, Object value) {
            other.put(name, value);
            return this;
        }
    }
}

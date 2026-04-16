package com.shift4.request;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(Include.NON_NULL)
public class TaxRequest {

    private String id;
    private String name;
    private BigDecimal value;
    private String merchantAccountId;

    @JsonIgnore
    private final Map<String, Object> other = new HashMap<>();

    public TaxRequest() {
    }

    public TaxRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getMerchantAccountId() {
        return merchantAccountId;
    }

    public TaxRequest id(String id) {
        this.id = id;
        return this;
    }

    public TaxRequest name(String name) {
        this.name = name;
        return this;
    }

    public TaxRequest value(BigDecimal value) {
        this.value = value;
        return this;
    }

    public TaxRequest merchantAccountId(String merchantAccountId) {
        this.merchantAccountId = merchantAccountId;
        return this;
    }

    @JsonAnyGetter
    private Map<String, Object> getOtherMap() {
        return other;
    }

    @JsonAnySetter
    public TaxRequest set(String name, Object value) {
        other.put(name, value);
        return this;
    }
}

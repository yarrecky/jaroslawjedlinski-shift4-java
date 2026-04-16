package com.shift4.request;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.shift4.response.Tax;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(Include.NON_NULL)
public class TaxUpdateRequest {

    @JsonIgnore
    private String taxId;

    private String name;
    private BigDecimal value;
    private String merchantAccountId;

    @JsonIgnore
    private final Map<String, Object> other = new HashMap<>();

    public TaxUpdateRequest() {
    }

    public TaxUpdateRequest(String taxId) {
        taxId(taxId);
    }

    public TaxUpdateRequest(Tax tax) {
        tax(tax);
    }

    public String getTaxId() {
        return taxId;
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

    public TaxUpdateRequest taxId(String taxId) {
        this.taxId = taxId;
        return this;
    }

    public TaxUpdateRequest tax(Tax tax) {
        return taxId(tax.getId());
    }

    public TaxUpdateRequest name(String name) {
        this.name = name;
        return this;
    }

    public TaxUpdateRequest value(BigDecimal value) {
        this.value = value;
        return this;
    }

    public TaxUpdateRequest merchantAccountId(String merchantAccountId) {
        this.merchantAccountId = merchantAccountId;
        return this;
    }

    @JsonAnyGetter
    private Map<String, Object> getOtherMap() {
        return other;
    }

    @JsonAnySetter
    public TaxUpdateRequest set(String name, Object value) {
        other.put(name, value);
        return this;
    }
}

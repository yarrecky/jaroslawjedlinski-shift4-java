package com.shift4.request;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(Include.NON_NULL)
public class ProductRequest {

    private String id;
    private String name;
    private String currency;
    private AmountRequest amount;
    private String description;
    private Boolean donation;
    private Boolean active;
    private String plan;
    private String merchantAccountId;
    private List<TaxRequest> taxes;

    @JsonIgnore
    private final Map<String, Object> other = new HashMap<>();

    public ProductRequest() {
    }

    public ProductRequest(String id) {
        this.id = id;
    }

    public ProductRequest(String name, String currency) {
        name(name).currency(currency);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCurrency() {
        return currency;
    }

    public AmountRequest getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getDonation() {
        return donation;
    }

    public Boolean getActive() {
        return active;
    }

    public String getPlan() {
        return plan;
    }

    public String getMerchantAccountId() {
        return merchantAccountId;
    }

    public List<TaxRequest> getTaxes() {
        return taxes;
    }

    public ProductRequest id(String id) {
        this.id = id;
        return this;
    }

    public ProductRequest name(String name) {
        this.name = name;
        return this;
    }

    public ProductRequest currency(String currency) {
        this.currency = currency;
        return this;
    }

    public ProductRequest amount(AmountRequest amount) {
        this.amount = amount;
        return this;
    }

    public ProductRequest amount(Integer value) {
        this.amount = new AmountRequest(value);
        return this;
    }

    public ProductRequest description(String description) {
        this.description = description;
        return this;
    }

    public ProductRequest donation(Boolean donation) {
        this.donation = donation;
        return this;
    }

    public ProductRequest active(Boolean active) {
        this.active = active;
        return this;
    }

    public ProductRequest plan(String plan) {
        this.plan = plan;
        return this;
    }

    public ProductRequest merchantAccountId(String merchantAccountId) {
        this.merchantAccountId = merchantAccountId;
        return this;
    }

    public ProductRequest taxes(List<TaxRequest> taxes) {
        this.taxes = taxes;
        return this;
    }

    @JsonAnyGetter
    private Map<String, Object> getOtherMap() {
        return other;
    }

    @JsonAnySetter
    public ProductRequest set(String name, Object value) {
        other.put(name, value);
        return this;
    }
}

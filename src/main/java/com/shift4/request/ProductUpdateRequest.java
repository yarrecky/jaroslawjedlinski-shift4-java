package com.shift4.request;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.shift4.response.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(Include.NON_NULL)
public class ProductUpdateRequest {

    @JsonIgnore
    private String productId;

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

    public ProductUpdateRequest() {
    }

    public ProductUpdateRequest(String productId) {
        productId(productId);
    }

    public ProductUpdateRequest(Product product) {
        product(product);
    }

    public String getProductId() {
        return productId;
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

    public ProductUpdateRequest productId(String productId) {
        this.productId = productId;
        return this;
    }

    public ProductUpdateRequest product(Product product) {
        return productId(product.getId());
    }

    public ProductUpdateRequest name(String name) {
        this.name = name;
        return this;
    }

    public ProductUpdateRequest currency(String currency) {
        this.currency = currency;
        return this;
    }

    public ProductUpdateRequest amount(AmountRequest amount) {
        this.amount = amount;
        return this;
    }

    public ProductUpdateRequest amount(Integer value) {
        this.amount = new AmountRequest(value);
        return this;
    }

    public ProductUpdateRequest description(String description) {
        this.description = description;
        return this;
    }

    public ProductUpdateRequest donation(Boolean donation) {
        this.donation = donation;
        return this;
    }

    public ProductUpdateRequest active(Boolean active) {
        this.active = active;
        return this;
    }

    public ProductUpdateRequest plan(String plan) {
        this.plan = plan;
        return this;
    }

    public ProductUpdateRequest merchantAccountId(String merchantAccountId) {
        this.merchantAccountId = merchantAccountId;
        return this;
    }

    public ProductUpdateRequest taxes(List<TaxRequest> taxes) {
        this.taxes = taxes;
        return this;
    }

    @JsonAnyGetter
    private Map<String, Object> getOtherMap() {
        return other;
    }

    @JsonAnySetter
    public ProductUpdateRequest set(String name, Object value) {
        other.put(name, value);
        return this;
    }
}

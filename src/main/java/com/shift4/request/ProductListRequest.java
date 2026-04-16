package com.shift4.request;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.shift4.response.Product;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(Include.NON_NULL)
public class ProductListRequest {

    private Integer limit;
    private String startingAfterId;
    private String endingBeforeId;
    private Boolean includeTotalCount;

    private CreatedFilter created;

    @JsonIgnore
    private final Map<String, Object> other = new HashMap<>();

    public Integer getLimit() {
        return limit;
    }

    public String getStartingAfterId() {
        return startingAfterId;
    }

    public String getEndingBeforeId() {
        return endingBeforeId;
    }

    public Boolean getIncludeTotalCount() {
        return includeTotalCount;
    }

    public CreatedFilter getCreated() {
        return created;
    }

    public ProductListRequest limit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public ProductListRequest startingAfterId(String startingAfterId) {
        this.startingAfterId = startingAfterId;
        return this;
    }

    public ProductListRequest startingAfter(Product product) {
        return startingAfterId(product.getId());
    }

    public ProductListRequest endingBeforeId(String endingBeforeId) {
        this.endingBeforeId = endingBeforeId;
        return this;
    }

    public ProductListRequest endingBefore(Product product) {
        return endingBeforeId(product.getId());
    }

    public ProductListRequest includeTotalCount(Boolean includeTotalCount) {
        this.includeTotalCount = includeTotalCount;
        return this;
    }

    public ProductListRequest includeTotalCount() {
        return includeTotalCount(true);
    }

    public ProductListRequest created(CreatedFilter created) {
        this.created = created;
        return this;
    }

    @JsonAnyGetter
    private Map<String, Object> getOtherMap() {
        return other;
    }

    @JsonAnySetter
    public ProductListRequest set(String name, Object value) {
        other.put(name, value);
        return this;
    }
}

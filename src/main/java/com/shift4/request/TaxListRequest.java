package com.shift4.request;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.shift4.response.Tax;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(Include.NON_NULL)
public class TaxListRequest {

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

    public TaxListRequest limit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public TaxListRequest startingAfterId(String startingAfterId) {
        this.startingAfterId = startingAfterId;
        return this;
    }

    public TaxListRequest startingAfter(Tax tax) {
        return startingAfterId(tax.getId());
    }

    public TaxListRequest endingBeforeId(String endingBeforeId) {
        this.endingBeforeId = endingBeforeId;
        return this;
    }

    public TaxListRequest endingBefore(Tax tax) {
        return endingBeforeId(tax.getId());
    }

    public TaxListRequest includeTotalCount(Boolean includeTotalCount) {
        this.includeTotalCount = includeTotalCount;
        return this;
    }

    public TaxListRequest includeTotalCount() {
        return includeTotalCount(true);
    }

    public TaxListRequest created(CreatedFilter created) {
        this.created = created;
        return this;
    }

    @JsonAnyGetter
    private Map<String, Object> getOtherMap() {
        return other;
    }

    @JsonAnySetter
    public TaxListRequest set(String name, Object value) {
        other.put(name, value);
        return this;
    }
}

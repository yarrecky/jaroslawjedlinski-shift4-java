package com.shift4.response;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shift4.util.Shift4Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Product {

    private String id;
    private Long created;
    private Long updated;
    private Boolean donation;
    private String name;
    private String description;
    private Object amount;
    private String currency;
    private String plan;
    private Boolean deleted;
    private Boolean active;
    private List<Tax> taxes;
    private String merchantAccountId;

    @JsonIgnore
    private final Map<String, Object> other = new HashMap<>();

    public String getId() {
        return id;
    }

    public Long getCreated() {
        return created;
    }

    public Long getUpdated() {
        return updated;
    }

    public Boolean getDonation() {
        return donation;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Object getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getPlan() {
        return plan;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public Boolean getActive() {
        return active;
    }

    public List<Tax> getTaxes() {
        return taxes;
    }

    public String getMerchantAccountId() {
        return merchantAccountId;
    }

    public String get(String name) {
        if ("merchantAccountId".equals(name)) {
            return merchantAccountId;
        }

        return Shift4Utils.toStringNullSafe(other.get(name));
    }

    @JsonAnySetter
    private void set(String name, Object value) {
        other.put(name, value);
    }
}

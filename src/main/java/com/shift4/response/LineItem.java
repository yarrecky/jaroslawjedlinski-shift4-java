package com.shift4.response;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shift4.util.Shift4Utils;

import java.util.HashMap;
import java.util.Map;

public class LineItem {

    private Product product;
    private Integer quantity;

    @JsonIgnore
    private final Map<String, Object> other = new HashMap<>();

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String get(String name) {
        return Shift4Utils.toStringNullSafe(other.get(name));
    }

    @JsonAnySetter
    private void set(String name, Object value) {
        other.put(name, value);
    }
}

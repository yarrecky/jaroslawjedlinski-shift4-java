package com.shift4.response;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shift4.util.Shift4Utils;

import java.util.HashMap;
import java.util.Map;

public class Tax {

    private String id;
    private Long created;
    private String name;
    private Integer value;
    private Boolean active;

    @JsonIgnore
    private final Map<String, Object> other = new HashMap<>();

    public String getId() {
        return id;
    }

    public Long getCreated() {
        return created;
    }

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }

    public Boolean getActive() {
        return active;
    }

    public String get(String name) {
        return Shift4Utils.toStringNullSafe(other.get(name));
    }

    @JsonAnySetter
    private void set(String name, Object value) {
        other.put(name, value);
    }
}

package com.shift4.response;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shift4.enums.CustomFieldPlacement;
import com.shift4.util.Shift4Utils;

import java.util.HashMap;
import java.util.Map;

public class CheckoutSessionCustomField {

    private String key;
    private String label;
    private Map<String, String> labelTranslations;
    private String value;
    private Boolean optional;
    private CustomFieldPlacement placement;

    @JsonIgnore
    private final Map<String, Object> other = new HashMap<>();

    public String getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }

    public Map<String, String> getLabelTranslations() {
        return labelTranslations;
    }

    public String getValue() {
        return value;
    }

    public Boolean getOptional() {
        return optional;
    }

    public CustomFieldPlacement getPlacement() {
        return placement;
    }

    public String get(String name) {
        return Shift4Utils.toStringNullSafe(other.get(name));
    }

    @JsonAnySetter
    private void set(String name, Object value) {
        other.put(name, value);
    }
}

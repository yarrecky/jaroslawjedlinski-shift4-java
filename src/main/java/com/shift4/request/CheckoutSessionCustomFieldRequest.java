package com.shift4.request;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.shift4.enums.CustomFieldPlacement;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a custom field request.
 */
@JsonInclude(Include.NON_NULL)
public class CheckoutSessionCustomFieldRequest {

    private String key;
    private String label;
    private Map<String, String> labelTranslations;
    private Boolean optional;
    private CustomFieldPlacement placement;

    @JsonIgnore
    private final Map<String, Object> other = new HashMap<>();

    public CheckoutSessionCustomFieldRequest() {
    }

    public String getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }

    public Map<String, String> getLabelTranslations() {
        return labelTranslations;
    }

    public Boolean getOptional() {
        return optional;
    }

    public CustomFieldPlacement getPlacement() {
        return placement;
    }

    public CheckoutSessionCustomFieldRequest key(String key) {
        this.key = key;
        return this;
    }

    public CheckoutSessionCustomFieldRequest label(String label) {
        this.label = label;
        return this;
    }

    public CheckoutSessionCustomFieldRequest labelTranslations(Map<String, String> labelTranslations) {
        this.labelTranslations = labelTranslations;
        return this;
    }

    public CheckoutSessionCustomFieldRequest optional(Boolean optional) {
        this.optional = optional;
        return this;
    }

    public CheckoutSessionCustomFieldRequest placement(CustomFieldPlacement placement) {
        this.placement = placement;
        return this;
    }

    @JsonAnyGetter
    private Map<String, Object> getOtherMap() {
        return other;
    }

    @JsonAnySetter
    public CheckoutSessionCustomFieldRequest set(String name, Object value) {
        other.put(name, value);
        return this;
    }
}

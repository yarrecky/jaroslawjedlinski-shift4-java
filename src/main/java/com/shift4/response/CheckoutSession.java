package com.shift4.response;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shift4.enums.CheckoutSessionAction;
import com.shift4.enums.CheckoutSessionStatus;
import com.shift4.util.Shift4Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutSession {

    private String id;
    private String lastCharge;
    private String paymentLink;
    private String invoice;
    private String customer;
    private String clientSecret;
    private List<LineItem> lineItems;
    private Map<String, String> metadata;
    private String locale;
    private Boolean collectBillingAddress;
    private Boolean collectShippingAddress;
    private String customFieldsTitle;
    private Map<String, String> customFieldsTitleTranslations;
    private List<CheckoutSessionCustomField> customFields;
    private List<CheckoutSessionStaticField> staticFields;
    private Boolean capture;
    private Boolean captchaRequired;
    private CheckoutSessionStatus status;
    private Boolean allowSavedCards;
    private CheckoutSessionAction action;
    private String currency;
    private String url;
    private String redirectUrl;
    private String vendorReference;

    @JsonIgnore
    private final Map<String, Object> other = new HashMap<>();

    public String getId() {
        return id;
    }

    public String getLastCharge() {
        return lastCharge;
    }

    public String getPaymentLink() {
        return paymentLink;
    }

    public String getInvoice() {
        return invoice;
    }

    public String getCustomer() {
        return customer;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public String getLocale() {
        return locale;
    }

    public Boolean getCollectBillingAddress() {
        return collectBillingAddress;
    }

    public Boolean getCollectShippingAddress() {
        return collectShippingAddress;
    }

    public String getCustomFieldsTitle() {
        return customFieldsTitle;
    }

    public Map<String, String> getCustomFieldsTitleTranslations() {
        return customFieldsTitleTranslations;
    }

    public List<CheckoutSessionCustomField> getCustomFields() {
        return customFields;
    }

    public List<CheckoutSessionStaticField> getStaticFields() {
        return staticFields;
    }

    public Boolean getCapture() {
        return capture;
    }

    public Boolean getCaptchaRequired() {
        return captchaRequired;
    }

    public CheckoutSessionStatus getStatus() {
        return status;
    }

    public Boolean getAllowSavedCards() {
        return allowSavedCards;
    }

    public CheckoutSessionAction getAction() {
        return action;
    }

    public String getCurrency() {
        return currency;
    }

    public String getUrl() {
        return url;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public String getVendorReference() {
        return vendorReference;
    }

    public String get(String name) {
        return Shift4Utils.toStringNullSafe(other.get(name));
    }

    @JsonAnySetter
    private void set(String name, Object value) {
        other.put(name, value);
    }
}

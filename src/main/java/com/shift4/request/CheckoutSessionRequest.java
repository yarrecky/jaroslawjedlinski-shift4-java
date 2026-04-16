package com.shift4.request;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.shift4.enums.CheckoutSessionAction;
import com.shift4.response.Customer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Request for creating a checkout session.
 * <p>
 * Checkout sessions power the checkout widget that merchants can embed on their websites.
 * The merchant's backend creates a session via the SDK and passes it to the frontend widget for rendering.
 */
@JsonInclude(Include.NON_NULL)
public class CheckoutSessionRequest {

    private String customer;
    private String locale;
    private List<LineItemRequest> lineItems;
    private Boolean collectBillingAddress;
    private Boolean collectShippingAddress;
    private Map<String, String> metadata;
    private String customFieldsTitle;
    private List<CheckoutSessionCustomFieldRequest> customFields;
    private List<CheckoutSessionStaticFieldRequest> staticFields;
    private Boolean capture;
    private String merchantAccountId;
    private Boolean allowSavedCards;
    private CheckoutSessionAction action;
    private String currency;
    private String redirectUrl;
    private String vendorReference;

    @JsonIgnore
    private final Map<String, Object> other = new HashMap<>();

    public CheckoutSessionRequest() {
    }

    public String getCustomer() {
        return customer;
    }

    public String getLocale() {
        return locale;
    }

    public List<LineItemRequest> getLineItems() {
        return lineItems;
    }

    public Boolean getCollectBillingAddress() {
        return collectBillingAddress;
    }

    public Boolean getCollectShippingAddress() {
        return collectShippingAddress;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public String getCustomFieldsTitle() {
        return customFieldsTitle;
    }

    public List<CheckoutSessionCustomFieldRequest> getCustomFields() {
        return customFields;
    }

    public List<CheckoutSessionStaticFieldRequest> getStaticFields() {
        return staticFields;
    }

    public Boolean getCapture() {
        return capture;
    }

    public String getMerchantAccountId() {
        return merchantAccountId;
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

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public String getVendorReference() {
        return vendorReference;
    }

    public CheckoutSessionRequest customer(String customer) {
        this.customer = customer;
        return this;
    }

    public CheckoutSessionRequest customer(Customer customer) {
        return customer(customer.getId());
    }

    public CheckoutSessionRequest locale(String locale) {
        this.locale = locale;
        return this;
    }

    public CheckoutSessionRequest lineItems(List<LineItemRequest> lineItems) {
        this.lineItems = lineItems;
        return this;
    }

    public CheckoutSessionRequest collectBillingAddress(Boolean collectBillingAddress) {
        this.collectBillingAddress = collectBillingAddress;
        return this;
    }

    public CheckoutSessionRequest collectShippingAddress(Boolean collectShippingAddress) {
        this.collectShippingAddress = collectShippingAddress;
        return this;
    }

    public CheckoutSessionRequest metadata(Map<String, String> metadata) {
        this.metadata = metadata;
        return this;
    }

    public CheckoutSessionRequest customFieldsTitle(String customFieldsTitle) {
        this.customFieldsTitle = customFieldsTitle;
        return this;
    }

    public CheckoutSessionRequest customFields(List<CheckoutSessionCustomFieldRequest> customFields) {
        this.customFields = customFields;
        return this;
    }

    public CheckoutSessionRequest staticFields(List<CheckoutSessionStaticFieldRequest> staticFields) {
        this.staticFields = staticFields;
        return this;
    }

    public CheckoutSessionRequest capture(Boolean capture) {
        this.capture = capture;
        return this;
    }

    public CheckoutSessionRequest merchantAccountId(String merchantAccountId) {
        this.merchantAccountId = merchantAccountId;
        return this;
    }

    public CheckoutSessionRequest allowSavedCards(Boolean allowSavedCards) {
        this.allowSavedCards = allowSavedCards;
        return this;
    }

    public CheckoutSessionRequest action(CheckoutSessionAction action) {
        this.action = action;
        return this;
    }

    public CheckoutSessionRequest currency(String currency) {
        this.currency = currency;
        return this;
    }

    public CheckoutSessionRequest redirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
        return this;
    }

    public CheckoutSessionRequest vendorReference(String vendorReference) {
        this.vendorReference = vendorReference;
        return this;
    }

    @JsonAnyGetter
    private Map<String, Object> getOtherMap() {
        return other;
    }

    @JsonAnySetter
    public CheckoutSessionRequest set(String name, Object value) {
        other.put(name, value);
        return this;
    }
}

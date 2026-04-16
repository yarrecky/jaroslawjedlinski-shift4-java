package com.shift4.testdata;


import com.shift4.request.AmountRequest;
import com.shift4.request.CheckoutSessionCustomFieldRequest;
import com.shift4.request.CheckoutSessionRequest;
import com.shift4.request.CheckoutSessionStaticFieldRequest;
import com.shift4.request.LineItemRequest;
import com.shift4.request.ProductRequest;

import java.util.Collections;
import java.util.List;

public abstract class CheckoutSessions {

    private CheckoutSessions() {
    }

    public static CheckoutSessionRequest checkoutSession() {
        return new CheckoutSessionRequest()
                .lineItems(Collections.singletonList(lineItem(simpleProduct())));
    }

    public static LineItemRequest lineItem(ProductRequest product) {
        return new LineItemRequest(product, 1);
    }

    public static ProductRequest simpleProduct() {
        return new ProductRequest()
                .name("Test Product")
                .amount(1000)
                .currency("USD");
    }

    public static CheckoutSessionCustomFieldRequest customField() {
        return new CheckoutSessionCustomFieldRequest()
                .key("company")
                .label("Company")
                .optional(false);
    }

    public static CheckoutSessionStaticFieldRequest staticField() {
        return new CheckoutSessionStaticFieldRequest()
                .key("order_id")
                .value("ORD-12345");
    }

    public static CheckoutSessionStaticFieldRequest staticField(String key, String value) {
        return new CheckoutSessionStaticFieldRequest()
                .key(key)
                .value(value);
    }

    public static ProductRequest subscriptionProduct(String planId) {
        return new ProductRequest(planId);
    }

    public static ProductRequest donationProductWithOptions(List<Integer> options) {
        return new ProductRequest()
                .name("Donation")
                .amount(new AmountRequest().options(options))
                .currency("USD");
    }

    public static ProductRequest donationProductWithCustomRange(Integer min, Integer max) {
        return new ProductRequest()
                .name("Donation")
                .amount(new AmountRequest().custom(min, max))
                .currency("USD");
    }
}

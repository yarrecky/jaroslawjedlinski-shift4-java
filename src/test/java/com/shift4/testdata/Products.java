package com.shift4.testdata;

import com.shift4.request.AmountRequest;
import com.shift4.request.ProductRequest;

public abstract class Products {

    private Products() {
    }

    public static ProductRequest product() {
        return new ProductRequest("Test Product", "USD")
                .amount(new AmountRequest(1000))
                .description("Test product description")
                .active(true);
    }

    public static ProductRequest product(String name) {
        return product().name(name);
    }

    public static ProductRequest donationProduct() {
        return new ProductRequest("Donation", "USD")
                .donation(true);
    }
}

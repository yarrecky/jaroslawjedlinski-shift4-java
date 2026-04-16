package com.shift4.testdata;

import com.shift4.request.TaxRequest;

import java.math.BigDecimal;

public abstract class Taxes {

    private Taxes() {
    }

    public static TaxRequest tax() {
        return new TaxRequest()
                .name("VAT")
                .value(new BigDecimal("23.00"));
    }

    public static TaxRequest tax(String name, String value) {
        return new TaxRequest()
                .name(name)
                .value(new BigDecimal(value));
    }
}

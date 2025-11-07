package com.shift4.request;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExternalNetworkTokenRequest {
    private String token;
    private String eci;
    private String cryptogram;
    private String expiryMonth;
    private String expiryYear;

    @JsonIgnore
    private final Map<String, Object> other = new HashMap<>();
    
    public ExternalNetworkTokenRequest() {
    }

    public String getToken() {
        return token;
    }

    public String getEci() {
        return eci;
    }

    public String getCryptogram() {
        return cryptogram;
    }

    public String getExpiryMonth() {
        return expiryMonth;
    }

    public String getExpiryYear() {
        return expiryYear;
    }

    public ExternalNetworkTokenRequest token(String token) {
        this.token = token;
        return this;
    }

    public ExternalNetworkTokenRequest eci(String eci) {
        this.eci = eci;
        return this;
    }

    public ExternalNetworkTokenRequest cryptogram(String cryptogram) {
        this.cryptogram = cryptogram;
        return this;
    }

    public ExternalNetworkTokenRequest expiryMonth(String expiryMonth) {
        this.expiryMonth = expiryMonth;
        return this;
    }

    public ExternalNetworkTokenRequest expiryYear(String expiryYear) {
        this.expiryYear = expiryYear;
        return this;
    }

    @JsonAnyGetter
    private Map<String, Object> getOtherMap() {
        return other;
    }

    @JsonAnySetter
    public ExternalNetworkTokenRequest set(String name, Object value) {
        other.put(name, value);
        return this;
    }
}

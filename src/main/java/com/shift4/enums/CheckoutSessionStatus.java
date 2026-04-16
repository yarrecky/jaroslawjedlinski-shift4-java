package com.shift4.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CheckoutSessionStatus {

	ACTIVE("active"),
	EXPIRED("expired"),
	FAILED("failed"),
	PAID("paid"),

	/**
	 * Used when received value can't be mapped to this enumeration.
	 */
	UNRECOGNIZED("unrecognized");

	private final String value;

	CheckoutSessionStatus(String value) {
		this.value = value;
	}

	@JsonCreator
	public static CheckoutSessionStatus fromValue(String value) {
		if (value == null) {
			return null;
		}
		for (CheckoutSessionStatus checkoutSessionStatus : values()) {
			if (checkoutSessionStatus.value.equalsIgnoreCase(value)) {
				return checkoutSessionStatus;
			}
		}

		return UNRECOGNIZED;
	}

	@JsonValue
	public String getValue() {
		return value;
	}
}

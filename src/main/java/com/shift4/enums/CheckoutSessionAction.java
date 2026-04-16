package com.shift4.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CheckoutSessionAction {

	CARD_VERIFICATION("card_verification"),
	PAYMENT("payment"),

	/**
	 * Used when received value can't be mapped to this enumeration.
	 */
	UNRECOGNIZED("unrecognized");

	private final String value;

	CheckoutSessionAction(String value) {
		this.value = value;
	}

	@JsonCreator
	public static CheckoutSessionAction fromValue(String value) {
		if (value == null) {
			return null;
		}
		for (CheckoutSessionAction checkoutSessionAction : values()) {
			if (checkoutSessionAction.value.equalsIgnoreCase(value)) {
				return checkoutSessionAction;
			}
		}

		return UNRECOGNIZED;
	}

	@JsonValue
	public String getValue() {
		return value;
	}
}

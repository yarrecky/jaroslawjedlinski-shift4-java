package com.shift4.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.shift4.request.AmountRequest;

import java.io.IOException;

/**
 * Custom serializer for Amount objects.
 * <p>
 * Serializes simple amounts (with only value set) as plain integers,
 * and donation amounts (with options/custom) as objects.
 */
public class AmountSerializer extends JsonSerializer<AmountRequest> {

    @Override
    public void serialize(AmountRequest amount, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (amount == null) {
            gen.writeNull();
            return;
        }

        // For simple amounts (only value is set), serialize as plain integer
        if (amount.getValue() != null && amount.getOptions() == null && amount.getCustom() == null) {
            gen.writeNumber(amount.getValue());
        }
        // For donations (options or custom are set), serialize as object
        else if (amount.getOptions() != null || amount.getCustom() != null) {
            gen.writeStartObject();

            if (amount.getOptions() != null) {
                gen.writeObjectField("options", amount.getOptions());
            }

            if (amount.getCustom() != null) {
                gen.writeObjectField("custom", amount.getCustom());
            }

            gen.writeEndObject();
        }
        // Edge case: empty amount object (should not happen in practice)
        else {
            gen.writeNull();
        }
    }
}

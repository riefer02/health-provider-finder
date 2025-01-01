package com.anisehealth.matching.model;

import lombok.Data;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;
import java.util.List;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.IOException;

@Data
public class PatientRequest {
    @NotEmpty(message = "At least one area of concern is required")
    private List<String> areasOfConcern;

    private List<String> treatmentModality;

    @NotNull(message = "Demographics information is required")
    @Valid
    private Demographics demographics;

    private TherapistPreferences therapistPreferences;

    @NotNull(message = "Location is required")
    private String location;

    @NotNull(message = "Payment method is required")
    @JsonDeserialize(using = PaymentMethodDeserializer.class)
    private PaymentMethod paymentMethod;

    @JsonDeserialize(using = InsuranceProviderDeserializer.class)
    private InsuranceProvider insuranceProvider;

    @Data
    public static class Demographics {
        @NotNull(message = "Patient ethnicity is required")
        private String ethnicity;
        
        @NotNull(message = "Patient gender is required")
        private String gender;
        
        @NotNull(message = "Patient religion is required")
        private String religion;
        
        @NotNull(message = "Patient marital status is required")
        private String maritalStatus;
    }

    @Data
    public static class TherapistPreferences {
        private String preferredGender;
        private String preferredEthnicity;
        private String preferredReligion;
        private String preferredLanguage;
    }

    @JsonDeserialize(using = PaymentMethodDeserializer.class)
    public enum PaymentMethod {
        INSURANCE,
        SELF_PAY;

        public static PaymentMethod fromString(String value) {
            if (value == null) return null;
            return switch (value.toLowerCase()) {
                case "insurance" -> INSURANCE;
                case "self-pay" -> SELF_PAY;
                default -> throw new IllegalArgumentException("Invalid payment method: " + value);
            };
        }

        @Override
        public String toString() {
            return switch (this) {
                case INSURANCE -> "Insurance";
                case SELF_PAY -> "Self-pay";
            };
        }
    }

    public static class PaymentMethodDeserializer extends JsonDeserializer<PaymentMethod> {
        @Override
        public PaymentMethod deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            String value = p.getValueAsString();
            try {
                return PaymentMethod.fromString(value);
            } catch (IllegalArgumentException e) {
                throw new JsonMappingException(p, "Invalid payment method: " + value);
            }
        }
    }

    @JsonDeserialize(using = InsuranceProviderDeserializer.class)
    public enum InsuranceProvider {
        AETNA,
        MAGELLAN,
        ANTHEM,
        OTHER;

        public static InsuranceProvider fromString(String value) {
            if (value == null) return null;
            return switch (value.toLowerCase()) {
                case "aetna" -> AETNA;
                case "magellan" -> MAGELLAN;
                case "anthem" -> ANTHEM;
                case "other" -> OTHER;
                default -> throw new IllegalArgumentException("Invalid insurance provider: " + value);
            };
        }

        @Override
        public String toString() {
            return switch (this) {
                case AETNA -> "Aetna";
                case MAGELLAN -> "Magellan";
                case ANTHEM -> "Anthem";
                case OTHER -> "Other";
            };
        }
    }

    public static class InsuranceProviderDeserializer extends JsonDeserializer<InsuranceProvider> {
        @Override
        public InsuranceProvider deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            String value = p.getValueAsString();
            try {
                return InsuranceProvider.fromString(value);
            } catch (IllegalArgumentException e) {
                throw new JsonMappingException(p, "Invalid insurance provider: " + value);
            }
        }
    }
} 
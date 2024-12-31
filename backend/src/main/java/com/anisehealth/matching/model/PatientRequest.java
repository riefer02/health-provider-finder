package com.anisehealth.matching.model;

import lombok.Data;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;
import java.util.List;

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
    private PaymentMethod paymentMethod;

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
} 
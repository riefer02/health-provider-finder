package com.anisehealth.matching.model;

import lombok.Data;
import com.opencsv.bean.CsvBindByName;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Provider {
    @CsvBindByName(column = "First Name")
    private String firstName;

    @CsvBindByName(column = "Last Name")
    private String lastName;

    @CsvBindByName(column = "Ethnic Identity")
    private String ethnicIdentity;

    @CsvBindByName(column = "Gender Identity")
    private String genderIdentity;

    @CsvBindByName(column = "No Of Clients Able To Take On")
    private Integer availableCapacity;

    @CsvBindByName(column = "Language")
    private String language;

    @CsvBindByName(column = "Location")
    private String location;

    @CsvBindByName(column = "Bio")
    private String bio;

    @CsvBindByName(column = "Sexual Orientation")
    private String sexualOrientation;

    @CsvBindByName(column = "Religious Background")
    private String religiousBackground;

    @CsvBindByName(column = "Treatment Modality")
    private String treatmentModality;

    @CsvBindByName(column = "Areas of Specialization")
    private String areasOfSpecialization;

    @CsvBindByName(column = "Accepted Payment Methods")
    private String acceptedPaymentMethods;

    @CsvBindByName(column = "Accepted Insurance Providers")
    private String acceptedInsuranceProviders;

    // Converted fields for easier processing
    private List<String> languages;
    private List<String> treatmentModalities;
    private List<String> specializationAreas;
    private List<PatientRequest.PaymentMethod> paymentMethods;
    private List<PatientRequest.InsuranceProvider> insuranceProviders;

    private String normalizeModality(String modality) {
        String normalized = modality.trim();
        
        // Handle common abbreviations
        if (normalized.equalsIgnoreCase("CBT") || 
            normalized.contains("Cognitive Behavioral") || 
            normalized.contains("Cognitive-Behavioral")) {
            return "CBT";
        }
        if (normalized.equalsIgnoreCase("DBT") || 
            normalized.contains("Dialectical Behavioral") || 
            normalized.contains("Dialectical-Behavioral")) {
            return "DBT";
        }
        if (normalized.equalsIgnoreCase("MBCT") || 
            normalized.contains("Mindfulness-Based") || 
            normalized.contains("Mindfulness Based")) {
            return "MBCT";
        }
        if (normalized.equalsIgnoreCase("ACT") || 
            normalized.contains("Acceptance and Commitment")) {
            return "ACT";
        }
        if (normalized.equalsIgnoreCase("MI") || 
            normalized.contains("Motivational Interviewing")) {
            return "MI";
        }
        
        // Remove parentheses and extra spaces
        return normalized.replaceAll("\\s*\\([^)]*\\)\\s*", "").trim();
    }

    private String normalizeArea(String area) {
        String normalized = area.trim();
        
        // Handle common variations
        if (normalized.contains("Anxiety")) return "Anxiety";
        if (normalized.contains("Depression")) return "Depression";
        if (normalized.contains("Panic")) return "Panic attacks";
        if (normalized.contains("Worry")) return "Worry";
        if (normalized.contains("self-esteem") || normalized.contains("self esteem")) return "Low self-esteem";
        if (normalized.contains("trauma")) return "Trauma-related stress";
        if (normalized.contains("LGBTQ")) return "LGBTQ+ related concerns";
        if (normalized.contains("Academic")) return "Academic stress";
        if (normalized.contains("Occupation")) return "Occupation-related stress";
        if (normalized.contains("transition")) return "Major life transitions";
        if (normalized.contains("Social fear")) return "Social fears";
        if (normalized.contains("Interpersonal")) return "Interpersonal problems";
        if (normalized.contains("Relationship")) return "Relationship difficulties";
        
        return normalized;
    }

    public void processRawFields() {
        // Process languages
        languages = language != null ? 
            Arrays.stream(language.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList()) : 
            List.of();

        // Process treatment modalities
        treatmentModalities = treatmentModality != null ? 
            Arrays.stream(treatmentModality.split("[,\\n]"))
                .map(this::normalizeModality)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList()) : 
            List.of();

        // Process specialization areas
        specializationAreas = areasOfSpecialization != null ? 
            Arrays.stream(areasOfSpecialization.split(","))
                .map(this::normalizeArea)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList()) : 
            List.of();

        // Process payment methods
        paymentMethods = acceptedPaymentMethods != null ?
            Arrays.stream(acceptedPaymentMethods.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(method -> {
                    try {
                        return PatientRequest.PaymentMethod.valueOf(method.toUpperCase().replace("-", "_"));
                    } catch (IllegalArgumentException e) {
                        // Try converting common formats
                        String normalized = method.toUpperCase()
                            .replace(" ", "_")
                            .replace("-", "_");
                        try {
                            return PatientRequest.PaymentMethod.valueOf(normalized);
                        } catch (IllegalArgumentException e2) {
                            return null;
                        }
                    }
                })
                .filter(m -> m != null)
                .collect(Collectors.toList()) :
            List.of();

        // Process insurance providers
        insuranceProviders = acceptedInsuranceProviders != null ?
            Arrays.stream(acceptedInsuranceProviders.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(provider -> {
                    try {
                        return PatientRequest.InsuranceProvider.fromString(provider);
                    } catch (IllegalArgumentException e) {
                        return null;
                    }
                })
                .filter(p -> p != null)
                .collect(Collectors.toList()) :
            List.of();
    }
} 
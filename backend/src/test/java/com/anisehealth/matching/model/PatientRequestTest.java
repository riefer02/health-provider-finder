package com.anisehealth.matching.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PatientRequestTest {
    private Validator validator;
    private PatientRequest request;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        
        // Set up a valid request
        request = new PatientRequest();
        request.setLocation("CA");
        request.setAreasOfConcern(Arrays.asList("Anxiety", "Depression"));
        request.setTreatmentModality(Arrays.asList("CBT", "Mindfulness"));
        request.setPaymentMethod(PatientRequest.PaymentMethod.SELF_PAY);

        PatientRequest.Demographics demographics = new PatientRequest.Demographics();
        demographics.setEthnicity("Asian");
        demographics.setGender("Female");
        demographics.setReligion("Buddhist");
        demographics.setMaritalStatus("Single");
        request.setDemographics(demographics);

        PatientRequest.TherapistPreferences preferences = new PatientRequest.TherapistPreferences();
        preferences.setPreferredGender("Any");
        preferences.setPreferredEthnicity("Any");
        preferences.setPreferredReligion("Any");
        preferences.setPreferredLanguage("English");
        request.setTherapistPreferences(preferences);
    }

    @Test
    void whenAllFieldsValid_NoValidationViolations() {
        Set<ConstraintViolation<PatientRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void whenAreasOfConcernEmpty_ValidationFails() {
        request.setAreasOfConcern(null);
        Set<ConstraintViolation<PatientRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertEquals("At least one area of concern is required", 
            violations.iterator().next().getMessage());
    }

    @Test
    void whenLocationNull_ValidationFails() {
        request.setLocation(null);
        Set<ConstraintViolation<PatientRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertEquals("Location is required", 
            violations.iterator().next().getMessage());
    }

    @Test
    void whenPaymentMethodNull_ValidationFails() {
        request.setPaymentMethod(null);
        Set<ConstraintViolation<PatientRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertEquals("Payment method is required", 
            violations.iterator().next().getMessage());
    }

    @Test
    void whenDemographicsNull_ValidationFails() {
        request.setDemographics(null);
        Set<ConstraintViolation<PatientRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertEquals("Demographics information is required", 
            violations.iterator().next().getMessage());
    }

    @Test
    void whenDemographicsEthnicityNull_ValidationFails() {
        request.getDemographics().setEthnicity(null);
        Set<ConstraintViolation<PatientRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
            .anyMatch(v -> v.getMessage().equals("Patient ethnicity is required")));
    }

    @Test
    void whenDemographicsGenderNull_ValidationFails() {
        request.getDemographics().setGender(null);
        Set<ConstraintViolation<PatientRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
            .anyMatch(v -> v.getMessage().equals("Patient gender is required")));
    }

    @Test
    void whenDemographicsReligionNull_ValidationFails() {
        request.getDemographics().setReligion(null);
        Set<ConstraintViolation<PatientRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
            .anyMatch(v -> v.getMessage().equals("Patient religion is required")));
    }

    @Test
    void whenDemographicsMaritalStatusNull_ValidationFails() {
        request.getDemographics().setMaritalStatus(null);
        Set<ConstraintViolation<PatientRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
            .anyMatch(v -> v.getMessage().equals("Patient marital status is required")));
    }

    @Test
    void whenTherapistPreferencesNull_ValidationPasses() {
        request.setTherapistPreferences(null);
        Set<ConstraintViolation<PatientRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void whenTreatmentModalityNull_ValidationPasses() {
        request.setTreatmentModality(null);
        Set<ConstraintViolation<PatientRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void whenPaymentMethodInsuranceAndProviderNull_ValidationPasses() {
        request.setPaymentMethod(PatientRequest.PaymentMethod.INSURANCE);
        request.setInsuranceProvider(null);
        Set<ConstraintViolation<PatientRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }
} 
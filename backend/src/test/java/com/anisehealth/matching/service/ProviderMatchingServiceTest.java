package com.anisehealth.matching.service;

import com.anisehealth.matching.model.PatientRequest;
import com.anisehealth.matching.model.Provider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProviderMatchingServiceTest {
    private ProviderMatchingService service;
    private Provider provider;
    private PatientRequest request;

    @BeforeEach
    void setUp() {
        service = new ProviderMatchingService();
        
        // Set up a basic provider with all required fields
        provider = new Provider();
        provider.setFirstName("John");
        provider.setLastName("Doe");
        provider.setLocation("New York");
        provider.setAvailableCapacity(5);
        provider.setEthnicIdentity("East Asian");
        provider.setGenderIdentity("Male");
        provider.setLanguage("English,Mandarin");
        provider.setTreatmentModality("CBT,DBT");
        provider.setAreasOfSpecialization("Anxiety,Depression");
        provider.setReligiousBackground("Buddhist");
        provider.setBio("Test bio");
        provider.setAcceptedPaymentMethods("INSURANCE,SELF_PAY");
        provider.setAcceptedInsuranceProviders("AETNA,MAGELLAN");
        provider.processRawFields();

        // Debug: Print provider fields
        System.out.println("Provider fields after setup:");
        System.out.println("Languages: " + provider.getLanguages());
        System.out.println("Treatment Modalities: " + provider.getTreatmentModalities());
        System.out.println("Specialization Areas: " + provider.getSpecializationAreas());

        // Verify that the provider is complete
        assertTrue(provider.getLanguages() != null && !provider.getLanguages().isEmpty(), "Languages should not be empty");
        assertTrue(provider.getTreatmentModalities() != null && !provider.getTreatmentModalities().isEmpty(), "Treatment modalities should not be empty");
        assertTrue(provider.getSpecializationAreas() != null && !provider.getSpecializationAreas().isEmpty(), "Specialization areas should not be empty");

        // Set up a basic request
        request = new PatientRequest();
        request.setLocation("New York");
        request.setAreasOfConcern(List.of("Anxiety"));
        request.setTreatmentModality(List.of("CBT"));
        request.setPaymentMethod(PatientRequest.PaymentMethod.INSURANCE);
        request.setInsuranceProvider(PatientRequest.InsuranceProvider.AETNA);

        PatientRequest.Demographics demographics = new PatientRequest.Demographics();
        demographics.setEthnicity("East Asian");
        demographics.setGender("Male");
        demographics.setReligion("Buddhist");
        demographics.setMaritalStatus("Single");
        request.setDemographics(demographics);

        PatientRequest.TherapistPreferences preferences = new PatientRequest.TherapistPreferences();
        preferences.setPreferredGender("Any");
        preferences.setPreferredEthnicity("Any");
        preferences.setPreferredReligion("Any");
        preferences.setPreferredLanguage("English");
        request.setTherapistPreferences(preferences);

        // Initialize service with our test provider
        ReflectionTestUtils.setField(service, "providers", List.of(provider));
    }

    @Test
    void whenAllCriteriaMatch_returnsProvider() {
        List<Provider> matches = service.findMatches(request);
        System.out.println("All criteria match - matches found: " + matches.size());
        assertTrue(!matches.isEmpty(), "Should find matches when all criteria match");
        assertEquals(provider, matches.get(0));
    }

    @Test
    void whenLocationDoesNotMatch_returnsNoProvider() {
        request.setLocation("Los Angeles");
        List<Provider> matches = service.findMatches(request);
        assertTrue(matches.isEmpty(), "Should not find matches when location doesn't match");
    }

    @Test
    void whenAreasOfConcernMatch_returnsProvider() {
        request.setAreasOfConcern(List.of("Depression"));
        List<Provider> matches = service.findMatches(request);
        System.out.println("Areas of concern match - matches found: " + matches.size());
        System.out.println("Provider areas: " + provider.getSpecializationAreas());
        System.out.println("Requested areas: " + request.getAreasOfConcern());
        assertTrue(!matches.isEmpty(), "Should find matches when areas of concern match");
        assertEquals(provider, matches.get(0));
    }

    @Test
    void whenTreatmentModalityMatches_returnsProvider() {
        request.setTreatmentModality(List.of("DBT"));
        List<Provider> matches = service.findMatches(request);
        System.out.println("Treatment modality match - matches found: " + matches.size());
        System.out.println("Provider modalities: " + provider.getTreatmentModalities());
        System.out.println("Requested modalities: " + request.getTreatmentModality());
        assertTrue(!matches.isEmpty(), "Should find matches when treatment modality matches");
        assertEquals(provider, matches.get(0));
    }

    @Test
    void whenLanguagePreferenceMatches_returnsProvider() {
        request.getTherapistPreferences().setPreferredLanguage("Mandarin");
        List<Provider> matches = service.findMatches(request);
        System.out.println("Language preference match - matches found: " + matches.size());
        System.out.println("Provider languages: " + provider.getLanguages());
        System.out.println("Requested language: " + request.getTherapistPreferences().getPreferredLanguage());
        assertTrue(!matches.isEmpty(), "Should find matches when language preference matches");
        assertEquals(provider, matches.get(0));
    }
} 
package com.anisehealth.matching.service;

import com.anisehealth.matching.model.PatientRequest;
import com.anisehealth.matching.model.Provider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProviderMatchingServiceTest {
    private ProviderMatchingService service;

    @BeforeEach
    void setUp() {
        service = new ProviderMatchingService();
        
        // Create test providers
        Provider provider1 = new Provider();
        provider1.setFirstName("John");
        provider1.setLastName("Doe");
        provider1.setEthnicIdentity("East Asian");
        provider1.setGenderIdentity("Male");
        provider1.setLocation("CA");
        provider1.setBio("Experienced therapist");
        provider1.setAvailableCapacity(3);
        provider1.setLanguage("English, Mandarin");
        provider1.setTreatmentModality("CBT, Mindfulness");
        provider1.setAreasOfSpecialization("Anxiety, Depression");
        provider1.processRawFields();

        Provider provider2 = new Provider();
        provider2.setFirstName("Jane");
        provider2.setLastName("Smith");
        provider2.setEthnicIdentity("South Asian");
        provider2.setGenderIdentity("Female");
        provider2.setLocation("NY");
        provider2.setBio("Compassionate counselor");
        provider2.setAvailableCapacity(2);
        provider2.setLanguage("English, Hindi");
        provider2.setTreatmentModality("Psychodynamic, DBT");
        provider2.setAreasOfSpecialization("Trauma, Relationships");
        provider2.processRawFields();

        Provider provider3 = new Provider();
        provider3.setFirstName("Maria");
        provider3.setLastName("Garcia");
        provider3.setEthnicIdentity("Hispanic");
        provider3.setGenderIdentity("Female");
        provider3.setLocation("CA");
        provider3.setBio("Bilingual therapist");
        provider3.setAvailableCapacity(5);
        provider3.setLanguage("English, Spanish");
        provider3.setTreatmentModality("CBT, Family Therapy");
        provider3.setAreasOfSpecialization("Anxiety, Family Issues");
        provider3.processRawFields();

        // Set the providers list using reflection
        ReflectionTestUtils.setField(service, "providers", Arrays.asList(provider1, provider2, provider3));
    }

    @Test
    void findMatches_ShouldReturnMatchingProviders_WhenLocationMatches() {
        PatientRequest request = new PatientRequest();
        request.setLocation("CA");
        request.setAreasOfConcern(List.of("Anxiety"));
        
        List<Provider> matches = service.findMatches(request);
        
        assertFalse(matches.isEmpty());
        assertTrue(matches.stream().allMatch(p -> p.getLocation().contains("CA")));
    }

    @Test
    void findMatches_ShouldReturnMatchingProviders_WhenAreasOfConcernMatch() {
        PatientRequest request = new PatientRequest();
        request.setLocation("CA");
        request.setAreasOfConcern(List.of("Anxiety"));
        
        List<Provider> matches = service.findMatches(request);
        
        assertFalse(matches.isEmpty());
        assertTrue(matches.stream().anyMatch(p -> 
            p.getSpecializationAreas().stream().anyMatch(area -> 
                area.toLowerCase().contains("anxiety"))));
    }

    @Test
    void findMatches_ShouldReturnMatchingProviders_WhenTreatmentModalityMatches() {
        PatientRequest request = new PatientRequest();
        request.setLocation("CA");
        request.setAreasOfConcern(List.of("Anxiety"));
        request.setTreatmentModality(List.of("CBT"));
        
        List<Provider> matches = service.findMatches(request);
        
        assertFalse(matches.isEmpty());
        assertTrue(matches.stream().anyMatch(p -> 
            p.getTreatmentModalities().stream().anyMatch(modality -> 
                modality.toLowerCase().contains("cbt"))));
    }

    @Test
    void findMatches_ShouldReturnMatchingProviders_WhenTherapistPreferencesMatch() {
        PatientRequest request = new PatientRequest();
        request.setLocation("CA");
        request.setAreasOfConcern(List.of("Anxiety"));
        
        PatientRequest.TherapistPreferences preferences = new PatientRequest.TherapistPreferences();
        preferences.setPreferredGender("Female");
        preferences.setPreferredLanguage("Spanish");
        request.setTherapistPreferences(preferences);
        
        List<Provider> matches = service.findMatches(request);
        
        assertFalse(matches.isEmpty());
        assertTrue(matches.stream().anyMatch(p -> 
            p.getGenderIdentity().toLowerCase().contains("female") &&
            p.getLanguages().stream().anyMatch(lang -> 
                lang.toLowerCase().contains("spanish"))));
    }

    @Test
    void findMatches_ShouldLimitResults_ToThreeProviders() {
        PatientRequest request = new PatientRequest();
        request.setLocation("CA");
        request.setAreasOfConcern(List.of("Anxiety"));
        
        List<Provider> matches = service.findMatches(request);
        
        assertTrue(matches.size() <= 3);
    }

    @Test
    void findMatches_ShouldReturnEmptyList_WhenNoMatchesFound() {
        PatientRequest request = new PatientRequest();
        request.setLocation("TX");
        request.setAreasOfConcern(List.of("Anxiety"));
        
        List<Provider> matches = service.findMatches(request);
        
        assertTrue(matches.isEmpty());
    }

    @Test
    void findMatches_ShouldOnlyReturnProviders_WithAvailableCapacity() {
        PatientRequest request = new PatientRequest();
        request.setLocation("CA");
        request.setAreasOfConcern(List.of("Anxiety"));
        
        List<Provider> matches = service.findMatches(request);
        
        assertTrue(matches.stream().allMatch(p -> p.getAvailableCapacity() > 0));
    }
} 
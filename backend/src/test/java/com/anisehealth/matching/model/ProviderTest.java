package com.anisehealth.matching.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProviderTest {
    private Provider provider;

    @BeforeEach
    void setUp() {
        provider = new Provider();
        provider.setFirstName("John");
        provider.setLastName("Doe");
        provider.setEthnicIdentity("Asian");
        provider.setGenderIdentity("Male");
        provider.setLocation("CA");
        provider.setBio("Experienced therapist");
        provider.setAvailableCapacity(3);
    }

    @Test
    void processRawFields_ShouldHandleLanguages_WithCommas() {
        provider.setLanguage("English, Mandarin, Spanish");
        provider.processRawFields();

        assertEquals(3, provider.getLanguages().size());
        assertTrue(provider.getLanguages().contains("English"));
        assertTrue(provider.getLanguages().contains("Mandarin"));
        assertTrue(provider.getLanguages().contains("Spanish"));
    }

    @Test
    void processRawFields_ShouldHandleLanguages_WithEmptyString() {
        provider.setLanguage("");
        provider.processRawFields();

        assertNotNull(provider.getLanguages());
        assertTrue(provider.getLanguages().isEmpty());
    }

    @Test
    void processRawFields_ShouldHandleLanguages_WithNull() {
        provider.setLanguage(null);
        provider.processRawFields();

        assertNotNull(provider.getLanguages());
        assertTrue(provider.getLanguages().isEmpty());
    }

    @Test
    void processRawFields_ShouldHandleTreatmentModalities_WithCommasAndNewlines() {
        provider.setTreatmentModality("CBT, Mindfulness-Based (MBCT)\nPsychodynamic, DBT");
        provider.processRawFields();

        assertEquals(4, provider.getTreatmentModalities().size());
        assertTrue(provider.getTreatmentModalities().contains("CBT"));
        assertTrue(provider.getTreatmentModalities().contains("MBCT"));
        assertTrue(provider.getTreatmentModalities().contains("Psychodynamic"));
        assertTrue(provider.getTreatmentModalities().contains("DBT"));
    }

    @Test
    void processRawFields_ShouldHandleTreatmentModalities_WithEmptyString() {
        provider.setTreatmentModality("");
        provider.processRawFields();

        assertNotNull(provider.getTreatmentModalities());
        assertTrue(provider.getTreatmentModalities().isEmpty());
    }

    @Test
    void processRawFields_ShouldHandleTreatmentModalities_WithNull() {
        provider.setTreatmentModality(null);
        provider.processRawFields();

        assertNotNull(provider.getTreatmentModalities());
        assertTrue(provider.getTreatmentModalities().isEmpty());
    }

    @Test
    void processRawFields_ShouldHandleSpecializationAreas_WithCommas() {
        provider.setAreasOfSpecialization("Anxiety, Depression, Trauma-related stress");
        provider.processRawFields();

        assertEquals(3, provider.getSpecializationAreas().size());
        assertTrue(provider.getSpecializationAreas().contains("Anxiety"));
        assertTrue(provider.getSpecializationAreas().contains("Depression"));
        assertTrue(provider.getSpecializationAreas().contains("Trauma-related stress"));
    }

    @Test
    void processRawFields_ShouldHandleSpecializationAreas_WithEmptyString() {
        provider.setAreasOfSpecialization("");
        provider.processRawFields();

        assertNotNull(provider.getSpecializationAreas());
        assertTrue(provider.getSpecializationAreas().isEmpty());
    }

    @Test
    void processRawFields_ShouldHandleSpecializationAreas_WithNull() {
        provider.setAreasOfSpecialization(null);
        provider.processRawFields();

        assertNotNull(provider.getSpecializationAreas());
        assertTrue(provider.getSpecializationAreas().isEmpty());
    }

    @Test
    void processRawFields_ShouldTrimWhitespace_FromAllFields() {
        provider.setLanguage(" English , Mandarin ");
        provider.setTreatmentModality(" CBT , DBT ");
        provider.setAreasOfSpecialization(" Anxiety , Depression ");
        provider.processRawFields();

        assertTrue(provider.getLanguages().contains("English"));
        assertTrue(provider.getLanguages().contains("Mandarin"));
        assertTrue(provider.getTreatmentModalities().contains("CBT"));
        assertTrue(provider.getTreatmentModalities().contains("DBT"));
        assertTrue(provider.getSpecializationAreas().contains("Anxiety"));
        assertTrue(provider.getSpecializationAreas().contains("Depression"));
    }

    @Test
    void processRawFields_ShouldRemoveEmptyEntries() {
        provider.setLanguage("English,,Mandarin,");
        provider.setTreatmentModality("CBT,,DBT,");
        provider.setAreasOfSpecialization("Anxiety,,Depression,");
        provider.processRawFields();

        assertEquals(2, provider.getLanguages().size());
        assertEquals(2, provider.getTreatmentModalities().size());
        assertEquals(2, provider.getSpecializationAreas().size());
    }
} 
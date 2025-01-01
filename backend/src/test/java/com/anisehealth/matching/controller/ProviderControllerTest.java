package com.anisehealth.matching.controller;

import com.anisehealth.matching.model.PatientRequest;
import com.anisehealth.matching.model.Provider;
import com.anisehealth.matching.service.ProviderMatchingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProviderController.class)
@WithMockUser
class ProviderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProviderMatchingService matchingService;

    @Autowired
    private ObjectMapper objectMapper;

    private PatientRequest validRequest;
    private Provider mockProvider;

    @BeforeEach
    void setUp() {
        // Set up a valid request
        validRequest = new PatientRequest();
        validRequest.setLocation("CA");
        validRequest.setAreasOfConcern(Arrays.asList("Anxiety", "Depression"));
        validRequest.setTreatmentModality(Arrays.asList("CBT", "Mindfulness"));

        PatientRequest.Demographics demographics = new PatientRequest.Demographics();
        demographics.setEthnicity("Asian");
        demographics.setGender("Female");
        demographics.setReligion("Buddhist");
        demographics.setMaritalStatus("Single");
        validRequest.setDemographics(demographics);

        PatientRequest.TherapistPreferences preferences = new PatientRequest.TherapistPreferences();
        preferences.setPreferredGender("Any");
        preferences.setPreferredEthnicity("Any");
        preferences.setPreferredReligion("Any");
        preferences.setPreferredLanguage("English");
        validRequest.setTherapistPreferences(preferences);

        validRequest.setPaymentMethod(PatientRequest.PaymentMethod.INSURANCE);

        // Set up a mock provider
        mockProvider = new Provider();
        mockProvider.setFirstName("John");
        mockProvider.setLastName("Doe");
        mockProvider.setEthnicIdentity("Asian");
        mockProvider.setGenderIdentity("Male");
        mockProvider.setLocation("CA");
        mockProvider.setBio("Experienced therapist");
        mockProvider.setAvailableCapacity(3);
        mockProvider.setLanguage("English, Mandarin");
        mockProvider.setTreatmentModality("CBT, Mindfulness");
        mockProvider.setAreasOfSpecialization("Anxiety, Depression");
        mockProvider.processRawFields();
    }

    @Test
    void findMatches_WithValidRequest_ReturnsOkResponse() throws Exception {
        when(matchingService.findMatches(any(PatientRequest.class)))
            .thenReturn(Collections.singletonList(mockProvider));

        mockMvc.perform(post("/api/providers/match")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].firstName").value("John"))
            .andExpect(jsonPath("$[0].lastName").value("Doe"));
    }

    @Test
    void findMatches_WithMissingRequiredFields_ReturnsBadRequest() throws Exception {
        PatientRequest invalidRequest = new PatientRequest();
        // Missing required fields

        mockMvc.perform(post("/api/providers/match")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void findMatches_WithNoMatches_ReturnsEmptyList() throws Exception {
        when(matchingService.findMatches(any(PatientRequest.class)))
            .thenReturn(Collections.emptyList());

        mockMvc.perform(post("/api/providers/match")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void findMatches_WithMultipleMatches_ReturnsAllMatches() throws Exception {
        Provider mockProvider2 = new Provider();
        mockProvider2.setFirstName("Jane");
        mockProvider2.setLastName("Smith");
        mockProvider2.setEthnicIdentity("Asian");
        mockProvider2.setGenderIdentity("Female");
        mockProvider2.setLocation("CA");
        mockProvider2.setBio("Trauma specialist");
        mockProvider2.setAvailableCapacity(2);
        mockProvider2.setLanguage("English");
        mockProvider2.setTreatmentModality("CBT");
        mockProvider2.setAreasOfSpecialization("Trauma, Anxiety");
        mockProvider2.processRawFields();

        List<Provider> mockProviders = Arrays.asList(mockProvider, mockProvider2);
        when(matchingService.findMatches(any(PatientRequest.class)))
            .thenReturn(mockProviders);

        mockMvc.perform(post("/api/providers/match")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].firstName").value("John"))
            .andExpect(jsonPath("$[1].firstName").value("Jane"));
    }

    @Test
    void findMatches_WithInvalidPaymentMethod_ReturnsBadRequest() throws Exception {
        validRequest.setPaymentMethod(null);

        mockMvc.perform(post("/api/providers/match")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void findMatches_WithInvalidJson_ReturnsBadRequest() throws Exception {
        String invalidJson = "{\"location\": \"CA\", invalid json}";

        mockMvc.perform(post("/api/providers/match")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
            .andExpect(status().isBadRequest());
    }
} 
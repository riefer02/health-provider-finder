package com.anisehealth.matching.service;

import com.anisehealth.matching.model.Provider;
import com.anisehealth.matching.model.PatientRequest;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProviderMatchingService {
    private List<Provider> providers;

    @PostConstruct
    public void init() {
        try {
            var csvResource = new ClassPathResource("mock-data.csv");
            providers = new CsvToBeanBuilder<Provider>(new InputStreamReader(csvResource.getInputStream()))
                .withType(Provider.class)
                .build()
                .parse();

            providers.forEach(Provider::processRawFields);
            providers = providers.stream()
                .filter(this::isCompleteProvider)
                .collect(Collectors.toList());
            log.info("Loaded {} valid providers from CSV", providers.size());
        } catch (Exception e) {
            log.error("Error loading provider data", e);
            providers = new ArrayList<>();
        }
    }

    private boolean isCompleteProvider(Provider provider) {
        return provider != null &&
               StringUtils.hasText(provider.getFirstName()) &&
               StringUtils.hasText(provider.getLastName()) &&
               StringUtils.hasText(provider.getEthnicIdentity()) &&
               StringUtils.hasText(provider.getGenderIdentity()) &&
               StringUtils.hasText(provider.getLocation()) &&
               StringUtils.hasText(provider.getBio()) &&
               provider.getAvailableCapacity() != null &&
               provider.getAvailableCapacity() > 0 &&
               provider.getLanguages() != null &&
               !provider.getLanguages().isEmpty() &&
               provider.getTreatmentModalities() != null &&
               !provider.getTreatmentModalities().isEmpty() &&
               provider.getSpecializationAreas() != null &&
               !provider.getSpecializationAreas().isEmpty();
    }

    public List<Provider> findMatches(PatientRequest request) {
        return providers.stream()
            .filter(provider -> hasAvailableCapacity(provider))
            .filter(provider -> matchesLocation(provider, request.getLocation()))
            .filter(provider -> matchesAreasOfConcern(provider, request.getAreasOfConcern()))
            .filter(provider -> matchesTreatmentModality(provider, request.getTreatmentModality()))
            .filter(provider -> matchesTherapistPreferences(provider, request.getTherapistPreferences()))
            .sorted(Comparator.comparingInt(this::calculateMatchScore).reversed())
            .limit(3)
            .collect(Collectors.toList());
    }

    private boolean hasAvailableCapacity(Provider provider) {
        return provider.getAvailableCapacity() != null && provider.getAvailableCapacity() > 0;
    }

    private boolean matchesLocation(Provider provider, String requestedLocation) {
        if (requestedLocation == null || provider.getLocation() == null) return false;
        return provider.getLocation().toLowerCase().contains(requestedLocation.toLowerCase());
    }

    private boolean matchesAreasOfConcern(Provider provider, List<String> requestedAreas) {
        if (requestedAreas == null || requestedAreas.isEmpty() || provider.getSpecializationAreas() == null) return false;
        return requestedAreas.stream()
            .anyMatch(requested -> provider.getSpecializationAreas().stream()
                .anyMatch(area -> area.equalsIgnoreCase(requested)));
    }

    private boolean matchesTreatmentModality(Provider provider, List<String> requestedModalities) {
        if (requestedModalities == null || requestedModalities.isEmpty() || provider.getTreatmentModalities() == null) return false;
        return requestedModalities.stream()
            .anyMatch(requested -> provider.getTreatmentModalities().stream()
                .anyMatch(modality -> modality.equalsIgnoreCase(requested)));
    }

    private boolean matchesTherapistPreferences(Provider provider, PatientRequest.TherapistPreferences preferences) {
        if (preferences == null) return true;

        boolean matchesGender = !StringUtils.hasText(preferences.getPreferredGender()) ||
            "Any".equalsIgnoreCase(preferences.getPreferredGender()) ||
            (provider.getGenderIdentity() != null &&
            provider.getGenderIdentity().equalsIgnoreCase(preferences.getPreferredGender()));

        boolean matchesEthnicity = !StringUtils.hasText(preferences.getPreferredEthnicity()) ||
            "Any".equalsIgnoreCase(preferences.getPreferredEthnicity()) ||
            (provider.getEthnicIdentity() != null &&
            provider.getEthnicIdentity().equalsIgnoreCase(preferences.getPreferredEthnicity()));

        boolean matchesReligion = !StringUtils.hasText(preferences.getPreferredReligion()) ||
            "Any".equalsIgnoreCase(preferences.getPreferredReligion()) ||
            (provider.getReligiousBackground() != null &&
            provider.getReligiousBackground().equalsIgnoreCase(preferences.getPreferredReligion()));

        boolean matchesLanguage = !StringUtils.hasText(preferences.getPreferredLanguage()) ||
            "Any".equalsIgnoreCase(preferences.getPreferredLanguage()) ||
            (provider.getLanguages() != null &&
            provider.getLanguages().stream()
                .anyMatch(lang -> lang.equalsIgnoreCase(preferences.getPreferredLanguage())));

        return matchesGender && matchesEthnicity && matchesReligion && matchesLanguage;
    }

    private int calculateMatchScore(Provider provider) {
        int score = 0;
        score += provider.getAvailableCapacity() * 2;
        score += provider.getLanguages().size();
        score += provider.getTreatmentModalities().size();
        score += provider.getSpecializationAreas().size();
        return score;
    }
} 
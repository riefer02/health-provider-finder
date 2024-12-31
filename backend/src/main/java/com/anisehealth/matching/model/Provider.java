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

    // Converted fields for easier processing
    private List<String> languages;
    private List<String> treatmentModalities;
    private List<String> specializationAreas;

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
            Arrays.stream(treatmentModality.split("\n"))  // Split by newline first
                .flatMap(line -> Arrays.stream(line.split(",")))  // Then split by comma
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList()) : 
            List.of();

        // Process specialization areas
        specializationAreas = areasOfSpecialization != null ? 
            Arrays.stream(areasOfSpecialization.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList()) : 
            List.of();
    }
} 
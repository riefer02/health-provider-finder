package com.anisehealth.matching.controller;

import com.anisehealth.matching.model.Provider;
import com.anisehealth.matching.model.PatientRequest;
import com.anisehealth.matching.service.ProviderMatchingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/providers")
@RequiredArgsConstructor
@Tag(name = "Provider Matching", description = "API endpoints for matching patients with providers")
@CrossOrigin(origins = "${app.cors.allowed-origins}")
public class ProviderController {
    private final ProviderMatchingService matchingService;

    @PostMapping("/match")
    @Operation(summary = "Find matching providers",
               description = "Returns up to 3 best matching providers based on patient preferences")
    public ResponseEntity<List<Provider>> findMatches(@Valid @RequestBody PatientRequest request) {
        var matches = matchingService.findMatches(request);
        return ResponseEntity.ok(matches);
    }
} 
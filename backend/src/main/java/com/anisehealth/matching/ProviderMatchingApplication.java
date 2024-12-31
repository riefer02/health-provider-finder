package com.anisehealth.matching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication
public class ProviderMatchingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderMatchingApplication.class, args);
    }

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
            .info(new Info()
                .title("Provider Matching API")
                .description("HIPAA-compliant API for matching patients with therapists")
                .version("1.0.0"));
    }
} 
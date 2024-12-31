# Backend Documentation - Patient-Provider Matching System

## Overview

The backend is built using Spring Boot, implementing a RESTful API for patient-provider matching. It processes patient preferences and returns matched providers based on various criteria.

## Architecture

### Core Components

1. **Controllers**

   - **ProviderController** (`/src/main/java/com/anisehealth/matching/controller/`)
     - Handles HTTP requests
     - Endpoints:
       - POST `/api/providers/match`: Processes patient requests and returns matched providers
     - Implements CORS configuration
     - Handles request validation

2. **Services**

   - **ProviderMatchingService** (`/src/main/java/com/anisehealth/matching/service/`)
     - Core business logic for provider matching
     - Implements matching algorithm based on:
       - Provider availability
       - Location matching
       - Areas of concern
       - Treatment modalities
       - Therapist preferences (gender, ethnicity, religion, language)
     - Loads and manages provider data
     - Handles data processing and filtering

3. **Models**

   - **PatientRequest** (`/src/main/java/com/anisehealth/matching/model/`)

     - Demographics
     - Therapist preferences
     - Areas of concern
     - Treatment modalities
     - Location
     - Payment information

   - **Provider** (`/src/main/java/com/anisehealth/matching/model/`)
     - Provider information
     - Specializations
     - Treatment approaches
     - Languages
     - Availability
     - Payment methods

### Data Management

1. **Data Source**

   - Mock data stored in CSV format
   - Located in `/src/main/resources/mock-data.csv`
   - Contains provider information including:
     - Demographics
     - Specializations
     - Languages
     - Availability
     - Payment methods

2. **Data Loading**
   - CSV file loaded on application startup
   - Providers cached in memory
   - Data processing includes:
     - Field normalization
     - Data validation
     - Type conversion

## Matching Algorithm

### Matching Criteria

1. **Required Matches**

   - Location
   - Available capacity
   - Payment method compatibility

2. **Preference Matching**

   - Gender preferences
   - Ethnicity preferences
   - Religious preferences
   - Language preferences

3. **Specialization Matching**
   - Areas of concern
   - Treatment modalities
   - Professional experience

### Matching Process

```
Patient Request → Validation → Initial Filtering
    → Preference Matching → Specialization Matching
    → Availability Check → Final Results
```

## Security

1. **HTTPS Configuration**

   - SSL/TLS enabled
   - Port 8443
   - Custom keystore configuration

2. **CORS Configuration**
   - Configured for frontend origin
   - Method restrictions
   - Header configurations

## Error Handling

1. **Exception Types**

   - ResourceNotFoundException
   - ValidationException
   - DataProcessingException

2. **Error Responses**
   - Standardized error format
   - Meaningful error messages
   - Appropriate HTTP status codes

## Configuration

1. **Application Properties**

   ```yaml
   server:
     port: 8443
     ssl:
       enabled: true
       key-store: classpath:keystore.p12
       key-store-password: ${KEYSTORE_PASSWORD}
       key-store-type: PKCS12
       key-alias: anisehealth

   cors:
     allowed-origins: http://localhost:4200
     allowed-methods: GET,POST,PUT,DELETE,OPTIONS
   ```

2. **Logging Configuration**
   - Configured for development and production
   - Includes request/response logging
   - Error logging

## Performance Considerations

1. **Data Management**

   - In-memory provider data storage
   - Efficient filtering algorithms
   - Response caching considerations

2. **Optimization**
   - Quick initial filtering
   - Efficient matching algorithms
   - Response size optimization

## Testing

1. **Unit Tests**

   - Service layer testing
   - Matching algorithm testing
   - Data processing testing

2. **Integration Tests**
   - API endpoint testing
   - Error handling testing
   - End-to-end flow testing

## Development Guidelines

1. **Code Organization**

   - Follow Spring Boot best practices
   - Clear package structure
   - Consistent naming conventions
   - Comprehensive documentation

2. **Adding New Features**

   - Maintain backward compatibility
   - Follow RESTful principles
   - Include appropriate tests
   - Update documentation

3. **Error Handling**
   - Use appropriate exception types
   - Implement proper logging
   - Return meaningful error messages

## Future Enhancements

1. **Potential Improvements**

   - Database integration
   - Caching implementation
   - Advanced matching algorithms
   - Performance optimizations
   - Additional API endpoints

2. **Scalability**
   - Load balancing considerations
   - Database scaling
   - Caching strategies
   - API versioning

## Deployment

1. **Requirements**

   - Java 17 or higher
   - Maven for building
   - SSL certificate
   - Environment variables

2. **Build Process**

   ```bash
   mvn clean package
   ```

3. **Running the Application**
   ```bash
   java -jar target/matching-system.jar
   ```

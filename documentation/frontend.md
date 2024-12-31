# Frontend Documentation - Patient-Provider Matching System

## Overview

The frontend is built using Angular 17 with Material UI components. It implements a patient form for collecting preferences and displays matched providers based on those preferences.

## Architecture

### Core Components

1. **PatientFormComponent** (`/src/app/components/patient-form/`)

   - Main form interface for collecting patient preferences
   - Implements reactive forms for data collection and validation
   - Handles form submission and provider matching requests
   - Sections include:
     - Areas of Concern (multiple selection)
     - Treatment Modality (multiple selection)
     - Demographics (patient information)
     - Therapist Preferences
     - Location
     - Payment Information

2. **ProviderListComponent** (`/src/app/components/provider-list/`)
   - Displays matched providers
   - Shows provider details including:
     - Name and credentials
     - Specializations
     - Treatment modalities
     - Languages spoken
     - Location and availability
     - Payment methods accepted

### Services

1. **ProviderService** (`/src/app/services/provider.service.ts`)

   - Handles HTTP communication with backend
   - Primary methods:
     - `findMatches(request: PatientRequest): Observable<Provider[]>`
   - Manages provider data retrieval and error handling

2. **ProviderStateService** (`/src/app/services/provider-state.service.ts`)
   - Manages application state
   - Handles:
     - Loading states
     - Error states
     - Provider results storage
   - Implements state management pattern for data flow

### Models

1. **PatientRequest** (`/src/app/models/patient-request.model.ts`)

   - Defines the structure for patient form data
   - Includes:
     - Demographics (ethnicity, gender, religion, marital status)
     - Therapist preferences (gender, ethnicity, religion, language)
     - Areas of concern
     - Treatment modalities
     - Location
     - Payment information

2. **Provider** (`/src/app/models/provider.model.ts`)
   - Defines the structure for provider data
   - Contains:
     - Personal information
     - Professional qualifications
     - Specializations
     - Treatment approaches
     - Availability
     - Payment options

## Data Flow

1. **Form Submission Process**

   ```
   User Input → Form Validation → PatientFormComponent
        → ProviderService → Backend API
        → ProviderStateService → ProviderListComponent
   ```

2. **State Management**
   ```
   ProviderStateService
   ├── Loading State
   ├── Error State
   └── Provider Results
   ```

## Form Validation

### Required Fields

- Areas of Concern
- Demographics (all fields)
- Location
- Payment Method
- Insurance Provider (if Insurance selected)

### Conditional Validation

- Insurance Provider field is only required when "Insurance" is selected as payment method
- Form submission is disabled until all required fields are valid

## UI/UX Features

1. **Form Organization**

   - Logically grouped sections
   - Clear headings and labels
   - Responsive grid layout
   - Material Design components

2. **User Feedback**

   - Loading indicators
   - Error messages
   - Form validation feedback
   - Smooth scrolling to results

3. **Accessibility**
   - ARIA labels
   - Keyboard navigation
   - Screen reader support
   - High contrast text

## Styling

- Uses Angular Material theming
- Responsive design with CSS Grid
- Custom styling for form sections
- Consistent spacing and typography
- Mobile-friendly layout

## Error Handling

1. **Form Level**

   - Field validation errors
   - Required field indicators
   - Custom error messages

2. **API Level**
   - Network error handling
   - Server error feedback
   - User-friendly error messages

## Future Enhancements

1. **Potential Improvements**

   - Add form state persistence
   - Implement advanced filtering
   - Add provider ratings/reviews
   - Enhanced error handling
   - Add form progress indicator

2. **Performance Optimizations**
   - Lazy loading of components
   - Caching of provider data
   - Optimized provider list rendering

## Development Guidelines

1. **Adding New Features**

   - Follow Angular best practices
   - Maintain component isolation
   - Use TypeScript types
   - Update documentation

2. **Testing**
   - Write unit tests for components
   - Test form validation
   - Test API integration
   - Test error scenarios

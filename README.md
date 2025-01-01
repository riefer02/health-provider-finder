# Anise Health - Culturally-Informed Mental Healthcare Matching Platform

## Project Overview

Anise Health is a specialized healthcare platform focused on connecting Asian and Asian American patients with culturally competent mental health providers. The platform employs an intelligent matching algorithm that considers cultural nuances, language preferences, and specific mental health needs within the Asian community.

## Current Implementation (MVP)

### Core Features

1. **Provider Matching System**

   - Cultural and language preference matching
   - Specialization and expertise matching
   - Basic availability filtering
   - CSV-based provider data storage

2. **Technical Stack**

   - Frontend: Angular 19 SPA with TypeScript
   - Backend: Spring Boot 3.2.1
   - RESTful API architecture
   - In-memory provider matching algorithm

3. **Basic Functionality**
   - Patient preference submission
   - Provider matching based on criteria
   - Match results display
   - Simple provider profiles

## Local Development Setup

### Prerequisites

- Java 17 (required by Spring Boot 3.2.1)
- Node.js 18 (as used in CI/CD)
- Angular CLI 19.0.6
- Maven 3.9.9

### Backend Setup

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

The backend service will start on `http://localhost:8080`

### Frontend Setup

```bash
cd frontend
npm install
npm start
```

The frontend application will be available at `http://localhost:4200`

## Documentation

For detailed information about specific components, please refer to the documentation folder:

- [Frontend Documentation](documentation/frontend.md)

  - Component structure
  - State management
  - UI/UX implementation

- [Backend Documentation](documentation/backend.md)

  - API endpoints
  - Matching algorithm details
  - Data processing

- [Matching Criteria Documentation](documentation/matching-criteria.md)
  - Detailed matching logic
  - Cultural considerations
  - Provider-patient compatibility scoring

## Future Roadmap

### 1. HIPAA Compliance Implementation Plan

#### Security Infrastructure

- Implement Web Application Firewall (WAF)
- Set up DDoS protection
- Configure rate limiting
- Implement IP whitelisting

#### Data Protection

- Enable TLS 1.3 for data in transit
- Implement AES-256 encryption for data at rest
- Set up secure key management system
- Plan PHI data handling procedures

#### Access Control

- Implement Role-Based Access Control (RBAC)
- Add JWT-based authentication
- Enable Multi-factor authentication
- Design audit logging system

### 2. Scaling Strategy

#### Infrastructure Evolution

- Migrate to cloud infrastructure (AWS/GCP)
- Implement containerization with Kubernetes
- Set up auto-scaling policies
- Add load balancing
- Plan geographic distribution

#### Database Migration

- Move from CSV to PostgreSQL
- Design efficient schema for matching
- Implement data sharding strategy
- Add Redis caching layer

#### Architecture Improvements

- Break down into microservices
- Implement service discovery
- Add API gateway
- Set up message queues for async operations

### 3. Monitoring and Reliability

#### Observability

- Set up ELK stack for logging
- Implement Prometheus/Grafana for metrics
- Add distributed tracing
- Create performance dashboards

#### Performance Optimization

- Implement caching strategy
- Optimize matching algorithm
- Add CDN for static assets
- Set up database indexing

### 4. Feature Enhancements

#### Provider Management

- Real-time availability calendar
- Provider onboarding workflow
- Credential verification system
- Performance analytics

#### Patient Experience

- Appointment scheduling
- Secure messaging system
- Video consultation platform
- Feedback and rating system

#### Analytics

- Match success metrics
- User behavior analytics
- Provider performance tracking
- Business intelligence dashboards

---

Note: This README represents our MVP state and outlines our vision for scaling the platform. Many features mentioned in the roadmap are planned for future implementation to ensure HIPAA compliance and system scalability.

# Patient-Provider Matching System

A secure, HIPAA-compliant system for matching patients with mental health providers based on preferences, specializations, and availability.

## Table of Contents

- [Quick Start](#quick-start)
- [System Architecture](#system-architecture)
- [Security & Compliance](#security--compliance)
- [Enterprise Scaling](#enterprise-scaling)
- [Development](#development)

## Quick Start

### Prerequisites

- Java 17 or higher
- Node.js 18 or higher
- Maven
- Angular CLI

### Backend Setup

```bash
cd backend
./mvnw clean install
./mvnw spring-boot:run
```

The backend will start on `https://localhost:8443`

### Frontend Setup

```bash
cd frontend
npm install
npm start
```

The application will be available at `http://localhost:4200`

## System Architecture

### Current Implementation

The system consists of two main components:

1. **Frontend (Angular 17)**

   - Reactive forms for patient preferences
   - Material UI components
   - State management for provider results
   - Real-time form validation
   - Responsive design

2. **Backend (Spring Boot)**
   - RESTful API endpoints
   - Provider matching algorithm
   - HTTPS/SSL encryption
   - CORS configuration
   - Error handling

### Data Flow

1. Patient submits preferences through the form
2. Frontend validates and sends request to backend
3. Backend processes matching algorithm
4. Matched providers returned to frontend
5. Results displayed in provider list

## Security & Compliance

### HIPAA Compliance Implementation

1. **Web Application Firewall (WAF)**

   - Protection against OWASP Top 10 threats
   - DDoS protection
   - Rate limiting
   - IP whitelisting
   - Request validation
   - SQL injection prevention

2. **Encryption**

   - TLS 1.3 for data in transit
   - AES-256 encryption for data at rest
   - Key rotation policies
   - Secure key storage using AWS KMS
   - End-to-end encryption for PHI

3. **Access Control**

   - Role-Based Access Control (RBAC)
   - JWT-based authentication
   - Session management
   - Multi-factor authentication
   - Principle of least privilege

4. **Audit Logging**

   - Comprehensive audit trails
   - Access logging
   - Change tracking
   - Error logging
   - Security event monitoring
   - Tamper-evident logs

5. **Key Management**
   - Hardware Security Modules (HSM)
   - Automated key rotation
   - Secure key distribution
   - Access control to keys
   - Key usage auditing

### Security Architecture

```
[Client] ─── [WAF] ─── [API Gateway]
                            │
                    [Identity Provider]
                            │
                    [Service Mesh]
                            │
               [Encryption Service (KMS)]
                            │
                [Data Access Layer]
```

## Enterprise Scaling

### High-Availability Architecture

```
                                     [Load Balancer]
                                           │
                    ┌──────────────┬──────┴───────┬──────────────┐
                    │              │              │              │
            [API Gateway]    [API Gateway]  [API Gateway]  [API Gateway]
                    │              │              │              │
        ┌───────────┼──────────────┼──────────────┼───────────┐
        │           │              │              │           │
   [Auth Service] [Matching]  [Provider]    [Patient]    [Analytics]
        │           │              │              │           │
        └─────────┐ │              │              │ ┌─────────┘
                  │ │              │              │ │
                [Message Queue / Event Bus (Kafka)]
                           │              │
                    [Cache Layer]   [Search Engine]
                           │              │
                    [Primary DB]    [Analytics DB]
```

### Scalability Features

1. **Infrastructure**

   - Containerization with Kubernetes
   - Auto-scaling policies
   - Load balancing
   - Geographic distribution
   - CDN integration

2. **Performance**

   - Redis caching
   - Connection pooling
   - Request batching
   - Async processing
   - Response optimization

3. **Monitoring**
   - Application metrics
   - Performance monitoring
   - Error tracking
   - User analytics
   - Resource utilization

## Development

### Code Organization

```
├── frontend/
│   ├── src/
│   │   ├── app/
│   │   │   ├── components/
│   │   │   ├── services/
│   │   │   └── models/
│   │   └── assets/
│   └── package.json
│
└── backend/
    ├── src/
    │   └── main/
    │       ├── java/
    │       │   └── com/
    │       │       └── anisehealth/
    │       │           └── matching/
    │       └── resources/
    └── pom.xml
```

### Adding New Features

1. Follow security-first approach
2. Implement proper error handling
3. Add comprehensive logging
4. Include unit tests
5. Update documentation

### Testing

1. **Unit Tests**

   ```bash
   # Backend
   ./mvnw test

   # Frontend
   npm run test
   ```

2. **Security Testing**
   - Regular penetration testing
   - Vulnerability scanning
   - Security code review
   - Compliance auditing

## Contributing

1. Fork the repository
2. Create feature branch
3. Commit changes
4. Create pull request
5. Await code review

## License

This project is proprietary and confidential.

## Support

For technical support or questions about HIPAA compliance, contact the development team.

## **Improved AI Agent Prompt for a HIPAA-Compliant Patient-Provider Matching Monorepo**

### **Project Overview**

You are an **AI agent** tasked with **creating and iteratively enhancing** a **monorepo** for a health-related project that must be **HIPAA-compliant**. The primary goal is to develop a **Patient-Provider Matching Web App** for Anise Health. The web application will allow patients to find therapists who best meet their needs and preferences.

- **Name of CSV file for provider data:** `mock-data.csv` (located at the **top level** of the repository).

---

### **Key Objectives**

1. **Monorepo Setup**
2. **Backend (Java Spring Boot) Development**
3. **Frontend (Angular) Development**
4. **Security and HIPAA Compliance**
5. **Testing**
6. **CI/CD Pipeline**
7. **Scalability and Extendability**

Each of these tasks must be completed in a manner that **adheres to HIPAA requirements** and ensures best practices for **security**, **performance**, and **maintainability**.

---

### **1. Monorepo Setup**

1. **Directory Structure**

   - Create a monorepo with two main folders:
     - `backend/` (Java Spring Boot project)
     - `frontend/` (Angular project)
   - Place `mock-data.csv` at the **top level** of the repository (outside both `backend/` and `frontend/`).

2. **Tooling**

   - You may use `nx` or a similar tool to manage the monorepo if desired.
   - Set up shared configuration files for:
     - CI/CD (e.g., GitHub Actions or similar)
     - Code linting
     - Testing

3. **Standard Files**
   - Include `.gitignore`, `README.md`, `.editorconfig`, and any other relevant configuration files to standardize development.

---

### **2. Backend (Java Spring Boot)**

Create a **Spring Boot** application to **receive patient requests**, **match patients** to therapists, and **return up to three best matches**.

1. **Input Endpoint**

   - Implement a RESTful API endpoint that accepts **JSON** data for:
     - Areas of Concern (e.g., anxiety, depression, racial identity-related issues, academic stress, trauma-related stress, work-related stress, insomnia)
     - Preferred Treatment Modality (e.g., CBT, DBT, EMDR)
     - Demographics (e.g., Japanese, Christian, married)
     - Therapist Preferences (e.g., female, East Asian, Muslim)
     - Location (e.g., California, New York)
     - Payment Method (e.g., Aetna, Magellan, Anthem, Self-pay)

2. **Mock Data Logic**

   - Load provider data from `mock-data.csv` (at the repository’s top level).
   - Each provider entry in `mock-data.csv` can include:
     - `Name`, `Specialty`, `Treatment Modality`, `Location`, `Ethnic/Gender Identity`, `Sexual Orientation`, `Language(s)`, `Short Bio`, `Capacity`, etc.
   - Use Java streams or another efficient method to **filter** and **sort** the provider list based on the patient’s preferences.

3. **Matching Criteria & Output**

   - **Rank** providers according to how well they match the submitted preferences.
   - **Return up to three** best matches, each with a **full provider profile**:
     - Name, contact or location info, brief bio, relevant specialties, capacity status, etc.

4. **Project Setup**

   - Create a Maven or Gradle project under `backend/`.
   - Use Spring Boot’s `@RestController` to define your endpoints.
   - Ensure the app runs on a secure port (HTTPS preferred for HIPAA compliance).

5. **HIPAA Compliance in the Backend**
   - Do **not** store any sensitive patient data in logs or in plain text.
   - Use **encryption** (TLS/SSL) for API communication.
   - Limit the amount of data logged to avoid PHI (Protected Health Information) exposure.

---

### **3. Frontend (Angular)**

Develop an **Angular** application that **communicates** with the Spring Boot backend to **collect patient inputs** and **display matching therapist results**.

1. **Angular Project Setup**

   - Create a new Angular project in the `frontend/` directory (e.g., using the Angular CLI).
   - Configure any necessary **proxy** settings to redirect API calls to the backend during development.

2. **Form for Patient Input**

   - Use **Angular Reactive Forms** to create a dynamic form that captures all required fields:
     - Areas of Concern
     - Preferred Treatment Modality
     - Demographics
     - Therapist Preferences
     - Location
     - Payment Method
   - Ensure proper **validation** (required fields, correct data types, etc.).

3. **API Integration**

   - Use Angular’s `HttpClient` to **send** the patient’s form data to the backend endpoint.
   - **Display** up to three matching therapist profiles in a clear format (e.g., cards or a simple list).

4. **HIPAA Considerations (Frontend)**
   - Do **not** store sensitive patient data in local storage, session storage, or cookies.
   - Communicate only over **HTTPS** to protect data in transit.

---

### **4. Security and HIPAA Compliance**

Throughout the entire stack, **HIPAA compliance** is paramount. Ensure the following:

1. **Data Encryption**

   - Use **TLS/SSL certificates** for all API calls.
   - Encrypt sensitive data in transit; avoid storing unnecessary PHI at rest.

2. **Access Control**

   - Protect backend endpoints using secure tokens, API keys, or OAuth as appropriate.
   - Consider role-based access control if needed for future provider or admin dashboards.

3. **Audit Logs**

   - Keep **minimal** but **sufficient** logs of system access and operations for auditing, ensuring no PHI is exposed.

4. **Validation & Sanitization**
   - Implement strict input validation on both the **frontend** and **backend** to prevent injection attacks and data corruption.

---

### **5. Testing**

Implement **comprehensive testing** to ensure functionality, reliability, and security:

1. **Backend Testing**

   - **Unit Tests** with **JUnit** for your Spring Boot controllers and services.
   - Use **Mockito** or similar libraries to mock I/O and data layers.
   - Verify matching logic with various test cases covering different patient preferences.

2. **Frontend Testing**

   - **Unit Tests** using **Jasmine/Karma** for components, services, and form validation.
   - **E2E Tests** with **Protractor** or **Cypress** to simulate real user flows (form submission, receiving matches).

3. **Integration Testing**
   - Test the entire flow end-to-end: from the Angular form submission to the Spring Boot endpoint and back.

---

### **6. CI/CD Pipeline**

Automate builds, tests, and deployments for both the **frontend** and **backend**:

1. **Pipeline Configuration**

   - Use **GitHub Actions**, **GitLab CI**, or similar platforms to define a pipeline.
   - Install dependencies and run tests automatically on each push or pull request.

2. **Backend Build & Deployment**

   - Build the Spring Boot project with Maven or Gradle.
   - Package the JAR or WAR file.
   - Optionally containerize using **Docker**.

3. **Frontend Build & Deployment**

   - Install Node.js dependencies and build the Angular app.
   - Optionally containerize the frontend or serve it via an Nginx container.

4. **Security Checks**
   - Integrate **security scanning** tools (e.g., Snyk) to catch vulnerabilities.

---

### **7. Scalability and Extendability**

Design the system to **scale** with growing user needs and evolving matching criteria:

1. **Backend Modularity**

   - Keep matching logic in separate service classes for easy updates or replacement.
   - Use caching strategies (e.g., Redis) for frequently accessed data if needed.

2. **Angular Scalability**

   - Implement **lazy loading** modules where appropriate.
   - Keep components focused and reusable.

3. **Documentation**

   - Document the API with **Swagger** or OpenAPI to enable easy integration and future expansion.
   - Provide a clear **README** describing the setup, testing instructions, and project structure.

4. **Ongoing Improvements**
   - Maintain a backlog of features to continuously improve the matching algorithm (e.g., weighting certain preferences, machine learning enhancements).
   - Plan for new data fields in `mock-data.csv` (e.g., appointment availability, telehealth vs. in-person, etc.).

---

## **Guidelines for Iteration**

1. **Continuously** check for HIPAA compliance as you develop.
2. **Refine** the matching logic and acceptance criteria based on potential user feedback.
3. **Optimize** performance by introducing caching or indexing if the data grows large.
4. **Regularly** update dependencies to protect against security vulnerabilities.

---

### **End Goal**

Deliver a **secure**, **scalable**, and **maintainable** monorepo that **fully implements** the Patient-Provider Matching Web App. This includes:

- A **Spring Boot** backend for patient input and matching logic.
- An **Angular** frontend for data submission and displaying results.
- A **CI/CD pipeline** for automated testing and deployment.
- Comprehensive **testing** to validate functionality, security, and performance.
- Proper **documentation** and **example mock data** in `mock-data.csv`.

By following this plan, you will provide a **functional prototype** that meets Anise Health’s **core requirements** and adheres to **HIPAA** regulations.

### **Mock Data**

Below is the sample dataset to be placed in the **`mock-data.csv`** file at the top level of your repository. This data provides example therapist profiles, including demographic, specialty, and availability information. Make sure to load and parse this file in your backend to perform patient-provider matching.

```
First Name,Last Name,Ethnic Identity,Gender Identity,No Of Clients Able To Take On,Language,Location,Bio,Sexual Orientation,Religious Background,Treatment Modality,Areas of Specialization,,,,,,,,
Richard,Peng,"Asian American, Taiwanese",Male,2,English,"CA, NY","I’m a licensed clinical psychologist specializing in anxiety disorders. Using a cognitive-behavioral approach, I’ve spent over 10 years helping clients reduce stress, manage panic attacks, and build resilience in their daily lives.",Gay / lesbian,None,"CBT, Motivational Interviewing, Psychodynamic therapy","Depression, Low self-esteem, Ethnicity and racial identity related issues and/or trauma, LGBTQ+ related concerns, Academic stress, Occupation-related stress, Major life transitions, Social fears, Interpersonal problems, Relationship difficulties, Sexual concerns",,,,,,,,
Alice,Zhang,Chinese Canadian,Female,10,"English,Hindi,French",CA,"I have a Ph.D. in clinical psychology and focus on supporting clients experiencing depression. I integrate mindfulness and positive psychology into my practice to help people find joy and meaning, even in challenging times.",Straight / heterosexual,Buddhist,"Acceptance and Commitment Therapy (ACT)
Cognitive Behavioral Therapy (CBT)
Dialectical Behavioral Therapy (DBT)
Mindfulness-Based (MBCT)","Anxiety, Panic attacks, Worry, Culturally-responsive treatments, Depression, Low self-esteem, Ethnicity and racial identity related issues and/or trauma, LGBTQ+ related concerns, Academic stress, Occupation-related stress, Major life transitions, Social fears, Interpersonal problems, Relationship difficulties, Work-related stress, Trauma-related stress, Sleep problems",,,,,,,,
Nisha,Desai,Indian,Female,3,"English,Spanish","NY, FL","As a trauma-informed therapist, I specialize in helping clients heal from past experiences using techniques like EMDR (Eye Movement Desensitization and Reprocessing). My goal is to help you regain control of your life and move forward with confidence.",Straight / heterosexual,Hindu,"Cognitive Behavioral Therapy (CBT)
Dialectical Behavioral Therapy (DBT)
Mindfulness-Based (MBCT)
Person Centered Therapy, Art Therapy","Culturally-responsive treatments, Ethnicity and racial identity related issues and/or trauma, Trauma-related stress, Interpersonal problems, Panic attacks, Worry, Anxiety, Low self-esteem, Depression, Relationship difficulties, Social fears, Major life transitions",,,,,,,,
Josephine,Tseng,Korean American,Female,5,"English,Korean",CA,"I specialize in stress management, helping clients balance the demands of work and personal life. Through mindfulness and solution-focused therapy, I provide tools to manage stress effectively and create space for well-being.",Straight / heterosexual,Catholic,"Acceptance and Commitment Therapy (ACT)
Cognitive Behavioral Therapy (CBT)
Dialectical Behavioral Therapy (DBT)
Mindfulness-Based (MBCT)
Motivational Interviewing","Anxiety, Culturally-responsive treatments, Depression, Ethnicity and racial identity related issues and/or trauma, Interpersonal problems, Low self-esteem, Panic attacks, Major life transitions, Relationship difficulties, Social fears, Work-related stress, Worry",,,,,,,,
Joe,Tanaka,Mixed - half indian half white,Male,8,English,NY,"As a licensed marriage and family therapist, I work with couples to improve communication and strengthen their relationships. My focus is on fostering deeper connections and helping partners navigate challenges together.",Bisexual / pansexual,"Prefer not to say","Acceptance and Commitment Therapy (ACT)
Cognitive Behavioral Therapy (CBT)","Anxiety, Depression, Culturally-responsive treatments, Panic attacks, Trauma-related stress, OCD",,,,,,,,
Grace,Yang,Chinese Taiwanese American,Female,10,"English,Mandarin",WA,"I work with parents to develop effective strategies for raising children and managing family dynamics. My empathetic approach helps families navigate transitions and build strong, healthy relationships at home.",Straight / heterosexual,Buddhist,"Narrative Therapy, Contextual Therapy, Family Systems Therapy","Anxiety, Panic attacks, Worry, Attention Deficit Hyperactivity Disorder (ADD/ADHD), Culturally-responsive treatments, Depression, Low self-esteem, Ethnicity and racial identity related issues and/or trauma, Social fears, Interpersonal problems, Relationship difficulties, LGBTQ+ related concerns, Academic stress, Occupation-related stress, Major life transitions, Sexual concerns, Trauma-related stress, Autism",,,,,,,,
Ashley,Diaz,Southeast Asian,Female,5,"English,Tagalog",WA,"I’m a compassionate grief counselor who supports individuals and families as they process loss. My client-centered approach is focused on fostering healing, building resilience, and finding a way forward after a difficult experience.",Straight / heterosexual,None,MI,,,,,,,,,
Nora,Smith,African American,Female,5,English,CA,"I specialize in helping clients build self-esteem and confidence. Through strengths-based counseling, I empower individuals to overcome self-doubt, embrace their strengths, and develop a positive self-image.",Straight / heterosexual,Christian,"CBT, DBT, MI, EMDR","Anger management, Anxiety, Grief/bereavement, Mood disorder, Personality disorders, Post-Traumatic Stress Disorder (PTSD), Race-based trauma, Substance use disorder, Trauma therapy",,,,,,,,
Van,Lam,Chines American,Male,4,"English,Cantonese",CA,"I focus on helping professionals manage workplace stress and burnout. By combining CBT with practical stress management tools, I support clients in regaining balance and fulfillment in their careers.",Straight / heterosexual,None,"Cognitive Behavioral Therapy (CBT)
Dialectical Behavioral Therapy (DBT)
Mindfulness-Based (MBCT)
Acceptance and Commitment Therapy (ACT)
Motivational Interviewing
Prolonged Exposure Therapy
Therapist's Recommendation
Trauma Focused CBT","Social fears, Interpersonal problems, Relationship difficulties, Academic stress, Occupation-related stress, Major life transitions, Culturally-responsive treatments, Ethnicity and racial identity related issues and/or trauma, Trauma-related stress, Anxiety, Panic attacks, Worry, Sexual concerns, Work-related stress, Attention Deficit Hyperactivity Disorder (ADD/ADHD), Depression, Low self-esteem",,,,,,,,
Nancy,Nguyen,Vienamese,Female,15,"English,Vietnamese","FL, CA","I specialize in working with individuals with ADHD to develop strategies for focus, organization, and emotional regulation. Whether you’re a child, teen, or adult, I’m here to help you harness your strengths and achieve your goals.",Bisexual / pansexual,Christian,"Trauma Focused CBT, Psychodynamic, Relational-Cultural Therapy, Art Therapy","Depression, Low self-esteem, Ethnicity and racial identity related issues and/or trauma, LGBTQ+ related concerns, Social fears, Interpersonal problems, Relationship difficulties, Academic stress, Occupation-related stress, Major life transitions, Anxiety, Panic attacks, Worry, Culturally-responsive treatments",,,,,,,,
Angela,Brandes,White/Caucasian,Female,5,English,WA,"I specialize in helping individuals navigate life transitions, whether it’s starting a new career, ending a relationship, or adjusting to a major change. My approach combines mindfulness and solution-focused therapy to help you move forward with clarity and confidence.",Straight / heterosexual,Catholic,"CBT, MBCT, MI","Academic stress, Occupation-related stress, Major life transitions, Anxiety, Panic attacks, Worry, Culturally-responsive treatments, Depression, Low self-esteem, Ethnicity and racial identity related issues and/or trauma, LGBTQ+ related concerns, Sexual concerns, Social fears, Interpersonal problems, Relationship difficulties, Sleep problems",,,,,,,,
Sabreen,Lee,Taiwanese American,Female,7,"MANDARIN,English",NY,"I work with teens and young adults dealing with identity issues, self-esteem challenges, or academic stress. My therapy sessions provide a safe space to explore your thoughts, process emotions, and build the skills needed to thrive.",Bisexual / pansexual,None,"Emotionally Focused Therapy, Restoration Therapy","Relationship difficulties, Major life transitions, Interpersonal problems",,,,,,,,
Geri,Charles,"South Asian- Mixed , Sri Lankan",Female,20,English,CA,"I’m passionate about supporting clients struggling with chronic pain or illness. By integrating mindfulness and cognitive-behavioral strategies, I help individuals cope with their symptoms and lead fulfilling lives despite physical challenges.",Gay / lesbian,Muslim,"Acceptance and Commitment Therapy (ACT)
CBT
Mindfulness-Based (MBCT)
Trauma Focused CBT
Motivational Interviewing","Academic stress, Anger Management, Anxiety, ADHD, Depression, Culturally-responsive treatments, Impulse-control difficulties, Ethnicity and racial identity related issues and/or trauma, Eating Disorders, Interpersonal problems, Low self-esteem, LGBTQ+ related concerns, Relationship difficulties, Work-related stress, Major life transitions, Trauma-related stress, Panic attacks, Worry",,,,,,,,
,,,,,,,,,,,,,,,,,,,
,,,,,,,,,,,,,,,,,,,
,,,,,,,,,,,,,,,,,,,
,,,,,,,,,,,,,,,,,,,
,,,,,,,,,,,,,,,,,,,
,,,,,,,,,,,,,,,,,,,
```

# Hospital Appointment System

A backend Hospital Appointment System built with **Spring Boot**, **Spring Data JPA/Hibernate**, and **MySQL**, following a standard layered architecture. The system manages departments, doctors, patients, appointments, medical records, and prescriptions. **Spring Security and JWT** are used for authentication and role-based authorization.

---

## 🏗️ Tech Stack

- **Java** (Spring Boot)
- **Spring Security / JWT**
- **Spring Data JPA / Hibernate**
- **MySQL**
- **Bean Validation**
- **Lombok**
- **Maven**
- **REST API**

---

## 📁 Project Structure

```text
jsp.hospital_appointment_system
 ├── configuration
 │    ├── SecurityConfig
 │    ├── JwtConfig
 │    └── JwtCustomFilter
 │
 ├── entity
 │    ├── Department
 │    ├── Doctor
 │    ├── Patient
 │    ├── Appointment
 │    ├── MedicalRecord
 │    ├── Prescription
 │    └── User
 │
 ├── repository
 │    ├── DepartmentRepository
 │    ├── DoctorRepository
 │    ├── PatientRepository
 │    ├── AppointmentRepository
 │    ├── MedicalRecordRepository
 │    ├── PrescriptionRepository
 │    └── UserRepository
 │
 ├── service
 │    ├── *Service
 │    └── *ServiceImpl
 │
 ├── controller
 │    ├── DepartmentController
 │    ├── DoctorController
 │    ├── PatientController
 │    ├── AppointmentController
 │    ├── MedicalRecordController
 │    └── PrescriptionController
 │
 ├── dto
 │    └── Request/Response DTOs
 │
 └── exception
      ├── Custom Exceptions
      └── GlobalExceptionHandler
🧩 Entity Overview
Department
Field	Type
departmentId (PK)	Long
departmentName	String
doctors	List<Doctor>
Doctor
Field	Type
doctorId (PK)	Long
doctorName	String
specialization	String
availability	List<DayOfWeek>
department	Department
appointments	List<Appointment>
records	List<MedicalRecord>
Patient
Field	Type
patientId (PK)	Long
patientName	String
medicine	String
dosage	String
instruction	String
contact	String (unique)
appointments	List<Appointment>
medicalRecords	List<MedicalRecord>
Appointment
Field	Type
appointmentId (PK)	Long
appointmentDateTime	LocalDateTime
status	Status
doctor	Doctor
patient	Patient
MedicalRecord
Field	Type
recordId (PK)	Long
diagnosis	String
treatment	String
visitDate	LocalDate
doctor	Doctor
patient	Patient
prescription	Prescription
Prescription
Field	Type
prescriptionId (PK)	Long
medicine	String
dosage	String
instruction	String
medicalRecord	MedicalRecord
User
Field	Type
id (PK)	Long
userName	String (unique)
passWord	String
roles	List<Role>
🔗 Relationships
Relationship	Cardinality
Department → Doctor	1 : N
Doctor → Appointment	1 : N
Patient → Appointment	1 : N
Doctor → MedicalRecord	1 : N
Patient → MedicalRecord	1 : N
MedicalRecord → Prescription	1 : 1
🔑 Foreign Keys
Table	Foreign Key	References
Doctor	department_id	Department.departmentId
Appointment	doctor_id	Doctor.doctorId
Appointment	patient_id	Patient.patientId
MedicalRecord	doctor_id	Doctor.doctorId
MedicalRecord	patient_id	Patient.patientId
Prescription	record_id	MedicalRecord.recordId
🔢 Enums
Doctor Availability
SUNDAY
MONDAY
TUESDAY
WEDNESDAY
THURSDAY
FRIDAY
SATURDAY
Appointment Status
PENDING
CONFIRMED
CANCELLED
COMPLETED
User Roles
USER
DOCTOR
ADMIN
🔐 Security & Authentication

The application uses Spring Security and JWT for stateless authentication and role-based authorization.

Authentication Flow
Username + Password
        ↓
AuthenticationManager
        ↓
UserDetailsService
        ↓
Password Verification
        ↓
JWT Generation
        ↓
Client receives JWT
        ↓
Authorization: Bearer <JWT>
        ↓
JwtCustomFilter
        ↓
JWT Validation
        ↓
SecurityContext
        ↓
Role-Based Authorization
Authorization
/security/**  → Public

/patient/**   → USER, ADMIN

/doctor/**    → DOCTOR, ADMIN
Security Features
JWT-based authentication
Stateless session management
BCrypt password encoding
Custom JWT filter
Role-based authorization
Custom AuthenticationEntryPoint for 401 Unauthorized
Custom AccessDeniedHandler for 403 Forbidden
Form login disabled
HTTP Basic authentication disabled
⚙️ Key Implementation Notes
@JsonIgnore is used on selected back-references to prevent infinite recursion during JSON serialization of bidirectional JPA relationships.
@ElementCollection + @Enumerated(EnumType.STRING) is used for Doctor.availability because it stores a list of enum values.
@JoinColumn is used on owning sides to define foreign-key columns.
mappedBy is used on inverse sides of bidirectional relationships.
DTOs are used for API responses to avoid directly exposing JPA entities where required.
Bean Validation is used to validate incoming request data.
Global exception handling provides centralized API error responses.
Derived JPA queries are used for querying entities based on their properties and relationships.
JWT claims contain the authenticated username and roles required for authorization.
🚀 Getting Started
1. Clone the Repository
git clone https://github.com/Me-sachin123/hospital-appointment-system-backend-.git
cd hospital-appointment-system
2. Configure MySQL

Create the database:

CREATE DATABASE hospital_appointment_system;

Configure your database credentials in:

src/main/resources/application.properties

Example:

spring.datasource.url=jdbc:mysql://localhost:3306/hospital_appointment_system
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
3. Run the Application
mvn spring-boot:run

Or:

mvn clean install
java -jar target/<application-name>.jar
🧪 API Testing

The APIs can be tested using:

Postman
Swagger UI
IntelliJ HTTP Client
Login
POST /security/login
Content-Type: application/json

Request:

{
  "userName": "sachin",
  "passWord": "12345"
}

After successful authentication, the server returns a JWT.

Use the JWT for protected endpoints:

Authorization: Bearer <JWT_TOKEN>
⚠️ Exception Handling

The application provides centralized exception handling for:

Bean validation errors
Resource not found
Invalid appointment operations
Database constraint violations
401 Unauthorized
403 Forbidden
Generic exceptions
HTTP Status Codes
Status	Meaning
200	Successful request
201	Resource created
400	Bad request / validation error
401	Authentication required
403	Insufficient permissions
404	Resource not found
500	Internal server error
📌 Project Status

🚀 Backend completed with core hospital management functionality and JWT-based security.

The project includes:

Entity relationships
CRUD operations
Appointment management
Doctor availability
Medical records
Prescriptions
Bean validation
Global exception handling
JWT authentication
Role-based authorization
👤 Author

Sachin

Java | Spring Boot | Spring Security | JPA | MySQL

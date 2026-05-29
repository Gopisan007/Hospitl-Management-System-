# 🏥 MediCare – Hospital Management System

A full-stack Hospital Management System built with **Spring Boot** (backend) and **React** (frontend), featuring JWT authentication, role-based access, and a responsive Bootstrap UI.

---

## 📁 Project Structure

```
hospital-management/
├── backend/          ← Spring Boot REST API
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/hospital/
│       │   ├── HospitalManagementApplication.java
│       │   ├── config/
│       │   │   ├── SecurityConfig.java      # JWT Security config + CORS
│       │   │   └── DataInitializer.java     # Seeds default users on startup
│       │   ├── controller/
│       │   │   ├── AuthController.java      # POST /api/auth/login | /register
│       │   │   ├── DoctorController.java    # CRUD /api/doctors
│       │   │   ├── PatientController.java   # CRUD /api/patients
│       │   │   ├── AppointmentController.java # CRUD /api/appointments
│       │   │   ├── PrescriptionController.java # CRUD /api/prescriptions
│       │   │   └── DashboardController.java # GET /api/dashboard/stats
│       │   ├── entity/
│       │   │   ├── User.java
│       │   │   ├── Doctor.java
│       │   │   ├── Patient.java
│       │   │   ├── Appointment.java
│       │   │   └── Prescription.java
│       │   ├── repository/
│       │   │   ├── UserRepository.java
│       │   │   ├── DoctorRepository.java    # Custom search queries
│       │   │   ├── PatientRepository.java
│       │   │   ├── AppointmentRepository.java
│       │   │   └── PrescriptionRepository.java
│       │   ├── security/
│       │   │   ├── JwtUtils.java            # Token generation + validation
│       │   │   └── JwtAuthFilter.java       # Request filter
│       │   └── service/
│       │       ├── UserDetailsServiceImpl.java
│       │       ├── DoctorService.java
│       │       ├── PatientService.java
│       │       ├── AppointmentService.java
│       │       └── PrescriptionService.java
│       └── resources/
│           └── application.properties       # DB + JWT config
│
└── frontend/         ← React Application
    ├── package.json
    ├── public/index.html
    └── src/
        ├── index.js
        ├── index.css                        # All global styles
        ├── App.js                           # Router + auth guard
        ├── api/index.js                     # Axios API layer
        ├── context/AuthContext.js           # JWT auth state
        ├── components/
        │   ├── common/Pagination.js
        │   └── layout/
        │       ├── Layout.js
        │       ├── Sidebar.js               # Collapsible sidebar nav
        │       └── Topbar.js
        └── pages/
            ├── auth/Login.js               # JWT login form
            ├── dashboard/Dashboard.js      # Stats + charts
            ├── doctors/Doctors.js          # Doctor CRUD + search
            ├── patients/Patients.js        # Patient CRUD + search
            ├── appointments/Appointments.js # Booking + status management
            ├── prescriptions/Prescriptions.js # Prescription module
            └── reports/Reports.js          # Analytics + charts
```

---

## 🛠️ Tech Stack

| Layer      | Technology                                           |
|------------|------------------------------------------------------|
| Backend    | Spring Boot 3.2, Spring Security, Spring Data JPA    |
| Auth       | JWT (jjwt), BCrypt password encoding                 |
| Database   | MySQL 8.x with Hibernate ORM                         |
| Frontend   | React 18, React Router v6                            |
| UI         | Bootstrap 5, Bootstrap Icons, React-Bootstrap        |
| Charts     | Chart.js + react-chartjs-2                           |
| HTTP       | Axios with interceptors                              |
| Dev Tools  | Spring Boot DevTools, LiveReload                     |

---

## ⚡ Prerequisites

- **Java 17+**
- **Maven 3.6+**
- **Node.js 18+** and **npm 9+**
- **MySQL 8.x** running locally

---

## 🚀 Getting Started

### 1. Database Setup

Create the MySQL database (Spring Boot will auto-create tables):

```sql
CREATE DATABASE hospital_db;
```

Or just run the app — `createDatabaseIfNotExist=true` is already in the config.

---

### 2. Backend Setup

```bash
cd hospital-management/backend

# Update credentials in src/main/resources/application.properties if needed:
# spring.datasource.username=root
# spring.datasource.password=root

mvn spring-boot:run
```

Backend starts at **http://localhost:8080**

✅ On first run, these users are auto-created:

| Username        | Password    | Role          |
|-----------------|-------------|---------------|
| admin           | admin123    | ADMIN         |
| doctor          | doctor123   | DOCTOR        |
| receptionist    | rec123      | RECEPTIONIST  |

---

### 3. Frontend Setup

```bash
cd hospital-management/frontend

npm install
npm start
```

Frontend starts at **http://localhost:3000**

---

## 🔐 API Endpoints

### Auth
| Method | Endpoint              | Description      |
|--------|-----------------------|------------------|
| POST   | /api/auth/login       | Login with JWT   |
| POST   | /api/auth/register    | Register user    |

### Doctors
| Method | Endpoint           | Description               |
|--------|--------------------|---------------------------|
| GET    | /api/doctors       | List all (search, paged)  |
| POST   | /api/doctors       | Create doctor             |
| PUT    | /api/doctors/{id}  | Update doctor             |
| DELETE | /api/doctors/{id}  | Delete doctor             |

### Patients
| Method | Endpoint            | Description               |
|--------|---------------------|---------------------------|
| GET    | /api/patients       | List all (search, paged)  |
| POST   | /api/patients       | Register patient          |
| PUT    | /api/patients/{id}  | Update patient            |
| DELETE | /api/patients/{id}  | Delete patient            |

### Appointments
| Method | Endpoint                        | Description             |
|--------|---------------------------------|-------------------------|
| GET    | /api/appointments               | List all (paged)        |
| POST   | /api/appointments               | Book appointment        |
| PATCH  | /api/appointments/{id}/status   | Update status           |
| DELETE | /api/appointments/{id}          | Delete appointment      |
| GET    | /api/appointments/stats         | Stats (counts by status)|

### Prescriptions
| Method | Endpoint                        | Description             |
|--------|---------------------------------|-------------------------|
| GET    | /api/prescriptions              | List all (paged)        |
| POST   | /api/prescriptions              | Create prescription     |
| PUT    | /api/prescriptions/{id}         | Update prescription     |
| DELETE | /api/prescriptions/{id}         | Delete prescription     |

### Dashboard
| Method | Endpoint             | Description      |
|--------|----------------------|------------------|
| GET    | /api/dashboard/stats | Summary stats    |

---

## ✨ Features

- **JWT Authentication** — Secure login, token stored in localStorage, auto-attached to API requests. Auto-logout on 401.
- **Admin Dashboard** — Live stats, doughnut chart (appointment status), bar chart (weekly trends), KPI cards.
- **Doctor Management** — Add, edit, delete doctors. Search by name/specialization. Availability toggle.
- **Patient Management** — Register patients with full medical info. Search by name/email/phone.
- **Appointment Booking** — Book appointments selecting patient + doctor. Mark as Completed, Cancelled, or No Show.
- **Prescriptions** — Create detailed prescriptions with medications, diagnosis, instructions. View in a formatted modal.
- **Reports & Analytics** — Patient growth line chart, appointment trend bar chart, specialization doughnut, summary table with progress bars.
- **Collapsible Sidebar** — Full navigation that collapses to icon-only mode.
- **Pagination** — Server-side pagination on all listing pages with record counts.
- **Responsive UI** — Works on desktop, tablet, and mobile.

---

## 🔧 Configuration

Edit `backend/src/main/resources/application.properties`:

```properties
# Change these to match your MySQL credentials
spring.datasource.username=root
spring.datasource.password=yourpassword

# JWT secret (change in production!)
app.jwt.secret=YourVeryLongSecretKeyHere

# JWT expiry (milliseconds) — default 24 hours
app.jwt.expiration=86400000
```

Edit `frontend/src/api/index.js` if your backend runs on a different port:
```js
const API_BASE = 'http://localhost:8080/api';
```

---

## 📸 Pages Overview

| Page            | Route           | Features                                          |
|-----------------|-----------------|---------------------------------------------------|
| Login           | /login          | JWT login, credential hints                       |
| Dashboard       | /dashboard      | Stats cards, bar chart, doughnut chart, KPIs     |
| Doctors         | /doctors        | List, search, add/edit/delete, pagination         |
| Patients        | /patients       | List, search, add/edit/delete, gender/blood group |
| Appointments    | /appointments   | Book, filter by status, mark complete/cancel      |
| Prescriptions   | /prescriptions  | Create, view, edit, delete prescriptions          |
| Reports         | /reports        | Line chart, bar chart, doughnut, summary table    |

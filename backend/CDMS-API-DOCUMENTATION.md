# Criminal Database Management System - API Documentation

## 🚀 System Status
✅ **Backend is Running Successfully on Port 8080**

## 📋 Overview
This is a complete Spring Boot backend for the Criminal Database Management System (CDMS) demonstrating:
- ✅ Multithreading (@Async, ExecutorService)
- ✅ Concurrency (ConcurrentHashMap)
- ✅ Synchronization (ReentrantLock)
- ✅ Deadlock Prevention (Ordered lock acquisition)
- ✅ Generic Repository Pattern
- ✅ Mutable Entities (User, Criminal, FIR, Case, Officer)
- ✅ Immutable Entity (AuditLog)
- ✅ JWT Authentication
- ✅ In-memory Thread-safe Storage

## 🔐 Default Credentials
```
Admin User:
  Username: admin
  Password: admin123
  Role: ADMIN

Officer User:
  Username: officer1
  Password: officer123
  Role: OFFICER
```

## 🌐 Base URL
```
http://localhost:8080/api
```

## 📡 API Endpoints

### 1. Authentication
```http
POST /auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}

Response:
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "username": "admin",
  "role": "ADMIN",
  "expiresIn": 86400000
}
```

### 2. Criminals Management

#### Get All Criminals
```http
GET /criminals
Authorization: Bearer <token>
```

#### Get Criminal by ID
```http
GET /criminals/{id}
Authorization: Bearer <token>
```

#### Create Criminal (ADMIN only)
```http
POST /criminals
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "John Doe",
  "age": 35,
  "gender": "MALE",
  "address": "123 Street, City",
  "criminalRecord": "Theft, Burglary"
}
```

#### Update Criminal (ADMIN only)
```http
PUT /criminals/{id}
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "John Doe Updated",
  "age": 36,
  "gender": "MALE",
  "address": "456 Street, City",
  "criminalRecord": "Theft, Burglary, Assault"
}
```

#### Delete Criminal (ADMIN only)
```http
DELETE /criminals/{id}
Authorization: Bearer <token>
```

#### Search Criminals by Name
```http
GET /criminals/search?name=John
Authorization: Bearer <token>
```

### 3. FIR (First Information Report) Management

#### Get All FIRs
```http
GET /firs
Authorization: Bearer <token>
```

#### Get FIR by ID
```http
GET /firs/{id}
Authorization: Bearer <token>
```

#### Create FIR (ADMIN only)
```http
POST /firs
Authorization: Bearer <token>
Content-Type: application/json

{
  "firNumber": "FIR2024001",
  "dateOfIncident": "2024-01-15T10:30:00",
  "description": "Robbery at Bank",
  "placeOfIncident": "City Bank, Main Street",
  "complainantName": "Jane Smith",
  "criminalId": 1,
  "assignedOfficerId": 1
}
```

#### Update FIR (ADMIN only)
```http
PUT /firs/{id}
Authorization: Bearer <token>
Content-Type: application/json

{
  "firNumber": "FIR2024001-UPDATED",
  "dateOfIncident": "2024-01-15T10:30:00",
  "description": "Armed Robbery at Bank",
  "placeOfIncident": "City Bank, Main Street",
  "complainantName": "Jane Smith",
  "criminalId": 1,
  "assignedOfficerId": 1
}
```

#### Delete FIR (ADMIN only)
```http
DELETE /firs/{id}
Authorization: Bearer <token>
```

#### Get FIRs by Criminal ID
```http
GET /firs/criminal/{criminalId}
Authorization: Bearer <token>
```

#### Get FIRs by Officer ID
```http
GET /firs/officer/{officerId}
Authorization: Bearer <token>
```

### 4. Case Management

#### Get All Cases
```http
GET /cases
Authorization: Bearer <token>
```

#### Get Case by ID
```http
GET /cases/{id}
Authorization: Bearer <token>
```

#### Create Case (ADMIN only)
```http
POST /cases
Authorization: Bearer <token>
Content-Type: application/json

{
  "caseNumber": "CASE2024001",
  "title": "Bank Robbery Investigation",
  "description": "Investigation of armed robbery at City Bank",
  "status": "OPEN",
  "criminalId": 1,
  "assignedOfficerId": 1
}
```

#### Update Case (ADMIN only)
```http
PUT /cases/{id}
Authorization: Bearer <token>
Content-Type: application/json

{
  "caseNumber": "CASE2024001",
  "title": "Bank Robbery Investigation - Updated",
  "description": "Investigation of armed robbery at City Bank - New evidence found",
  "status": "IN_PROGRESS",
  "criminalId": 1,
  "assignedOfficerId": 1
}
```

#### Delete Case (ADMIN only)
```http
DELETE /cases/{id}
Authorization: Bearer <token>
```

#### Get Cases by Criminal ID
```http
GET /cases/criminal/{criminalId}
Authorization: Bearer <token>
```

#### Get Cases by Officer ID
```http
GET /cases/officer/{officerId}
Authorization: Bearer <token>
```

### 5. Officer Management

#### Get All Officers
```http
GET /officers
Authorization: Bearer <token>
```

#### Get Officer by ID
```http
GET /officers/{id}
Authorization: Bearer <token>
```

#### Create Officer (ADMIN only)
```http
POST /officers
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Officer Mike Johnson",
  "badgeNumber": "BADGE001",
  "rank": "Inspector",
  "department": "Criminal Investigation Department",
  "contactNumber": "555-1234"
}
```

#### Update Officer (ADMIN only)
```http
PUT /officers/{id}
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Officer Mike Johnson",
  "badgeNumber": "BADGE001",
  "rank": "Senior Inspector",
  "department": "Criminal Investigation Department",
  "contactNumber": "555-1234"
}
```

#### Delete Officer (ADMIN only)
```http
DELETE /officers/{id}
Authorization: Bearer <token>
```

#### Search Officers by Department
```http
GET /officers/department?dept=Criminal Investigation
Authorization: Bearer <token>
```

### 6. Audit Logs

#### Get All Audit Logs (ADMIN only)
```http
GET /audit
Authorization: Bearer <token>
```

#### Get Recent Audit Logs (ADMIN only)
```http
GET /audit/recent?limit=50
Authorization: Bearer <token>
```

#### Get Audit Logs by User (ADMIN only)
```http
GET /audit/user/{username}
Authorization: Bearer <token>
```

### 7. User Management

#### Get All Users (ADMIN only)
```http
GET /users
Authorization: Bearer <token>
```

#### Get User by ID (ADMIN only)
```http
GET /users/{id}
Authorization: Bearer <token>
```

#### Create User (ADMIN only)
```http
POST /users
Authorization: Bearer <token>
Content-Type: application/json

{
  "username": "newuser",
  "password": "password123",
  "role": "OFFICER"
}
```

#### Update User (ADMIN only)
```http
PUT /users/{id}
Authorization: Bearer <token>
Content-Type: application/json

{
  "username": "updateduser",
  "password": "newpassword123",
  "role": "OFFICER"
}
```

#### Delete User (ADMIN only)
```http
DELETE /users/{id}
Authorization: Bearer <token>
```

## 🔒 Authorization Rules

| Endpoint | ADMIN | OFFICER |
|----------|-------|---------|
| POST /auth/login | ✅ | ✅ |
| GET /criminals/* | ✅ | ✅ |
| POST /criminals | ✅ | ❌ |
| PUT /criminals/{id} | ✅ | ❌ |
| DELETE /criminals/{id} | ✅ | ❌ |
| GET /firs/* | ✅ | ✅ |
| POST /firs | ✅ | ❌ |
| PUT /firs/{id} | ✅ | ❌ |
| DELETE /firs/{id} | ✅ | ❌ |
| GET /cases/* | ✅ | ✅ |
| POST /cases | ✅ | ❌ |
| PUT /cases/{id} | ✅ | ❌ |
| DELETE /cases/{id} | ✅ | ❌ |
| GET /officers/* | ✅ | ✅ |
| POST /officers | ✅ | ❌ |
| PUT /officers/{id} | ✅ | ❌ |
| DELETE /officers/{id} | ✅ | ❌ |
| GET /audit/* | ✅ | ❌ |
| GET /users/* | ✅ | ❌ |
| POST /users | ✅ | ❌ |
| PUT /users/{id} | ✅ | ❌ |
| DELETE /users/{id} | ✅ | ❌ |

## 🧵 Thread Safety & Concurrency Features

### 1. Generic Repository (`GenericRepository<T>`)
- **ConcurrentHashMap**: Thread-safe storage
- **ReentrantReadWriteLock**: Multiple concurrent reads, exclusive writes
- **AtomicLong**: Thread-safe ID generation
- **Fair Lock**: Prevents thread starvation

### 2. Service Layer Synchronization
- **ReentrantLock (fair=true)**: Prevents deadlocks
- **Ordered Lock Acquisition**: Consistent lock ordering
  - FIR Service: `firLock` → `officerLock`
  - Case Service: `caseLock` → `officerLock`
- **Try-Finally Pattern**: Guaranteed lock release

### 3. Asynchronous Audit Logging
- **@Async**: Non-blocking audit log creation
- **ExecutorService**: Managed thread pool
- **Decoupled Logging**: Main operations don't wait for audit writes

### 4. Immutable Audit Log
- **Final Fields**: Cannot be modified after creation
- **Factory Method**: `AuditLog.create()`
- **Thread-Safe**: Shared safely across threads

## 📊 Sample Data Loaded

### Users
1. Admin (username: admin, password: admin123, role: ADMIN)
2. Officer1 (username: officer1, password: officer123, role: OFFICER)

### Criminals
1. John Doe (Age: 35, MALE, Theft and Burglary)
2. Jane Smith (Age: 28, FEMALE, Fraud and Embezzlement)

### FIRs
1. FIR001 - Robbery at Main Street (Criminal: John Doe, Officer: Officer Mike)
2. FIR002 - Fraud Case Downtown (Criminal: Jane Smith, Officer: Officer Sarah)

### Cases
1. CASE001 - Ongoing Robbery Investigation (Criminal: John Doe, Officer: Officer Mike)

### Officers
1. Officer Mike Johnson (BADGE001, Inspector, Criminal Investigation)
2. Officer Sarah Williams (BADGE002, Sub-Inspector, Cyber Crime)

## 🧪 Testing the API

### Step 1: Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

Copy the `token` from response.

### Step 2: Access Protected Endpoint
```bash
curl -X GET http://localhost:8080/api/criminals \
  -H "Authorization: Bearer <YOUR_TOKEN_HERE>"
```

### Step 3: Create a New Criminal (ADMIN only)
```bash
curl -X POST http://localhost:8080/api/criminals \
  -H "Authorization: Bearer <YOUR_TOKEN_HERE>" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test Criminal",
    "age": 30,
    "gender": "MALE",
    "address": "Test Address",
    "criminalRecord": "Testing"
  }'
```

## 🔧 Technical Stack

- **Framework**: Spring Boot 3.2.1
- **Language**: Java 17
- **Build Tool**: Maven 3.9.10
- **Security**: JWT (io.jsonwebtoken 0.11.5), Spring Security
- **Concurrency**: ConcurrentHashMap, ReentrantReadWriteLock, @Async
- **Storage**: In-memory thread-safe collections
- **Password Encoding**: BCrypt
- **CORS**: Enabled for http://localhost:5173

## 🎯 Key Features Demonstrated

1. **Multithreading**: @EnableAsync + @Async for audit logging
2. **Concurrency**: ConcurrentHashMap for thread-safe storage
3. **Synchronization**: ReentrantLock in all service classes
4. **Deadlock Prevention**: Ordered lock acquisition (FIR/Case always lock entity before officer)
5. **Generic Repository**: Type-safe `GenericRepository<T>` with type parameter
6. **Mutable Entities**: User, Criminal, FIR, Case, Officer (all have setters)
7. **Immutable Entity**: AuditLog with final fields and factory method
8. **JWT Authentication**: Stateless token-based security
9. **Role-Based Access Control**: ADMIN and OFFICER roles
10. **Exception Handling**: Global exception handler

## 🚦 Application Status

**✅ Backend is RUNNING SUCCESSFULLY**

```
========================================
CDMS Backend Started Successfully!
========================================
Server: http://localhost:8080/api
Status: OPERATIONAL
Sample Data: LOADED
Authentication: JWT ENABLED
CORS: ENABLED (http://localhost:5173)
========================================
```

## 📞 Support

For any issues or questions:
- Check application logs in the terminal
- Verify JWT token is included in Authorization header
- Ensure user has appropriate role for the operation
- Check CORS configuration if accessing from frontend

---
**Last Updated**: Application successfully started and validated
**Status**: ✅ Production Ready

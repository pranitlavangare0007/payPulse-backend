# PayPulse – Backend

> Secure Digital Banking Platform — Spring Boot REST API

The backend of PayPulse is a production-grade REST API built with **Spring Boot 4**, **Spring Security**, and **JWT**. It handles all core banking operations — account management, UPI/IMPS fund transfers, transaction processing, MPIN verification, and email notifications — with PostgreSQL as the database, deployed on **Render**.

🔗 **Frontend Repo:** [bank-app-frontend](https://github.com/pranitlavangare0007/payPulse-)  
🔗 **Live Demo:** [paypulse-banking.netlify.app](https://69d2441c2b469f440d817c08--paypulse-banking.netlify.app)

---

## Features

- **JWT Authentication** — Stateless authentication using signed JWT tokens; validated on every request via a custom `JwtFilter` in the Spring Security filter chain
- **BCrypt Password Encryption** — All passwords hashed with BCrypt before storage; plaintext is never persisted
- **MPIN Verification** — Every financial transaction (deposit, withdrawal, transfer) requires a 6-digit MPIN
- **Multi-Account Support** — One user can hold multiple Savings/Current accounts with different purposes
- **UPI Transfer** — Transfer by UPI ID with real-time account lookup and MPIN verification
- **IMPS Transfer** — Transfer by account number with atomic debit-credit using `@Transactional`
- **Cash Deposit & Withdrawal** — Direct balance operations with full audit trail
- **Transaction History** — Paginated transaction records with reference ID, channel, type, amount, and balance
- **Account Statement** — Date-range filtered statement generation
- **Email Verification** — OTP-based email verification using Spring Mail (SMTP)
- **Role-Based Access Control** — USER and ADMIN roles with separate endpoint security
- **Global Exception Handling** — `@ControllerAdvice` with 15+ custom exception classes returning structured error responses
- **AOP Logging** — Aspect-Oriented Programming with `ProgressMonitor` for method-level execution logging
- **Docker Support** — Dockerfile included for containerized deployment

---

## Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| Spring Boot | 4.0.2 | Core framework |
| Spring Security | — | Auth & authorization |
| Spring Data JPA | — | ORM & database access |
| Hibernate | — | JPA implementation |
| PostgreSQL | — | Primary database |
| JWT (JSON Web Token) | — | Stateless auth tokens |
| BCrypt | — | Password hashing |
| Spring Mail | — | Email/OTP service |
| Lombok | — | Boilerplate reduction |
| Maven | — | Build & dependency management |
| Docker | — | Containerization |
| Java | 17 | Language version |

---

## Project Structure

```
bank-services-app/
├── src/main/java/bank_services_app/
│   ├── BankServicesAppApplication.java     # Entry point
│   ├── config/
│   │   └── SecurityConfiguration.java     # Spring Security filter chain config
│   ├── filters/
│   │   └── JwtFilter.java                 # JWT request filter
│   ├── controller/
│   │   ├── AuthController.java            # Login, Register endpoints
│   │   ├── AccountControllerUser.java     # User account operations
│   │   ├── AccountControllerAdmin.java    # Admin account management
│   │   ├── TransactionController.java     # All transaction endpoints
│   │   └── CustomerController.java        # Customer profile endpoints
│   ├── services/
│   │   ├── AuthService.java               # Login/register logic
│   │   ├── AccountServices.java           # Account CRUD
│   │   ├── TransactionServices.java       # Transaction orchestration
│   │   ├── JwtService.java                # JWT generation & validation
│   │   ├── MpinService.java               # MPIN BCrypt verification
│   │   ├── EmailService.java              # OTP email sending
│   │   ├── CustomerServices.java          # Profile management
│   │   ├── Router.java                    # Transaction channel router
│   │   ├── CoreBanking/
│   │   │   └── Core.java                 # Core debit/credit logic
│   │   └── TransactionProcesses/
│   │       ├── TransactionProcesses.java  # Strategy interface
│   │       ├── CashProcess.java           # Cash deposit/withdrawal
│   │       ├── UpiProcess.java            # UPI transfer
│   │       ├── ImpsProcess.java           # IMPS transfer
│   │       ├── NeftProcess.java           # NEFT transfer
│   │       └── RtgsProcess.java           # RTGS transfer
│   ├── models/
│   │   ├── Customer.java                  # User entity
│   │   ├── AccountDetails.java            # Bank account entity
│   │   ├── Transaction.java               # Transaction entity
│   │   ├── EmailVerification.java         # OTP entity
│   │   └── UserPrincipal.java             # Spring Security user wrapper
│   ├── Dto/
│   │   ├── request/                       # LoginRequest, TransactionRequest, etc.
│   │   └── response/                      # AccountResponse, TransactionResult, etc.
│   ├── repositry/
│   │   ├── AccountRepo.java
│   │   ├── CustomerRepo.java
│   │   ├── TransactionRepo.java
│   │   └── EmailVerificationRepo.java
│   ├── exceptionHandling/
│   │   ├── GlobalExceptionHandler.java    # @ControllerAdvice handler
│   │   ├── InsufficientBalanceException.java
│   │   ├── InvalidMpin.java
│   │   ├── AccountNotFoundException.java
│   │   └── ... (15+ custom exceptions)
│   ├── aop/
│   │   ├── Aop.java                       # Aspect definitions
│   │   └── ProgressMonitor.java           # Method execution logging
│   └── util/
│       ├── AccountType.java               # SAVINGS / CURRENT
│       ├── AccountStatus.java             # ACTIVE / INACTIVE
│       ├── TransactionChannels.java       # CASH / UPI / IMPS / NEFT / RTGS
│       ├── TransactionType.java           # DEBIT / CREDIT
│       └── Role.java                      # USER / ADMIN
├── src/main/resources/
│   └── application.properties            # Env-variable-driven config
├── Dockerfile
├── pom.xml
└── mvnw
```

---

## API Endpoints

### Auth
| Method | Endpoint | Description |
|---|---|---|
| POST | `/auth/register` | Register new customer |
| POST | `/auth/login` | Login and receive JWT token |
| POST | `/auth/verify-email` | OTP email verification |

### Account
| Method | Endpoint | Description |
|---|---|---|
| POST | `/account/open` | Open new bank account |
| GET | `/account/all` | Get all accounts for logged-in user |
| GET | `/account/balance` | Get account balance |
| GET | `/account/profile` | Get customer profile |

### Transactions
| Method | Endpoint | Description |
|---|---|---|
| POST | `/transaction/deposit` | Cash deposit (MPIN required) |
| POST | `/transaction/withdraw` | Cash withdrawal (MPIN required) |
| POST | `/transaction/upi` | UPI transfer (MPIN required) |
| POST | `/transaction/imps` | IMPS transfer (MPIN required) |
| GET | `/transaction/history` | Get transaction history |
| POST | `/transaction/statement` | Get date-range statement |

### Admin
| Method | Endpoint | Description |
|---|---|---|
| GET | `/admin/accounts` | View all accounts |
| PUT | `/admin/account/status` | Activate/deactivate account |

---

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- PostgreSQL 14+
- (Optional) Docker

### Environment Setup

Create a `.env` file in the project root:

```env
APP_NAME=PayPulse
PORT=8080

DB_URL=jdbc:postgresql://localhost:5432/paypulse
DB_USERNAME=your_db_user
DB_PASSWORD=your_db_password
DB_DRIVER=org.postgresql.Driver

JWT_SECRET=your_256bit_secret_key
JWT_EXPIRATION=86400000

MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=your_email@gmail.com
MAIL_PASSWORD=your_app_password
```

### Running Locally

```bash
# Clone the repository
git clone https://github.com/pranitlavangare0007/bank-services-app.git
cd bank-services-app

# Build and run
./mvnw spring-boot:run
```

API runs at `http://localhost:8080`

### Running with Docker

```bash
# Build the Docker image
docker build -t paypulse-backend .

# Run the container
docker run -p 8080:8080 --env-file .env paypulse-backend
```

---

## Security Design

- All `/auth/**` endpoints are public; everything else requires a valid JWT
- JWT is validated on every request by `JwtFilter` which extracts the token from the `Authorization: Bearer <token>` header and sets the `SecurityContext`
- MPIN is stored as a BCrypt hash — never in plaintext
- `@Transactional` ensures atomic fund transfers — if credit fails after debit, both operations roll back

---

## Deployment

This backend is deployed on **Render**.

1. Push to GitHub
2. Create a new Web Service on Render
3. Set build command: `./mvnw clean package -DskipTests`
4. Set start command: `java -jar target/payPulse-banking-app.jar`
5. Add all `.env` variables as Render environment variables

---

## Author

**Pranit Lavangare**  
📧 pranitlavangare0007@gmail.com  
🔗 [LinkedIn](https://linkedin.com/in/pranit-lavangare-5a49a1373) | [GitHub](https://github.com/pranitlavangare0007)

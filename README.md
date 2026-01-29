# Midas Core – Transaction Processing System

## Overview
Midas Core is a Spring Boot application that processes financial transactions.
It listens to Kafka events, validates transactions, stores data in a database,
calls an external Incentive API, and allows users to check their account balance
through a REST endpoint.

This project was completed by implementing each feature step by step across
multiple tasks.

---

## Tech Stack Used
- Java
- Spring Boot
- Spring Kafka
- Spring Data JPA
- H2 In-Memory Database
- REST APIs
- Maven
- IntelliJ IDEA

---

## What I Implemented

### Task 1 – Kafka Listener
- Created a Kafka listener to receive `Transaction` messages.
- Verified that transactions were being consumed correctly.
- Used logs and debugger to inspect incoming data.

---

### Task 2 – Debugging Transactions
- Ran tests in debug mode using IntelliJ.
- Observed live transaction data such as senderId, recipientId, and amount.
- Learned how Kafka tests keep running until manually stopped.

---

### Task 3 – Database Integration
- Integrated an H2 in-memory database using JPA.
- Created `UserRecord` entity to store users and balances.
- Created `TransactionRecord` entity to store transactions.
- Implemented validation rules:
    - Sender must exist
    - Recipient must exist
    - Sender must have enough balance
- Stored only valid transactions.
- Updated balances for sender and recipient.
- Used debugger to find final balances (e.g., waldorf).

---

### Task 4 – Incentive API Integration
- Connected Midas Core to an external Incentive API running locally.
- Sent validated transactions to the `/incentive` endpoint.
- Received an incentive amount in response.
- Updated balance logic:
    - Incentive is added to recipient
    - Incentive is NOT deducted from sender
- Stored incentive amount along with the transaction.
- Used debugger to find wilbur’s final balance.

---

### Task 5 – Balance REST API
- Added a REST controller inside Midas Core.
- Exposed endpoint:
  GET `/balance?userId={id}`
- Returned user balance as JSON.
- Returned balance `0` if the user does not exist.
- Configured the application to run on port `33400`.
- Verified using provided tests.

---

## How to Run the Project

### Start Incentive API
```bash
java -jar services/transaction-incentive-api.jar
```

### Run Midas Core
```bash
mvn spring-boot:run
```

### Run Tests
```bash
mvn test
```

### Debugging Notes

- Use Shift + F9 in IntelliJ to run tests in debug mode.
- Place breakpoints in:
- TransactionListener
- BalanceController
- Kafka tests do not stop automatically and must be killed manually.
- Some required answers are visible only through the debugger.

### Problems Faced
- Kafka tests run continuously without stopping.
- Output disappears quickly unless execution is paused.
- Incentive API must be running before tests.
- Many answers were not printed and had to be found via debugger.

### What I Learned
- How Kafka works with Spring Boot
- Database persistence using JPA
- REST API integration using RestTemplate
- Debugging asynchronous systems
- Writing validation and business logic cleanly

### What Can Be Improved
- Clearer test instructions
- More visible test outputs
- Automatic stopping of Kafka tests

### Final Thoughts
This project helped me understand how real backend systems work together.
Each task added a new layer of functionality and improved my confidence in
working with Spring Boot, Kafka, databases, and REST APIs.
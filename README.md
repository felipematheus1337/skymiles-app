# ✈️ SkyMiles Travel Reservation System

A reactive microservice-based system for managing travel reservations, validating passenger miles, and sending booking confirmations — powered by Quarkus, Kafka, and PostgreSQL.

---

## 🧩 Architecture Overview

This system is composed of **three reactive microservices** communicating via **REST** and **Kafka**:

- `passenger-travel-service`: Manages passengers and creates travels
- `miles-service`: Simulates an external API that returns available miles
- `notification-service`: Listens to Kafka and sends confirmation notifications

             +-------------------------+
             |  passenger-travel-svc   |
             +-----------+-------------+
                         |
             REST ↘      |      ↙ Kafka
                   +-----v-----+
                   |  miles-svc |
                   +-----------+
                         |
                  Kafka ↘
             +-----------v-------------+
             |   notification-svc      |
             +-------------------------+



---

## ⚙️ Tech Stack

- ☕ Java 17+
- ⚡ Quarkus Reactive
- 🧬 Hibernate Reactive with Panache
- 🐘 PostgreSQL (via Dev Services or Docker)
- 📬 Apache Kafka (via SmallRye Messaging)
- 🔁 SmallRye REST Client
- ♻️ Fault Tolerance (Retry, Timeout, CircuitBreaker)
- 🧾 MDC Logging

---

## 📚 Domain Model

### 📄 Passenger

- Fields: `id`, `name`, `email`
- Participates in multiple travels

### 🧳 Travel

- Fields: `id`, `destination`, `date`, `status`
- One-to-many relationship with passengers
- Status:
  - `APPROVED`: if at least one passenger has enough miles
  - `REJECTED`: if none are eligible
- Applies **20% discount** if miles > 1000

---

## 🔄 Business Flow

1. A client sends a request to create a travel with a list of passenger IDs and a destination.
2. `passenger-travel-service` fetches each passenger's miles from `miles-service` via REST.
3. If eligible, the service applies a discount and marks the travel as `APPROVED`; otherwise, `REJECTED`.
4. The service sends a Kafka event to `notification-service` with the travel status.
5. `notification-service` consumes the event and simulates sending an email.

---

## 🐳 Running the System

### Requirements

- Docker & Docker Compose
- Java 17+
- Maven

### 1. Build All Microservices

```bash
mvn clean package -DskipTests

Start Everything with Docker Compose
cd docker/
docker-compose up --build

Kafka Event Example
{
  "name": "John Doe",
  "email": "john@example.com",
  "status": "APPROVED"
}

```

<hr>




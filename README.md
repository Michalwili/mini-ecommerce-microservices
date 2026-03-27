# 🛒 Mini eCommerce Microservices System

Simple microservices-based eCommerce backend built with **Java + Spring Boot + Kafka + Docker Compose**.

---

# 🚀 Architecture Overview

The system consists of 3 microservices:

* **product-service** → manages product catalog
* **order-service** → handles order creation
* **inventory-service** → manages stock

---

# 🔁 Business Flow

1. Client sends request to create an order
2. `order-service` checks product availability via `inventory-service` (REST)
3. If available → order is created
4. `order-service` publishes `OrderCreatedEvent` to Kafka
5. `inventory-service` consumes event and updates stock

---

# ⚙️ Tech Stack

* Java 21
* Spring Boot
* Apache Kafka
* Docker Compose
* REST API
* Swagger / OpenAPI

---

# 🔧 Features

### ✅ Microservices architecture

* Independent services
* Clear separation of concerns

### ✅ Synchronous communication

* REST call:
  `order-service → inventory-service`

### ✅ Asynchronous communication

* Kafka event-driven flow:

    * `OrderCreatedEvent`

### ✅ Caching

* Implemented in `product-service`
* Reduces repeated reads

### ✅ Rate limiting

* Implemented in `order-service`
* Protects `/orders` endpoint

### ✅ Global exception handling

* Consistent API error responses
* No default Whitelabel errors

### ✅ Swagger UI

* Interactive API documentation
* No need for Postman

---

# 📦 Services & Ports

| Service           | Port |
| ----------------- | ---- |
| product-service   | 8081 |
| inventory-service | 8082 |
| order-service     | 8083 |
| kafka             | 9092 |

---

# 🔌 API Endpoints

## 🟢 Product Service

```http
GET /products
GET /products/{id}
```

---

## 🟡 Inventory Service

```http
GET /inventory/check?productId=1&quantity=2
GET /inventory/stock?productId=1
```

---

## 🔵 Order Service

```http
POST /orders
```

### Request body:

```json
{
  "productId": 1,
  "quantity": 2
}
```

---

# 📘 Swagger UI

* Product Service:
  http://localhost:8081/swagger-ui/index.html

* Inventory Service:
  http://localhost:8082/swagger-ui/index.html

* Order Service:
  http://localhost:8083/swagger-ui/index.html

---

# 🐳 Run with Docker

## 1. Build JARs

```bash
mvn clean package -DskipTests
```

## 2. Run system

```bash
docker compose up --build
```

---

# 🔍 Example Flow

1. Check stock:

```http
GET /inventory/stock?productId=1
```

2. Create order:

```http
POST /orders
```

3. Kafka event is published

4. Inventory updates stock

---

# 🏁 Summary

This project demonstrates:

* microservices architecture
* synchronous + asynchronous communication
* event-driven design
* performance optimizations (cache, rate limiting)
* containerized environment

---


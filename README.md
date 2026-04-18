#  BusMate Backend - Scalable Bus Reservation System

A backend system for bus reservation.
Built with **Spring Boot**, this system handles real-world challenges like **concurrent seat booking, race conditions, and distributed locking using Redis**.

---

##  Features

*  JWT-based Authentication (Role-based: Admin/User)
*  Bus, Route, and Schedule Management
*  Seat Booking System with:

  * Redis-based seat locking (5-minute TTL)
  * Optimistic locking using `@Version`
  * Race condition handling
*  Real-time seat availability (AVAILABLE / LOCKED / BOOKED)
*  Session-based booking (no login required for seat locking)
*  PostgreSQL database integration
*  Clean architecture using DTOs, Service Layer, and Repository Pattern

---

##  Tech Stack

* **Backend:** Spring Boot, Spring Data JPA
* **Database:** PostgreSQL
* **Cache / Locking:** Redis
* **Build Tool:** Maven
* **Language:** Java

---

##  System Design Highlights

###  Seat Locking (Redis)

* When a user selects a seat:

  * A Redis key is created:

    ```
    seat_lock:{scheduleId}:{seatNumber}
    ```
  * TTL = 5 minutes
* Prevents multiple users from selecting the same seat

---

###  Race Condition Handling

* Even if 2 users hit booking at the same time:

  * `@Version` (Optimistic Locking) ensures only one succeeds

---

###  Booking Flow

1. User selects seat → Redis lock
2. User proceeds to payment
3. Booking API:

   * Validates Redis lock ownership
   * Updates DB (seat booked)
   * Removes Redis lock
   * Creates reservation

---

##  APIs (Sample)

### 🔹 Search Buses

```
GET /api/schedules/search?cityFrom=Delhi&cityTo=Kanpur
```

### 🔹 Get Seats

```
GET /api/seats?scheduleId=1
```

### 🔹 Lock Seat

```
POST /api/seats/lock?scheduleId=1&seatNumber=5&sessionId=abc123
```

### 🔹 Book Seat

```
POST /api/reservations/book
```

---

##  How to Run

### 1️ Start PostgreSQL

### 2️ Start Redis

```bash
redis-server
```

### 3️ Run Spring Boot App

```bash
mvn spring-boot:run
```

---

##  Key Learnings

* Handling concurrency in real-world systems
* Designing scalable booking systems
* Using Redis for distributed locking
* Implementing optimistic locking with JPA
* Clean backend architecture

---


--

Aryan
B.Tech

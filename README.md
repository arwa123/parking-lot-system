# Parking Lot System (Spring Boot)

Implements a Parking Lot Management System with Google OAuth2 login, JPA/H2, pricing rules, atomic payments, and slot allocation via Strategy pattern.

## Features
- OAuth2 Login with Google (Spring Security). All users get `ROLE_USER` by default; protect admin APIs with `ROLE_ADMIN`.
- Vehicles enter via `/api/tickets/entry`, system allocates nearest slot by floor/number and creates a ticket.
- Exit via `/api/tickets/exit`, then pay via `/api/payments/pay`. Payment is atomic; slot is freed only on success.
- Pricing rules per vehicle type with free minutes + hourly rate.
- Concurrency-safe allocation using pessimistic locking + slot `@Version`.
- Duplicate vehicle entry prevented while an active ticket exists.
- H2 database with sample data (`data.sql`).
- Postman collection in `postman/ParkingLot.postman_collection.json`.

## Build & Run
```bash
./mvnw spring-boot:run
# Or with Maven installed:
mvn spring-boot:run
```
Then sign in with Google at `/oauth2/authorization/google` (provide `GOOGLE_CLIENT_ID/SECRET` as env vars).

H2 console: `/h2-console` (JDBC URL: `jdbc:h2:mem:parkingdb`)

## API Flow
1. **Entry** (USER): `POST /api/tickets/entry`
```json
{"plateNo":"KA01AB1234","type":"CAR","entryGateId":1}
```
2. **Exit**: `POST /api/tickets/exit`
```json
{"ticketId": 1}
```
3. **Pay**: `POST /api/payments/pay`
```json
{"ticketId": 1, "paymentMethod": "CARD"}   // use "FAIL" to simulate failure
```
4. **Admin: Pricing**
- Upsert: `POST /api/admin/pricing?type=CAR&freeMinutes=15&hourlyRate=60`
- List: `GET /api/admin/pricing`

## Notes
- The allocation strategy is pluggable via `SlotAllocationStrategy`; the default is `NearestFirstStrategy` (floor asc, slot asc). You can add strategies and choose by profile or config.
- For multiple entry gates distance calculation, extend slots with coordinates and implement a distance-based strategy.

## Transaction Demo
- Allocation (`/entry`) runs in a transaction with pessimistic lock to avoid double allocation.
- Payment (`/pay`) wraps payment + freeing slot + closing ticket in **one transaction**; if payment fails, the slot remains occupied.

## Roles
- All authenticated users have `ROLE_USER`. Assign `ROLE_ADMIN` via your IdP or extend `SecurityConfig` to map by email domain.

## Tests
- Basic `PricingServiceTest` included.

## Postman
Import `postman/ParkingLot.postman_collection.json`.

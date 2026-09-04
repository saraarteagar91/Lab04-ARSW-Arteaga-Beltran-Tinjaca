# ARSW Collaborative Architecture Board — Lab 04

Backend foundation for the ARSW Collaborative Architecture Board, built on the official Lab #4 starter.

The goal was **not** to practice REST syntax. The goal was to build a small backend with explicit architectural boundaries, dependency inversion, constructor injection, consistent error handling, tests, and architecture evidence — this is the baseline for Lab #5.

## Technology baseline

- Java 21
- Spring Boot 3.x
- Maven
- In-memory persistence for this lab

## Target architecture

```text
REST Controller
      |
      v
Application Service
      |
      v
BoardRepository (port)
      |
      v
InMemoryBoardRepository (adapter)
```

## What this backend provides

- Domain types with invariants: `Board`, `BoardElement`, `ElementType` (`domain/model`, no HTTP/persistence dependencies).
- Output port `BoardRepository` and its in-memory adapter `InMemoryBoardRepository`.
- `BoardApplicationService`: create / get / replace use cases, depending only on the `BoardRepository` port (constructor injection).
- Thin `BoardRestController` exposing `POST /api/boards`, `GET /api/boards/{boardId}`, `PUT /api/boards/{boardId}`.
- `GlobalExceptionHandler`: uniform `ApiError` contract for not-found boards, invalid requests, invalid domain input, and any unexpected error (no stack traces or internal messages ever leak to the client).
- Unit tests for the domain model invariants, the in-memory adapter, `BoardNotFoundException`, the application service, and MockMvc tests for the REST contract, including the board-not-found and invalid-element cases.

See `docs/api-contract.md` for the full REST contract, `docs/ADR-001-repository-boundary.md` for the repository-boundary decision, and `docs/architecture/` for the ArchiMate application view and class diagram.

## Run

```bash
mvn spring-boot:run
```

The app serves a small landing page at:

```text
http://localhost:8080/
```

## Try the API

```bash
curl -X POST http://localhost:8080/api/boards -H "Content-Type: application/json" -d "{\"name\":\"Architecture Session\"}"

curl http://localhost:8080/api/boards/{boardId}

curl -X PUT http://localhost:8080/api/boards/{boardId} -H "Content-Type: application/json" -d "{\"name\":\"Renamed\",\"elements\":[]}"
```

## Verify

```bash
mvn test
```

All tests (application service + REST controller) pass, including the case where a board does not exist.

## Continuity rule

Your completed Lab 04 repository becomes the conceptual baseline for **Lab 05 — Interactive Board**. Avoid unnecessary changes to contracts and package boundaries.

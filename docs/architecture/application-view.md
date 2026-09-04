# ArchiMate Application View — Lab 04

Modeled using ArchiMate 3.2 Application Layer concepts (Application Component, Application Interface, Data Object). Rendered as a Mermaid diagram — no Archi/Draw.io installation was available in this environment — with the exact notation mapping documented below so it can be redrawn in Archi/Draw.io if required.

```mermaid
flowchart TB
    subgraph ext["External"]
        client["Web Client\n(later labs)"]
    end

    subgraph app["ARSW Collaborative Board — Application Layer"]
        iface["«Application Interface»\nREST Interface\n/api/boards"]
        controller["«Application Component»\nBoardRestController"]
        service["«Application Component»\nBoardApplicationService\n(create / get / replace use cases)"]
        port["«Application Interface»\nBoardRepository (port)"]
        adapter["«Application Component»\nInMemoryBoardRepository (adapter)"]
        data[("«Data Object»\nBoard data\n(in-memory map)")]
        errors["«Application Component»\nGlobalExceptionHandler\n(uniform ApiError)"]
    end

    client -- "HTTP/JSON" --> iface
    iface --> controller
    controller -- "invokes" --> service
    service -- "depends on (DIP)" --> port
    port -. "implemented by" .-> adapter
    adapter -- "reads/writes" --> data
    controller -. "delegates error translation" .-> errors
    service -. "raises domain/app exceptions" .-> errors
```

## Notation mapping

| Diagram element | ArchiMate concept |
|---|---|
| `REST Interface` | Application Interface |
| `BoardRestController` | Application Component (exposes the interface, no business logic — RA-01) |
| `BoardApplicationService` | Application Component (realizes the Application Service / use cases) |
| `BoardRepository (port)` | Application Interface (output port, owned by the application boundary — RA-02) |
| `InMemoryBoardRepository (adapter)` | Application Component (realizes the port — RA-03) |
| `Board data (in-memory map)` | Data Object |
| `GlobalExceptionHandler` | Application Component (cross-cutting, centralizes the error contract — RA-05) |

This view matches the package structure actually implemented:
`infrastructure.web.rest` → `application.service` → `application.port.out` → `infrastructure.persistence`.

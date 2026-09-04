# Class Diagram — Lab 04

Only the classes/interfaces that explain the main structure and their real dependency direction (RA-06).

```mermaid
classDiagram
    class BoardRestController {
        -BoardApplicationService service
        +create(CreateBoardRequest) ResponseEntity~Board~
        +get(String boardId) Board
        +replace(String boardId, ReplaceBoardRequest) Board
    }

    class BoardApplicationService {
        -BoardRepository repository
        +createBoard(String name) Board
        +getBoard(String boardId) Board
        +replaceBoard(String boardId, String name, List~BoardElement~) Board
    }

    class BoardRepository {
        <<interface>>
        +save(Board) Board
        +findById(String boardId) Optional~Board~
        +existsById(String boardId) boolean
    }

    class InMemoryBoardRepository {
        -Map~String, Board~ boards
        +save(Board) Board
        +findById(String boardId) Optional~Board~
        +existsById(String boardId) boolean
    }

    class Board {
        <<record>>
        +String id
        +String name
        +List~BoardElement~ elements
    }

    class BoardElement {
        <<record>>
        +String id
        +ElementType type
        +double x
        +double y
        +double width
        +double height
        +String text
    }

    class ElementType {
        <<enumeration>>
        RECTANGLE
        TEXT
    }

    class BoardNotFoundException {
        -String boardId
    }

    class GlobalExceptionHandler {
        +boardNotFound(BoardNotFoundException) ResponseEntity~ApiError~
        +invalidRequest(MethodArgumentNotValidException) ResponseEntity~ApiError~
        +invalidDomainInput(IllegalArgumentException) ResponseEntity~ApiError~
        +malformedRequestBody(HttpMessageNotReadableException) ResponseEntity~ApiError~
        +starterTodo(UnsupportedOperationException) ResponseEntity~ApiError~
        +unexpected(Exception) ResponseEntity~ApiError~
    }

    class ApiError {
        <<record>>
        +Instant timestamp
        +int status
        +String code
        +String message
        +String path
    }

    BoardRestController --> BoardApplicationService : uses
    BoardApplicationService --> BoardRepository : depends on (DIP)
    InMemoryBoardRepository ..|> BoardRepository : implements
    BoardApplicationService --> Board : creates/returns
    BoardApplicationService --> BoardNotFoundException : throws
    Board *-- BoardElement : contains
    BoardElement --> ElementType : has
    GlobalExceptionHandler --> BoardNotFoundException : handles
    GlobalExceptionHandler --> ApiError : builds
```

## Key dependency directions (verifiable in code)

- `BoardRestController` → `BoardApplicationService` → `BoardRepository` (interface). No arrow points from `BoardApplicationService` to `InMemoryBoardRepository` (RA-02).
- `InMemoryBoardRepository` is the only class that implements `BoardRepository` and the only class touching the `Map` (RA-03/RA-07).
- `domain.model` classes (`Board`, `BoardElement`, `ElementType`) have no dependency on Spring, HTTP, or persistence types.

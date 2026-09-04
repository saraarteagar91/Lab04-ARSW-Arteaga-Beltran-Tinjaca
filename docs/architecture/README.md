# Architecture Evidence — Lab 04

1. **[ArchiMate Application View](application-view.md)** — REST interface, application service, repository port/adapter, in-memory data object, and error handler, with the ArchiMate concept each element maps to.

2. **[Class diagram](class-diagram.md)** — `BoardRestController`, `BoardApplicationService`, `BoardRepository`, `InMemoryBoardRepository`, the relevant domain model, and the error-handling classes.

## Quality rule

The diagrams describe the code actually delivered in this repository (see `src/main/java/edu/eci/arsw/collabboard`) — no decorative boxes, no framework classes beyond what carries architectural meaning.

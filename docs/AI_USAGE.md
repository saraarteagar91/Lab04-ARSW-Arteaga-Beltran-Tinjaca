# AI Usage Declaration

Declaring AI use does not reduce the grade. You must be able to explain and validate every submitted decision.

| Tool | Activity | Prompt / purpose | How I validated the result | What I changed / rejected |
|---|---|---|---|---|
| Claude Code (Sonnet 5) | Implement `BoardApplicationService` use cases (create/get/replace) | Asked to complete the `TODO LAB-04` methods following the port already defined by `BoardRepository`, keeping id generation server-side and using `BoardNotFoundException` for missing boards | Ran `mvn test` and manually re-read each method against the lab's RA-01..RA-07 requirements before accepting | None rejected; kept the generated code as-is since it matched the required DIP structure |
| Claude Code (Sonnet 5) | Implement `InMemoryBoardRepository` (upsert semantics) and finish the `BoardRepository` port javadoc | Asked to decide and document save/replace semantics per the existing TODO comments | Verified with unit tests that create + replace both work through the same `save` method and that replace preserves the original id | Removed a suggestion to add defensive copying, since `Board`/`BoardElement` are already immutable records — unnecessary |
| Claude Code (Sonnet 5) | Add a catch-all `Exception` handler in `GlobalExceptionHandler` | Asked how to guarantee no stack trace or internal Java message ever reaches the client, per RA-05 | Confirmed the handler returns a fixed generic message (`"Unexpected server error"`), not `ex.getMessage()` | Accepted as proposed |
| Claude Code (Sonnet 5) | Write unit tests (`BoardApplicationServiceTest`) and MockMvc REST tests (`BoardRestControllerTest`) | Asked to enable the disabled test, add a replace-flow test, a not-found-on-replace test, and HTTP-level tests covering create/get/replace and the 404/400 error contract | Executed `mvn test` locally; all 7 tests pass (`BUILD SUCCESS`) | None rejected |
| Claude Code (Sonnet 5) | Draft `docs/api-contract.md` and `docs/ADR-001-repository-boundary.md` | Asked to document the actual implemented contract and the repository-boundary decision with the required ADR sections | Cross-checked every field/status code against the real controller and exception handler code, not from memory | Adjusted the ADR's "Trade-off" section to be honest about the added indirection instead of only listing benefits |

No architecture, package structure, or domain model was invented by the AI beyond what the starter/lab guide specified — the AI completed the marked `TODO LAB-04` items and documentation within the existing structure.

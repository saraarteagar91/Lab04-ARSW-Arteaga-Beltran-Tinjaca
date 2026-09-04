# REST Contract — Lab 04

Base path: `/api/boards`

| Method | Resource | Request | Success response | Error cases |
|---|---|---|---|---|
| POST | `/api/boards` | `{ "name": "string (required, not blank)" }` | `201 Created` + created `Board` (server-generated `id`, empty `elements`) | `400 INVALID_REQUEST` if `name` is blank/missing |
| GET | `/api/boards/{boardId}` | - | `200 OK` + `Board` | `404 BOARD_NOT_FOUND` if `boardId` does not exist |
| PUT | `/api/boards/{boardId}` | `{ "name": "string (required)", "elements": [BoardElement...] (required) }` | `200 OK` + updated `Board` (same `id`, new `name`/`elements`) | `404 BOARD_NOT_FOUND` if `boardId` does not exist; `400 INVALID_REQUEST`/`400 INVALID_INPUT` if the payload or an element is invalid |

## Board representation

```json
{
  "id": "5e6f2c2a-...-uuid",
  "name": "Architecture Session",
  "elements": [
    {
      "id": "el-1",
      "type": "RECTANGLE",
      "x": 10.0,
      "y": 20.0,
      "width": 120.0,
      "height": 80.0,
      "text": ""
    }
  ]
}
```

`type` is one of: `RECTANGLE`, `TEXT`.

## Error contract

Every error response (thrown from the application or infrastructure layer) is translated by `GlobalExceptionHandler` into the same shape:

```json
{
  "timestamp": "2026-09-04T12:00:00Z",
  "status": 404,
  "code": "BOARD_NOT_FOUND",
  "message": "Board not found: missing-board",
  "path": "/api/boards/missing-board"
}
```

| HTTP status | `code` | When |
|---|---|---|
| 404 | `BOARD_NOT_FOUND` | `GET`/`PUT` on a `boardId` that does not exist |
| 400 | `INVALID_REQUEST` | Bean validation failure on the request body (e.g. blank `name`) |
| 400 | `INVALID_INPUT` | Domain invariant violated (e.g. invalid `BoardElement`) |
| 500 | `INTERNAL_ERROR` | Any unexpected exception — no internal message or stack trace is ever returned |

## Deviations from the starter template

None. The contract implemented matches the starter template exactly (create/get/replace, same error shape).

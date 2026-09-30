# TypeScript rules

- Node 22, TypeScript strict mode. No `any`; use `unknown` or precise types.
- Named exports only, no default exports. One solution function per file.
- Tests use Vitest with `it.each` and object cases (`{ input, expected }`). Cover the empty input, a single element, duplicates, negatives, and the no-answer case where relevant.
- Prefer `Map`/`Set` over plain objects for hash lookups.
- Run `npm test` after every change and `npm run typecheck` before calling a problem done.
- Do not add dependencies without asking.

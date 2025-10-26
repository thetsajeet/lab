# Spring Query Parameters

- to use queryParams in API -> `@RequestParam()` in the method argument.
- can pass in params to the annotation such as name, value, required, etc.
- manually handle all query param methods
- alternate for complex queries:
  - Spring Data JPA's JpaSpecificationExecutor
  - Query by Example (QBE).
  - For advanced use cases, Querydsl is also an excellent option.

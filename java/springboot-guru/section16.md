# Pagination and Sorting

- paging in sql -> limit and offset
- sorting in sql -> order by
- limit is memory of JVM (so page data)
- PageRequest
  - page number and size
  - Page is an interface for the result of paged query
  - get next and previous Pagable
  - replace List<Y> with Page<Y>
- Sort
  - add it to the page request by creating a Sort object

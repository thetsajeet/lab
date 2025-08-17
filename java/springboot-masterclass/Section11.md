# Section 11 - Pagination

- Jackson is used for serialization and deserialization of data
- DTO pattern is used for transfering data required for further processing instead of changing underlying data.
- Data Transfer Object patterns
- ModelMapper package to convert from one object structure to another structure
- @RequestParam to get queryParams
- Pageable from data.domain is an interface that let us create a pagination data with pageNumber, pageSize, sorting, etc.
- PageRequest is an implementation of Pageable
- `Pageable page = PageRequest.of(pageNumber, pageSize);`
- `Page<Category> categoryPage = categoryRepository.findAll(categoryPage);`
- `List<Category> categories = categoryPage.getContent();`
- for using sort-> `Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();`
- `Pageable page = PageRequest.of(pageNumber, pageSize, sort);`

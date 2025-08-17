# Section 10 - Validations

- Hibernate validator
- Used to validate inputs
- eg: @NotNull, @NotEmpty, @Email
- JPA makes use of hibernate (ORM)
- @Valid in controller methods checks if the incoming request is valid by checking the Hibernate's validator defined in request
- @RestControllerAdvice (intercept any exception thrown by any application) and @ExceptionHandler (define methods for exceptions) together used in creating a global exception handler.

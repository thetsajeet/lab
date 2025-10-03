# Lombok

- Lombok hooks in via the Annotation Processor API
- AST is passed to Lombok for code generation before Java compiles
- No run-time performance, since code is generated at compile time.
- val -> final local variable
- var -> declare mutable local variable
- @Getter, @Setter, @ToString
- @EqualsAndHashCode
- @NoArgsConstructor, @AllArgsConstructor, @RequiredArgsConstructor
- @Data -> getter, setter, tostring, EqualsAndHashCode, RequiredArgsConstructor,
- @Value -> immutable variant of Data
- @NonNull
- @Builder -> builder pattern
- @Synchronized
- @Log
- @Slf4j logger
- Delombok tool in intellij

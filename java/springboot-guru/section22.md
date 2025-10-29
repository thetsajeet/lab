# Spring Security HTTP basic

- not recommended for entreprise grade projects
- spring-boot-starter-security
- user generated security password will be displayed
- set basic auth in header: `user, password`
- passed as encoded value: `Basic encodedpassword` in Authorization
- customize uesrname and password in `application.properties`
- to pass test cases: `spring-security-test`
- `.with(httpBasic(name, pass))`
- for create requests -> invalid CSRF token.
- CSRF tokens help protect against Cross-Site Request Forgery attacks
- TODO:// learn more about CSRF
- we disable this in springboot with @configuration for "/api/\*\*"
- flow: authenticate http requests with http basic and ignore csrf for "/api/\*\*"
- // todo: integrate with rest template

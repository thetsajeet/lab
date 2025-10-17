# MySQL with Springboot

- JDBC - Java DataBase Connectivity (API)
- All major relational databases have a db specific JDBC driver
- To use mysql with springboot, bring in jdbc dependency in pom.xml
- create a `.properties` file for activating a specific profile. you can have multiple properties file or have profile in default application.properties file
- to show and format sql, check properties in hibernate (show_sql, format_sql)
- UUID won't work directly in MySQL -> convert to String
- Hikari for managing connection pool with database
- Can control connection pool via springboot's application.properties
- hibernate allows to generate sql script for the things that are run

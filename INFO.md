Technical Details:
Java 8
Spring Boot
Spring JPA
H2
Maven 3.6.0
Mockito
SpringJunit
SpringBCrypt --> password hashing and salting.

API url:
http://localhost:8080/user/registration/register
request Body --> "application/json"
{
  "dateOfBirth": "string",
  "password": "string",
  "ssn": "string",
  "userName": "string"
}

swagger:
http://localhost:8080/swagger-ui.html

H2 Console:
http://localhost:8080/h2-console
db properties:
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

Domain object:
SELECT * FROM USER;
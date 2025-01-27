# elevator-assessment
technical assessment for elevator


follow these steps to run a Spring Boot application with updated database credentials and access the Swagger UI:  
Update Database Credentials in application.yml: Open the application.yml file and update the database credentials as needed. For example:  
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/your_database
    username: your_username
    password: your_password


Run the Application: Open a terminal and navigate to the root directory of your Spring Boot project. Run the application using Gradle:  
./gradlew bootRun

Alternatively, you can run the application from your IDE (IntelliJ IDEA) by right-clicking the main class (annotated with @SpringBootApplication) and selecting "Run". 

Access Swagger UI: Once the application is running, open a web browser and navigate to:  
http://localhost:9090/swagger-ui/index.html#/

This URL should display the Swagger UI with API documentation.

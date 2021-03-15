# Dice Roll Application

This is a Java 8 / Maven / Spring Boot (version 2.4.3) application. It Spring Data with JPA/Hibernate to store the data in an H2 database.

### How to Run
This application is packaged as a war which has Tomcat 8 embedded
- You can build it and run tests by running ```mvn clean package```
- Once built you may run it using ```mvn spring-boot:run```

### About the application
This application simulates dice rolls. A dice roll simulation is defined by the following factors:
- Dice Count - Number of dice per roll
- Side Count - Number of sides of each dice
- Roll Count - Total number of rolls in a single simulation

### The REST-ful API
The REST API exposes endpoints for simulating dice rolls & generating reports for dice rolls.

#### Simulate dice roll
```
POST /api/roll/
```
#### Get count summary report
```
GET /api/roll/report/count-summary/{diceCount}/{sideCount}
```
#### Get relative distribution report
```
GET /api/roll/report/relative-distribution/{diceCount}/{sideCount}
```

### Swagger
The API is documented using Swagger OpenAPI. Run the server and browse it in [localhost:8080/swagger-ui.html](localhost:8080/swagger-ui.html)

Questions and comments: sonnyjustine06@gmail.com


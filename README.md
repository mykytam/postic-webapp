# Postic Web Application
It's a Twitter like web app made with Spring, in which you can create, edit and like posts and subscribe on other users.


**Technologies used:**
- Java
- Spring Boot/ Mail/ Security/ Testing
- Turbolinks
- Flyway
- PostgreSQL
- Bootstrap
- Heroku

> You can view website here -> https://postic.herokuapp.com/

**Operating procedure:**
- Phase 1: Created a simple web application with Spring Boot
- Phase 2: Added a database to Spring application
- Phase 3: Added Spring Security (users and authorization) to the application
- Phase 4: Added a relationship between database tables
- Phase 5: Moved application from Mustache template library to Freemarker
- Phase 6: Added user administration panel and user access control using the hasAuthority annotation from Spring Securit
- Phase 7: Added a mechanism to upload files and configured access to static files such as pictures, styles and JS
- Phase 8: Сonnected the Bootstrap library to the project
- Phase 9: Added user email alerts, and user account activation
- Phase 10: Using flyway for database versioning. Added a default administrator to the database and user profile page
- Phase 11: Added passwords encryption and validation of data coming to the server
- Phase 12: Added rememberMe and storing sessions in the database
- Phase 13: Added oneToMany communication between the user and messages, made a user message page and a message editing button
- Phase 14: Added manyToMany communication between users, which allowed to form channel-subscriber communications
- Phase 15: Added integration tests with the Spring Testing Framework
- Phase 16: Added pagination
- Phase 17: Added "likes" to the application using Hibernate HQL
- Phase 18: Creating the Blog Archive & Add Pagination
- Phase 19: Speeded up page display using Turbolinks

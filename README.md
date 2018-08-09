# Backend modules
This project is enterprise - ready features pack built on top of Spring Boot. It provides few predefined configurations,
entities, properties and many more.

### svm-backend-core
Shared functionality which is used across whole project.
- Internationalization.

### svm-backend-data-core
- Migrations;
- Database level security filters;
- JPA unique value checking;
- Exception handler implementations;
- Querydsl default bindings;

### svm-backend-data-jpa
Contains some predefined entities and repositories. It uses `@EntityScan` annotation internally, so if you import one of these
projects, make sure you defined same `@EntityScan` annotation on main Application class.

### svm-backend-data-jpa-core
- UUID primary key generator with ability to set id manually;
- Convenient list to string and json converters;

### svm-backend-parent
Base projects to inherit from. Contains different plugins configurations, dependency managements and parameters definitions.

### svm-backend-security
- OAuth2 authorization server and resource server basic configurations;
- Stateless OAuth2 client;
- Exception handling implementations;

### svm-backend-sms
Core features and implementations to work with SMS gateways;

### svm-backend-web
- Custom exception handling;
- Base configurations;

See concrete project for details.
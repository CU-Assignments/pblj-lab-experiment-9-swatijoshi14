# PBLJ-Experiment-9 (Spring Basic and Hibernate) 
## Upload Assignment and Experiment

## Introduction
### Spring Framework
Spring is a lightweight Java framework for building enterprise applications. It provides features like Dependency Injection (DI), Inversion of Control (IoC), and simplifies web development using Spring MVC and Spring Boot.

-----
### Hibernate ORM
Hibernate is an Object Relational Mapping (ORM) tool for Java. It maps Java classes to database tables, simplifies CRUD operations, and reduces the need for boilerplate SQL code.

-----
### Spring + Hibernate Integration
Spring manages application layers and transactions, while Hibernate handles database interactions. Together, they enable clean, scalable, and maintainable enterprise application development.

-----

## Topic Covered
- Introduction, Spring IoC, Dependency Injection, Hibernate ORM, annotations, configuration,
- CRUD operations, SessionFactory, Transactions, and integration with Spring.

## 📌 Experiment 9:Create Java applications using Spring and Hibernate for dependency injection, CRUD operations, and transaction management.

| Program | Description | Requirements |
|---------|-------------|--------------|
| **[9.1 - Dependency Injection](/Exp9.1.java)** | Create a simple Spring application using Java-based configuration to demonstrate Dependency Injection (DI). | - Define a `Course` class with `courseName` and `duration`.<br>- Define a `Student` class with `name` and a reference to `Course`.<br>- Use `@Configuration` and `@Bean` for Java-based configuration.<br>- Load Spring context in `main()` and print student details. |
| **[9.2 - Hibernate CRUD](/Exp9.2.java)** | Develop a Hibernate-based application to perform CRUD operations on a `Student` entity with MySQL. | - Configure Hibernate using `hibernate.cfg.xml`.<br>- Create `Student.java` entity with `id`, `name`, `age`.<br>- Use Hibernate `SessionFactory` to perform CRUD.<br>- Test functionality with sample data. |
| **[9.3 - Spring + Hibernate Transactions](/Exp9.3.java)** | Create a banking system with Spring and Hibernate to manage money transfers using transactions. | - Configure Spring with Hibernate ORM.<br>- Create `Account.java` and `Transaction.java` entities.<br>- Use Hibernate Transaction Management.<br>- Ensure rollback on failure.<br>- Demonstrate both success and failure cases. |


### How to Submit (ZIP File)
- Ensure all required files are included (HTML, JSP, Servlet .java files, web.xml, database scripts).
- Test your project on Tomcat before submission.
- Zip the complete folder and name it Exp9_YourName/uid.zip.


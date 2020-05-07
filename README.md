# Simply Shop

San Jose State University
Project submitted for Babu Thomas's Enterprise Software - CMPE172 Spring 2020 class. 
Created by Richard Pham, Adam Ball, and Christina Nguyen.

### Table of Contents

- [Project Introduction](https://github.com/richardphamsjsu2016/172project#project-introduction)
- [Screenshots](https://github.com/richardphamsjsu2016/172project#screenshots)
- [Pre-requisites for Set-up](https://github.com/richardphamsjsu2016/172project#pre-requisites-for-set-up)
- [How to Run the Project](https://github.com/richardphamsjsu2016/172project#how-to-run-the-project)
- [Database Schema and Example Queries](https://github.com/richardphamsjsu2016/172project#database-schema-and-example-queries)
- [Spring Boot API Endpoints](https://github.com/richardphamsjsu2016/172project#spring-boot-api-endpoints)
- [Frontend to Backend Data Transport](https://github.com/richardphamsjsu2016/172project#frontend-to-backend-data-transport)

### Project Introduction



### Screenshots



### Pre-requisites for Set-up



### How to Run the Project



### Database Schema and Example Queries



### Spring Boot API Endpoints



### Frontend to Backend Data Transport

---

The springboot app can be dockerized. To do so:
1) Load the project onto Eclipse IDE for Java EE Developers 2020-03.
2) Generate jar by right clicking entire project and clicking Run as “Maven Install."
4) Run docker.
5) Go to the file location of the project.
6) Type docker build -t simplyshop-docker.jar .
7) Type docker image ls
8) Type docker run -p 9090:8080 simplyshop-docker.jar

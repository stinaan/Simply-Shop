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
- [Home Page](https://github.com/richardphamsjsu2016/172project/blob/master/Screenshots/Home.png)
- [View Items](https://github.com/richardphamsjsu2016/172project/blob/master/Screenshots/Item_View.png)
- [Edit, Delete, Create Items](https://github.com/richardphamsjsu2016/172project/blob/master/Screenshots/Manage.png)
- [Style Reference](https://github.com/richardphamsjsu2016/172project/blob/master/Screenshots/Style_Reference.png)


### Pre-requisites for Set-up



### How to Run the Project



### Database Schema and Example Queries



### Spring Boot API Endpoints



### Frontend to Backend Data Transport

---

The link(s) for the application are attached below:

[Main application, including frontend](http://52.87.248.183:3000/)

[Springboot backend](http://ec2-54-164-61-67.compute-1.amazonaws.com:8080/)


The Spring Boot app can be dockerized, and it was dockerized before deploying it on AWS EC2. The dockerized file has not been provided here; only the .jar file is. To manually dockerize the app:
1) Load the project into Eclipse IDE for Java EE Developers 2020-03.
2) Inside the project folder, create a file called “Dockerfile”. Make sure to keep the capital “D”. 
3) Fill in the Dockerfile with the following information:

FROM java:8


EXPOSE 5000


ADD target/simplyshop.jar simplyshop.jar


ENTRYPOINT ["java","-jar","/simplyshop.jar"]

4) Generate jar by right clicking the entire project and clicking Run as “Maven Install."
5) Run docker.
6) Go to the file location of the project.
7) Type docker build -t simplyshop.jar .
8) Type docker image ls
9) Type docker run -p 9090:5000 simplyshop.jar
10) On your browser, go to localhost:9090/api/items to see the list of items.

If you are deploying to EC2, use this text for Dockerfile instead:

FROM java:8


WORKDIR /


ADD simplyshop.jar simplyshop.jar


COPY application.properties application.properties


EXPOSE 5000


CMD java -jar simplyshop.jar -Dspring.config.location=application.properties

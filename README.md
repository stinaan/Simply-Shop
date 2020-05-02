# 172project
Simply Shop app for Babu Thomas's 172 class. Richard Pham, Adam Ball, and Christina Nguyen


The springboot app can be dockerized. To do so:
1) Load the project onto Eclipse IDE for Java EE Developers 2020-03.
2) Generate jar by right clicking entire project and clicking Run as “Maven Install."
4) Run docker.
5) Go to the file location of the project.
6) Type docker build -t simplyshop-docker.jar .
7) Type docker image ls
8) Type docker run -p 9090:8080 simplyshop-docker.jar

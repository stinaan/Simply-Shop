FROM java:8
WORKDIR /
ADD simplyshop.jar simplyshop.jar
COPY application.properties application.properties
EXPOSE 5000
CMD java -jar simplyshop.jar -Dspring.config.location=application.properties

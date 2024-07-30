FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY target/ebanking-backend-0.0.1-SNAPSHOT.jar ms-user-management.jar
ENTRYPOINT ["java","-jar","/ms-user-management.jar"]

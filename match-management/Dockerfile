FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY target/match-management-0.0.1-SNAPSHOT.jar match-management.jar
ENTRYPOINT ["java","-jar","/match-management.jar"]

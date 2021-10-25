FROM openjdk:12-jdk-alpine
LABEL maintainer="arutyun88@hotmail.com"
ARG JAR_FILE
COPY ${JAR_FILE} producer.jar
ENTRYPOINT ["java", "-jar", "/producer.jar"]
FROM openjdk:12-jdk-alpine
LABEL maintainer="arutyun88@hotmail.com"
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} consumer.jar
ENTRYPOINT ["java", "-jar", "/consumer.jar"]
FROM openjdk:12-jdk-alpine
ARG JAR_FILE
COPY ${JAR_FILE} rsocket_server.jar
ENTRYPOINT ["java", "-jar", "/rsocket_server.jar"]
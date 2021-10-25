FROM openjdk:12-jdk-alpine
LABEL maintainer="agevorkian@neoflex.ru"
ARG JAR_FILE
COPY ${JAR_FILE} rsocket_client.jar
ENTRYPOINT ["java", "-jar", "/rsocket_client.jar"]
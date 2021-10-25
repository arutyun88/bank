FROM openjdk:12-jdk-alpine
VOLUME /src/main/resources/static/files
ARG JAR_FILE
COPY ${JAR_FILE} account.jar
ENTRYPOINT ["java", "-jar", "/account.jar"]
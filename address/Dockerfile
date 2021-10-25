FROM openjdk:12-jdk-alpine
ARG JAR_FILE
COPY ${JAR_FILE} address.jar
ENTRYPOINT ["java", "-jar", "/address.jar"]
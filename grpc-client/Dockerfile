FROM openjdk:12-jdk-alpine
ARG JAR_FILE
COPY ${JAR_FILE} grpc_client.jar
ENTRYPOINT ["java", "-jar", "/grpc_client.jar"]
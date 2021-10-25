FROM openjdk:12-jdk-alpine
LABEL maintainer="agevorkian@neoflex.ru"
ARG JAR_FILE
COPY ${JAR_FILE} address.jar
ENTRYPOINT ["java", "-jar", "/address.jar"]
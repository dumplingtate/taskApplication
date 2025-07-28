FROM openjdk:24-jdk-slim
COPY target/*.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]
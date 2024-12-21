FROM openjdk:17-jdk-slim
COPY build/libs/SearchWeb-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]
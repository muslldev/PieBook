FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY target/*.jar piebook.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "piebook.jar"]
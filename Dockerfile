FROM maven:3.8.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .

RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

COPY --from=builder /app/target/piebook-3.1.4.jar piebook.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "piebook.jar"]
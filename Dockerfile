# ---------- Stage 1: build the Spring Boot jar with Maven ----------
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -B dependency:go-offline
COPY src ./src
RUN mvn -q -B package -DskipTests

# ---------- Stage 2: lightweight image that only runs the jar ----------
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/horsecare-1.0.0.jar app.jar
ENV PORT=8080
EXPOSE 8080
# Keep memory low so it fits in free cloud plans (512 MB)
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "app.jar"]

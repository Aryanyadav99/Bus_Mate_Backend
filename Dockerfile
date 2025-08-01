# ===== Stage 1: Build the app using Maven =====
FROM maven:3.8.4-openjdk-17 AS build

# Set working directory inside the container
WORKDIR /app

# Copy pom.xml and download dependencies (for caching)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the entire source code
COPY src ./src

# Build the application and skip tests
RUN mvn clean package -DskipTests

# Use official OpenJDK base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file from the local machine into the container
COPY --from=build /app/target/BusBackend-0.0.1-SNAPSHOT.jar .

# Expose port (optional: match your app port, usually 8080)
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app/BusBackend-0.0.1-SNAPSHOT.jar"]

# Use OpenJDK base image
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the built jar file (update the jar name accordingly)
COPY target/library-0.0.1-SNAPSHOT.jar app.jar

# Expose port (match with your server.port in application.properties if changed)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

# Use OpenJDK 17 image as the base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the .jar file from the swe folder into the container as app.jar
COPY swe-0.0.1-SNAPSHOT.jar

# Expose the application on port 8080
EXPOSE 8080

# Define the command to run the JAR file when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]

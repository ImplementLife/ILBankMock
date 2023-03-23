# Base image
FROM openjdk:17-jdk

# Set working directory
WORKDIR /app

# Copy the application jar file
COPY target/bank-mock-1.0.jar bank-mock-1.0.jar

# Expose port 8080
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "bank-mock-1.0.jar"]
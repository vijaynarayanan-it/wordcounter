FROM openjdk:8-alpine

# Working directory creation
WORKDIR /app

# Copy the JAR file into the app container
COPY target/*jar /app/word-counter.jar

# Set the entrypoint command to run your application
ENTRYPOINT ["java", "-jar", "/app/word-counter.jar"]
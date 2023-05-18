FROM maven:3.8.1-openjdk-17-slim
# AS build

WORKDIR /app

# Copy the Maven project file
COPY tool-library-java-backend/pom.xml .

# Resolve dependencies (only if pom.xml has changed)
RUN mvn dependency:go-offline -B

# Copy the source code
COPY tool-library-java-backend/src ./src

# Build the application
RUN mvn package

# Use a lightweight base image for the final container
#FROM openjdk:11

#WORKDIR /app

# Copy the compiled application from the build stage
#COPY --from=build /app/target/tool-library.jar .

# Expose the port the server uses
EXPOSE 8080

# Set the entry point to run your application
CMD ["java", "-jar", "/app/target/tool-library.jar"]
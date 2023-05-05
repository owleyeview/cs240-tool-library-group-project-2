# Use an official Java runtime as a parent image
FROM openjdk:11-jre-slim

# Set the working directory to /app
WORKDIR /app

# Copy all the required files into the container
COPY tool-library-java-backend/target/tool-library-java-backend-0.0.1-SNAPSHOT.jar /app/backend.jar
COPY docker-start.sh /app/
COPY react-frontend /app/frontend

# Expose port 3000 (the port the frontend uses)
EXPOSE 3000

# Install Node.js and npm for the frontend
RUN apt-get update && apt-get install -y nodejs npm

# Install dependencies for the frontend
RUN cd frontend && npm install

# Make the docker-start file executable
RUN chmod +x docker-start.sh

# Run the docker-start file (which starts both the frontend and backend)
CMD ["/bin/sh", "docker-start.sh"]
#!/bin/sh
echo "Starting backend..."

# run the backend and move the process to the background
java -jar tool-library-java-backend/target/tool-library-java-backend-0.0.1-SNAPSHOT.jar &

echo "Starting frontend..."

# run the frontend
cd react-frontend
npm start

wait
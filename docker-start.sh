#!/bin/sh

# this should be run as the command in docker

echo "Starting backend..."

# run the backend and move the process to the background
java -jar backend.jar > /app/backend-log.txt &

echo "Starting frontend..."

# run the frontend
cd frontend
npm start > /app/frontend-log.txt &
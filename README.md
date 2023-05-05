# cs240-tool-library-group-project-2
CS240 Full Stack App Group Project

Work in progress...

## Architecture
### Frontend
source code is found in the /react-frontend/src/ folder
#### Router
- Configure the UI related to components
- index.js file
#### Components
- Component-related UI logic
- hooks
- /components/ folder
#### Service
- REST API methods defined here
- The service-layer methods can be called from Components
- 3rd party libraries are required to make REST API calls (Axios)
	- could also use a JS fetch API
- 'consumes' the REST API
- /services/ToolService.js file

### Backend 
source code is found in the /tool-library-java-backend/src/main/java/com/whatcom/cs240/toollibrary folder
currently the Controller directly communicates with the DAO(Data Access Object), we might want to separate these and implement a service layer
#### Presentation Layer (Controller/API)
- This layer handles frontend requests (eg. HTTP REST calls), communicates with the service layer, and returns responses to the frontend
- Exposes REST endpoints
- /api/ToolControler.java file
#### Service Layer (Business and Validation Logic)
- validation and business logic lives here
- calls to other 3rd party services live here
#### Data Access Layer (DAO/Repository)
- responsible for communicating with the database
- takes calls from the service layer and translates to actual database queries
- this program uses the JPA library to manage database interactions.  Learn more here: https://spring.io/guides/gs/accessing-data-jpa/
- /dao/ToolDao.java file
### Database // currently built for a MySQL database
- if you run mysql server (or MySQL Workbench) on your local computer and create a database called tool_library, when you run the Java backend program ToolLibraryApplication.java, a database table for tools will automatically be created

## To do:
- create a custom list data structure to hold our tool objects (in ToolController.java or a new service layer?)
- implement the library Members objects (maybe with another custom data structure?)
- associate tools with members who own them
- integrate testing of all the Java methods with JUnit
- figure out how to handle and present the boolean value toolIsAvailable in the frontend
- polish the front end
- implement UUID for the id field/primary key
- host the app in the cloud?
- ?

## Helpful Programs
- Postman has been used to test the http REST API and make sure the backend is functioning properly

## Dependencies
- mysql server (in terminal on my mac, I ran `brew install mysql`)
- npm (node package manager from node.js)

## How to get it running locally

### Setup (do this once)
- in terminal, navigate to the folder you want the project to live in
- use `git clone https://github.com/owleyeview/cs240-tool-library-group-project-2.git` to download the project
- create a MySQL database named "tool_library"
- navigate to `tool-library-java-backend` directory
- run `mvn package`. Do this whenever you modify the backend.

### Running the program

- in terminal, navigate to root directory of this project
- run `./start.sh`
- that's it
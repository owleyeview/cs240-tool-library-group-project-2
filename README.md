# cs240-tool-library-group-project-2
CS240 Full Stack App Group Project

Work in progress...

## Architecture
### Frontend
- source code is found in the /react-frontend/src/ folder
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
// source code is found in the /tool-library-java-backend/src/main/java/com/whatcom/cs240/toollibrary folder
// currently the Controller directly communicates with the DAO(Data Access Object), we might want to separate these and implement a service layer
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
- if you run MySQL Workbench on your local computer and run the Java backend program ToolLibraryApplication.java, a database table for tools will automatically be created

## To do:
- create a custom list data structure to hold our tool objects (in ToolController.java or a new service layer?)
- implement the library Members objects (maybe with another custom data structure?)
- integrate testing of all the Java methods with JUnit
- polish the front end
- host the app in the cloud?

## Helpful Programs
- Postman has been used to test the http REST API and make sure the backend is functioning properly

## Dependencies
- npm (node package manager from node.js)

## How to get it running locally
- in terminal, navigate to the folder you want the project to live in
- use `git clone https://github.com/owleyeview/cs240-tool-library-group-project-2.git` to download the project
- I used VSCode to create the frontend javascript code and IntelliJ to create the backend code 
- open the tool-library-java-backend folder with your Java editor of choice
- open MySQL Workbench
- run ToolLibraryApplication.java (this should create a database table in MySQL Workbench)
- to open the frontend code with VSCode, `cd react-frontend`, `code .` or just open that folder from within VSCode
- in terminal, from the /react-frontend/ folder, type `npm start` to launch the frontend
- if the browser doesn't automatically open, navigate to  http://localhost:3000/ in your web browser to interact with the frontend

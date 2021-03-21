# pgr203konteeksamen-oleedv
pgr203konteeksamen-oleedv created by GitHub Classroom

## How to run this project

### Build and test executable .jar file

* Clone the project
* (Advised) Run `mvn clean`
* legg til `task-manager.properties file` Create pgr203.properties in dir `src/main/resources/pgr203.properties`
* Add `dataSource.url = jdbc:postgresql://localhost:5432/` followed up with your PostgreSQL database name
* Add `dataSource.username =` followed up with your PostgreSQL username
* Add `dataSource.password =` followed up with your PostgreSQL password
* Then run `mvn package`

### To run the code
* run the command `java -jar target/yatzygame-1.0-SNAPSHOT.jar` in terminal

## Functionality
The program has a user interface that can be visited using localhost: 8080 in the browser.
Once you have accessed the user interface, you can create a new player. After you have created a player
you will automatically be redirected to play.html. You can now enter inn a dice squence of ints seperated by commas.
eg `1,1,2,3,3` and select the player you want to play as. 



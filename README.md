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

## Questions(In norwegian)
*1. Hva er test-drevet utvikling? (Hvilke steg inngår i test-drevet utvikling, hva kjennetegner gode tester og hvilke fordeler får du fra testingen?)
*2. Hva er parprogrammering? (Hvordan fordeler man arbeidet mellom to partnere, hvilke fordeler gir teknikken?)
*3. Hva er continuous integration? (Hvordan har du benyttet Github Actions til å bygge koden din?)

`1. Test-drevet utvikling refererer til en stil programmering, bestående av tre aktiviteter: koding, testing (skriving av enhetstester) og design (refactoring).
Første steg i test-drevet utvikling er å skrive en enkel enhetstest som beskriver et aspekt av programmet. Kjør testen, som mislykkes fordi programmet mangler funksjonen. Så skriver du kode på enklest mulig måte som får testen til å bestå. Så refactorerer du koden.`

`2. Parprogrammering består av to programmerere som deler en datamaskin. En av programmene skal programmere mens den andre skal følge med og komme med innspill der det trengs. Det forventes at programmererne bytter roller med noen minutters mellomrom.`

`3. “Continuous integration” er en programvare praksis som krever ofte commits til den delt repository.Når du commite kode oppdager du oftere feil raskere og reduserer mengden kode en utvikler trenger å feilsøke når han finner en feil. Hyppige kode oppdateringer gjør det også enklere å slå sammen endringer fra forskjellige medlemmer av et programvareutvikling steam. `



### UML diagrams

![Http](https://i.imgur.com/ucp8N4y.png)
![Games](https://i.imgur.com/3SX4Y8u.png)
![Category](https://i.imgur.com/9NitIPH.png)
![YatzyGame](https://i.imgur.com/RjichrJ.png)

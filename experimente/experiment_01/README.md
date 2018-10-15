# Experiment 1 - MVP
Für die Projektwoche 3 - 4 (05.10.2018 bis 19.10.2018) ist ein kleiner Prototyp zu erstellen.
Dafür wurde die Architektur in Microservices aufgeteilt und daraus ein MVP (Most Viable Product) gewählt.

## Dependencies
- docker
- docker-compose

## Anleitungen
#### RabbitMq Server starten
Stelle sicher das Docker und Docker-Compose auf dem rechner installiert sind.
Öffne die docker-compose.yml Datei im Ordner /experimente/experiment_01 und stelle sicher das die Ports nicht bereits in Verwendung sind.
```
/experimente/experiment_01> docker-compose up
```
Wenn der Server gestartet wurde kannst du in deinem Browser über die Url: http://localhost:7000/ den RabbitMq Manager aufrufen. Logge dich mit folgenden Credentials ein.
```
Username: guest
Passwort: guest
```
#### Maven Project installieren
Um die .jar Dateien in den jeweiligen target Ordnern zu erstellen, kannst du folgenden Befehl ausführen:
```
mvn clean install
```
#### SalesManager Microservice starten
Um diesen Microservice zu starten überprüfe ob im target Ordner ein -jar-with-dependencies vorhanden ist. Dann führe einen der folgenden Befehl aus:
```
java -jar .\salesmanagement\target\salesmanagement-1.0-SNAPSHOT-jar-with-dependencies.jar
.\run-salesmanagement.ps1
```
#### WarehouseManager Microservice starten
Um diesen Microservice zu starten überprüfe ob im target Ordner ein -jar-with-dependencies vorhanden ist. Dann führe einen der folgenden Befehl aus:
```
java -jar .\warehousemanagement\target\warehousemanagement-1.0-SNAPSHOT-jar-with-dependencies.jar
.\run-warehousemanagement.ps1
```

#### CustomerManager Microservice starten
Um diesen Microservice zu starten überprüfe ob im target Ordner ein -jar-with-dependencies vorhanden ist. Dann führe einen der folgenden Befehl aus:
```
java -jar .\customermanagement\target\customermanagement-1.0-SNAPSHOT-jar-with-dependencies.jar
.\run-customermanagement.ps1
```
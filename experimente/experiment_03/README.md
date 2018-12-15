# Experiment 3 - Discovery Service
Für die Projektwoche 5 - 6 (19.10.2018 bis 02.11.2018) ist ein kleiner Prototyp zu erstellen.
In diesem Experiment wird der Verbindungsaufbau mit einem Discovery Service behandelt.

## Dependencies
- docker
- docker-compose
- Java 8
- Python 3.6 (siehe requirements.txt)

## Anleitungen
#### RabbitMq Server starten
Stelle sicher das Docker und Docker-Compose auf dem rechner installiert sind.
Öffne die docker-compose.yml Datei im Ordner /experimente/experiment_03 und stelle sicher das die Ports nicht bereits in Verwendung sind.
```
/experimente/experiment_03> docker-compose up
```
Wenn der Server gestartet wurde kannst du in deinem Browser über die Url: http://localhost:7000/ den RabbitMq Manager aufrufen. Logge dich mit folgenden Credentials ein.
```
Username: guest
Passwort: guest
```
#### Maven Project installieren
Um die .jar Dateien in den jeweiligen target Ordnern zu erstellen, kannst du folgenden Befehl ausführen:
```
/experimente/experiment_03> mvn clean install
```

#### Discovery Microservice starten
Um diesen Microservice zu starten überprüfe ob python 3.6 installiert ist und ob die requirements in requirements.txt erfüllt sind. Dann führe einen der folgenden Befehl aus:
```
/experimente/experiment_03/discovery_service> python app.py
```

#### SalesManager Microservice starten
Um diesen Microservice zu starten überprüfe ob im target Ordner ein -jar-with-dependencies vorhanden ist. Dann führe einen der folgenden Befehl aus:
```
/experimente/experiment_03> java -jar .\salesmanagement\target\salesmanagement-1.0-SNAPSHOT-jar-with-dependencies.jar
```
#### WarehouseManager Microservice starten
Um diesen Microservice zu starten überprüfe ob im target Ordner ein -jar-with-dependencies vorhanden ist. Dann führe einen der folgenden Befehl aus:
```
/experimente/experiment_03> java -jar .\warehousemanagement\target\warehousemanagement-1.0-SNAPSHOT-jar-with-dependencies.jar
```

#### Resultat
```
[INFO ] 2018-12-11 11:59:45.062 [pool-2-thread-1] WarehouseManagerApp - retrieving connection info for domain = order
[INFO ] 2018-12-11 11:59:46.281 [pool-2-thread-1] DiscoverStrategy - received connection = {
  "commandQueue": "ch.hslu.wipro.micros.OrderCommand",
  "eventQueue": "ch.hslu.wipro.micros.OrderEvent",
  "exchange": "ch.hslu.wipro.micros.Order"
}

[INFO ] 2018-12-11 11:59:46.403 [pool-3-thread-4] OrderCommandConsumer - received ch.hslu.wipro.micros.common.command.CheckOrderCommand
[INFO ] 2018-12-11 11:59:46.411 [pool-3-thread-4] OrderCommandConsumer - order confirmed!
```
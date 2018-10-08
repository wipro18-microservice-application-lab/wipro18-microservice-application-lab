# Experiment 1 - MVP
Für die Projektwoche 3 - 4 (05.10.2018 bis 19.10.2018) ist ein kleiner Prototyp zu erstellen.
Dafür wurde die Architektur in Microservices aufgeteilt und daraus ein MVP (Most Viable Product) gewählt.

## Dependencies
- docker
- docker-compose

## Anleitungen
#### Maven Project installieren
Um die .jar Dateien in den jeweiligen target Ordnern zu erstellen, kannst du folgenden Befehl ausführen:
```
mvn clean install
```
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
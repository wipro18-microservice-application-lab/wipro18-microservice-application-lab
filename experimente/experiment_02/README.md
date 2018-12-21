# Experiment 2 - Python Logger
Für die Projektwoche 5 - 6 (19.10.2018 bis 02.11.2018) ist ein kleiner Prototyp zu erstellen.

## Dependencies
- Python (V3.7.1)
- pika (V0.11.0)

## Anleitungen
#### RabbitMq Server starten -> Von Experiment 01
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
#### Python Logger starten
Der Logger benötigt Python 3 und pika. Es steht eine requirements-Datei zur Verfügung, welche die benötigte pika-Version enthält. Die Pyhton Datei get_logs.py kann einfach ausgeführt werden. Wenn anschliessend die Microservices SalesManagement und WarehouseManagement (siehe Experiment 01) gestartet werden, sollten Lognachrichten in die Python-Konsole geschrieben werden.
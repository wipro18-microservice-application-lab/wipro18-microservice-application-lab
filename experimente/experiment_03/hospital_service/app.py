from threading import Thread

import pika
from flask import Flask, jsonify

app = Flask(__name__)


def callback(ch, method, properties, body):
    f = open("dead-letters.txt", "a")
    f.write('{}\n'.format(body.decode('utf-8', 'ignore')))
    f.close()


def start_listen_dlq():
    print("start listening for dead letters..")
    connection = pika.BlockingConnection(pika.ConnectionParameters('localhost'))
    channel = connection.channel()

    channel.basic_consume(callback,
                          queue='ch.hslu.wipro.micros.DeadLetter',
                          no_ack=True)

    channel.start_consuming()


@app.route("/")
def hospital():
    messages = []
    f = open("dead-letters.txt", "r")
    for line in f:
        messages.append(line)

    return jsonify(messages)


if __name__ == '__main__':
    dead_letter_listener = Thread(target=start_listen_dlq)
    dead_letter_listener.start()

    app.run(debug=True, host='0.0.0.0', port=5001)

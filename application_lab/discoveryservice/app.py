import time
from threading import Thread

import pika
from pika import exceptions

DISCOVERY_EXCHANGE = 'ch.hslu.wipro.micros.Discovery'
REGISTER_COMMAND_QUEUE = 'ch.hslu.wipro.micros.discovery.RegisterCommand'
DISCOVER_COMMAND_QUEUE = 'ch.hslu.wipro.micros.discovery.DiscoverCommand'

CREDENTIALS = pika.PlainCredentials('guest', 'guest')
HOST = 'rabbitmq'

registered_services = []


def register_command_consumer(ch, method, properties, body):
    registered_services.append(body.decode('utf-8', 'ignore'))


def listen_for_register_commands():
    connection = pika.BlockingConnection(pika.ConnectionParameters(HOST, 5672, '/', CREDENTIALS))
    channel = connection.channel()

    channel.basic_consume(register_command_consumer,
                          queue=REGISTER_COMMAND_QUEUE,
                          no_ack=True)

    channel.start_consuming()


def discover_command_consumer(ch, method, properties, body):
    domain = body.decode('utf-8', 'ignore')

    discovery = [s for s in registered_services if domain in s]

    if discovery is not None:
        reply_properties = pika.BasicProperties(correlation_id=properties.correlation_id)

        ch.basic_publish(exchange='',
                         routing_key=properties.reply_to,
                         properties=reply_properties,
                         body=discovery[0])

        ch.basic_ack(delivery_tag=method.delivery_tag)
    else:
        ch.basic_reject(delivery_tag=method.delivery_tag, requeue=False)


def listen_for_discover_commands():
    connection = pika.BlockingConnection(pika.ConnectionParameters(HOST, 5672, '/', CREDENTIALS))
    channel = connection.channel()

    channel.basic_consume(discover_command_consumer,
                          queue=DISCOVER_COMMAND_QUEUE,
                          no_ack=True)

    channel.start_consuming()


def test_rabbitmq_connection_blocking(sleep_time):

    while True:
        time.sleep(sleep_time)

        try:
            connection = pika.BlockingConnection(pika.ConnectionParameters(HOST, 5672, '/', CREDENTIALS))
            channel = connection.channel()

            break
        except (pika.exceptions.AMQPChannelError, pika.exceptions.AMQPConnectionError):
            print("Connection was closed, retrying...")

    return channel


if __name__ == '__main__':
    test_rabbitmq_connection_blocking(sleep_time=5)

    Thread(target=listen_for_register_commands).start()
    Thread(target=listen_for_discover_commands).start()

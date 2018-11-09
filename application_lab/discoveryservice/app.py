import pika

DISCOVERY_EXCHANGE = 'ch.hslu.wipro.micros.Discovery'
REGISTER_EVENT_QUEUE = 'ch.hslu.wipro.micros.discovery.RegisterEventQueue'
DISCOVERY_COMMAND_QUEUE = 'ch.hslu.wipro.micros.discovery.DiscoverCommandQueue'


patients = []


def registered_event_consumer(ch, method, properties, body):
    patients.append(body)


def listen_for_register_events():
    connection = pika.BlockingConnection(pika.ConnectionParameters('localhost'))
    channel = connection.channel()

    channel.basic_consume(registered_event_consumer,
                          queue=REGISTER_EVENT_QUEUE,
                          no_ack=True)

    channel.start_consuming()


if __name__ == '__main__':
    listen_for_register_events()

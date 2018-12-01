import pika
import config

host = config.values['host']
order_exchange = config.values['order_exchange']


def prepare_channel():
    """Prepares the channel to the rabbitmq instance
    :return: channel
    """
    # setup connection to the rabbitmq instance
    connection = pika.BlockingConnection(pika.ConnectionParameters(host=host))
    channel = connection.channel()

    # declare a new exclusive queue (rabbitmq declares the name of it)
    result = channel.queue_declare(exclusive=True)
    queue_name = result.method.queue

    # binds the queue to the order exchange
    channel.queue_bind(exchange=order_exchange,
                       queue=queue_name,
                       routing_key='order.event.complete')
    # consumes every message with no ack
    channel.basic_consume(callback,
                          queue=queue_name,
                          no_ack=True)
    return channel


# callback function to invoke for each incoming message
def callback(ch, method, properties, body):
    print(body)


if __name__ == '__main__':
    channel = prepare_channel()
    print('[*] Waiting for events...')
    channel.start_consuming()

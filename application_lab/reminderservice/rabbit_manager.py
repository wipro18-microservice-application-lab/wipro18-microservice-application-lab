import pika
import config

# Gets the rabbitmq configurations
HOST = config.values['host']

ORDER_EXCHANGE = config.values['order_exchange']
ORDER_EVENT = config.values['order_event']

REMINDER_EXCHANGE = config.values['reminder_exchange']
REMINDER_QUEUE = config.values['reminder_queue']
REMINDER_COMMAND = config.values['reminder_command']


def prepare_order_channel(callback):
    """Prepares a channel for order complete events
    :return: channel
    """
    # setup connection to the rabbitmq instance
    connection = pika.BlockingConnection(pika.ConnectionParameters(host=HOST))
    channel = connection.channel()

    # declare a new exclusive queue (rabbitmq declares the name of it)
    result = channel.queue_declare(exclusive=True)
    queue_name = result.method.queue

    # binds the queue to the order exchange
    channel.queue_bind(exchange=ORDER_EXCHANGE,
                       queue=queue_name,
                       routing_key=ORDER_EVENT)

    # consumes every message with no ack
    channel.basic_consume(callback,
                          queue=queue_name,
                          no_ack=True)
    return channel


def declare_reminder_exchange(channel):
    """Declares the exchange for the reminder domain
    :param channel: The rabbitmq channel to declare the exchange with
    """
    channel.exchange_declare(exchange=REMINDER_EXCHANGE,
                             exchange_type='topic')


def prepare_command_channel(callback):
    """Prepares a channel for incoming commands
    :return: channel
    """
    # setup connection to the rabbitmq instance
    connection = pika.BlockingConnection(pika.ConnectionParameters(host=HOST))
    channel = connection.channel()

    # declare the reminder exchange
    declare_reminder_exchange(channel)

    # declare a new queue with the name REMINDER_QUEUE
    queue_name = REMINDER_QUEUE
    channel.queue_declare(queue=queue_name)

    # binds the queue to the reminder exchange
    channel.queue_bind(exchange=REMINDER_EXCHANGE,
                       queue=queue_name,
                       routing_key=REMINDER_COMMAND)

    # consumes every message with no ack
    channel.basic_consume(callback,
                          queue=queue_name,
                          no_ack=True)
    return channel

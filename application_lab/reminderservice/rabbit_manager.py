import pika
import uuid
import config

# Gets the rabbitmq configurations
HOST = config.values['host']

ORDER_EXCHANGE = config.values['order_exchange']
ORDER_EVENT = config.values['order_event']

CUSTOMER_EXCHANGE = config.values['customer_exchange']
CUSTOMER_COMMAND = config.values['customer_command']

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

    channel.exchange_declare(exchange=ORDER_EXCHANGE,
                             exchange_type='topic')

    # declare a new exclusive queue (rabbitmq declares the name of it)
    queue_name = 'ch.hslu.wipro.micros.reminder.order_complete'
    channel.queue_declare(queue=queue_name)

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


def prepare_customer_channel():
    """Prepares a channel for commands to the customer domain
    :return: channel
    """
    # setup connection to the rabbitmq instance
    connection = pika.BlockingConnection(pika.ConnectionParameters(host=HOST))
    channel = connection.channel()

    return channel


def send_command_to_customer(channel, request):
    # declare a new exclusive queue (rabbitmq declares the name of it)
    result = channel.queue_declare(exclusive=True)
    reply_queue = result.method.queue
    corr_id = str(uuid.uuid4())
    print("correlation id is",corr_id)
    channel.basic_publish(exchange=CUSTOMER_EXCHANGE,
                          routing_key=CUSTOMER_COMMAND,
                          properties=pika.BasicProperties(
                              reply_to=reply_queue,
                              correlation_id=corr_id
                          ),
                          body=request)

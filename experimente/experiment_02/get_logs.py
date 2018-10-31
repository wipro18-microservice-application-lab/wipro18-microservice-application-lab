import pika
import time
import datetime

# setup connection to a localhost rabbitmq instance
connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost'))
channel = connection.channel()

# declare a new exclusive queue (rabbitmq declares the name of it)
result = channel.queue_declare(exclusive=True)
queue_name = result.method.queue

# binds the queue to the two ArticleExchanges
# we will get all messages from this queues
channel.queue_bind(exchange='ch.hslu.wipro.micros.ArticleRequestExchange',
                   queue=queue_name,
                   routing_key='')

channel.queue_bind(exchange='ch.hslu.wipro.micros.ArticleResponseExchange',
                   queue=queue_name,
                   routing_key='')


# creates a new timestamp
def create_timestamp():
    return datetime.datetime.fromtimestamp(time.time()).strftime('%Y-%m-%d %H:%M:%S')


# callback function to invoke for each incoming message
def callback(ch, method, properties, body):
    timestamp = create_timestamp()
    print(timestamp, ' - ', body)


# consumes every message with no ack
channel.basic_consume(callback,
                      queue=queue_name,
                      no_ack=True)


print('[*] Waiting for logs. To exit press CTRL+C')
channel.start_consuming()

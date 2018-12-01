import pika
import time
import config
import rabbit_manager

from threading import Thread

host = config.values['host']
order_exchange = config.values['order_exchange']

reminders = []


def listen_for_order_events():
    channel = rabbit_manager.prepare_order_channel(callback_order)
    print('[*] Waiting for events...')
    channel.start_consuming()


def callback_order(ch, method, properties, body):
    print(body.decode('utf-8', 'ignore'))


def listen_for_commands():
    channel = rabbit_manager.prepare_command_channel(callback_command)
    channel.start_consuming()


def callback_command(ch, method, properties, body):
    all_reminders = reminders
    reply_properties = pika.BasicProperties(correlation_id=properties.correlation_id)

    ch.basic_publish(exchange='',
                     routing_key=properties.reply_to,
                     properties=reply_properties,
                     body=all_reminders)

    ch.basic_ack(delivery_tag=method.delivery_tag)
    print("all reminders",all_reminders)


if __name__ == '__main__':
    Thread(target=listen_for_order_events).start()
    Thread(target=listen_for_commands).start()

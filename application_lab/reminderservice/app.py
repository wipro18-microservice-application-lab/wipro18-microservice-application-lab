import pika
import time
import datetime
import json
import config
import rabbit_manager

from threading import Thread

host = config.values['host']
order_exchange = config.values['order_exchange']

reminders = []


class ReminderEntry:
    def __init__(self, customer_id, fee, issue_date):
        self.customer_id = customer_id
        self.fee = fee
        self.issue_date = issue_date

    def to_json_str(self):
        return '{"customerId:"' + self.customer_id \
               + ',"fee":' + self.fee \
               + ',"issue_date":' + str(self.issue_date)


def listen_for_order_events():
    """Listen for order complete events.
    """
    channel = rabbit_manager.prepare_order_channel(callback_order)
    print('[*] Waiting for events...')
    channel.start_consuming()


# creates a new timestamp
def create_timestamp():
    return datetime.datetime.fromtimestamp(time.time()).strftime('%Y-%m-%d %H:%M:%S')


def callback_order(ch, method, properties, body):
    body_dec = body.decode('utf-8', 'ignore')
    event = json.loads(body_dec)

    entry = ReminderEntry(event['customerId'], 100, create_timestamp())
    print(entry.to_json_str())
    reminders.append(entry)


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
    #Thread(target=listen_for_order_events).start()
    #Thread(target=listen_for_commands).start()

    e = ReminderEntry(1,44,create_timestamp())

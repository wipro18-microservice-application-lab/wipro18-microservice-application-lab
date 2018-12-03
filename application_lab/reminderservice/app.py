import pika
import json
import time
import config
import rabbit_manager
import timeutil

from threading import Thread
from pika import exceptions

# that much time must be elapsed to mark a bad customer
TIME_THRESHOLD = config.values['threshold_seconds']
HOST = config.values['host']

reminders = []
bad_customer_list = []


class ReminderEntry:
    """
    This class represents a reminder.
    """
    def __init__(self, customer_id, fee, issue_date):
        self.customer_id = customer_id
        self.fee = fee
        self.issue_date = issue_date

    def to_json_str(self):
        return json.dumps({"customerId": self.customer_id,
                           "fee:": self.fee,
                           "issueDate": timeutil.pretty_print(self.issue_date)})


def listen_for_order_events():
    """Listen for order complete events.
    """
    channel = rabbit_manager.prepare_order_channel(callback_order)
    channel.start_consuming()


def callback_order(ch, method, properties, body):
    """Interact with an order-complete event.
    Appends the event to the reminder list.
    """
    body_dec = body.decode('utf-8', 'ignore')
    event = json.loads(body_dec)

    entry = ReminderEntry(event['customerId'], 100, timeutil.stamp())
    reminders.append(entry)


def listen_for_commands():
    """Listen for incoming commands.
    """
    channel = rabbit_manager.prepare_command_channel(callback_command)
    channel.start_consuming()


def callback_command(ch, method, properties, body):
    """Interact with an incoming command.
    """
    bad_customers_json = get_reminders_as_json(bad_customer_list)
    reply_properties = pika.BasicProperties(correlation_id=properties.correlation_id)

    ch.basic_publish(exchange='',
                     routing_key=properties.reply_to,
                     properties=reply_properties,
                     body=bad_customers_json)

    print("all reminders", bad_customers_json)


def get_reminders_as_json(reminder_list):
    """Parse the reminder list to json
    """
    len_list = len(reminder_list)
    reminders_json = "["
    for i in range(len_list):
        reminder = reminder_list[i]
        reminders_json += (reminder.to_json_str())
        if not i == len_list - 1:
            reminders_json += ","
    reminders_json += "]"
    return '{"reminders":' + reminders_json+'}'


def check_for_bad_customers():
    """Checks the reminder list for bad customers.
    """
    time_threshold = TIME_THRESHOLD
    print("start checking for bad customers")
    while True:
        bad_customers = [c for c in reminders if timeutil.is_date_elapsed(c.issue_date, time_threshold)]
        for customer in bad_customers:
            bad_customer_list.append(customer)
            channel = rabbit_manager.prepare_customer_channel()
            rabbit_manager.send_command_to_customer(channel, json.dumps({'customerId': customer.customer_id}))
            reminders.remove(customer)
        time.sleep(time_threshold)


def test_rabbitmq_connection_blocking(sleep_time):

    while True:
        time.sleep(sleep_time)

        try:
            connection = pika.BlockingConnection(pika.ConnectionParameters(host=HOST))
            connection.channel()
            break
        except (pika.exceptions.AMQPChannelError, pika.exceptions.AMQPConnectionError):
            print("Connection was closed, retrying...")


if __name__ == '__main__':
    test_rabbitmq_connection_blocking(5)

    Thread(target=listen_for_order_events).start()
    Thread(target=listen_for_commands).start()
    Thread(target=check_for_bad_customers).start()
    """e1 = ReminderEntry(1,1,timeutil.stamp())
    e2 = ReminderEntry(2,1,timeutil.stamp())
    reminders.append(e1)
    reminders.append(e2)
    res = get_reminders_as_json(reminders)
    print(res)"""

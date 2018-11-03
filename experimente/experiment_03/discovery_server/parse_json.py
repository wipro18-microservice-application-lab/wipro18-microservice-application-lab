import json

json_file = './definitions.json'


def get_exchange_for_domain(exchanges, domain):
    for exchange in exchanges:
        if domain.lower() in exchange['name'].lower():
            return exchange['name']


def get_exchange_bindings(bindings, exchange):
    matching_queues = []

    for binding in bindings:
        if exchange.lower() in binding['source'].lower():
            matching_queues.append(binding['destination'])

    return matching_queues


def get_connection_info(domain):
    with open(json_file) as json_data:
        data = json.load(json_data)

        exchange = get_exchange_for_domain(data['exchanges'], domain)
        queues = (get_exchange_bindings(data['bindings'], exchange))

        return exchange, queues

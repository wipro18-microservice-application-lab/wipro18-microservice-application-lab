import json

json_file = '../rabbitmq/definitions.json'


def get_exchange_for_domain(exchanges, domain):
    matching_exchanges = []

    for exchange in exchanges:
        if domain.lower() in exchange['name'].lower():
            matching_exchanges.append(exchange)

    return matching_exchanges


def get_exchange_bindings(bindings, exchange):
    matching_queues = []

    for binding in bindings:
        if exchange.lower() in binding['source'].lower():
            matching_queues.append(binding)

    return matching_queues


def get_connection_info(domain):
    with open(json_file) as json_data:
        data = json.load(json_data)

        exchanges = get_exchange_for_domain(data['exchanges'], domain)
        queues = []

        for exchange in exchanges:
            queues.append(get_exchange_bindings(data['bindings'], exchange['name']))

        return exchanges, queues

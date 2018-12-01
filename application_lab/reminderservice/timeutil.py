from datetime import datetime, timedelta


def pretty_print(time_stamp):
    return time_stamp.strftime('%Y-%m-%d %H:%M:%S')


def stamp():
    return datetime.now()


def is_date_elapsed(date, delta_seconds):
    past = datetime.now() - timedelta(seconds=delta_seconds)
    return date < past

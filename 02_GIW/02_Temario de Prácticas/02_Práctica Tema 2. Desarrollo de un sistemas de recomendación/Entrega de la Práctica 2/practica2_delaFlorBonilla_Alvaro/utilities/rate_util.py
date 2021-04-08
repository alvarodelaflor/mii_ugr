import json

from bson.json_util import dumps
from pymongo import MongoClient

from utilities import constants
from classes.Rate import Rate

const = constants.Constants()


def read_u_data():
    rates = []
    with open(const.get_url_u_data()) as f:
        lines = f.readlines()
        for line in lines:
            x = line.split('\t')
            user_id = x[0].strip()
            item_id = x[1].strip()
            rating = x[2].strip()
            timestamp = x[3].strip()
            rate = Rate(user_id=user_id, item_id=item_id, rating=rating, timestamp=timestamp)
            rates.append(rate)

    return rates


def save_rates(rates):
    client = MongoClient('mongodb+srv://giw:giw@cluster0.upja7.mongodb.net/giw_db?retryWrites=true&w=majority')
    db = client.get_database('giw_db')
    rates_mongodb = db.rates
    rates_mongodb.drop()
    rate_dict = []
    for rate in rates:
        rate_dict.append(json.loads(rate.to_json()))
    rates_mongodb.insert_many(rate_dict)


def to_class(rate):
    return Rate(user_id=rate.get('user_id'), item_id=rate.get('item_id'), rating=rate.get('rating'), timestamp=rate.get('timestamp'))


def get_all_rates():
    client = MongoClient('mongodb+srv://giw:giw@cluster0.upja7.mongodb.net/giw_db?retryWrites=true&w=majority')
    db = client.get_database('giw_db')
    rates_mongodb = db.rates
    rates = list(map(lambda x: to_class(x), list(rates_mongodb.find({}))))
    return rates


def filter_rate(user_id=None, item_id=None, rating=None, timestamp=None):
    client = MongoClient('mongodb+srv://giw:giw@cluster0.upja7.mongodb.net/giw_db?retryWrites=true&w=majority')
    db = client.get_database('giw_db')
    rates_mongodb = db.rates

    query = {}
    if user_id is not None:
        query['user_id'] = user_id
    if item_id is not None:
        query['item_id'] = item_id
    if rating is not None:
        query['rating'] = rating
    if timestamp is not None:
        query['timestamp'] = timestamp

    rates = list(map(lambda x: to_class(x), list(rates_mongodb.find(query))))

    return rates

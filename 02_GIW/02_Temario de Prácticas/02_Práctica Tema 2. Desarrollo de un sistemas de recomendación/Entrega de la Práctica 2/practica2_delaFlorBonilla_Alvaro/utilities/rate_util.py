import json

from datetime import datetime
from utilities.constants import *
from classes.Rate import Rate


def read_u_data():
    rates = []
    with open(get_url_u_data()) as f:
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
    db = get_mongo_db()
    rates_mongodb = db.rates
    rates_mongodb.drop()
    rate_dict = []
    for rate in rates:
        rate_dict.append(json.loads(rate.to_json()))
    rates_mongodb.insert_many(rate_dict)


def save_user_rate_search(rates, recommendations):
    print("\nGuardando resultados en base de datos...\n")
    db = get_mongo_db()
    recommendation_mongodb = db.recommendation
    today = datetime.today().strftime("%d/%m/%Y %H:%M:%S")
    recommendation_json = []
    for recommendation in recommendations:
        recommendation_json.append({"title": recommendation[0]['title'], "id": recommendation[1]['id'], "rate": recommendation[2]['rate']})
    recommendation_mongodb.insert_one({today: (rates, recommendation_json)})


def to_class(rate):
    return Rate(user_id=rate.get('user_id'), item_id=rate.get('item_id'), rating=rate.get('rating'), timestamp=rate.get('timestamp'))


def get_all_rates():
    db = get_mongo_db()
    rates_mongodb = db.rates
    rates = list(map(lambda x: to_class(x), list(rates_mongodb.find({}))))
    return rates


def filter_rate(user_id=None, item_id=None, rating=None, timestamp=None):
    db = get_mongo_db()
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

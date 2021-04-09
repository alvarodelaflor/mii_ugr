import json
from pymongo import MongoClient
from utilities.constants import *
from classes.User import User


def read_u_user():
    users = []
    with open(get_url_u_user()) as f:
        lines = f.readlines()
        for line in lines:
            x = line.split('|')
            user_id = x[0].strip()
            age = x[1].strip()
            gender = x[2].strip()
            occupation = x[3].strip()
            zip_code = x[4].strip()
            user = User(user_id=user_id, age=age, gender=gender, occupation=occupation, zip_code=zip_code)
            users.append(user)

    return users


def save_users(rates):
    client = MongoClient('mongodb+srv://giw:giw@cluster0.upja7.mongodb.net/giw_db?retryWrites=true&w=majority')
    db = client.get_database('giw_db')
    users_mongodb = db.users
    users_mongodb.drop()
    user_dict = []
    for rate in rates:
        user_dict.append(json.loads(rate.to_json()))
    users_mongodb.insert_many(user_dict)


def to_class(user):
    return User(user_id=user.get('user_id'), age=user.get('age'), gender=user.get('gender'), occupation=user.get('occupation'), zip_code=user.get('zip_code'))


def filter_user(user_id=None, age=None, gender=None, occupation=None, zip_code=None):
    client = MongoClient('mongodb+srv://giw:giw@cluster0.upja7.mongodb.net/giw_db?retryWrites=true&w=majority')
    db = client.get_database('giw_db')
    users_mongodb = db.users

    query = {}
    if user_id is not None:
        query['user_id'] = user_id
    if age is not None:
        query['age'] = age
    if gender is not None:
        query['gender'] = gender
    if occupation is not None:
        query['occupation'] = occupation
    if zip_code is not None:
        query['zip_code'] = zip_code

    users = list(map(lambda x: to_class(x), list(users_mongodb.find(query))))

    return users

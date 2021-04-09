from pymongo import *

number_of_movies_to_qualify = 20
number_of_recommended_movies = 20
url_principal = './ml-100k'
url_dict = './data/p2.dat'
url_mongo = 'mongodb+srv://giw:giw@cluster0.upja7.mongodb.net/giw_db?retryWrites=true&w=majority'
database_name = 'giw_db'
movies_file_name = 'u.item'
rates_file_name = 'u.data'
users_file_name = 'u.user'


def get_new_user_id():
    return "101010"


def get_number_of_movies_to_qualify():
    return number_of_movies_to_qualify


def get_number_of_recommended_movies():
    return number_of_recommended_movies


def get_url_dict():
    return url_dict


def get_url_u_item():
    return url_principal + '/' + movies_file_name


def get_url_u_data():
    return url_principal + '/' + rates_file_name


def get_url_u_user():
    return url_principal + '/' + users_file_name


def get_mongo_client():
    return MongoClient(url_mongo)


def get_mongo_db():
    return get_mongo_client().get_database(database_name)

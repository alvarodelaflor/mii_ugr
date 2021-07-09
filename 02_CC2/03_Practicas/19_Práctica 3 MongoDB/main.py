from datetime import datetime

import pymongo
from csv import reader
import requests
import csv


def login():
    user = 'user'
    password = 'cc_password'

    cliente = pymongo.MongoClient(
        "mongodb+srv://" + user + ":" + password + "@cluster0.puwqu.mongodb.net/myFirstDatabase?ssl=true&ssl_cert_reqs=CERT_NONE&retryWrites=true&w=majority")

    cliente.test

    """Creamos la base de datos y la colección"""
    collection = cliente['cc_data']['raw']

    return collection


def save_data():
    """Obtenemos los datos"""
    link = "https://raw.githubusercontent.com/manuparra/MasterCyberSec_Practice/master/datasetmongodb/SacramentocrimeJanuary2006.csv"

    """Tratamos los datos"""
    elements = []

    with open("./raw_offline.csv", 'r' ) as theFile:
        reader = csv.DictReader(theFile)
        for line in reader:
            # line is { 'workers': 'w0', 'constant': 7.334, 'age': -1.406, ... }
            # e.g. print( line[ 'workers' ] ) yields 'w0'
            # row variable is a list that represents a row in csv
            #cdatetime = line['cdatetime'].split(' ')[1].split(':')[0]
            day = int(line['cdatetime'].split(" ")[0].split("/")[1])
            month = int(line['cdatetime'].split(" ")[0].split("/")[0])
            year = int('20' + line['cdatetime'].split(" ")[0].split("/")[2])
            hour = int(line['cdatetime'].split(" ")[1].split(':')[0])
            minute = int(line['cdatetime'].split(" ")[1].split(':')[0])
            d = datetime(year, month, day, hour, minute)
            cdatetime = d
            crimedescr = line['crimedescr']

            element = {"cdatetime":cdatetime, "crimedescr":crimedescr}
            elements.append(element)

    collection = login()
    collection.remove()

    bulk = collection.initialize_unordered_bulk_op()

    print("\nInicio del guardado")
    for element in elements:
        bulk.insert(element)
    bulk.execute()
    print("\nFin del guardado")


def count_different_crimes():
    print("\nTotalizar el número de delitos por cada tipo")
    collection = login()
    result = collection.aggregate(
        [{"$group": {"_id": "$crimedescr", "count": {"$sum": 1}}}, {"$sort": {"count": 1}}])
    i = 0
    auxiliar = []
    for res in result:
        print(res)
        id = res.get("_id")
        i += 1
        auxiliar.append(id)
    conjunto = set(auxiliar)
    print("Total de resultados: " + str(i))


def most_crime_time():
    print("\nFranja horaria (o franjas) con más número de delitos")
    collection = login()
    result = collection.aggregate(
        [{"$group": {
            "_id": {"hour": {"$hour": '$cdatetime'}},
            "count": {"$sum": 1}
        }}, {"$sort": {"count": 1}}])
    for res in result:
        print(res)


def save():
    print("\nGuardamos los datos")
    save_data()


if __name__ == '__main__':
    save()
    count_different_crimes()
    most_crime_time()


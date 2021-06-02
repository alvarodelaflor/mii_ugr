import pymongo
import requests


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
    raw = requests.get(link).text

    """Tratamos los datos"""
    lines = raw.split("\n")
    titles = lines[0].split(",")

    elements = []
    for i in range(1, len(lines)):
        first_split = lines[i].split("        ")
        cdatetime = first_split[0].split(",")[0]
        second_split = first_split[1][1:].split(",")
        crimedescr = second_split[1]

        element = {"cdatetime":cdatetime, "crimedescr":crimedescr}
        elements.append(element)
    """Conectamos con el contenedor de MongoDB"""

    collection = login()
    collection.remove()

    bulk = collection.initialize_unordered_bulk_op()

    print("Inicio del guardado")
    for element in elements:
        bulk.insert(element)
    bulk.execute()
    print("Fin del guardado")


def count_different_crimes():
    collection = login()
    result = collection.aggregate(
        [{"$group": {"_id": "$crimedescr", "count": {"$sum": 1}}}, {"$sort": {"count": 1}}])
    for res in result:
        print(res)


def most_crime_time():
    print("time")


if __name__ == '__main__':
    print("Init")
    #save_data()
    #count_different_crimes()
    most_crime_time()
    print("End")


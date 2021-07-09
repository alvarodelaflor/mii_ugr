#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Clase auxiliar para la obtención de predicciones

@author: Álvaro de la Flor Bonilla (github: alvarodelaflor)
"""
import pandas as pd
import pmdarima as pm
import pymongo
import pickle
from datetime import datetime, timedelta
import requests
import os
import zipfile
import time


def create_arima_model(dataframe, modelo):
    """Crea el modelo ARIMA según el argumento modelo: TEMP (temperatura)/HUM (humedad)."""
    arima_temp = pm.auto_arima(dataframe[modelo].dropna(), start_p=1,
                               start_q=1, test='adf', max_p=3, max_q=3, m=1, d=None, seasonal=False,
                               start_P=0, D=0, trace=True, error_action='ignore', suppress_warnings=True,
                               stepwise=True)
    """Guardamos el modelo como objeto en un fichero"""
    pickle.dump(arima_temp, open("./modelos/modelo_" + modelo + ".p", "wb"))
    """Lo comprimimos para luego subirlo a GitHub"""
    zipObj = zipfile.ZipFile('./modelos/modelo_' + modelo + '.zip', 'w', zipfile.ZIP_DEFLATED)
    zipObj.write("./modelos/modelo_" + modelo + ".p")
    zipObj.close()


class Prediction:
    user = 'admin'
    password = 'pass'

    def connect_db(self):
        """Conectamos con el sistema de almacenamiento cloud MongoDBAtlas"""
        self.mongodb = pymongo.MongoClient(
            "mongodb+srv://" + self.user + ":" + self.password + "@cluster0.zif5v.mongodb.net/myFirstDatabase?retryWrites=true&w=majority")

        """Accedemos a la base de datos y en particular a la colección"""
        self.coleccion = self.mongodb['PrediccionesBD']['DatosTiempo']

    def get_predictions_arima(self, period):
        """Comprobamos si el período es realmente un número."""
        try:
            tiempo = int(period)
        except ValueError:
            raise ValueError("El periodo debe ser un número entero.")

        """Obtenemos los datos del contenedor MongoDB"""
        self.connect_db()
        datosbd = self.coleccion.find_one({'index': 'SF'})
        """Lo pasamos a dataframe para trabajar con ARIMA"""
        dataframe = pd.DataFrame(datosbd["datos"])

        """Intentamos cargar el modelo ARIMA si existe. Si no lo creamos y lo guardamos
            como un objeto en un fichero."""
        if os.path.isfile('./modelos/modelo_TEMP.zip') == False:
            create_arima_model(dataframe, 'TEMP')

        """Extraemos el modelo del fichero comprimido."""
        with zipfile.ZipFile('./modelos/modelo_TEMP.zip', 'r') as zipObj:
            zipObj.extractall("./")

        """Cargamos el objeto del modelo desde el fichero"""
        arima_temp = pickle.load(open('./modelos/modelo_TEMP.p', "rb"))
        """Predicciones"""
        predicc_temp, confint = arima_temp.predict(n_periods=tiempo, return_conf_int=True)

        """HUMEDAD."""
        if os.path.isfile('./modelos/modelo_HUM.zip') == False:
            create_arima_model(dataframe, 'HUM')

        """Extraemos el modelo del fichero comprimido."""
        with zipfile.ZipFile('./modelos/modelo_HUM.zip', 'r') as zipObj:
            zipObj.extractall("./")
        """Cargamos el objeto del modelo desde el fichero"""
        arima_hum = pickle.load(open('./modelos/modelo_HUM.p', "rb"))
        """Predicciones"""
        predicc_hum, confint = arima_hum.predict(n_periods=tiempo, return_conf_int=True)
        """Componemos el resultado de las predicciones"""
        primera_fecha = datetime.now() + timedelta(hours=3)
        rango_fechas = pd.date_range(primera_fecha.replace(second=0, microsecond=0), periods=tiempo, freq='H')
        resultado = []
        """JSON"""
        for tiempo, temp, hum in zip(rango_fechas, predicc_temp, predicc_hum):
            tiempo_unix = time.mktime(tiempo.timetuple())
            resultado.append(
                {'hour': datetime.utcfromtimestamp(tiempo_unix).strftime('%d-%m %H:%M'), 'temp': temp, 'hum': hum})

        return resultado

    def get_prediction_wheatherapi(self, periodo):
        """Comprobamos si el período es realmente un número."""
        try:
            tiempo = int(periodo)
        except ValueError:
            raise ValueError("El periodo debe ser un número entero.")

        """Conectamos con la API Dark Sky y obtenemos la predicción por horas.
            Por defecto devuelve 168 horas de predicción meteorológica."""
        url = "http://api.weatherapi.com/v1/forecast.json?key=b4f68621e0154c4f8cb164738211005&q=San Francisco&days=" + str(
            tiempo) + "&aqi=yes&alerts=yes"
        respuesta = requests.get(url)
        """Convertimos los datos obtenidos a JSON"""
        datos = respuesta.json()
        prediccion = datos['forecast']['forecastday']

        """Componemos el resultado con el periodo pasado como argumento"""
        horas = 0
        resultado = []
        for key in prediccion:
            days = key['hour']
            for day in days:
                t = datetime.utcfromtimestamp(day['time_epoch']).strftime('%d-%m %H:%M')
                """JSON"""
                resultado.append({'hour': t, 'temp': day['temp_c'], 'hum': (day['humidity'])})

        return resultado

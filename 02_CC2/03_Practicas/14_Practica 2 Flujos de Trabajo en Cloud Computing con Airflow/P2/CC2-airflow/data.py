#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Esta clase procesa los archivos cedidos para la práctica de tal forma que
construiremos un único dataframe con el tiempo, temperatura y humedad únicamente
para la ciudad de San Francisco.

El registro obtenido final es almacenado en una base de datos MongoDB.

@author: Álvaro de la Flor Bonilla (github: alvarodelaflor)
"""

import pandas
import pymongo


class Data:
    user = 'admin'
    password = 'pass'

    def get_data(self):
        """En primer lugar leemos los dos archivos de datos"""
        df_humedad = pandas.read_csv('./data/humidity.csv')
        df_temperatura = pandas.read_csv('./data/temperature.csv')

        """Extraemos las fechas (es la mismas en ambos archivos)"""
        datetime = df_humedad['datetime']

        """Extraemos los datos tanto de temperatura como humedad para la ciudad de San Francisco"""
        humedad_sf = df_humedad['San Francisco']
        temperatura_sf = df_temperatura['San Francisco']

        """Unificamos el dataframe tal y como se indica en la práctica (DATE;TEMP,HUM)"""
        data = {'DATE': datetime, 'TEMP': temperatura_sf, 'HUM': humedad_sf}
        dataframe = pandas.DataFrame(data=data)

        """Conectamos con MongoDB"""
        client = pymongo.MongoClient("mongodb+srv://" + self.user + ":" + self.password + "@cluster0.zif5v.mongodb.net/myFirstDatabase?retryWrites=true&w=majority")

        """Creamos la base de datos y la colección"""
        self.coleccion = client['PrediccionesBD']['DatosTiempo']

        """Insertamos el dataframe"""
        df_dict = dataframe.to_dict("records")
        index = self.coleccion.insert_one({'index': 'SF', 'datos': df_dict}).inserted_id

        return index


if __name__ == "__main__":
    d = Data()
    g = d.get_data()

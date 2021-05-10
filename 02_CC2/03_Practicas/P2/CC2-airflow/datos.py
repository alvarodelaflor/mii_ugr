#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Clase que procesa los datos descargados y los unifica para componer un solo
dataframe con el tiempo, la temperatura y la humedad. Una vez obtenido el conjunto
final se almacena cada registro en una base de datos MongoDB que se encuentra
dentro de un contenedor.

@author: Lidia Sánchez Mérida
"""

import pandas
import pymongo

class Datos:
    
    def get_datos(self):
        """Leemos los ficheros CSV"""
        df_humedad = pandas.read_csv('./data/humidity.csv')
        df_temperatura = pandas.read_csv('./data/temperature.csv')
        """Extraemos la columna de la ciudad de San Francisco de humedad y temperatura"""
        humedad_sf = df_humedad['San Francisco']
        temperatura_sf = df_temperatura['San Francisco']
        """Obtenemos la columna de las fechas porque es la misma en ambos datasets"""
        datetime = df_humedad['datetime']
        """Formamos el nuevo dataframe unificado"""
        d = {'DATE':datetime, 'TEMP':temperatura_sf, 'HUM':humedad_sf}
        dataframe = pandas.DataFrame(data=d)
        
        """Conectamos con el contenedor de MongoDB"""
        usuario = 'admin'
        clave = 'pass'
        if (type(usuario) != str or usuario == None or usuario == "" or
            type(clave) != str or clave == None or clave == ""):
            raise ConnectionError('Credenciales no válidas para acceder a MongoDBAtlas.')
            
        cliente = pymongo.MongoClient(
            "mongodb+srv://"+usuario+":"+clave+"@cluster0.zif5v.mongodb.net/myFirstDatabase?retryWrites=true&w=majority")
        
        """Creamos la base de datos y la colección"""
        self.coleccion = cliente['PrediccionesBD']['DatosTiempo']
        """Transformamos el dataframe a diccionario para poder insertarlo"""
        df_dict = dataframe.to_dict("records")
        indice = self.coleccion.insert_one({'index':'SF', 'datos':df_dict}).inserted_id
        return indice

if __name__== "__main__":
    d = Datos()
    g = d.get_datos()
#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
API REST que proporciona el servicio de predicción de temperatura y humedad 
utilizando la API Dark Sky. Para hacer uso de ella es necesario disponer de una
cuenta en la plataforma y su correspondiente API key.

@author: Lidia Sánchez Mérida
"""
import json
"""Framework Flask para implementar el microservicio REST"""
from flask import Flask, Response
app = Flask(__name__)

"""Objeto de la clase de la lógica de negocio."""
import prediccion
pred = prediccion.Prediccion()

@app.route("/")
def index():
    return Response("Microservicio REST para la predicción de temperatura y humedad utilizando Dark Sky API.", status=200)

@app.route("/api/<string:tiempo>", methods=['GET'])
def obtener_prediccion_arima(tiempo):
    resultado = pred.get_predicciones_api(tiempo)
    if (len(resultado) > 0):
        return Response(json.dumps(resultado), status=200, mimetype="application/json")
    else:
        return Response("No hay predicciones", status=400)
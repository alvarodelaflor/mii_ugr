#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
API REST que proporciona el servicio de predicción de temperatura y humedad para
24/48/72 horas utilizando ARIMA.

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
    return Response("Microservicio REST para la predicción de temperatura y humedad utilizando ARIMA.", status=200)

@app.route("/arima/<string:tiempo>", methods=['GET'])
def obtener_prediccion_arima(tiempo):
    resultado = pred.get_predicciones_arima(tiempo)
    if (len(resultado) > 0):
        return Response(json.dumps(resultado), status=200, mimetype="application/json")
    else:
        return Response("No hay predicciones", status=400)
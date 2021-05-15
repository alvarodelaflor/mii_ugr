#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
@author: Álvaro de la Flor Bonilla (github: alvarodelaflor)
"""
import json
import prediction
from flask import Flask, Response

app = Flask(__name__)
pred = prediction.Prediction()


@app.route("/")
def index():
    return Response("<p>"
                    "<strong>WeatherApi</strong> - Predicción de temperatura y humedad<br>"

                    "</br>Puede usar las siguientes rutas:</br>"

                    "</br>  1.- <a href='/servicio/v2/prediccion/24horas/'>/servicio/v2/prediccion/24horas/<a> Para predicciones en las proximas 24 horas"
                    "</br>  2.- <a href='/servicio/v2/prediccion/48horas/'>/servicio/v2/prediccion/48horas/<a> Para predicciones en las proximas 48 horas"
                    "</br>  3.- <a href='/servicio/v2/prediccion/72horas/'>/servicio/v2/prediccion/72horas/<a> Para predicciones en las proximas 72 horas"
                    "</p>", status=200)


@app.route("/servicio/v2/prediccion/24horas/", methods=['GET'])
def get_prediction_wheatherapi_24():
    resultado = pred.get_prediction_wheatherapi(1)
    if len(resultado) > 0:
        return Response(json.dumps(resultado), status=200, mimetype="application/json")
    else:
        return Response("No hay predicciones", status=400)


@app.route("/servicio/v2/prediccion/48horas/", methods=['GET'])
def get_prediction_wheatherapi_48():
    resultado = pred.get_prediction_wheatherapi(2)
    if len(resultado) > 0:
        return Response(json.dumps(resultado), status=200, mimetype="application/json")
    else:
        return Response("No hay predicciones", status=400)


@app.route("/servicio/v2/prediccion/72horas/", methods=['GET'])
def get_prediction_wheatherapi_72():
    resultado = pred.get_prediction_wheatherapi(3)
    if len(resultado) > 0:
        return Response(json.dumps(resultado), status=200, mimetype="application/json")
    else:
        return Response("No hay predicciones", status=400)
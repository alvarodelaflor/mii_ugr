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
                        "<strong>ARIMA</strong> - Predicción de temperatura y humedad<br>"
                        
                        "</br>Puede usar las siguientes rutas:</br>"
                    
                        "</br>  1.- <a href='/servicio/v1/prediccion/24horas/'>/servicio/v1/prediccion/24horas/<a> Para predicciones en las proximas 24 horas"
                        "</br>  2.- <a href='/servicio/v1/prediccion/48horas/'>/servicio/v1/prediccion/48horas/<a> Para predicciones en las proximas 48 horas"
                        "</br>  3.- <a href='/servicio/v1/prediccion/72horas/'>/servicio/v1/prediccion/72horas/<a> Para predicciones en las proximas 72 horas"
                    "</p>", status=200)


@app.route("/servicio/v1/prediccion/24horas/", methods=['GET'])
def get_arima_prediction_24():
    result = pred.get_predictions_arima(24)
    if len(result) > 0:
        return Response(json.dumps(result), status=200, mimetype="application/json")
    else:
        return Response("No hay predicciones", status=400)


@app.route("/servicio/v1/prediccion/48horas/", methods=['GET'])
def get_arima_prediction_48():
    result = pred.get_predictions_arima(48)
    if len(result) > 0:
        return Response(json.dumps(result), status=200, mimetype="application/json")
    else:
        return Response("No hay predicciones", status=400)


@app.route("/servicio/v1/prediccion/72horas/", methods=['GET'])
def get_arima_prediction_72():
    result = pred.get_predictions_arima(72)
    if len(result) > 0:
        return Response(json.dumps(result), status=200, mimetype="application/json")
    else:
        return Response("No hay predicciones", status=400)

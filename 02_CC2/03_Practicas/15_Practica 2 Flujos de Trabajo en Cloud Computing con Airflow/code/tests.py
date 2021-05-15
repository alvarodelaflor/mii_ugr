#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Tests para comprobar el funcionamiento de los microservicios

@author: Álvaro de la Flor Bonilla
"""
import pytest
import prediction
import api_v1
import api_v2

"""Cargamos las predicciones"""
prediction_arima = prediction.Prediction()


def test_arima_1():
    """Solo pueden indicarse horas en arima, no strings"""
    with pytest.raises(ValueError):
        assert prediction_arima.get_predictions_arima('fail')


def test_arima_2():
    """El objeto devuelto debe ser una lista"""
    respuesta = prediction_arima.get_predictions_arima(24)
    assert type(respuesta) == list


def test_arima_3():
    """Funcionamiento correcto de la API 1"""
    app_v1 = api_v1.app.test_client()
    respuesta = app_v1.get('/servicio/v1/prediccion/24horas/')
    assert (respuesta.status_code == 200)


def test_wheatherapi_1():
    """Solo pueden indicarse número de dias en wheatherapi, no strings"""
    with pytest.raises(ValueError):
        assert prediction_arima.get_prediction_wheatherapi('hola')


def test_wheatherapi_2():
    """El objeto devuelto debe ser una lista"""
    response = prediction_arima.get_predictions_arima(12)
    assert type(response) == list


def test_wheatherapi_3():
    """Funcionamiento correcto de la API 2"""
    app_v2 = api_v2.app.test_client()
    response = app_v2.get('/servicio/v2/prediccion/24horas/')
    assert (response.status_code == 200)


if __name__ == '__main__':
    pytest.main()

#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Tests para comprobar el funcionamiento acerca de las clases que contienen la lógica
de negocio así como el de los microservicios.

@author: Lidia Sánchez Mérida
"""
import pytest

"""Clase con la lógica de la aplicación para realizar las predicciones"""
import prediccion
pred = prediccion.Prediccion()

def test1_get_predicciones_arima():
    """Test para comprobar que el período indicado es un número. En este caso
        no lo es y por lo tanto se produce una excepción."""
    with pytest.raises(ValueError):
        assert pred.get_predicciones_arima('hola')
        
def test2_get_predicciones_arima():
    """Test para comprobar que el objeto devuelto es un dataframe con las predicciones
        realizadas por ARIMA."""
    respuesta = pred.get_predicciones_arima(24)
    assert type(respuesta) == list

def test1_get_predicciones_api():
    """Test para comprobar que el período indicado es un número. En este caso
        no lo es y por lo tanto se produce una excepción."""
    with pytest.raises(ValueError):
        assert pred.get_prediction_wheatherapi('hola')

def test2_get_predicciones_api():
    """Test para comprobar que el objeto devuelto es un dataframe con las predicciones
        realizadas por la API Dark Sky."""
    respuesta = pred.get_predicciones_arima(12)
    assert type(respuesta) == list

"""Microservicio de la versión 1: ARIMA"""
import api_v1
app_v1 = api_v1.app.test_client()

def test_obtener_predicciones_arima():
    """Test para comprobar el funcionamiento del microservicio de la versión 1."""
    respuesta = app_v1.get('/arima/12')
    assert (respuesta.status_code == 200)

"""Microservicio de la versión 2: Dark Sky API"""
import api_v2
app_v2 = api_v2.app.test_client()

def test_obtener_predicciones_api():
    """Test para comprobar el funcionamiento del microservicio de la versión 2."""
    respuesta = app_v2.get('/api/12')
    assert (respuesta.status_code == 200)

if __name__ == '__main__':
    pytest.main()

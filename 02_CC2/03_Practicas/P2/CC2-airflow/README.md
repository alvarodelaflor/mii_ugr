# Cloud Computing 2

## Máster en Ingeniería Informática. Curso 19-20

### Práctica: Despliegue de un servicio Cloud Native

El objetivo consiste en crear y desplegar un servicio *Cloud Native* utilizando la herramienta de orquestación *Airflow* para obtener la predicción de la temperatura y humedad de la ciudad de San Francisco. Para ello se ha desarrollado una arquitectura basada en microservicios, en particular, los dos siguientes:

* *api_v1*, utiliza modelos ARIMA pre-entrenados para predecir la temperatura y la humedad en un espacio determinado de tiempo. Para ello se utilizará el conjunto de datos preprocesado y almacenado en MongoDB Atlas.

* *api_v2*, hace uso de la API [Dark Sky](https://darksky.net/dev) que proporciona el pronóstico meteorológico de hasta 168 horas. Para hacer uso de ella es necesario registrarse y obtener la correspondiente API key.

La arquitectura se ha desarrollado por capas de manera que dispone de una clase con la lógica de negocio que se encuentra en el módulo *prediccion*. Esta dispone de métodos para conectar con la base de datos y obtener las predicciones para sendas versiones del servicio.

Por último, en este repositorio también se encuentran los ficheros *dockerfile* con los que construir y desplegar dos contenedores correspondientes a los dos tipos de microservicios existentes.

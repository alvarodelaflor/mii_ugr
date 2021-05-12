from datetime import timedelta
from airflow import DAG
from airflow.operators.bash_operator import BashOperator
from airflow.operators.python_operator import PythonOperator
from airflow.utils.dates import days_ago
#import requests
import pandas
import pymongo
import pytest

default_args = {
    'owner': 'airflow',
    'depends_on_past': False,
    'start_date': days_ago(2), # Comienza inmediatamente.
    'email': ['lidiasm96@correo.ugr.es'], # Email al que enviar el informe si hay error.
    'email_on_failure': False,
    'email_on_retry': False,
    'retries': 1,
    'retry_delay': timedelta(minutes=5), # Cada cuanto se reintenta la ejecución.
}

############################ FUNCIONES DE PYTHON ############################
def get_datos():
    """Función que captura, preprocesa y almacena los datos"""
    import sys
    sys.path.append('/tmp/workflow/servicio')
    import data
    datos_clase = data.Datos()
    datos_clase.get_data()

############################ FIN FUNCIONES DE PYTHON ############################

# Inicializamos el grafo de tareas.
dag = DAG(
    'practica2',
    default_args=default_args,
    description='Grafo de tareas de la practica 2',
    schedule_interval=timedelta(days=1),
)
# PrepararEntorno es una tarea encargada de crear el direcotorio donde almacenar
# los ficheros de datos que se descargarán a continuación y lanzar el contenedor de la base de datos.
# Con la opción "-p" intentará crear el directorio si no existe. Si existe no lanza error.
PrepararEntorno = BashOperator(
                    task_id='preparar_entorno',
                    depends_on_past=False,
                    bash_command='mkdir -p /tmp/workflow/',
                    dag=dag
                    )
# CapturaDatosHumedad: se encarga de descargar el fichero de datos que contiene la humedad.
CapturaDatosHumedad = BashOperator(
                        task_id='captura_datos_hum',
                        depends_on_past=False,
                        bash_command='wget --output-document /tmp/workflow/humidity.csv.zip https://raw.githubusercontent.com/manuparra/MaterialCC2020/master/humidity.csv.zip',
                        dag=dag
                        )
# CapturaDatosTemperatura: tarea encargada de descargar el otro fichero de datos con las temperaturas.
CapturaDatosTemperatura = BashOperator(
                            task_id='captura_datos_temp',
                            depends_on_past=False,
                            bash_command='wget --output-document /tmp/workflow/temperature.csv.zip https://raw.githubusercontent.com/manuparra/MaterialCC2020/master/temperature.csv.zip',
                            dag=dag
                            )
# DescargarServicio
DescargarServicio = BashOperator(
                        task_id='descargar_servicio',
                        depends_on_past=False,
                        bash_command='rm -rf /tmp/workflow/servicio/ ; mkdir /tmp/workflow/servicio ; git clone https://github.com/lidiasm/CC2-airflow.git /tmp/workflow/servicio',
                        dag=dag
                        )
# DescomprimirDatos: tarea encargada de descomprimir ambos ficheros.
## Con la opción "-d" especificamos la ruta donde queremos que descomprima los ficheros.
## Con la opción "-o" le indicamos que sobreescriba los ficheros sin preguntar.
DescomprimirDatos = BashOperator(
                        task_id='descomprimir_datos',
                        depends_on_past=False,
                        bash_command='unzip -o /tmp/workflow/temperature.csv.zip -d /tmp/workflow ; unzip -o /tmp/workflow/humidity.csv.zip -d /tmp/workflow',
                        dag=dag
                        )
# AlmacenarDatos: tarea encargada de extraer la humedad y temperatura de
# San Francisco así como la hora a la que se ha medido para almacenarla en al base de datos.
AlmacenarDatos = PythonOperator(
                    task_id='almacenar_datos',
                    python_callable=get_datos,
                    op_kwargs={},
                    dag=dag
                    )
# EjecutarTests: tarea encargada de ejecutar los tests desarrollados para comprobar
# el funcionamiento de los microservicios.
EjecutarTests = BashOperator(
                    task_id='ejecutar_tests',
                    depends_on_past=False,
                    bash_command='cd /tmp/workflow/servicio ; pytest test_all.py',
                    dag=dag
                    )
# DesplegarArima: tarea encargada de construir el contenedor del primer microservicio
# y desplegarlo para acceder a él a través de la dirección 0.0.0.0:8000/
DesplegarArima = BashOperator(
                    task_id='desplegar_arima',
                    depends_on_past=False,
                    bash_command='cd /tmp/workflow/servicio ; docker build -f DockerfileV1 -t servicio_v1 . ; docker run -d -e PORT=8000 -e USER_ATLAS=$USER_ATLAS -e PSW_ATLAS=$PSW_ATLAS -p 8000:8000 servicio_v1',
                    dag=dag
                    )
# DesplegarApi: tarea encargada de construir el contenedor del segundo microservicio
# y desplegarlo para acceder a él a través de la dirección 0.0.0.0:8001/
DesplegarApi = BashOperator(
                    task_id='desplegar_api',
                    depends_on_past=False,
                    bash_command='cd /tmp/workflow/servicio ; docker build -f DockerfileV2 -t servicio_v2 . ; docker run -d -e PORT=8001 -e WEATHER_KEY=$WEATHER_KEY -p 8001:8001 servicio_v2',
                    dag=dag
                    )

## ORDEN DE EJECUCIÓN DE TAREAS
PrepararEntorno >> [CapturaDatosHumedad, CapturaDatosTemperatura, DescargarServicio] >> DescomprimirDatos >> AlmacenarDatos >> EjecutarTests >> [DesplegarArima, DesplegarApi]
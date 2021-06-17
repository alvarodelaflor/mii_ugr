from pyspark import SparkContext, SparkConf
from pyspark.sql import SparkSession

'''Creamos la configuración inicial de Spark'''
name = 'CC P4 Alvaro de la Flor Bonilla'
conf = SparkConf().setAppName(name)
spark_context = SparkContext(conf=conf)
spark_session = SparkSession.builder.appName(name).getOrCreate()


def read_data():
    '''Leemos el archivo utilizando las cabeceras que nos dan en el guion'''
    print('Leer datos')
    headers = spark_context.textFile('/user/datasets/ecbdl14/ECBDL14_IR2.header').collect()
    '''Obtenemos las columnas'''
    print('Obtener columnas')
    columns = [inp for inp in headers if '@inputs' in inp]
    list_columns = columns[0].replace('@inputs', '').replace(' ','').split(',')
    print(str(list_columns))
    '''Añadimos la variable de clase'''
    list_columns.append('class')
    '''Le damos valor a las columnas'''
    print('Valor a las columnas')
    data = spark_session.read.csv('/user/datasets/ecbdl14/ECBDL14_IR2.data', header=False, inferSchema=True)
    for c in range(0, len(data.columns)):
        data = data.withColumnRenamed(data.columns[c], list_columns[c])

    return (data)


def create_new_df(df, selected_columns):
    '''Creamos el DF con las columnas seleccionadas'''
    print('Creando DT')
    nuevo_dataframe = df.select(selected_columns)
    print('Escribiendo DF')
    nuevo_dataframe.coalesce(1).write.csv('file:///home/ccsa15408846/cc_p4/filteredC.small.training', header=True, mode='overwrite')


if __name__ == '__main__':
    data = read_data()
    selected_columns = ['PSSM_r1_3_E', 'PSSM_central_0_Y', 'PSSM_r2_-4_S', 'PSSM_central_-2_Q', 'PSSM_r1_-2_Y', 'PSSM_r1_0_G']
    selected_columns.append('class')
    create_new_df(data, selected_columns)
    spark_context.stop()

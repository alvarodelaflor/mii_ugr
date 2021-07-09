from pyspark import SparkContext, SparkConf
from pyspark.sql import SparkSession
import os.path
from pyspark.ml.feature import VectorAssembler, StringIndexer
from pyspark.ml import Pipeline
from pyspark.ml.classification import LogisticRegression
from pyspark.sql.types import StringType, StructType, StructField
from pyspark.ml.evaluation import BinaryClassificationEvaluator
from pyspark.ml.tuning import CrossValidator, ParamGridBuilder
from pyspark.ml.feature import MinMaxScaler
from pyspark.ml.classification import NaiveBayes
from pyspark.mllib.evaluation import MulticlassMetrics
from pyspark.ml.classification import DecisionTreeClassifier
from pyspark.ml.classification import RandomForestClassifier

'''Creamos la configuración inicial de Spark'''
name = 'CC P4 Alvaro de la Flor Bonilla'
conf = SparkConf().setAppName(name)
spark_context = SparkContext(conf=conf)
spark_session = SparkSession.builder.appName(name).getOrCreate()


def existe_directorio(df_file):
    """Comprobamos que se ha ejecutado el anterior script y tenemos el fichero con nuestras columnas"""
    if (os.path.exists(df_file)):
        dataframe = spark_session.read.csv(df_file, header=True, sep=",", inferSchema=True)
        return dataframe
    else:
        """En el caso de que no exista notificamos al usuario que debe ejecutar el script para obtener los datos pruneados"""
        raise Exception("ERROR. Debes ejecutar antes create_new_df.py")


def balancear_clases(df, file):
    """En primer lugar vamos a comprobar el balanceo del dataset, comprobando los casos positivos y negativos"""
    positivos = df[df['class'] == 1].count()
    negativos = df[df['class'] == 0].count()

    """Lo guardamos en un nuevo DF"""
    resultado = [(str(positivos), str(negativos))]
    schema = StructType([
        StructField('Positives', StringType(), False),
        StructField('Negatives', StringType(), False)
    ])
    resultado_dataframe = spark_session.createDataFrame(resultado, schema)

    """Guardamos la clase balanceada en local y en HDFS"""
    resultado_dataframe.write.csv('file:///home/ccsa15408846/cc_p4/' + file, header=True, mode="overwrite")
    resultado_dataframe.write.csv(file, header=True, mode="overwrite")


def preprocesamos_dataframe(df, selected_columns):
    """Preprocesamos los datos siguiente este tutorial https://www.analyticsvidhya.com/blog/2019/11/build-machine-learning-pipelines-pyspark/"""
    caracteristicas_ensamblador = VectorAssembler(inputCols=selected_columns, outputCol="features")
    etiquetas = StringIndexer(inputCol='class', outputCol='label')
    etiquetas = etiquetas.setHandleInvalid("skip")
    etapas = []
    etapas += [caracteristicas_ensamblador]
    etapas += [etiquetas]
    pipeline = Pipeline(stages=etapas)
    modelo_pipeline = pipeline.fit(df)
    dataframe_preprocesado = modelo_pipeline.transform(df)
    cols = ['label', 'features'] + df.columns
    dataframe_preprocesado = dataframe_preprocesado.select(cols)
    return dataframe_preprocesado


def normalizar_escalar(df):
    """Normalizamos los datos y los incluimos en una nueva columna"""
    scaler = MinMaxScaler(inputCol="features", outputCol="scaledFeatures")
    scaler_model = scaler.fit(df)
    scaled_data = scaler_model.transform(df)
    return scaled_data


def under_sampling(df):
    """Tecnica undersampling para balancear las clases"""
    negativos = df[df['class'] == 0]
    positivos = df[df['class'] == 1]

    porcentaje = float(positivos.count()) / float(negativos.count())

    nuevo_dataframe = negativos.sample(withReplacement=False, fraction=porcentaje, seed=2020)
    dataframe_balanceado = nuevo_dataframe.union(positivos)

    return dataframe_balanceado


def over_sampling(df):
    """Tecnica oversampling para balancear las clases"""
    negativos = df[df['class'] == 0]
    positivos = df[df['class'] == 1]

    porcentaje = float(negativos.count()) / float(positivos.count())

    nuevo_dataframe = positivos.sample(withReplacement=True, fraction=porcentaje, seed=2020)
    dataframe_balanceado = nuevo_dataframe.union(negativos)

    return dataframe_balanceado


def evaluar(predictions, file):
    """Evaluamos los modelos usando distintas técnicas, enconcreto curva roc, matriz de confusion, coeficiente kappa y presicion general"""

    # ROC
    evaluador_binary = BinaryClassificationEvaluator()
    roc = round(evaluador_binary.evaluate(predictions) * 100, 3)

    # Matriz de confusión
    predicciones = predictions.select("prediction", "label").rdd
    metricas = MulticlassMetrics(predicciones)

    valores = metricas.confusionMatrix()
    valores_listado = valores.toArray().tolist()
    negativos_aciertos = int(valores_listado[0][0])
    negativos_error = int(valores_listado[1][0])
    positivos_aciertos = int(valores_listado[1][1])
    positivos_error = int(valores_listado[0][1])
    total = negativos_aciertos + negativos_error + positivos_aciertos + positivos_error

    # Precision
    precision = round(metricas.accuracy * 100, 3)

    # Coeficiente Kappa
    probabilidad_observada = float(positivos_aciertos + negativos_aciertos) / total
    probabilidad_esperada = float(((negativos_aciertos + positivos_error) * (negativos_aciertos + negativos_error)) + ((negativos_error + positivos_aciertos) * (positivos_error + positivos_aciertos))) / (total * total)

    kappa = (float(probabilidad_observada - probabilidad_esperada) / (1 - probabilidad_esperada))
    kappa = round(kappa * 100, 3)

    """Guardamos todos los archivos en un documento csv"""
    resultados = [(str(roc), str(precision), str(kappa), str(negativos_aciertos), str(negativos_error), str(positivos_error), str(positivos_aciertos))]
    schema = StructType([
        StructField('ROC', StringType(), False),
        StructField('Precision', StringType(), False),
        StructField('Kappa', StringType(), False),
        StructField('Negativos acierto', StringType(), False),
        StructField('Negativos error', StringType(), False),
        StructField('Positivos error', StringType(), False),
        StructField('Positivos acierto', StringType(), False),
    ])
    resultados_dataframe = spark_session.createDataFrame(resultados, schema)
    resultados_dataframe.coalesce(1).write.csv('file:///home/ccsa15408846/cc_p4/schemas/' + file, header=True, mode="overwrite")
    resultados_dataframe.coalesce(1).write.csv('file:///home/ccsa15408846/cc_p4/results/' + file, header=True, mode="overwrite")


def regresion_logistica_binaria(train, test, iters, regularization):
    """Siguiendo el tutorial https://stackoverflow.com/questions/36697304/how-to-extract-model-hyper-parameters-from-spark-ml-in-pyspark"""
    lr = LogisticRegression(featuresCol='features', labelCol='label', maxIter=iters, elasticNetParam=regularization)

    grid = ParamGridBuilder().addGrid(lr.regParam, [0.1, 0.01, 0.001, 0.0001]).build()
    evaluator = BinaryClassificationEvaluator()
    cv = CrossValidator(estimator=lr, estimatorParamMaps=grid, evaluator=evaluator)
    cv_model = cv.fit(train)
    best_model = cv_model.bestModel

    best_lambda = best_model._java_obj.getRegParam()
    lr = LogisticRegression(featuresCol='scaledFeatures', labelCol='label',
                            maxIter=iters, regParam=best_lambda, elasticNetParam=regularization)
    lr_model = lr.fit(train)
    """Summary of the model and predictions"""
    predictions = lr_model.transform(test)

    return predictions


def naive_bayes(train, test):
    """Siguiendo el tutorial https://ai.plainenglish.io/build-naive-bayes-spam-classifier-on-pyspark-58aa3352e244"""
    naive_bayes_construct = NaiveBayes(modelType="multinomial", featuresCol='scaledFeatures')
    evaluador = BinaryClassificationEvaluator()
    grid = ParamGridBuilder().addGrid(naive_bayes_construct.smoothing, [0.0, 0.5, 1.0]).build()
    cross_validator = CrossValidator(estimator=naive_bayes_construct, estimatorParamMaps=grid, evaluator=evaluador)
    cross_validation_model = cross_validator.fit(train)
    best_model = cross_validation_model.bestModel
    smoothing = best_model._java_obj.getSmoothing()

    naive_bayes_mejorado = NaiveBayes(smoothing=smoothing, modelType="multinomial", featuresCol='scaledFeatures')
    naive_bayes_model = naive_bayes_mejorado.fit(train)
    predicciones = naive_bayes_model.transform(test)

    return predicciones


def decision_tree(train, test, imp, depth):
    decision_tree = DecisionTreeClassifier(labelCol="label", featuresCol="scaledFeatures", impurity=imp, maxDepth=depth, seed=2020)
    decision_tree_model = decision_tree.fit(train)
    predicciones = decision_tree_model.transform(test)

    return predicciones


def random_forest(train, test, imp, depth, n_trees):
    random_forest = RandomForestClassifier(labelCol="label", featuresCol="scaledFeatures", maxDepth=depth, impurity=imp, seed=2020, numTrees=n_trees)
    random_forest_model = random_forest.fit(train)
    predicciones = random_forest_model.transform(test)

    return predicciones


if __name__ == "__main__":
    dataframe = existe_directorio("./filteredC.small.training")
    balancear_clases(dataframe, './original.df.balanced.classes')
    columnas = ['PSSM_r1_3_E', 'PSSM_central_0_Y', 'PSSM_r2_-4_S', 'PSSM_central_-2_Q', 'PSSM_r1_-2_Y', 'PSSM_r1_0_G', 'class']
    dataframe_preprocesado = preprocesamos_dataframe(dataframe, columnas)
    dataframe_scalado = normalizar_escalar(dataframe_preprocesado)

    """Particionamos para el conjunto de entrenamiento y validacion"""
    entrenamiento, validacion = dataframe_scalado.randomSplit([0.7, 0.3], seed=2020)

    """Podemos utilizar una de las dos técnicas"""
    conjunto_balanceado = under_sampling(entrenamiento)
    # balanced_train = over_sampling(train)

    """Modelos de regresión logística binario"""
    prediciones_rlb_1 = regresion_logistica_binaria(conjunto_balanceado, validacion, 10000, 0.0)
    evaluar(prediciones_rlb_1, 'rlb.1')
    prediciones_rlb_2 = regresion_logistica_binaria(conjunto_balanceado, validacion, 10000, 1.0)
    evaluar(prediciones_rlb_2, 'rlb.2')

    """Naive Bayes"""
    prediciones_nb = naive_bayes(conjunto_balanceado, validacion)
    evaluar(prediciones_nb, 'nb')

    """Decision Tree"""
    prediciones_rlb_gini = decision_tree(conjunto_balanceado, validacion, 'gini', 15)
    evaluar(prediciones_rlb_gini, 'dt.gini')
    prediciones_rlb_entropy = decision_tree(conjunto_balanceado, validacion, 'entropy', 15)
    evaluar(prediciones_rlb_entropy, 'dt.entropy')

    """Random Forest"""
    prediciones_rf = random_forest(conjunto_balanceado, validacion, 'entropy', 15, 20)
    evaluar(prediciones_rf, 'rf')

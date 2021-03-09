# Detectando el bosón de Higgs

En este ejercicio se analizarán datos del experimento ATLAS del CERN-LHC, que perseguía la identificación experimental de la partícula bosón de Higgs.

El problema consiste en predecir si un registro de evento corresponde al decaimiento de un bosón de Higgs o se trata de ruido de fondo.

Se trabajará sobre el conjunto de datos ofrecido en la competición de Kaggle [Higgs Boson Machine Learning Challenge](https://www.kaggle.com/c/higgs-boson/). El conjunto de datos se puede descargar directamente desde [este enlace](http://sl.ugr.es/higgs_sige). Los eventos recogidos en este conjunto de datos han sido generados de forma sintética con un simulador.

La descripción de las variables se encuentra en la sección Data del desafío de Kaggle. Cada evento está caracterizado por un identificador, los valores de 30 variables y la etiqueta correspondiente (‘b’: ruido de fondo, ‘s’: bosón).

La descripción detallada de las variables se encuentra [aquí](https://higgsml.lal.in2p3.fr/files/2014/04/documentation_v1.8.pdf). No obstante, únicamente hay que tener en cuenta lo siguiente:

1. Todas las variables son reales, excepto PRI_jet_num, que es un entero.
2. Las variables con el prefijo PRI (PRImitivas) son valores del experimento, mientras que las variables DER (DERivadas) son calculadas por los investigadores del experimento a partir de las anteriores. Se asume que no se conoce cómo se realiza este cálculo.
3. Los valores perdidos o desconocidos se codifican como -999.0.

El conjunto de datos consta de varios ficheros:

- _training.csv_: conjunto de entrenamiento con 250.000 eventos. Cada evento incluye, además de las columnas mencionadas, una columna adicional weight. Aproximadamente, columna especifica la probabilidad de que el evento simulado ocurra en la realidad. Se utiliza para entrenar y validar los clasificadores.
- _test.csv_: conjunto de test con 550.000 eventos. Se utiliza para realizar un envío (submission) a la competición, según las instrucciones de la sección Evaluation del desafío.
- _random\_submission.csv_: Fichero de ejemplo con un envío a Kaggle, según las instrucciones de la sección Evaluation del desafío.
- _HiggsBosonCompetition\_AMSMetric.py_: Implementación en Python de la métrica utilizada en la competición para evaluar la calidad de las soluciones.

El ejercicio se abordará como un problema de clasificación binaria, con dos posibles salidas: {b, s}. La elección de los procedimientos de selección de datos, clasificación y (pre-)procesamiento queda a criterio del estudiante.
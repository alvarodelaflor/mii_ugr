# Master Profesional de Ingeniería Informática
# Inteligencia Computacional
# Álvaro de la Flor Bonilla - alvdebon@correo.ugr.es
import random

import tensorflow as tf
import numpy as np

from keras.constraints import maxnorm
from mnist import MNIST
from keras.callbacks import EarlyStopping

mndata = MNIST('samples')

def crear_modelo(images, labels):

    # Lectura y visualización del set de datos
    print('Prueba de lectura. Se imprimirá un valor aleatorio')
    i = random.randint(0, len(images))
    print('Imprimiendo el ejemplo número ' + str(i) + '. El valor de su etiqueta es: ' + str(labels[i]))
    print(mndata.display(images[i]))

    # Normalizamos los datos
    train_labels = np.asarray(labels)
    train_images = tf.keras.utils.normalize(images, 1)

    # Creamos el modelo
    model = tf.keras.models.Sequential()
    model.add(tf.keras.layers.Flatten())
    model.add(tf.keras.layers.Dense(256, input_dim=10, activation=tf.nn.relu))
    model.add(tf.keras.layers.Dense(128, activation=tf.nn.relu))
    model.add(tf.keras.layers.Dropout(0.4))
    model.add(tf.keras.layers.Dense(64, activation=tf.nn.relu))
    model.add(tf.keras.layers.Dropout(0.3))
    model.add(tf.keras.layers.Dense(32, activation=tf.nn.relu, kernel_constraint=maxnorm(6)))
    model.add(tf.keras.layers.Dropout(0.2))
    model.add(tf.keras.layers.Dense(32, activation=tf.nn.relu, kernel_constraint=maxnorm(6)))
    model.add(tf.keras.layers.Dropout(0.3))
    model.add(tf.keras.layers.Dense(16, activation=tf.nn.relu, kernel_constraint=maxnorm(3)))
    model.add(tf.keras.layers.Dense(10, activation=tf.nn.softmax))

    # Lo compilamos eligiendo la estrategia de función de pérdida, óptimización y las métricas a utilizar
    model.compile(optimizer='adam', loss='sparse_categorical_crossentropy', metrics=['accuracy'])

    # Creamos un criterio de parada para evitar sobreajsute
    es = EarlyStopping(monitor='loss', verbose=2, patience=3)

    # Entrenamos al modelo
    model.fit(train_images, train_labels, epochs=150, callbacks=[es])
    model.summary()

    # Evaluamos los resultados obtenidos
    val_loss, val_acc = model.evaluate(train_images, train_labels)
    print(val_loss, val_acc)

    return model


def guardar_modelo(model):
    model.save('save_models/rnm.model')


def aplicar_modelo(images, labels, indice):
    model = tf.keras.models.load_model('save_models/rnm.model')
    model.compile(optimizer='adam', loss='sparse_categorical_crossentropy', metrics=['accuracy'])
    train_images = np.asarray(images)
    predictions = model.predict([train_images])
    print('Para este ejemplo, el modelo predice que su resultado es:' + str(np.argmax(predictions[indice])))
    print('\nEl resultado real es: ' + str(labels[indice]) + '\nSu representación era la que se muestra a continuación.\n')
    print(mndata.display(images[indice]))


def evaluate(images, labels):
    model = tf.keras.models.load_model('save_models/rnm.model')
    model.compile(optimizer='adam', loss='sparse_categorical_crossentropy', metrics=['accuracy'])
    train_images = np.asarray(images)
    train_images = tf.keras.utils.normalize(train_images, 1)
    train_labels = np.asarray(labels)
    val_loss, val_acc = model.evaluate(train_images, train_labels)
    print(val_loss, val_acc)

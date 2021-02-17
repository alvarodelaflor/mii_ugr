# Master Profesional de Ingeniería Informática
# Inteligencia Computacional
# Álvaro de la Flor Bonilla - alvdebon@correo.ugr.es
import random

import tensorflow as tf
import numpy as np
import sqlite3

from tensorflow.python.estimator import keras
from tensorflow.python.keras.engine.input_layer import InputLayer
from tensorflow.python.keras.models import model_from_json
from mnist import MNIST

mndata = MNIST('samples')


def crear_modelo(images, labels):

    # Lectura y visualización del set de datos. Se elige aleatoriamente una imagen y se muestra
    print('Prueba de lectura. Se imprimirá un valor aleatorio')
    i = random.randint(0, len(images))
    print('Imprimiendo el ejemplo número ' + str(i) + '. El valor de su etiqueta es: ' + str(labels[i]))
    print(mndata.display(images[i]))

    # Normalizamos los datos
    train_images = tf.keras.utils.normalize(images, 1)
    train_labels = np.asarray(labels)

    # Creamos el modelo
    model = tf.keras.models.Sequential()
    model.add(InputLayer(input_shape=(28*28,)))
    model.add(tf.keras.layers.Dense(10, activation=tf.nn.softmax))

    # Compilamos el modelo y comenzamos el entrenamiento
    model.compile(optimizer='adam', loss='sparse_categorical_crossentropy', metrics=['accuracy'])
    model.fit(train_images, train_labels, epochs=15)

    # Evaluamos el modelo construido
    val_loss, val_acc = model.evaluate(train_images, train_labels)
    print(val_loss, val_acc)

    return model


def guardar_modelo(model):
    model.save('save_models/rns.model')


def aplicar_modelo(images, labels, indice):
    model = tf.keras.models.load_model('save_models/rns.model')
    model.compile(optimizer='adam', loss='sparse_categorical_crossentropy', metrics=['accuracy'])
    train_images = np.asarray(images)
    predictions = model.predict([train_images])
    print('Para este ejemplo, el modelo predice que su resultado es: ' + str(np.argmax(predictions[indice])))
    print('El resultado real es: ' + str(labels[indice]) + '\nSu representación era la que se muestra a continuación.')
    print(mndata.display(images[indice]))


def evaluate(images, labels):
    model = tf.keras.models.load_model('save_models/rns.model')
    train_images = tf.keras.utils.normalize(images, 1)
    train_labels = np.asarray(labels)
    val_loss, val_acc = model.evaluate(train_images, train_labels)
    print(val_loss, val_acc)

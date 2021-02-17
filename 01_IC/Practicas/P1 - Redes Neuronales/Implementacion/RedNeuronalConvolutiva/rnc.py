# Master Profesional de Ingeniería Informática
# Inteligencia Computacional
# Álvaro de la Flor Bonilla - alvdebon@correo.ugr.es
import random

import tensorflow as tf
import numpy as np
from keras.constraints import maxnorm
from tensorflow import keras
from tensorflow.keras import layers
from mnist import MNIST
from tensorflow.python.keras.callbacks import EarlyStopping

mndata = MNIST('samples')


def crear_modelo(images, labels):
    num_classes = 10
    input_shape = (28, 28, 1)

    # Normalizamos los datos y redimensionamos
    train_images = np.asarray(images)
    train_images = train_images.reshape(-1, 28, 28, 1)
    train_images = np.expand_dims(train_images, -1)

    train_labels = keras.utils.to_categorical(labels, num_classes)

    # Creamos el modelo
    model = keras.Sequential(
        [
            keras.Input(shape=input_shape),
            layers.Conv2D(32, kernel_size=(3, 3), activation="relu"),
            layers.MaxPooling2D(pool_size=(2, 2)),
            layers.Conv2D(64, kernel_size=(3, 3), activation="relu"),
            layers.MaxPooling2D(pool_size=(2, 2)),
            layers.Flatten(),
            layers.Dropout(0.5),
            layers.Dense(32, activation=tf.nn.relu, kernel_constraint=maxnorm(6)),
            layers.Dropout(0.3),
            layers.Dense(16, activation=tf.nn.relu, kernel_constraint=maxnorm(3)),
            layers.Dense(num_classes, activation="softmax"),
        ]
    )

    # Compilamos nuestro modelo
    model.compile(loss="categorical_crossentropy", optimizer="adam", metrics=["accuracy"])

    # Entrenamos la red
    batch_size = 128
    epochs = 150
    es = EarlyStopping(monitor='loss', verbose=2, patience=5)
    model.fit(train_images, train_labels, batch_size=batch_size, epochs=epochs, validation_split=0.1, callbacks=[es])

    # Evaluamos los resultados obtenidos
    val_loss, val_acc = model.evaluate(train_images, train_labels)
    print(val_loss, val_acc)

    return model


def guardar_modelo(model):
    model.save('save_models/rnc.model')


def aplicar_modelo(images, labels, indice):
    model = tf.keras.models.load_model('save_models/rnc.model')
    model.compile(optimizer='adam', loss='sparse_categorical_crossentropy', metrics=['accuracy'])
    normalize_images = np.asarray(images)
    normalize_labels = np.asarray(labels)
    normalize_images = normalize_images.reshape(-1, 28, 28, 1)
    normalize_images = np.expand_dims(normalize_images, -1)

    predictions = model.predict([normalize_images])
    res = np.argmax(predictions[indice])
    print('Para este ejemplo, el modelo predice que su resultado es:' + str(res))
    print('\nEl resultado real es: ' + str(normalize_labels[indice]) + '\nSu representación era la que se muestra a continuación.\n')
    print(mndata.display(images[indice]))


def evaluate(images, labels):
    num_classes = 10
    input_shape = (28, 28, 1)

    # the data, split between train and test sets

    # Scale images to the [0, 1] range
    x_train = np.asarray(images)
    x_train = x_train.reshape(-1, 28, 28, 1)
    # Make sure images have shape (28, 28, 1)
    x_train = np.expand_dims(x_train, -1)

    # convert class vectors to binary class matrices
    y_train = keras.utils.to_categorical(labels, num_classes)

    model = tf.keras.models.load_model('save_models/rnc.model')
    model.compile(loss="categorical_crossentropy", optimizer="adam", metrics=["accuracy"])
    val_loss, val_acc = model.evaluate(x_train, y_train)
    print(val_loss, val_acc)

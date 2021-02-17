# Master Profesional de Ingeniería Informática
# Inteligencia Computacional
# Álvaro de la Flor Bonilla - alvdebon@correo.ugr.es
import random

import tensorflow as tf
import numpy as np
from keras import layers
from tensorflow import keras
from mnist import MNIST
from tensorflow.python.keras.callbacks import EarlyStopping

mndata = MNIST('samples')


def crear_modelo(train_images, train_labels, test_images, test_labels):
    # Lectura y visualización del set de datos

    i = random.randint(0, len(train_images))
    print('Prueba de lectura. Se imprimirá un valor aleatorio')
    print('Imprimiendo el ejemplo número ' + str(i) + '. El valor de su etiqueta es: ' + str(train_labels[i]))
    print(mndata.display(train_images[i]))

    # Normalizado de los datos de entrenamiento
    train_images = np.asarray(train_images)
    train_images = tf.keras.utils.normalize(train_images, 1)
    train_labels = np.asarray(train_labels)

    # Normalizado de los datos de prueba
    test_images = np.asarray(test_images)
    test_images = tf.keras.utils.normalize(test_images, 1)

    # Contrucción del modelo (autoencoders)
    input_img = keras.Input(shape=(784,))
    encoded = layers.Dense(128, activation='relu')(input_img)
    encoded = layers.Dense(64, activation='relu')(encoded)
    encoded = layers.Dense(32, activation='relu')(encoded)

    decoded = layers.Dense(64, activation='relu')(encoded)
    decoded = layers.Dense(128, activation='relu')(decoded)
    decoded = layers.Dense(784, activation='sigmoid')(decoded)

    autoencoder = keras.Model(input_img, decoded)
    autoencoder.compile(optimizer='adam', loss='binary_crossentropy')

    es = EarlyStopping(monitor='loss', verbose=2, patience=3)
    autoencoder.fit(train_images, train_images,
                    epochs=256,
                    batch_size=256,
                    shuffle=True,
                    validation_data=(test_images, test_images),
                    callbacks=[es])

    # Captura de las imagenes reconstruidas por el autoencoder
    encoded_imgs = autoencoder.predict(train_images)
    encoded_imgs = np.array(encoded_imgs)

    # Construcción del segundo modelo para evaluar resultados
    model = tf.keras.models.Sequential()
    model.add(tf.keras.layers.Dense(10, activation=tf.nn.softmax))

    model.compile(optimizer='adam', loss='sparse_categorical_crossentropy', metrics=['accuracy'])
    model.fit(encoded_imgs, train_labels, epochs=1)

    # Evaluación de los resultados obtenidos
    val_loss, val_acc = model.evaluate(encoded_imgs, train_labels)
    print(val_loss, val_acc)

    return model


def guardar_modelo(model):
    model.save('save_models/a.model')


def aplicar_modelo(images, labels, indice):
    model = tf.keras.models.load_model('save_models/a.model')
    model.compile(optimizer='adam', loss='sparse_categorical_crossentropy', metrics=['accuracy'])
    train_images = np.asarray(images)
    predictions = model.predict([train_images])
    print('Para este ejemplo, el modelo predice que su resultado es:' + str(np.argmax(predictions[indice])))
    print('\nEl resultado real es: ' + str(labels[indice]) + '\nSu representación era la que se muestra a continuación.\n')
    print(mndata.display(images[indice]))


def evaluate(images, labels):
    model = tf.keras.models.load_model('save_models/a.model')
    model.compile(optimizer='adam', loss='sparse_categorical_crossentropy', metrics=['accuracy'])
    train_images = np.asarray(images)
    train_images = tf.keras.utils.normalize(train_images, 1)
    train_labels = np.asarray(labels)
    val_loss, val_acc = model.evaluate(train_images, train_labels)
    print(val_loss, val_acc)



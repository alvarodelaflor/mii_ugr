import random

from Autoencoders import a as a
from RedNeuronalSimple import rns as RNS
from RedNeuronalMulticapa import rnm as RNM
from RedNeuronalConvolutiva import rnc as RNC
from mnist import MNIST

mndata = MNIST('samples')
imagenes_entrenamiento, etiquetas_entrenamiento = mndata.load_training()
imagenes_test, etiquetas_test = mndata.load_testing()


print('Inteligencia Computacional - Máster Oficial de Ingeniería Informática\n')
print('Elija el tipo de Red Neuronal que desea ejecutar\n')
print('    1 - RED NEURONAL SIMPLE')
print('    2 - RED NEURONAL MULTICAPA')
print('    3 - RED NEURONAL CONVOLUTIVA')
print('    4 - AUTOENCODERS')

red = input('\nEscriba el número que identifica la red que desea ejecutar\nElección: ')
while red not in ['1', '2', '3', '4']:
    print(red + ' no es ninguna opción disponible, inténtelo de nuevo')
    red = input('\nEscriba el número que identifica la red que desea ejecutar\nElección: ')

if red == '1':
    print('Ha elegido RED NEURONAL MULTICAPA\n')
    entrenar = input('¿Desea entrenar la red? (s/n)\nElección: ')
    while entrenar not in ['s', 'n']:
        entrenar = input('\nValor no válido, inténtelo de nuevo\n¿Desea entrenar la red? (s/n)\nElección: ')
    if entrenar == 's':
        modelo = RNS.crear_modelo(imagenes_entrenamiento, etiquetas_entrenamiento)
        RNS.guardar_modelo(modelo)
    evaluar = input('¿Desea evaluar la red? (s/n)\nElección: ')
    while evaluar not in ['s', 'n']:
        evaluar = input('\nValor no válido, inténtelo de nuevo\n¿Desea evaluar la red? (s/n)\nElección: ')
    if evaluar == 's':
        tipo = input('¿Desea utilizar datos de entrenamiento (e) o de test (t)? (e/t)\nElección: ')
        while tipo not in ['e', 't']:
            tipo = input('\nValor no válido, inténtelo de nuevo\n¿Desea utilizar datos de entrenamiento (e) o de test (t)? (e/t)\nElección: ')
        if tipo == 'e':
            imagenes = imagenes_entrenamiento
            etiquetas = etiquetas_entrenamiento
        else:
            imagenes = imagenes_test
            etiquetas = etiquetas_test
        RNS.evaluate(imagenes, etiquetas)
    ejemplo = input('\n¿Desea realizar un ejemplo aleatorio para probar el modelo entrenado? (s/n)\nElección: ')
    while ejemplo not in ['s', 'n']:
        ejemplo = input('\nValor no válido, inténtelo de nuevo\n¿Desea realizar un ejemplo aleatorio para probar el modelo entrenado (s/n)\nElección: ')
    if ejemplo == 's':
        indice = random.randint(0, len(imagenes_test))
        RNS.aplicar_modelo(imagenes_test, etiquetas_test, indice)

if red == '2':
    print('Ha elegido RED NEURONAL MULTICAPA\n')
    entrenar = input('¿Desea entrenar la red? (s/n)\nElección: ')
    while entrenar not in ['s', 'n']:
        entrenar = input('\nValor no válido, inténtelo de nuevo\n¿Desea entrenar la red? (s/n)\nElección: ')
    if entrenar == 's':
        modelo = RNM.crear_modelo(imagenes_entrenamiento, etiquetas_entrenamiento)
        RNM.guardar_modelo(modelo)
    evaluar = input('¿Desea evaluar la red? (s/n)\nElección: ')
    while evaluar not in ['s', 'n']:
        evaluar = input('\nValor no válido, inténtelo de nuevo\n¿Desea evaluar la red? (s/n)\nElección: ')
    if evaluar == 's':
        tipo = input('¿Desea utilizar datos de entrenamiento (e) o de test (t)? (e/t)\nElección: ')
        while tipo not in ['e', 't']:
            tipo = input('\nValor no válido, inténtelo de nuevo\n¿Desea utilizar datos de entrenamiento (e) o de test (t)? (e/t)\nElección: ')
        if tipo == 'e':
            imagenes = imagenes_entrenamiento
            etiquetas = etiquetas_entrenamiento
        else:
            imagenes = imagenes_test
            etiquetas = etiquetas_test
        RNM.evaluate(imagenes, etiquetas)
    ejemplo = input('\n¿Desea realizar un ejemplo aleatorio para probar el modelo entrenado? (s/n)\nElección: ')
    while ejemplo not in ['s', 'n']:
        ejemplo = input('\nValor no válido, inténtelo de nuevo\n¿Desea realizar un ejemplo aleatorio para probar el modelo entrenado (s/n)\nElección: ')
    if ejemplo == 's':
        indice = random.randint(0, len(imagenes_test))
        RNM.aplicar_modelo(imagenes_test, etiquetas_test, indice)

if red == '3':
    print('Ha elegido RED NEUROANL CONVOLUTIVA\n')
    entrenar = input('¿Desea entrenar la red? (s/n)\nElección: ')
    while entrenar not in ['s', 'n']:
        entrenar = input('\nValor no válido, inténtelo de nuevo\n¿Desea entrenar la red? (s/n)\nElección: ')
    if entrenar == 's':
        modelo = RNC.crear_modelo(imagenes_entrenamiento, etiquetas_entrenamiento)
        RNC.guardar_modelo(modelo)
    evaluar = input('¿Desea evaluar la red? (s/n)\nElección: ')
    while evaluar not in ['s', 'n']:
        evaluar = input('\nValor no válido, inténtelo de nuevo\n¿Desea evaluar la red? (s/n)\nElección: ')
    if evaluar == 's':
        tipo = input('¿Desea utilizar datos de entrenamiento (e) o de test (t)? (e/t)\nElección: ')
        while tipo not in ['e', 't']:
            tipo = input(
                '\nValor no válido, inténtelo de nuevo\n¿Desea utilizar datos de entrenamiento (e) o de test (t)? (e/t)\nElección: ')
        if tipo == 'e':
            imagenes = imagenes_entrenamiento
            etiquetas = etiquetas_entrenamiento
        else:
            imagenes = imagenes_test
            etiquetas = etiquetas_test
        RNC.evaluate(imagenes, etiquetas)
    ejemplo = input('\n¿Desea realizar un ejemplo aleatorio para probar el modelo entrenado? (s/n)\nElección: ')
    while ejemplo not in ['s', 'n']:
        ejemplo = input(
            '\nValor no válido, inténtelo de nuevo\n¿Desea realizar un ejemplo aleatorio para probar el modelo entrenado (s/n)\nElección: ')
    if ejemplo == 's':
        indice = random.randint(0, len(imagenes_test))
        RNC.aplicar_modelo(imagenes_test, etiquetas_test, indice)

if red == '4':
    print('Ha elegido AUTOENCODERS\n')
    entrenar = input('¿Desea entrenar la red? (s/n)\nElección: ')
    while entrenar not in ['s', 'n']:
        entrenar = input('\nValor no válido, inténtelo de nuevo\n¿Desea entrenar la red? (s/n)\nElección: ')
    if entrenar == 's':
        modelo = a.crear_modelo(imagenes_entrenamiento, etiquetas_entrenamiento, imagenes_test, etiquetas_test)
        a.guardar_modelo(modelo)
    evaluar = input('¿Desea evaluar la red? (s/n)\nElección: ')
    while evaluar not in ['s', 'n']:
        evaluar = input('\nValor no válido, inténtelo de nuevo\n¿Desea evaluar la red? (s/n)\nElección: ')
    if evaluar == 's':
        tipo = input('¿Desea utilizar datos de entrenamiento (e) o de test (t)? (e/t)\nElección: ')
        while tipo not in ['e', 't']:
            tipo = input('\nValor no válido, inténtelo de nuevo\n¿Desea utilizar datos de entrenamiento (e) o de test (t)? (e/t)\nElección: ')
        if tipo == 'e':
            imagenes = imagenes_entrenamiento
            etiquetas = etiquetas_entrenamiento
        else:
            imagenes = imagenes_test
            etiquetas = etiquetas_test
        a.evaluate(imagenes, etiquetas)
    ejemplo = input('\n¿Desea realizar un ejemplo aleatorio para probar el modelo entrenado? (s/n)\nElección: ')
    while ejemplo not in ['s', 'n']:
        ejemplo = input('\nValor no válido, inténtelo de nuevo\n¿Desea realizar un ejemplo aleatorio para probar el modelo entrenado (s/n)\nElección: ')
    if ejemplo == 's':
        indice = random.randint(0, len(imagenes_test))
        a.aplicar_modelo(imagenes_test, etiquetas_test, indice)

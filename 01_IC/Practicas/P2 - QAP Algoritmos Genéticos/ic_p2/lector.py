import numpy as np
def readData (file):
    f = open("data/"+file)

    # Dimensión del problema
    dimension = int(f.readline().split()[0])

    # Lectura de la primera matriz
    f.readline()
    matrixLines = [f.readline().split() for i in range(dimension)]
    matriz_flujo = np.asarray([[int(number) for number in line] for line in matrixLines], dtype = np.int64)

    # Lectura de la segunda matriz
    f.readline()
    matrixLines = [f.readline().split() for i in range(dimension)]
    matriz_distancia = np.asarray([[int(number) for number in line] for line in matrixLines], dtype = np.int64)

    return dimension, matriz_flujo, matriz_distancia
import lector as rd
import ag_simple
import ag_lamarck
import ag_balwinian
import numpy as np
from collections import namedtuple

genericParameters = namedtuple("parametros", "tamano_poblacion probabilidad_cruce probabilidad_mutacion")
np.random.seed(12345678)

parameters = genericParameters(100, 0.5, 0.02)
problemDim, weightMtx, distanceMtx = rd.readData("tai256c.dat")

ags = ag_simple.AG(problemDim, weightMtx, distanceMtx)
agl = ag_lamarck.AG(problemDim, weightMtx, distanceMtx)
agb = ag_balwinian.AG(problemDim, weightMtx, distanceMtx)

text = "Escriba el tipo de Algoritmo genético que desea utilizar\n    1 - Algoritmo Genético Simple\n    2 - Algoritmo Genético Lamarck\n    3 - Algoritmo Genético Balwin\nElección: "
valor_usuario = input(text)
while valor_usuario not in ["1", "2", "3"]:
    print("Valor introducido: " + valor_usuario)
    print("Valor no válido, inténtelo de nuevo")
    valor_usuario = input(text)
if valor_usuario == "1":
    print(ags.AG(parameters))
elif valor_usuario == "2":
    print(agl.AG(parameters))
elif valor_usuario == "3":
    print(agb.AG(parameters))

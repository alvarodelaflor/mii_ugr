import numpy as np
from math import *
import time
import random

#Implementation of a generic genetic algorithm

class AG:
    def __init__(self, dimension_problema, matriz_peso, matriz_distancia):
        self.dim = dimension_problema
        self.mP = matriz_peso
        self.mD = matriz_distancia

    def cruce(self, parent1, parent2, i, j):
        if i > j:
            i,j = j,i

        n = self.dim

        idx = list(range(j+1,n)) + list(range(0, i))
        child1, child2 = parent1.copy(), parent2.copy()

        # En este apartado lo que vamos a hacer es cruzar a los dos padres
        # En concreto vamos a cruzar desde la posición todos los elementos exteriores al intervalo i,j
        # Tenemos que tener en cuenta que el nuevo elemento que insertemos no se encuentre ya en el intervalo i,j
        child1["cromosoma"][idx] = [elem for elem in parent2["cromosoma"] if elem not in parent1["cromosoma"][i:j+1]]
        child2["cromosoma"][idx] = [elem for elem in parent1["cromosoma"] if elem not in parent2["cromosoma"][i:j+1]]

        child1["coste"], child2["coste"] = self.coste(child1["cromosoma"]), self.coste(child2["cromosoma"])

        return child1, child2

    def mutar(self, individual, i, j):
        if i > j:
            i,j = j,i

        individual["coste"] = self.coste_mutacion(individual["cromosoma"], individual["coste"], i, j)
        individual["cromosoma"][i], individual["cromosoma"][j] = individual["cromosoma"][j], individual["cromosoma"][i]

    def coste(self, cromosoma):
        return sum(np.sum(self.mP * self.mD[cromosoma[:, None], cromosoma], 1))

    def coste_mutacion(self, cromosoma_actual, coste_actual, i, j):
        newChromosome = np.copy(cromosoma_actual)
        newChromosome[i], newChromosome[j] = newChromosome[j], newChromosome[i]

        coste_mutacion = coste_actual - sum(np.sum(self.mP[[i, j], :] * self.mD[cromosoma_actual[[i, j], None], cromosoma_actual], 1))
        coste_mutacion += sum(np.sum(self.mP[[i, j], :] * self.mD[newChromosome[[i, j], None], newChromosome], 1))

        idx = list(range(self.dim))
        del (idx[i])
        del (idx[j - 1])

        coste_mutacion -= sum(sum(self.mP[idx][:, [i, j]] * self.mD[cromosoma_actual[idx, None], cromosoma_actual[[i, j]]]))
        coste_mutacion += sum(sum(self.mP[idx][:, [i, j]] * self.mD[newChromosome[idx, None], newChromosome[[i, j]]]))
        return coste_mutacion

    def AG(self, parametros):
        # Establecemos la dimensión
        dimension = self.dim
        print("Se está ejecutando el algoritmo genético simple en un problema de dimensión: ", self.dim)
        comienzo = time.time()

        # Establecemos el tamaño de la población, la probabilidad de cruce y la probabilidad de mutación
        tamano_poblacion = parametros.tamano_poblacion
        probabilidad_cruce = parametros.probabilidad_cruce
        probabilidad_mutacion = parametros.probabilidad_mutacion

        # GUARDAR LOS DATOS DESPUES
        baseName = "dataGAgeneric/PC" + str(probabilidad_cruce) + "PM" + str(probabilidad_mutacion)
        # GUARDAR LOS DATOS DESPUES

        results = np.zeros(100, dtype = np.int64)

        # Establecemos el numero de cruces y el número de mutaciones, devolvemos el mayor entero cercano
        # Para establecer los siguientes parametros se ha utilizado la guia que queda adjunta en la documetacion como
        # cruce_mutacion.pdf
        # Vease el extracto en: https://imgur.com/a/lQ1FnGG
        numero_cruces = ceil(tamano_poblacion / 2.0 * probabilidad_cruce)
        numero_mutaciones = ceil(tamano_poblacion * dimension * probabilidad_mutacion)

        # Le damos nombre a la cadena del cromosoma
        tipo_escalar = str(dimension) + 'int'
        # La propiedad dtype se utiliza para definir y acceder al tipo de datos de un array
        # A cada cadena le asignamos un coste o puntuación
        dataType = np.dtype([('cromosoma', tipo_escalar), ('coste', np.int64)])

        # Creamos la población inicial
        parent = np.zeros(tamano_poblacion, dtype=dataType)

        for individual in parent:
            individual["cromosoma"] = np.random.permutation(dimension)
            # Se obtiene el peso para cada uno de los cromosomas de la permutacion
            individual["coste"] = self.coste(individual["cromosoma"])

        # Ordenamos la población inicial en funcion del coste de cada cromosoma
        parent.sort(order="coste", kind='mergesort')

        for i in range(100):
            # Mecanismo de selección basada en torneo

            # Participantes
            participantes_idx = np.empty(tamano_poblacion, dtype=np.int32)

            # En este paso seleccionamos los participantes aleatoriamente
            for idx in range(tamano_poblacion):
                participantes_idx[idx] = np.random.randint(low=0, high=np.random.randint(1, tamano_poblacion))

            # En el siguiente método utilizaremos "array slicing" -> array[start:end:step] y contruimos los pares seleccionados sobre los que vamos a operar
            pares_seleccionados = zip(participantes_idx[0:2*numero_cruces:2], participantes_idx[1:2*numero_cruces:2])

            # Cruzamos los cromosomas seleccionados
            hijo = np.zeros(tamano_poblacion, dtype = dataType)
            puntos_cruce = np.random.randint(dimension, size = 2*numero_cruces)

            # range(start, stop, step)
            # Comenzamos a iterar sobre el conjunto de pares seleccionados aleatorios que construimos antes y los puntos de corte que también creamos antes
            for par, indice_1, indice_2 in zip(pares_seleccionados, range(0, 2*numero_cruces, 2), range(1, 2*numero_cruces, 2)):
                hijo[indice_1], hijo[indice_2] = self.cruce(parent[par[0]], parent[par[1]], puntos_cruce[indice_1], puntos_cruce[indice_2])

            # En este momento tenemos la mitad producto de un cruce y la otra mitad copiados de su padre
            hijo[2*numero_cruces:] = parent[participantes_idx[2*numero_cruces:]].copy()

            # Mutamos el cromosoma hijo
            hijo_mutado_idx = np.random.randint(tamano_poblacion, size=numero_mutaciones)
            genes_mutados = np.random.randint(dimension, size = 2*numero_mutaciones)

            # Por cada hijo seleccionado vamos a mutar su gen
            for indice_poblacion, indice_gen in zip(hijo_mutado_idx, range(numero_mutaciones)):
                self.mutar(hijo[indice_poblacion], genes_mutados[2 * indice_gen], genes_mutados[2 * indice_gen + 1])

            # Reemplazo
            hijo.sort(order = "coste", kind = 'mergesort')

            if hijo["coste"][0] > parent["coste"][0]:
                hijo[-1] = parent[0]

            parent = hijo

            parent.sort(order = "coste", kind = 'mergesort')
            print("Coste del mejor padre en la generación (AGS)", i, int(parent[0]["coste"]))
            results[i] = parent[0]["coste"]

        return parent[0]["cromosoma"], parent[0]["coste"]

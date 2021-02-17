import numpy as np


class Opt:
    def __init__(self, dimension_problema, matriz_distancia, matriz_peso):
        self.dimension = dimension_problema
        self.mD = matriz_distancia
        self.mP = matriz_peso

    def coste(self, cromosoma):
        return sum(np.sum(self.mP * self.mD[cromosoma[:, None], cromosoma], 1))

    def twoOpt(self, individuo_inicial):
        S = individuo_inicial.copy()
        T = S.copy()
        T["coste"] = -1

        while (S["coste"] != T["coste"]):
            s_coste = S["coste"]
            t_coste = T["coste"]
            for i in range(self.dimension):
                for j in range(i + 1, self.dimension):
                    T = S.copy()
                    T["cromosoma"][i], T["cromosoma"][j] = S["cromosoma"][j], S["cromosoma"][i]
                    T["coste"] = self.coste(T["cromosoma"])

                    if T["coste"] < S["coste"]:
                        S = T
            s_coste = S["coste"]
            t_coste = T["coste"]
        return S

    def twoOptBalwin (self, initialIndividual):
        bestIndividual = initialIndividual.copy()
        bestIndividual["coste"] = -1
        S = initialIndividual.copy()

        a_cost = 0
        b_cost = 1

        while (a_cost != b_cost):
            bestIndividual = S.copy()


            for i in range(self.dimension):
                for j in range(i+1, self.dimension):
                    S_copy = S.copy()
                    S["cromosoma"][i], S["cromosoma"][j] = S["cromosoma"][j], S["cromosoma"][i]
                    newScore = self.coste(S["cromosoma"])

                    if S["coste"] > newScore:
                        S["coste"] = newScore
                    else:
                        S["cromosoma"][i], S["cromosoma"][j] = S["cromosoma"][j], S["cromosoma"][i]
            a_cost = S["coste"]
            b_cost = bestIndividual["coste"]

        return S["coste"]

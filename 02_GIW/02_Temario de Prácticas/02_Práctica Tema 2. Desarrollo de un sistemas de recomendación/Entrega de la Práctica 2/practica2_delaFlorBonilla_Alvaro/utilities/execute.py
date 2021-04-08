from utilities.movie_util import *
from utilities.rate_util import *
from utilities.user_util import *


def p2():
    print(filter_user(age="24"))


def ask(question, options, attempts):
    res = (False, -1)
    aux = True
    i = 1
    while aux and i < attempts:
        user_input = input(question + ". Opciones: " + str(options) + "\n\nDesición: ")
        if user_input not in options:
            print('Opción, no válida, inténtelo de nuevo. Intento ' + str(i) + " (máximo: " + str(attempts) + ")")
            i = i + 1
        else:
            aux = False
            res = (True, user_input)
    return res

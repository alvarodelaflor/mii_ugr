from utilities.movie_util import *
from utilities.rate_util import *
from utilities.user_util import *
from recommender.recommender import *
import shelve


def p2():
    user_input = ask("1   Ejecutar recomendador\n2   Volver a cargar base de datos\n0   Salir", ["1", "2", "0"], 3)
    if user_input[True] == "1":
        loadDict(user_rate(get_20_random_movies()))
        ranking = getRecommendations(shelve.open("dataRS.dat")['ItemsPrefs'], 944)
        print("Le recomendamos las siguientes películas:")
        for i in range(0, 20):
            movie = filter_movie(str(ranking[i][1]))[0]
            print('Nombre: ' + movie.title + '\nPuntuación: ' + str(ranking[i][0]) + '/5')
    elif user_input[True] == "2":
        print("Volviendo a cargar datos de películas. En progreso...")
        save_movies(read_u_item())
        print("Volviendo a cargar datos de puntuaciones. En progreso...")
        save_rates(read_u_data())
        print("Volviendo a cargar datos de usuarios. En progreso...")
        save_users(read_u_user())
    elif user_input[True] == "0":
        print("¡Hasta luego!")


def ask(question, options, attempts):
    res = (False, "-1")
    aux = True
    i = 1
    while aux and i <= attempts:
        user_input = input(question + "\n\nDesición (opciones: " + str(options) + "): ")
        if user_input not in options:
            print('Opción, no válida, inténtelo de nuevo. Intento ' + str(i) + " (máximo: " + str(attempts) + ")")
            i = i + 1
        else:
            aux = False
            res = (True, user_input)
    return res

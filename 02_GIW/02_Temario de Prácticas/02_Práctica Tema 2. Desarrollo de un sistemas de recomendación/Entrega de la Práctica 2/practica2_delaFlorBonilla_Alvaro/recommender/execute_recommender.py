from recommender.recommender import *
from utilities.movie_util import *


def execute_search():
    load_dict(user_rate(get_20_random_movies()))
    ranking = getRecommendations(shelve.open("./data/p2.dat")['ItemsPrefs'], 944)
    print("Le recomendamos las siguientes películas:")
    for i in range(0, 20):
        movie = filter_movie(str(ranking[i][1]))[0]
        print('Nombre: ' + movie.title + '\nPuntuación: ' + str(ranking[i][0]) + '/5')
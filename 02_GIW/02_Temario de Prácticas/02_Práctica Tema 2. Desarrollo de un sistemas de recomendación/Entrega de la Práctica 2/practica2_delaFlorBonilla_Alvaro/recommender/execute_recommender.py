from recommender.recommender import *
from utilities.movie_util import *
from utilities.constants import *


def custom_order(e):
    return float(e[2]['rate'])


def execute_search():
    rates = user_rate(get_20_random_movies())
    load_dict(rates)
    ranking = getRecommendations(shelve.open(get_url_dict())['dict_item'], 944)
    ids = list(map(lambda x: str(x[1]), ranking))[:get_number_of_recommended_movies()]
    movies = filter_movie({"$in": ids})
    rates_json = list(map(lambda x: x.to_json(), rates))
    movies_and_id = list(map(lambda x: ({"title": x.title}, {"id": x.movie_id}, {"rate": list(filter(lambda y: (str(y[1]) == x.movie_id), ranking))[0][0]}), movies))
    movies_and_id.sort(key=custom_order, reverse=True)
    save_user_rate_search(rates_json, movies_and_id)
    print("Le recomendamos las siguientes películas:")
    for i in range(0, get_number_of_recommended_movies()):
        print('Nombre: ' + movies[i].title + '\nPuntuación: ' + str(ranking[i][0]) + '/5\n')

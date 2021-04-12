from recommender.recommender import *
from utilities.movie_util import *
from utilities.constants import *


def custom_order(e):
    return float(e[2]['rate'])


def execute_search():
    rates = user_rate(get_20_random_movies())
    load_dict(rates)
    ranking = get_recommendations(shelve.open(get_url_dict())['dict_user'], int(get_new_user_id()))
    ids = list(map(lambda x: str(x[1]), ranking))[:get_number_of_recommended_movies()]
    movies = filter_movie({"$in": ids})
    rates_json = list(map(lambda x: x.to_json(), rates))
    movies_and_id = list(map(lambda x: ({"title": x.title}, {"id": x.movie_id}, {"rate": list(filter(lambda y: (str(y[1]) == x.movie_id), ranking))[0][0]}), movies))
    movies_and_id.sort(key=custom_order, reverse=True)
    movies_rated = save_user_rate_search(rates_json, movies_and_id)
    print("Le recomendamos las siguientes películas:")
    for i in range(0, get_number_of_recommended_movies()):
        try:
            print('Nombre: ' + movies_rated[i]['title'] + '\nPuntuación: ' + str(movies_rated[i]['rate']) + '/5\n')
        except ValueError as f:
            print('Error: ' + str(f))

import json
from datetime import datetime
from random import sample
from utilities.constants import *
from classes.Movie import Movie
from classes.Rate import Rate


def get_genres(genres_cod):
    genres = []
    if genres_cod[0].strip() == '1':
        genres.append('unknown')
    if genres_cod[1].strip() == '1':
        genres.append('Action')
    if genres_cod[2].strip() == '1':
        genres.append('Adventure')
    if genres_cod[3].strip() == '1':
        genres.append('Animation')
    if genres_cod[4].strip() == '1':
        genres.append("Children s")
    if genres_cod[5].strip() == '1':
        genres.append('Comedy')
    if genres_cod[6].strip() == '1':
        genres.append('Crime')
    if genres_cod[7].strip() == '1':
        genres.append('Documentary')
    if genres_cod[8].strip() == '1':
        genres.append('Drama')
    if genres_cod[9].strip() == '1':
        genres.append('Fantasy')
    if genres_cod[10].strip() == '1':
        genres.append('Film-Noir')
    if genres_cod[11].strip() == '1':
        genres.append('Horror')
    if genres_cod[12].strip() == '1':
        genres.append('Musical')
    if genres_cod[13].strip() == '1':
        genres.append('Mystery')
    if genres_cod[14].strip() == '1':
        genres.append('Romance')
    if genres_cod[15].strip() == '1':
        genres.append('Sci-Fi')
    if genres_cod[16].strip() == '1':
        genres.append('Thriller')
    if genres_cod[17].strip() == '1':
        genres.append('War')
    if genres_cod[18].strip() == '1':
        genres.append('Western')

    return genres


def read_u_item():
    movies = []
    with open(get_url_u_item()) as f:
        lines = f.readlines()
        for line in lines:
            x = line.split('|')
            movie_id = str(x[0])
            title = x[1]
            release_date = x[2]
            video_release_date = x[3]
            imdb_url = x[4]
            genres = get_genres(x[-19:])

            movie = Movie(movie_id=movie_id, title=title, release_date=release_date, video_release_date=video_release_date, imdb_url=imdb_url,genres=genres)
            movies.append(movie)

    return movies


def save_movies(movies):
    db = get_mongo_db()
    movies_mongodb = db.movies
    movies_mongodb.drop()
    movie_dict = []
    for movie in movies:
        movie_dict.append(json.loads(movie.to_json()))
    movies_mongodb.insert_many(movie_dict)


def parse_date(date):
    new_date = ""
    if date:
        new_date = datetime.strptime(str(date[0:10]), '%Y-%m-%d')
        new_date = str(new_date.day) + "-" + str(new_date.strftime("%B")[0:3]) + "-" + str(new_date.year)
    return new_date


def to_class(movie_json):
    return Movie(movie_id=movie_json.get('movie_id'), title=movie_json.get('title'), release_date=parse_date(movie_json.get('release_date')), video_release_date=parse_date(movie_json.get('video_release_date')), imdb_url=movie_json.get('imdb_url'), genres=movie_json.get('genres'))


def filter_movie(movie_id=None, title=None, release_date=None, video_release_date=None, imdb_url=None, genres=None, exact_genres=False):
    db = get_mongo_db()
    movies_mongodb = db.movies

    query = {}
    if movie_id is not None:
        query['movie_id'] = movie_id
    if title is not None:
        query['title'] = title
    if release_date is not None:
        query['release_date'] = release_date
    if video_release_date is not None:
        query['video_release_date'] = video_release_date
    if imdb_url is not None:
        query['imdb_url'] = imdb_url
    if genres is not None:
        if exact_genres:
            query['genres'] = genres
        else:
            query['genres'] = {"$in": genres}

    movies = list(map(lambda x: to_class(x), list(movies_mongodb.find(query))))

    return movies


def get_20_random_movies():
    movies_id = sample(range(1, 1682), 20)
    ids = list(map(lambda x: str(x), movies_id))

    db = get_mongo_db()
    movies_mongodb = db.movies

    query = {"movie_id": {"$in": ids}}
    movies = list(map(lambda x: to_class(x), list(movies_mongodb.find(query))))
    return movies


def user_rate(movies):
    rates = []
    i = 1
    print("Establezca su valoración para las siguientes películas 20 películas\n")
    for movie in movies:
        print("\n" + str(i) + "   " + movie.title)
        rate = ask("Inserte su valoración", ["1", "2", "3", "4", "5"], 3)
        if rate[1] == "-1":
            print("No se ha insertado ninguna calificación válida, se establece 3 por defecto\n")
            user_id = get_new_user_id()
            item_id = movie.movie_id
            rating = "3"
            timestamp = datetime.now().timestamp()
            new_rate = Rate(user_id=user_id, item_id=item_id, rating=rating, timestamp=timestamp)
            rates.append(new_rate)
        else:
            user_id = get_new_user_id()
            item_id = movie.movie_id
            rating = rate[1]
            timestamp = datetime.now().timestamp()
            new_rate = Rate(user_id=user_id, item_id=item_id, rating=rating, timestamp=timestamp)
            rates.append(new_rate)
        i = i + 1
    return rates


def ask(question, options, attempts):
    res = (False, "-1")
    aux = True
    i = 1
    while aux and i <= attempts:
        user_input = input(question + "\nDesición (opciones: " + str(options) + "): ")
        if user_input not in options:
            print('Opción, no válida, inténtelo de nuevo. Intento ' + str(i) + " (máximo: " + str(attempts) + ")")
            i = i + 1
        else:
            aux = False
            res = (True, user_input)
    return res

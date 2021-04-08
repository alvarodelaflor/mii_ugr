import json
from datetime import datetime


def parse_date(date):
    new_date = ""
    if date:
        new_date = datetime.strptime(date, '%d-%b-%Y')
    return new_date


class Movie:
    def __init__(self, movie_id, title, release_date, video_release_date, imdb_url, genres):
        self.movie_id = movie_id
        self.title = title
        self.release_date = parse_date(release_date)
        self.video_release_date = parse_date(video_release_date)
        self.imdb_url = imdb_url
        self.genres = genres

    def to_json(self):
        self.release_date = self.release_date.__str__()
        self.video_release_date = self.video_release_date.__str__()
        return json.dumps(self, default=lambda o: o.__dict__,
                          sort_keys=True, indent=4)



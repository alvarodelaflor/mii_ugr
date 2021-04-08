import json


class Rate:

    def __init__(self, user_id, item_id, rating, timestamp):
        self.user_id = user_id
        self.item_id = item_id
        self.rating = rating
        self.timestamp = timestamp

    def to_json(self):
        return json.dumps(self, default=lambda o: o.__dict__,
            sort_keys=True, indent=4)
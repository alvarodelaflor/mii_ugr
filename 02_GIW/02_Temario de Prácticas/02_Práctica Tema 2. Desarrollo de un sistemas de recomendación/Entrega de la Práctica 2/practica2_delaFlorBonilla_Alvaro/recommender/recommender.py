from math import sqrt
from utilities.rate_util import *
from utilities.constants import *
import shelve


def load_dict(rates_add):
    """
    Main method for finding similarity between items
    :param rates_add: Last rating made by the user to whom movies are to be recommended
    :return:
    """
    # We create the matrix of users and scores for each item
    dict_user={}
    # Instantiate a persistent dictionary
    static_dict = shelve.open(get_url_dict())
    # We obtain all the initial scores
    rates = get_all_rates()
    length = len(rates)
    # We add the latest ratings made by users
    rates.extend(rates_add)
    # We check that the last ratings have been added correctly
    check = (length + get_number_of_movies_to_qualify()) == len(rates)
    if check:
        for rate in rates:
            # We obtain the main variables
            user = int(rate.user_id)
            itemid = int(rate.item_id)
            rating = float(rate.rating)
            # We create the key in the dictionary in case it does not exist
            dict_user.setdefault(user, {})
            # We associate to each user/movie its rating
            dict_user[user][itemid] = rating
        # We associate our static dictionary with our previous dictionary (dict_user)
        static_dict['dict_user'] = dict_user
        static_dict.close()
    # If the last rates have not been added correctly, an error is thrown
    else:
        raise ValueError("The last ratings have not been added correctly.")


def get_distance(dict_user, user_1, user_2):
    """
    Calculates distance between two users
    Translated from slide 31 of topic 2
    :param dict_user: dictionary with all user ratings
    :param user_1: user who just made the ratings
    :param user_2: user to which it is compared
    :return:
    """
    # Get the dict of shared movies
    common_movies = {}
    for item in dict_user[user_1]:
        if item in dict_user[user_2]:
            common_movies[item] = 1

        if len(common_movies) == 0:
            return 0

        # Add up the squares of all the differences
        sum_of_squares = sum([pow(dict_user[user_1][item] - dict_user[user_2][item], 2)
                              for item in dict_user[user_1] if item in dict_user[user_2]])

        return 1 / (1 + sum_of_squares)


def get_pearson(dict_user, user_1, user_2):
    """
    Calculates Pearson's correlation between two users
    :param dict_user:
    :param user_1:
    :param user_2:
    :return:
    """
    # First we calculate the set of shared rates
    common_item = {}
    for item in dict_user[user_1]:
        if item in dict_user[user_2]:
            common_item[item] = 1

    # Sum calculations
    size = len(common_item)

    # If it do not share any rating, we return 0
    if size == 0:
        return 0

    # Sums of all the preferences
    sum_1 = sum([dict_user[user_1][movie] for movie in common_item])
    sum_2 = sum([dict_user[user_2][movie] for movie in common_item])

    # Sums of the squares
    sum_sqrt_1 = sum([pow(dict_user[user_1][movie], 2) for movie in common_item])
    sum_sqrt_2 = sum([pow(dict_user[user_2][movie], 2) for movie in common_item])

    # Sum of the products
    product_sum = sum([dict_user[user_1][movie] * dict_user[user_2][movie] for movie in common_item])

    # Calculate r (Pearson score)
    numerator = product_sum - (sum_1 * sum_2 / size)
    denominator = sqrt((sum_sqrt_1 - pow(sum_1, 2) / size) * (sum_sqrt_2 - pow(sum_2, 2) / size))

    if denominator == 0:
        return 0

    res = numerator / denominator

    return res


def top_matches(dict_user, person, similarity=get_pearson):
    """
    Calculates similarity to other user-movies
    :param dict_user:
    :param person:
    :param similarity:
    :return:
    """
    scores = [(similarity(dict_user, int(person), other), other) for other in dict_user if other != person]
    scores.sort()
    scores.reverse()
    return scores[0:get_number_of_recommended_movies()]


def get_recommendations(dict_user, person, similarity=get_pearson):
    """
    Movies rated based on previous user ratings
    :param dict_user:
    :param person:
    :param similarity:
    :return:
    """
    weighted_score = {}
    similarity_sums = {}
    for other in dict_user:
        # Don't compare the same user
        if other == person:
            continue
        similarity_result = similarity(dict_user, person, other)
        # Ignore scores of zero or lower
        if similarity_result <= 0:
            continue
        for movie in dict_user[other]:
            # We add movies not seen by the user
            if movie not in dict_user[person] or dict_user[person][movie] == 0:
                # Similarity * Score
                weighted_score.setdefault(movie, 0)
                weighted_score[movie] += dict_user[other][movie] * similarity_result
                # Sum of similarities
                similarity_sums.setdefault(movie, 0)
                similarity_sums[movie] += similarity_result

    # Create the normalized list
    rankings_aux = [(total / similarity_sums[item], item) for item, total in weighted_score.items()]
    rankings = list(filter(lambda x: (5 - x[0] > 0.01), rankings_aux))

    # Return the sorted list
    rankings.sort()
    rankings.reverse()

    return rankings


def transform_dict_user(dict_user):
    dict_item = {}
    for person in dict_user:
        for movie in dict_user[person]:
            dict_item.setdefault(movie, {})

            # Permute the values of person by movie
            dict_item[movie][person] = dict_user[person][movie]

    return dict_item

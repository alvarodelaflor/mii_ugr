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
        # We calculate the similarity matrix
        static_dict['sim_items'] = calculate_similar_items(dict_user, n=get_number_of_recommended_movies)
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
    # Get the list of shared_items
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


def get_pearson(dict_user, item_1, item_2):
    """
    Calculates Pearson's correlation between two objects
    :param dict_user:
    :param item_1:
    :param item_2:
    :return:
    """
    # First we calculate the set of shared rates
    common_item = {}
    for item in dict_user[item_1]:
        if item in dict_user[item_2]:
            common_item[item] = 1

    # If it do not share any rating, we return 0
    if len(common_item) == 0:
        return 0

    # Sum calculations
    n = len(common_item)

    # Sums of all the preferences
    sum_1 = sum([dict_user[item_1][subitem] for subitem in common_item])
    sum_2 = sum([dict_user[item_2][subitem] for subitem in common_item])

    # Sums of the squares
    sum_sqrt_1 = sum([pow(dict_user[item_1][subitem], 2) for subitem in common_item])
    sum_sqrt_2 = sum([pow(dict_user[item_2][subitem], 2) for subitem in common_item])

    # Sum of the products
    product_sum = sum([dict_user[item_1][subitem] * dict_user[item_2][subitem] for subitem in common_item])

    # Calculate r (Pearson score)
    numerator = product_sum - (sum_1 * sum_2 / n)
    denominator = sqrt((sum_sqrt_1 - pow(sum_1, 2) / n) * (sum_sqrt_2 - pow(sum_2, 2) / n))

    if denominator == 0:
        return 0

    r = numerator / denominator

    return r


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
    totals = {}
    simSums = {}
    for other in dict_user:
        # don't compare me to myself
        if other == person: continue
        sim = similarity(dict_user, person, other)
        # ignore scores of zero or lower
        if sim <= 0: continue
        for item in dict_user[other]:
            # only score movies I haven't seen yet
            if item not in dict_user[person] or dict_user[person][item] == 0:
                # Similarity * Score
                totals.setdefault(item, 0)
                totals[item] += dict_user[other][item] * sim
                # Sum of similarities
                simSums.setdefault(item, 0)
                simSums[item] += sim

    # Create the normalized list
    rankings_aux = [(total / simSums[item], item) for item, total in totals.items()]
    rankings = list(filter(lambda x: (5 - x[0] > 0.01), rankings_aux))
    # Return the sorted list
    rankings.sort()
    rankings.reverse()
    return rankings


def transform_dict_user(dict_user):
    result = {}
    for person in dict_user:
        for item in dict_user[person]:
            result.setdefault(item, {})

            # Permute the values
            result[item][person] = dict_user[person][item]
    return result


def calculate_similar_items(dict_user, n=get_number_of_recommended_movies()):
    # Create a dictionary of items
    result = {}
    # Invert the preference
    print("Permutando matriz...")
    dict_item = transform_dict_user(dict_user)

    print("\nCalculando matriz de similitud...\n")

    for item in dict_item:
        # Status updates for large datasets
        # Find the most similar items to this one
        scores = top_matches(dict_item, item, similarity=get_distance)
        result[item] = scores
    return result

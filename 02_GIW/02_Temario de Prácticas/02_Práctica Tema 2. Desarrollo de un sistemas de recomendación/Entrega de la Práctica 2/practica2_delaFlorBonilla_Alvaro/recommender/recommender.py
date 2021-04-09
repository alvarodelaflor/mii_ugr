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


# Returns a distance-based similarity score for person1 and person2
def sim_distance(dict_user, person1, person2):
    # Get the list of shared_items
    si = {}
    for item in dict_user[person1]:
        if item in dict_user[person2]:
            si[item] = 1

        # if they have no ratings in common, return 0
        if len(si) == 0:
            return 0

        # Add up the squares of all the differences
        sum_of_squares = sum([pow(dict_user[person1][item] - dict_user[person2][item], 2)
                              for item in dict_user[person1] if item in dict_user[person2]])

        return 1 / (1 + sum_of_squares)


# Returns the Pearson correlation coefficient for p1 and p2
def sim_pearson(dict_user, p1, p2):
    # Get the list of mutually rated items
    si = {}
    for item in dict_user[p1]:
        if item in dict_user[p2]: si[item] = 1

    # if they are no ratings in common, return 0
    if len(si) == 0: return 0

    # Sum calculations
    n = len(si)

    # Sums of all the preferences
    sum1 = sum([dict_user[p1][it] for it in si])
    sum2 = sum([dict_user[p2][it] for it in si])

    # Sums of the squares
    sum1Sq = sum([pow(dict_user[p1][it], 2) for it in si])
    sum2Sq = sum([pow(dict_user[p2][it], 2) for it in si])

    # Sum of the products
    pSum = sum([dict_user[p1][it] * dict_user[p2][it] for it in si])

    # Calculate r (Pearson score)
    num = pSum - (sum1 * sum2 / n)
    den = sqrt((sum1Sq - pow(sum1, 2) / n) * (sum2Sq - pow(sum2, 2) / n))
    if den == 0: return 0

    r = num / den

    return r


# Returns the best matches for person from the dict_user dictionary.
# Number of results and similarity function are optional params.
def top_matches(dict_user, person, similarity=sim_pearson):
    scores = [(similarity(dict_user, person, other), other)
              for other in dict_user if other != person]
    scores.sort()
    scores.reverse()
    return scores[0:get_number_of_recommended_movies()]


# Gets recommendations for a person by using a weighted average of every other user's rankings
def getRecommendations(dict_user, person, similarity=sim_pearson):
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
    rankings = [(total / simSums[item], item) for item, total in totals.items()]
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
        scores = top_matches(dict_item, item, similarity=sim_distance)
        result[item] = scores
    return result

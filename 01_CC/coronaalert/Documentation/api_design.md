# **API design**

### *API Built*

*What exactly do we need in our project?* In this section we will make a justification of the design of the API to be built. To begin this iteration let's assume that users have been able to register and have an account in the system.

First of all (and as indicated in the user history [US3](https://github.com/alvarodelaflor/CoronaAlert/issues/18), one of the main resources that we will need (necessary for many functions, such as building an attendance) will be everything related to access to a student's schedule. First of all, we will need to be able to create a POST to the following URL.

> /schedulle

As in the previous case, a JSON must be sent in the following format:

        {
                studentId: '243434r3v34v3v',
                subjects: ['CC1', 'PGPI', 'IC']
        }

We will use the following URL:

1. **TYPE GET**. To obtain a query (since many functions of our project require it) of type GET, for example for cases where the system needs to check that the student belongs to that class in order to create an attendance ([US2](https://github.com/alvarodelaflor/CoronaAlert/issues/17) or to complete a contact tracking
2. **TYPE DELETE**. To delete the entity, as required in [US4](https://github.com/alvarodelaflor/CoronaAlert/issues/19).

> /schedule/:id

In the case that what you want to do is an edition of a certain schedule, as required in [US5](https://github.com/alvarodelaflor/CoronaAlert/issues/20) we will make a **PUT** request to the same URL, in which we will have to add a JSON with the body of the request equal to the one shown above.

Once the user has a valid schedulle, he or she can carry out one of the main objectives of our project, the system will be able to create an attendance to this user, as required in [US2](https://github.com/alvarodelaflor/CoronaAlert/issues/17). To do this, we will make a POST to a URL determines that counts the JSON needed to create this class, specifically we have thought of using:

> /attendance

This JSON should follow the structure as the following example:

        {
                studentId: '243434r3v34v3v',
                positionX: 12,
                positionY: 4,
                turn: 1
        }

The object is pruned since with the student's ID and the shift he has attended, checking his schedule we can see which class it is. In fact, it has also been thought to remove the *turn* attribute and use the system time. However, it has been discarded because the user may want to insert this attendance after it has been done.

In addition, it may be the case that internally we need to access a certain attendance, for example to do a count as requested in [US7](https://github.com/alvarodelaflor/CoronaAlert/issues/23). For this reason, we have thought that the most correct thing to do is to perform a GET to the following URL:

> /attendance/:id


Finishing with everything related to the attendance class, we may also require at some point to completely eliminate a created entity, for this the best is to make a DELETE request to the same URL indicated above.

One of the main options that the user should be allowed to do is to add the results of his tests ([US6](https://github.com/alvarodelaflor/CoronaAlert/issues/21)), as this will trigger the search for possible infections that may have occurred and prevent the system itself from allowing the user to attend a classroom class (or to allow him to attend again in the event that he adds a negative test result). To do this, you must make a POST call to the following URL that we propose:

> /test

The content that the body of this call must have must follow the following scheme (JSON):

        {
                date: '2020-02-02,
                userId: '243434r3v34v3v',
                typeTest: 'PCR',
                result: true
        }

On the other hand, these results should also be accessible internally, so we also propose a query URL with GET call type:

> /test/:id

The system administrator may require that a search for possible contacts be made from the result of a post-test, so the following call is proposed as a GET type and asynchronous behavior, which will be responsible for, given the identification of a positive test, to track all possible infections that have occurred, as required in [US7](https://github.com/alvarodelaflor/CoronaAlert/issues/23). We propose the following URL for this purpose:

> /infection/do/:id

On the other hand, it is quite interesting to obtain all the reports of possible infections that have occurred, so we propose the following URL once again:

> /infection/get

Finally, internally it will be necessary that, once detected the respective possible infections inform each of the affected persons ([US8](https://github.com/alvarodelaflor/CoronaAlert/issues/24)), because it is proposed the following call type GET, which must provide the id of the test consistent start of this contact:

> /notification/:id

***
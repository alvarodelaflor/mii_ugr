# **Using the task manager in CI**

For this project we have decided to use two platforms for continuous integration, *Travis* as main tool and *CircleCI* as support.

In this section we will explain how we have used in both our task manager.

## **Travis**

In the case of *Travis*, unlike *CircleCI*, we have done it explicitly. Then we will comment which have been the differences.

First of all, since we use a variant of *Make*, in our case *Mmake*, we needed to do the installation. For it in *Travis* it is very simple and the only thing that we have had to make is to add to our file the following sentences:

    before_install:
    - curl -sf https://gobinaries.com/tj/mmake/cmd/mmake | sudo sh
    - alias make=mmake

From now on we are completely independent from *SBT* to run the tests and we can make a completely customized configuration for its execution, as you can check in our [Makefile file](../coronaalert/Makefile).

## **CircleCI**

Regarding our secondary continuous integration system, we have not made an explicit configuration of the task manager in it, since due to the use of Docker, we can make use of it directly as it is part of the container.

For this it has been very simple and you can check it in the [Dockerfile](../Dockerfile) file. The only thing we have done is to add it to the *RUN* statement. The excerpt of the added statement would be the following:

    RUN:
        sh -c "$(curl -sf https://gobinaries.com/tj/mmake/cmd/mmake)" && \
        alias make=mmake

With the previous sentence our task manager is already available in the container. To run the tests it is as simple as executing the following statement:

> docker run -t -v `pwd`:/app/test alvarodelaflor/coronaalert

In fact this is the statement we should use in *CircleCI*, specifically, the configuration we should add to *CircleCI* is:

     - run: |
         echo "$PASSWORD" | docker login --username $USERNAME --password-stdin
         docker run -t -v `pwd`:/app/test alvarodelaflor/coronaalert sh -c "make test"

Later we will see in the [Circle configuration settings](docker_ci.md) there are several variants that affect the way our task manager is invoked.
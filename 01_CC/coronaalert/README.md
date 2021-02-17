![CoronaAlert UGR](https://i.imgur.com/AOs72bD.png)

# CoronaAlert UGR

[![License](https://img.shields.io/badge/License-GPL_v3.0-blue)]()
[![Release](https://img.shields.io/badge/Release-7.0.0-blue)]()
[![Build Status](https://travis-ci.com/alvarodelaflor/CoronaAlert.svg?branch=master)](https://travis-ci.com/alvarodelaflor/CoronaAlert)
[![CircleCI](https://circleci.com/gh/alvarodelaflor/CoronaAlert.svg?style=svg)](https://app.circleci.com/pipelines/github/alvarodelaflor/CoronaAlert)

>:warning: **Project currently under development**

CoronaAlert UGR is an application that monitors tracking in the different classrooms of the [ETSIIT](https://etsiit.ugr.es/) of the [University of Granada](https://www.ugr.es/).

The problem to be solved by this application is to detect cases of contact with positive cases of the [Coronavirus](https://www.who.int/health-topics/coronavirus#tab=tab_1) from the subject's schedule and the classrooms he has visited. The application will notify the students who have had contact with such a positive case.

You can obtain a more extensive version of the problem description by clicking on the following [link](Documentation/description.md).

***


## **Roadmap**

In the following links you can see information about the following topics:

  - :mag: [Justification of the problem](Documentation/problem_justification.md) *Analysis of the need for classes from a functional point of view*
  - :calendar: [Project Plan](./Documentation/project_plan.md) *Development and management of the various tasks*
  - :construction: [Architecture of the problem](Documentation/architecture-description.md)
  - :star: [Main classes](Documentation/classes.md) *A short explanation of the main classes and related issues has been made in this link.*
  - :man: :woman: [User Stories](Documentation/user_stories.md)
  - :whale2: [Docker configuration](Documentation/docker.md)
  - :wrench: [Tool selection](Documentation/tools.md)
  - :computer: [Technical justification of the framework chosen for the microservice](Documentation/microservice.md)
  - :mailbox_with_mail: [Overall API design](Documentation/api_design.md)
  - :pencil: [Distributed configuration](Documentation/distributed_configuration.md) and [Logs](Documentation/logs.md)
  - :microscope: [Microframework Test](Documentation/test_microframework.md)
  - :arrows_counterclockwise: [Continuous integration - Travis](Documentation/ci_travis.md) and [Continuous integration alternative](Documentation/alternative_ci_system.md)
  - :hammer: [Using the task manager in CI](Documentation/ci_task_manager.md)
  - :whale2: :clipboard: [Use of the Docker container in the CI](Documentation/docker_ci.md)

***

## :m: :six: *progress*

[Documentation and justification of the cluster structure](Documentation/cluster_structure.md)

[Documentation and justification of the configuration of each of the containers that compose it](Documentation/containers_configuration.md)

[Composition file documentation](Documentation/composition_file.md)

[Testing, performance testing and deployment](Documentation/speed_deploy.md)

***

## *Installation and use*

If you wish to use our system via *Docker* simply use the following instruction:

    docker run -t -v `pwd`:/app/test alvarodelaflor/coronaalert sh -c "make run"

On the other hand, in case you want to do it through this repository you must have *Mmake* installed on your computer. See more information about it in this [link](https://github.com/tj/mmake).

Once installed *Mmake* is very simple, to run the project (after having cloned this repository and once inside the directory) you only need to use:

    mmake run

In case you want to run the tests you should use:

    mmake test

***

## **License**

This project uses a [GNU GENERAL PUBLIC LICENSE](https://www.gnu.org/licenses/gpl-3.0.html) in particular, for more information on this you can access through the following [link](https://github.com/alvarodelaflor/CoronaAlert/blob/master/LICENSE) .

***

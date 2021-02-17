# **Tool selection**

## **Programming language**

The main objective of this course, in my opinion, is to learn as much as possible and that is why I would like the language used for this application to be something totally new to me.

There were several languages that appeared as candidates:

- Scala
- Go
- Ruby

Finally the chosen one was *Scala*, it is one of the most used languages in the last years and little by little it is having a great relevance internationally.

Below are some of the reasons why this programming language was finally chosen.

#### *Object Oriented Programming and functional programming*

Having specialized in Java, I can extend patterns similar to those I know to this language. In addition, the object oriented programming allows the division of the problem in small parts completely compatible with the microservices architecture that has been raised.

On the other hand Scala also supports functional programming, something relatively new to me and which will be interesting to learn.

#### *Similarity with Java*

Although this project also aims to learn a new language as a personal challenge, there is a limited time available for a learning period, so using a language similar to Java will facilitate this process.

***

## *Construction tool*

Unlike other programming languages *Scala* has its own configuration and construction tool, which is part of the language. 

*[SBT(Scala Build Tool)](https://www.scala-sbt.org/)* has been chosen in this case as the main construction tool.

## *Continuous Integration*

As we have done in the self-assessment thesis and in order to reuse previous knowledge, in addition to good integration with GitHub, the continuous integration software [*Travis*](https://travis-ci.org/github/alvarodelaflor/CoronaAlert) will be used on this occasion.

## *Database*

For this project the use of a non-relational database is being considered, in particular the use of [*MongoDB*](https://www.mongodb.com), [*Realm*](https://realm.io) and [*Firebase Realtime Database*](https://firebase.google.com/docs/database/).

At first, *Realm* has been discarded as it is a more thoughtful database and closer to the development of mobile applications.

As for the comparison between *Firebase* and *MongoDB*, surprisingly (I personally did not expect such a difference) as shown in [this comparison](https://www.educba.com/firebase-vs-mongodb/), *MongoDB* is far superior in many respects, not only in efficiency.

Therefore, for this project, **MongoDB** will finally be used.

## **Justification of the task manager used**

One of the objectives of this course is to achieve that both the development and the deployment are automated, and therefore achieve the replication of the whole project.

If we focus on our specific project, it was clarified that *Scala* will be used as the main programming language. Consequently, using Scala allows the use of **SBT** which is a language-specific construction tool.

However, there are many situations in which **SBT** is limited, for example if another programming language is to be used in the project itself. Other external tools are therefore needed.

### **Choice**

Before starting this course I was unaware of the existence of task managers. Moreover, I knew hardly anything about construction tools, except perhaps for *'npm'*.

As a basis for this choice, this [link](https://awesomeopensource.com/projects/task-manager) has been used. It simply compares the different task managers according to the score of its GitHub repository.

After comparing the different options offered, it has finally been decided to use *[Modern Make - mmake](https://github.com/tj/mmake)*. Some of the reasons why it has finally been chosen are the following:

1. **Same syntax as Make**

Mmake is simply the same as make, but with some added functionality, for example, mmake provides a help command.

2. **Documentation**

I have never used a task manager as such and, as I have seen, *make* has a large community of users. There are an infinite number of help portals to go to, as well as excellent documentation on their use, in comparison with the other task managers that appear on the previous portal.

Personally, I think that to begin with, thanks to how well known and used it is, *make* (and therefore *make*) is an excellent option for getting started in the world of task managers.

3. **Project compatibility**

For now, the tasks we have used are so basic that using **SBT** would be enough:

> sbt clean
> 
> sbt compile
> 
> sbt run

However, there will come a point when this construction tool is not enough and something more complex needs to be used, such as *mmake*.

A good example could be the use of Makefiles for the displacement, which we will certainly have to use and which is totally impossible to do with the use of *SBT* alone.

*Example taken from [full360.com](https://insight.full360.com/the-magic-of-makefiles-d8256cbd17cb), which uses a Makefile for GitLab CI:*

    stages:
      - test
      - build
      - deploy

    test:
      stage: test
      image: scala-sbt:8-2-1-jdk-alpine
      script:
        - make test
      tags:
        - service

    build:
      stage: test
      image: docker
      script:
        - make build
      tags:
        - service
        
    deploy:
      stage: test
      image: nomad
      script:
        - make deploy
      tags:
        - service

> :warning: **Note:** As the project progresses this section will be extended, adding the real uses that have been given to *mmake* as a restropective.

### **Installation and use**

To install **mmake** just run:

> curl -sf https://gobinaries.com/tj/mmake/cmd/mmake | sudo sh

In addition, we can assign an alias to *mmake* to treat it exactly like *make*:

> alias make=mmake

From this moment on, make is already installed in the system.

The next step is to build the first *[Makefile](Makefile)* file. A very simple one has been built, only the task of compiling and executing the [Main](src/main/scala/Main.scala) file has been created (compiling all the classes previously):

    compile:
      sbt clean
      sbt compile

    run:
      sbt clean
      sbt compile
      sbt run

If you run *"make run "*, make will take care of performing sbt clean, sbt compile and finally sbt run.

## **Tests**

### **Assertion Library**

The [Assertions](http://doc.scalatest.org/1.7/org/scalatest/Assertions.html) library will be used to carry out the tests. Among other things, the test framework *ScalaTest* will be used later, and this library is native to this framework.

In particular, we will show some of the functionalities that this library allows (although it allows many others). The ones that have been more interesting, and therefore have made us choose this library, are:

1. assert(**express**)

> *express*: expression to be evaluated.

2. assert(**express**, **msg**)

> *express*: expression to be evaluated.

> *msg*: message expressed when the assertion fails.

3. **Expected result**

For me, one of the most interesting functions of this library and the reason why I chose it.

We will explain it with a brief example:

      val a = 5
      val b = 2
      expect(2) {
        a - b
      }

This method is very simple, you can perform operations (for example in this case *a - b*) and the final result will be evaluated with the expression indicated in the expect.

Unlike the two previous methods, this method allows you to perform much more complex operations in a more comfortable and simple way.

### **Test framework used**

The test framework for this project will be [*Scalatest*](https://www.scalatest.org/).

It is clear that another great choice would have been to use [*JUnit*](https://junit.org/), however, there are certain characteristics that have finally made the final choice *Scalatest*.

1. **Matchers**

It is true that we will use assertions as described above (in particular the *Assertions* library). However, *Scalatest* includes an interesting and powerful functionality, the use of *Matchers*. 

The *Matchers* allow you to test by using a very natural language, close to the one you would use to express yourself. Let's use an example to show it (*extracted from this [link](https://dzone.com/articles/top-5-reasons-choose-scalatest)*):

      Array(3,2,1) should have size 3// check the size of an array
      string should include regex "wo.ld"// check string against regular expression
      temp should be a 'file // check that temp is a file

I found it very useful, interesting and I was quite impressed by it. I think that it could be a good idea to use it in our project, as it can speed up the construction of the tests by using a syntax so close to natural language.

2. **Parallel Tests**

If the tests are correctly defined, they can be run independently, just by adding the following definition to SBT:

> testForkedParallel in Test := true

If you want to run them sequentially, as before, you just have to use them:

> fork in Test := true

### **Installation**

The following rooms must be added to *build.sbt*:

    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.2"
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.2" % "test

Just run *make compile* to check that everything went well.

### **Tests done**

So far, the initial tests have been performed on the main class of the system, *[Attendance](src/main/scala/Attendance.scala)* and on the class *[Schedule](src/main/scala/Schedule.scala)*.

Specifically, the tests have been carried out in the classes:

- [AttendanceTest](src/test/scala/AttendanceTest.scala)
- [ScheduleTest](src/test/scala/ScheduleTest.scala)

To run the tests, the *Makefile* file has been configured to run them in a simple way, you only have to execute:

> make test

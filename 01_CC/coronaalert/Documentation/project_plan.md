# **Project Plan**

In this section we will explain how the project will be developed. That is, it will describe each of the stages that will be set, that will be carried out in each of them and finally it will specify in what state the project is when each of these stages is completed.

Each phase of development is aimed at achieving a MVP. This section therefore aims to describe the set of phases in which the product will be advanced, obtaining in each of them a MVP that adds new functionality to the system being developed.

## **PHASE I**

In the first phase of the project I want to achieve as MVP a schedule, and for that, consequently, we should also be able to make the system able to create new accounts, both for professors and for students.

### *Development*

For this development, the following issues must be completed. They will be ordered by grouping them according to the user stories they belong to:

 - [[US9] Create a professor's account](https://github.com/alvarodelaflor/CoronaAlert/issues/57):
   - [Create the initial model of the person class](https://github.com/alvarodelaflor/CoronaAlert/issues/61)
   - [A professor must request the system to create an account](https://github.com/alvarodelaflor/CoronaAlert/issues/62)
 - [[US10] Create a student's account](https://github.com/alvarodelaflor/CoronaAlert/issues/58)
   - [Create the initial model of the person class](https://github.com/alvarodelaflor/CoronaAlert/issues/61).
   - [A student must apply to the system to create an account](https://github.com/alvarodelaflor/CoronaAlert/issues/63)

As you have seen, US9 and US10 user stories share some tasks, since their container class is the same.

- [[US1] Edit my personal data as user](https://github.com/alvarodelaflor/CoronaAlert/issues/16)
  - [The user can modify his contact details](https://github.com/alvarodelaflor/CoronaAlert/issues/66)

- [[US3] Create a Schedule as user](https://github.com/alvarodelaflor/CoronaAlert/issues/18)
  - [Create the initial model of the schedule class](https://github.com/alvarodelaflor/CoronaAlert/issues/64)
  - [A user can create a schedule and link it to their account](https://github.com/alvarodelaflor/CoronaAlert/issues/65)

### *Project status*

When this phase is completed, a product will have been obtained that will be able to allow users who have a registered account in the system to create their schedules.

## **PHASE II**

For this phase of the project I want a product as assistance, in which the system is able to record where each student has been every day he or she attends the university.

### *Development*

For this development, the following issues must be completed. They will be ordered by grouping them according to the user stories they belong to:

 - [[US2] Create a class attendance as professor](https://github.com/alvarodelaflor/CoronaAlert/issues/17)
   - [Create the initial model of the attendance class](https://github.com/alvarodelaflor/CoronaAlert/issues/67)
   - [A professor can record a student's attendance](https://github.com/alvarodelaflor/CoronaAlert/issues/68)

It may happen, that the timetables are subject to errors, or must be updated so that a professor can correctly record an attendance, so the following tasks must also be done.

- [[US5] Edit a Schedule as user](https://github.com/alvarodelaflor/CoronaAlert/issues/20)
  - [Users can edit the subjects on their schedule](https://github.com/alvarodelaflor/CoronaAlert/issues/69)

- [[US4] Delete a Schedule as user](https://github.com/alvarodelaflor/CoronaAlert/issues/19)
  - [In case it is completely wrong, the user can delete his schedule from the system](https://github.com/alvarodelaflor/CoronaAlert/issues/70)

### *Project status*

When this phase of the project is finished, we will have a product in which, adding to everything that could be done from the previous phase, now professors will be able to register the attendance of their students and therefore, we will have a system that is able to know the positioning that the students of a university have had while they were in their facilities.

## **PHASE III**

In this phase of the project we want to obtain as MVP the traceability, that is to say, in case of an outbreak, to carry out a tracking of the possible infections that have taken place. Of course, the system will have to contact them in case this possible contagion is confirmed.

### *Development*

For this development, the following issues must be completed. They will be ordered by grouping them according to the user stories they belong to:

 - [[US6] Create a Test result as user](https://github.com/alvarodelaflor/CoronaAlert/issues/21)
   - [Create the initial model of the test class](https://github.com/alvarodelaflor/CoronaAlert/issues/71)
   - [The user can register the result of his test in the system](https://github.com/alvarodelaflor/CoronaAlert/issues/72)

- [[US7] Collect contacts with other users as administrator](https://github.com/alvarodelaflor/CoronaAlert/issues/23)
  - [Find all classes a student has been in in a period of time](https://github.com/alvarodelaflor/CoronaAlert/issues/73)
  - [Look for possible contagion that a student may have caused](https://github.com/alvarodelaflor/CoronaAlert/issues/74)

- [[US8] Notify other students of contact with positive case as administrator](https://github.com/alvarodelaflor/CoronaAlert/issues/24)
  - [Configure the system so that it can send emails](https://github.com/alvarodelaflor/CoronaAlert/issues/75)
  - [Notify possible infections that they have been exposed](https://github.com/alvarodelaflor/CoronaAlert/issues/76)

### *Project status*

At the end of this phase, we will have a product that, in the event of an outbreak at the university, will be able to trace all the infections that may have occurred as a result of this person attending the university, and notify these people of what happened.
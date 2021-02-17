# **Justification of the Problem** :mag:

> :warning: Thanks to this analysis, 2 previous classes that had no real use have been eliminated: Clasroom and Subject

The objective of this document is to carry out an analysis of the problem that tries to solve the application that will be developed in this project.

Firstly, as a context, the main problem to be solved is the following scenario:

    Jaime is a third year student of the degree in Computer Engineering at ETSIIT. Given the current situation, Jaime is aware of the care he must take. 
    
    However, last Wednesday, because a member he had contact with notified him that he had been infected by the virus, he decided to take a test, which unfortunately was also positive.

    After this, Jaime notified his situation to the management of the centre to alert all the people who had contact with him in class, one week before his test and until today (as indicated by the authorities).

    The problem that Jaime discovers is that no attendance control has been carried out, and therefore it is impossible to trace the contacts he may have made.

So how can this problem be solved?

**1. ATTENDANCE CLASS**. Modeling student attendance. 
 
This will be the main class in the system.

First of all, it is clear that the attendance of each student must be recorded, so that later on a trace can be made with real data of who has had contact with which person. This record should also indicate what data really needs to be stored.

- Map of persons and positions. This means that in this main class you will need to store the student (or teacher) who has attended and where he sat
- Date. In order to make a later tracking of data, it will be a fundamental element to carry out the filtering.
- Place of the contact. Because each class has a unique identifier we could use this as a class identifier. For example, the theoretical class of Planning and Management of Computer Projects is PGPI, in the case that it is practical of group 1 it is PGPI1, in addition, each one of them has a fixed classroom that cannot be modified during the whole academic course.

In addition, the user must indicate in which position he or she is sitting, since on this occasion the relative distance between the different users is very important. For them the map of persons named above will be configured as follows:

    Map(Person -> (x, y))

Only the professor user will be able to add this assistance to the system, to guarantee the veracity of the data.

That is to say, a map where the key is the person who registers attendance and the value the position x,y with respect to the place where he has sat.

Let's use an example to explain it:

    The last class of DSS1, on 11/10/2020 was attended by 4 people, Luis, Jaime, Maria and Marta. The class modelled will be Attendance = [11/10/2020, ((Luis, (1,3)), (Jaime, (1,2)), (María, (1,1)), (Marta, (2,2))), DSS1]

    Jaime alerts the system that he has COVID since 04/10/20. The only thing the system will have to do is filter out all the attendances after 04/10/20, which Javi has attended. In this way, he will know that he has to alert his colleagues Luis, María and Marta.

    The system will also take into account the distance between users.

According to the above we can assure you that we need 5 more classes: 
- The first will be a control class that will allow us to add the objects attended and the result of the tests and carry out a trace. 
- The second one will be another class that models the person.
- The third class will model the tests.
- The fourth other class that models to the schedules.
- The fifth class a controller that allows to notify people that they could be infected.

**2. CONTACT CONTROLLER CLASS** *Adding new attendances to the system*

First of all, this class should allow the addition of the attendance of the different students and teachers as data. In this way, it will be possible to track all possible infections only by making sub-consultations. The user who registers his or her attendance is added as *"Person "* to the above-mentioned attribute.

On the other hand, he will also have to allow the addition of test results (in the following point we will explain what the tests consist of). In other words, when a user reports a positive test the system must allow this information to be stored, in order to be able to initiate the trace.

Finally, a scan must be carried out in which, after a user indicates a positive case, a search is made for possible infections that may have been caused.

**3. CLASS PERSON** *Modeling both students and teachers*

This class will simply have to model the user of the application and have the necessary attributes to be able to identify and notify him/her of any event. For example his name, surname, age, telephone, university and mail.

In addition, in order to facilitate the search of the classes in which he has been also could be requested that he indicates his schedule. For example, a user has been infected since 13/10/2020 but has only attended the DSS class. We could make a query to find all the attendances after the 13th in which the student X has been, specifically in the subject DSS.

**4. TEST CLASS** *Modelling the result of a test by COVID-19*

We will need to have important information regarding this test such as
- Results. Positive or negative.
- Date of the test
- Person who has taken this test

As we have mentioned before, when a person adds the result of a test the controller will simply have to, in case the result is positive, start the traced search of the contacts that have been maintained by this person who has added the test to the system.

**5. CLASS SCHEDULE** Modeling a student's schedule

Due to the current situation, a student's attendance schedule is very volatile, so it is required that the student has a validity deadline. The student must also have all the subjects he can attend in a week. 

In addition, this class will be used to restrict which users can and cannot attend a class.

An example of a schedule could be the following.

    Jaime has a timetable valid until 01/12/2020 in which he can attend in person the Computer Project Planning and Management course (and the group 2 of practices of this course) and Cloud Computing (and the group 1 of practices of this course).

    The timetable will be:
     - validityDay: 01/12/2020
     - subjects: ("PGPI", "PGPI2", "CC", "CC1")

**6. NOTIFICATION CONTROLLER CLASS** Notify users of infections

The function of this class is, once the analysis and tracking of possible infections has been carried out, to notify each user that they may have been infected by other people.
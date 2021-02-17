package controllers

import java.time.LocalDate
import models.{Attendance, Person, Schedule, Test}

class DatabaseController {

  // TEST DATA INITIAL

  val date = LocalDate.of(2020,12,12)
  var listClassroom1 = List("PGPI", "PGPI2", "CC")
  var listClassroom2 = List("DES", "DSS", "TID")
  var listClassroom3 = List("DES", "D", "SSII")
  val schedule1 = new Schedule(date, listClassroom1)
  val schedule2 = new Schedule(date, listClassroom2)
  val schedule3 = new Schedule(date, listClassroom2)
  val alum1 = new Person("154088845H","Alvaro", "de la Flor Bonilla", false, 22, "+34665381123", "UGR", "cc.coronaalert@gmail.com", schedule1, true)
  val alum2 = new Person("232088841H","Javier", "Cuevas Lozano", false, 21, "+34665391235", "UGR", "javier@correo.ugr.es", schedule1, true)
  val alum3 = new Person("243234236B","Miguel", "Gonzalez Morales", false, 22, "+34656237217", "UGR", "miguel@correo.ugr.es", schedule3, true)
  val alum4 = new Person("243234235B","Miguel", "Gonzalez Morales", false, 22, "+34656237217", "UGR", "miguel@correo.ugr.es", schedule3, false)
  val professor = new Person("154088845Y","Carlos", "Torres", true, 34, "+34665341123", "UGR", "carlos@ugr.es", null, true)

  val personAux1 = new Person("154088845Y", "Alvaro", "de la Flor Bonilla", true, 22, "+34665381123", "UGR", "cc.coronaalert@gmail.com", schedule1, true)
  val personAux2 = new Person("254088845Y","Carlos", "de la Flor Bonilla", false, 22, "+34665381123", "UGR", "cc.coronaalert@gmail.com", schedule1, true)
  val personAux3 = new Person("254788845Y","Carlos", "de la Flor Bonilla", false, 22, "+34665381123", "UGR", "cc.coronaalert@gmail.com", schedule1, true)

  val persons = List(alum1, alum2, alum3, alum4, professor, personAux1, personAux2, personAux3)

  val listPerson = Map(personAux1 -> (1,2), personAux2 -> (2,3), personAux3 -> (16,9))
  val attendance1 = new Attendance(listPerson, LocalDate.now().minusDays(5), "PGPI")
  val attendance2 = new Attendance(listPerson, LocalDate.now().plusDays(40), "PGPI")

  val test1 = new Test("ADDA", true, date, personAux1, "PCR")
  val tests = List(test1)

  val attendances = List(attendance1, attendance2)

  def checkUserInDatabase(person: Person): Boolean = {
    var res = true
    // TODO Check in DB
    val personToFind = persons.filter(x => x.dni.equals(person.dni))
    if (personToFind.isEmpty) {
      res = false
    } else if (personToFind.head != null && !personToFind.head.equals(person)){
      val person1 = person
      val person2 = personToFind.head
      println(person1)
      println(person2)
      throw new IllegalArgumentException("Data integrity has changed, the person has different data")
    }
    res
  }

  def getUserById(id: String): Person = {
    return persons.filter(x => x.dni.equals(id)).head
  }

  def getTestById(id: String): Test = {
    return tests.filter((x => x.id.equals(id))).head
  }

  def addAttendanceToDb(attendance: Attendance) : Boolean = {
    true
  }

  def getLastTestByPerson(person: Person) : Test = {
    //TODO
    null
  }

  def searchAttendance(day: LocalDate, subject: String) : Attendance = {
    /*
    This method looks for an existing assistance in the system, in case it exists it will be used to add new people,
    in case it does not exist it will be returned null
     */
    null
  }
}


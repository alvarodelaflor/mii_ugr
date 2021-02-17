package controllers

import models.{Attendance, Person, Schedule}
import controllers.ContactController
import org.scalatest._

import java.time.LocalDate

class ContactTest extends FlatSpec {

  var contactController = new ContactController

  // Checking possible contact
  "An contact " should "exist in the system" in {
    var listClassroom1 = List("PGPI", "PGPI2", "CC")
    val schedule1 = new Schedule(LocalDate.now(), listClassroom1)
    val personAux1 = new Person("154088845Y", "Alvaro", "de la Flor Bonilla", true, 22, "+34665381123", "UGR", "info@alvarodelaflor.com", schedule1, true)
    val personAux2 = new Person("254088845Y","Carlos", "de la Flor Bonilla", false, 22, "+34665381123", "UGR", "info@alvarodelaflor.com", schedule1, true)
    val personAux3 = new Person("554088845Y","Carlos", "de la Flor Bonilla", false, 22, "+34665381123", "UGR", "info@alvarodelaflor.com", schedule1, true)
    val listPerson = Map(personAux1 -> (1,2), personAux2 -> (2,3), personAux3 -> (14, 4))
    val attendance1 = new Attendance(listPerson, LocalDate.now().minusDays(5), "PGPI")
    assert(contactController.getPossibleContact(attendance1, personAux1).size == 1)
  }
}

import controllers.ContactController
import models.{Person, Schedule, Test}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

import java.time.LocalDate


class TestTest extends AnyFlatSpec {

  var contactController = new ContactController()

  "An user" should "not be able to send the result of a test if he/she are not registered in the system" in {
    val date = LocalDate.now()
    val listClassroom = List("PGPI", "PGPI2", "CC")
    val schedule = new Schedule(date, listClassroom)
    val alum = new Person("765452379V","Daniel", "Chamizo López", false, 22, "+34665381123", "UGR", "alvdebon@correo.ugr.es", schedule, true)
    val test = new Test("PGPI", true, date, alum, "PCR")
    val thrownError = intercept[Exception] {
      contactController.sendTestResult(alum, test)
    }
    thrownError.getMessage should equal ("The user is not registered, is not a member of the educational community")
  }

  "An user with a not activate account" should "not be able to send the result of a test if he/she are not registered in the system" in {
    val date = LocalDate.of(2020,12,12)
    val listClassroom = List("DES", "DSS", "TID")
    val schedule = new Schedule(date, listClassroom)
    val alum = new Person("243234235B","Miguel", "Gonzalez Morales", false, 22, "+34656237217", "UGR", "miguel@correo.ugr.es", schedule, false)
    val test = new Test("EGC213", true, date, alum, "PCR")
    val thrownError = intercept[Exception] {
      contactController.sendTestResult(alum, test)
    }
    thrownError.getMessage should equal ("User account not activate")
  }

}

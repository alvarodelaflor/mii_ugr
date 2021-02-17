import java.time.LocalDate
import controllers.{AccountController, ContactController}
import models.{Person, Schedule}
import org.scalatest._
import matchers.should.Matchers._
import org.scalatest.flatspec.AnyFlatSpec


class PersonTest extends AnyFlatSpec {

  var accountController = new AccountController()
  val alum = new Person("154088845Y","Alvaro", "de la Flor Bonilla", false, 22, "+34665381123", "UGR", "info@alvarodelaflor.com", null, false)
  val professor = new Person("154088845Y","Carlos", "Torres", true, 34, "+34665341123", "UGR", "correo@alvarodelaflor.com", null, false)
  val date = LocalDate.now()
  var listClassroom = List("PGPI", "PGPI2", "CC")
  val schedule = new Schedule(date, listClassroom)
  val alum2 = new Person("154088845Y","Alvaro", "de la Flor Bonilla", false, 22, "+34665381123", "UGR", "info@alvarodelaflor.com", schedule,false)

  "An admin" should "not be able to save a professor account if the person does not have the role of professor" in {
    val thrownProfessor = intercept[Exception] {
      accountController.createProfessorAccount(alum)
    }
    thrownProfessor.getMessage should equal ("This person does not have the role of professor")
  }

  "An admin" should "be able to save a professor account" in {
    // It is possible to create a professor in the system
    false should equal (professor.activate)
    accountController.createProfessorAccount(professor)
    true should equal (professor.activate)
  }

  "An admin" should "not be able to save a student account if the person does not have the role of student" in {
    val thrownStudent = intercept[Exception] {
      accountController.createStudentAccount(professor)
    }
    thrownStudent.getMessage should equal ("This person does not have the role of student")
  }

  "An admin" should "be able to save a student account" in {
    // It is possible to create a professor in the system
    false should equal (alum.activate)
    accountController.createStudentAccount(alum)
    true should equal (alum.activate)
  }

  "An email" should "be valid" in {

    val validProfessor = new Person("154088845Y","Carlos", "Torres", true, 34, "+34665341123", "UGR", "professor@ugr.es", null, false)
    val professorWrongEmail = new Person("154088845Y","Carlos", "Torres", true, 34, "+34665341123", "UGR", "professor@ugr.com", null, false)
    val professorWrongPhone = new Person("154088845Y","Carlos", "Torres", true, 34, "-34665341123", "UGR", "professor@ugr.es", null, false)
    val professorWrongEmailAndPhone = new Person("154088845Y","Carlos", "Torres", true, 34, "-34665341123", "UGR", "professor@correo.ugr.es", null, false)

    val validStudent = new Person("154088845Y","Carlos", "Torres", false, 34, "+34665341123", "UGR", "student@correo.ugr.es", null, false)
    val studentWrongEmail = new Person("154088845Y","Carlos", "Torres", false, 34, "+34665341123", "UGR", "student@correo.ugr.com", null, false)
    val studentWrongPhone = new Person("154088845Y","Carlos", "Torres", false, 34, "-34665341123", "UGR", "professor@correo.ugr.es", null, false)
    val studentWrongEmailAndPhone = new Person("154088845Y","Carlos", "Torres", false, 34, "-34665341123", "UGR", "professor@ugr.es", null, false)

    val illegalEmailProfessor = intercept[Exception] {
      accountController.editContactDetails(professorWrongEmail)
    }
    val illegalPhoneProfessor = intercept[Exception] {
      accountController.editContactDetails(professorWrongPhone)
    }
    val illegalEmailPhoneProfessor = intercept[Exception] {
      accountController.editContactDetails(professorWrongEmailAndPhone)
    }

    val illegalEmailStudent = intercept[Exception] {
      accountController.editContactDetails(studentWrongEmail)
    }
    val illegalPhoneStudent = intercept[Exception] {
      accountController.editContactDetails(studentWrongPhone)
    }
    val illegalEmailPhoneStudent = intercept[Exception] {
      accountController.editContactDetails(studentWrongEmailAndPhone)
    }

    illegalEmailProfessor.getMessage should equal ("The mail has an incorrect format\n")
    illegalPhoneProfessor.getMessage should equal ("The phone has an incorrect format\n")
    illegalEmailPhoneProfessor.getMessage should equal ("The mail has an incorrect format\nThe phone has an incorrect format\n")

    illegalEmailStudent.getMessage should equal ("The mail has an incorrect format\n")
    illegalPhoneStudent.getMessage should equal ("The phone has an incorrect format\n")
    illegalEmailPhoneStudent.getMessage should equal ("The mail has an incorrect format\nThe phone has an incorrect format\n")

    true should equal(accountController.editContactDetails(validProfessor))
    true should equal(accountController.editContactDetails(validStudent))
  }
}

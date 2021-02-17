import org.scalatest._
import java.time.LocalDate

import controllers.AttendanceController
import models.{Person, Schedule}

class AttendanceTest extends FlatSpec {

  var attendanceController = new AttendanceController()
  var listClassroom = List("PGPI", "PGPI2", "CC")
  var listClassroom2 = List("PGPI2", "CC")
  val date = LocalDate.now().plusDays(5)
  val date2 = LocalDate.now().minusDays(5)
  val schedule = new Schedule(date, listClassroom)
  val schedule2 = new Schedule(date2, listClassroom)
  val schedule3 = new Schedule(date2, listClassroom2)
  val alum = new Person("154088845Y","Alvaro", "de la Flor Bonilla", false, 22, "+34665381123", "UGR", "info@alvarodelaflor.com", schedule, true)
  val alumSchedule = new Person("154088845Y","Alvaro", "de la Flor Bonilla", false, 22, "+34665381123", "UGR", "info@alvarodelaflor.com", null, true)
  val alumSchedule2 = new Person("154088845Y","Alvaro", "de la Flor Bonilla", false, 22, "+34665381123", "UGR", "info@alvarodelaflor.com", schedule2, true)
  val professor = new Person("154088845Y","Carlos", "de la Flor Bonilla", true, 22, "+34665381123", "UGR", "info@alvarodelaflor.com", schedule, true)
  val subject = "PGPI"
  val alumSchedule3 = new Person("154088845Y","Alvaro", "de la Flor Bonilla", false, 22, "+34665381123", "UGR", "info@alvarodelaflor.com", schedule3, true)

  // In this test, the creation of an attendance will be tested using a teacher and a student with a valid schedule.
  "A professor" should "be able to record an attendance" in {
    assert(attendanceController.addAttendance(professor, alum, (2,3), subject) != null)
  }

  // This test will prove that a student cannot register an attendance
  "An alum" should "not be able to record an attendance" in {
    assert(attendanceController.addAttendance(alum, alum, (2,3), subject) == null)
  }


  // This test proves that a professor cannot record a student's attendance without a schedule
  "A professor" should "not be able to record an attendance of a student without schedule" in {
    assert(attendanceController.addAttendance(professor, alumSchedule, (2,3), subject) == null)
  }

  // This test proves that it is not possible to register the attendance of a student with an experienced schedule
  "A professor" should "not be able to record an attendance of a student with an invalid validity date" in {
    assert(attendanceController.addAttendance(professor, alumSchedule2, (2,3), subject) == null)
  }

  // A professor cannot register the attendance of a student who is not registered in that subject
  "A professor" should "not be able to record an attendance of an alum who is not enrolled in the subject" in {
    assert(attendanceController.addAttendance(professor, alumSchedule3, (2,3), subject) == null)
  }

  // Checking the auxiliary method for looking for attendances
  "An attendance " should "should exist in the system between 50 days (past and future)" in {
    var listClassroom1 = List("PGPI", "PGPI2", "CC")
    val schedule1 = new Schedule(date, listClassroom1)
    val personAux1 = new Person("154088845Y", "Alvaro", "de la Flor Bonilla", true, 22, "+34665381123", "UGR", "info@alvarodelaflor.com", schedule1, true)
    assert(attendanceController.getAttendanceByUserAndPeriodTime(personAux1, LocalDate.now().minusDays(50), LocalDate.now().plusDays(50)).size > 0)
  }

  // Checking the auxiliary method for looking for attendances
  "An attendance " should "should not exist in the system between 3 days (past and future)" in {
    var listClassroom1 = List("PGPI", "PGPI2", "CC")
    val schedule1 = new Schedule(date, listClassroom1)
    val personAux1 = new Person("154088845Y", "Alvaro", "de la Flor Bonilla", true, 22, "+34665381123", "UGR", "info@alvarodelaflor.com", schedule1, true)
    assert(attendanceController.getAttendanceByUserAndPeriodTime(personAux1, LocalDate.now().minusDays(3), LocalDate.now().plusDays(3)).size == 0)
  }

  // This test proves that a teacher cannot record a student's attendance twice
  // TODO Impossible to do until data are persistent

  // This test proves that it is not possible to record the attendance of two students in the same position
  // TODO Impossible to do until data are persistent
}

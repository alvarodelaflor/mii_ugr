import java.time.LocalDate
import controllers.{ContactController, SchedulerController}
import models.{Person, Schedule}
import org.scalatest._
import matchers.should.Matchers._
import org.scalatest.flatspec.AnyFlatSpec

class ScheduleTest extends AnyFlatSpec {

  var scheduleController = new SchedulerController()
  var listClassroom = List("PGPI", "PGPI2", "CC")
  val alum = new Person("154088845Y","Alvaro", "de la Flor Bonilla", false, 22, "+34665381123", "UGR", "info@alvarodelaflor.com", null, true)
  val professor = new Person("154088845Y","Carlos", "Torres", true, 34, "+34665341123", "UGR", "correo@alvarodelaflor.com", null, true)
  val date = LocalDate.now()
  val schedule = new Schedule(date, listClassroom)
  val alum2 = new Person("154088845Y","Alvaro", "de la Flor Bonilla", false, 22, "+34665381123", "UGR", "info@alvarodelaflor.com", schedule, true)

  // In this test a user without a registered schedule adds a new schedule.
  "A user without a schedule in the system" should "be able to save a new schedule" in {
    assert(scheduleController.addSchedule(alum, listClassroom, 40) != null)
  }

  // Schedules cannot be created with a validity of more than 6 months
  "The system" should "not allow timetables to be kept for more than 6 months" in {
    val thrownSchedule = intercept[Exception] {
      scheduleController.addSchedule(professor, listClassroom, 200)
    }
    thrownSchedule.getMessage should equal ("The validity of the schedule must be between 1 and 190 days")
  }

  // You cannot create a schedule with a validity of less than 1 day in the future
  "The system" should "not not create a schedule with a validity of less than one day" in {
    val thrownSchedule = intercept[Exception] {
      assert(scheduleController.addSchedule(professor, listClassroom, 1) == null)
    }
    thrownSchedule.getMessage should equal ("The validity of the schedule must be between 1 and 190 days")
  }

  // A user with a schedule in the system cannot add a new schedule
  "A user with a schedule in the system" should "not be able to save a new schedule" in {
    assert(scheduleController.addSchedule(alum2, listClassroom, 40) == null)
  }

  // The system administrator can extend the validity of the schedule
  "The system administrator" should " be able to extend the validity of a user's schedule" in {
    assert(scheduleController.extendValidityTime(alum2, 31).validityDate.equals(date.plusDays(31)))
  }

  // The system administrator cannot extend the validity of the schedule more than 190 days
  "The system administrator" should " not be able to extend more than 190 days the validity of a user's schedule" in {
    assert(scheduleController.extendValidityTime(alum2, 200) == null)
  }

  // No days can be subtracted from the validity of the schedule
  "The system administrator" should " not be able to extend negative days the validity of a user's schedule" in {
    assert(scheduleController.extendValidityTime(alum2, -6) == null)
  }

  val newSubjects = List("PGPI", "PGPI2", "CC", "IC")
  // The user must be able to modify the subjects of his schedule
  "The user" should " be able to modify his schedule with new subjects" in {
    assert(scheduleController.editSchedule(alum2, newSubjects).subjects.size == newSubjects.size)
  }

  val newSubjects2 = List("PGPI", "PGPI2", "CC", "IC", "IC")
  // The user cannot add the same subject twice to his schedule
  "The user" should "add the same subject twice" in {
    val illegalSchedule = intercept[Exception] {
      scheduleController.editSchedule(alum2, newSubjects2)
    }
    illegalSchedule.getLocalizedMessage equals("The same subject has been inserted twice")
  }

  // The user can delete his schedule
  "The user" should "be able to delete his schedule" in {
    true should equal(alum2.schedule!= null)
    scheduleController.deleteSchedule(alum2)
    true should equal(alum2.schedule == null)
  }

  // Can not edit a schedule without subjects
  "User" should "not be able to save a schedule without subjects" in {
    val illegalSchedule = intercept[Exception] {
      scheduleController.editSchedule(alum, List[String]())
    }
    illegalSchedule.getLocalizedMessage equals("Schedule dont have any subject")
  }

  "User" should "not be able to edit his schedule if he does not have one stored in the database" in {
    alum.schedule = null
    val illegalSchedule = intercept[Exception] {
      scheduleController.editSchedule(alum, List[String]("PGPI", "CC"))
    }
    illegalSchedule.getLocalizedMessage equals("The user does not have any schedule")
  }
}

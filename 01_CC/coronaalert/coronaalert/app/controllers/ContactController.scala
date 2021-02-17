package controllers

import models.{Attendance, Person, Test}
import play.api.libs.mailer.{Email, SMTPConfiguration, SMTPMailer}


class ContactController {

  var databaseController = new DatabaseController

  def getPossibleContact(attendance: Attendance, person: Person) : List[Person] = {
    val position : (Int, Int) = attendance.persons.filter(x => x._1.dni.equals(person.dni)).head._2
    val res = attendance.persons.filter(y => !y._1.dni.equals(person.dni) && ((y._2._1+1) == position._1 || (y._2._1-1) == position._1 || (y._2._2+1) == position._2 || (y._2._2-1) == position._2))
    var persons : List[Person] = null
    if (!res.isEmpty) {
      persons = res.keySet.toList
    }
    persons
  }

  def sendTestResult(user : Person, test : Test)  = {
    if (databaseController.checkUserInDatabase(user)) {
      if (!user.activate) {
        throw new IllegalArgumentException("User account not activate")
      } else {
        // TODO Save in DB
      }
    } else {
      throw new IllegalArgumentException("The user is not registered, is not a member of the educational community")
    }
  }

  def alertAllUsers(users: List[Person], test: Test, msg: String): List[String] = {
    var usersNotificates = List[String]()
    for (x <- 0 until users.size) {
      try {
        val user = users(0)
        val newNotification: String = alertUser(user.dni, test.id, msg)
        usersNotificates = usersNotificates.appended(newNotification)
      } catch {
        case e: Exception => println("exception caught: " + e);
          throw new IllegalArgumentException("Can not send email to " + x + " Error: " + e)
      }
    }
    usersNotificates
  }

  def alertUser(userId: String, testId: String, msg: String): String = {
    var databaseController = new DatabaseController
    val test: Test = databaseController.getTestById(testId)
    val user: Person = databaseController.getUserById(userId)
    val name = user.name
    val emailUser = user.email
    val email = Email("You may have had a contact " + user.name, "CoronaAlert <info@coronaalert.com>", Seq(name + " <" + emailUser + ">"), bodyText = Some(msg + " TEST: " + test.typeTest + "(date: " + test.date + ")"))
    try{
      new SMTPMailer(SMTPConfiguration("smtp.gmail.com", 587, false, true, true, Some("cc.coronaalert@gmail.com"), Some("cloudcomputing"))).send(email)
    } catch {
      case e: Exception => println("exception caught: " + e);
        throw new IllegalArgumentException("Can not send email")
    }
    user.dni
  }

  def trackingAndReporting(testId: String): List[String] = {
    val databaseController: DatabaseController = new DatabaseController
    val attendanceController: AttendanceController = new AttendanceController
    val test: Test = databaseController.getTestById(testId)
    if (test == null || (test  != null && !test.result)) {
      throw new IllegalArgumentException("No valid Test")
    } else {
      val user: Person = test.person
      val attendances: List[Attendance] = attendanceController.getAttendanceByUserAndPeriodTime(user, test.date.minusDays(100), test.date.plusDays(100))
      var resConctact = List[Person]()
      for (x <- 0 until attendances.size) {
        val aux = getPossibleContact(attendances(x), user)
        resConctact = resConctact.appendedAll(aux)
        print(resConctact)
      }
      val userNotification: List[String] = alertAllUsers(resConctact, test, "A positive case has been detected that may affect you.")
      userNotification
    }
  }
}

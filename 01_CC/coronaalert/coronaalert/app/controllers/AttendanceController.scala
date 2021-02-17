package controllers

import models.{Attendance, Person}

import java.time.LocalDate
import scala.collection.convert.ImplicitConversions.`map AsJavaMap`

class AttendanceController {

  val databaseController = new DatabaseController()

  def getAttendanceByUserAndPeriodTime(person: Person, dateStart: LocalDate, dateEnd: LocalDate) : List[Attendance] = {
    var res: List[Attendance] = databaseController.attendances.filter(x => x.date.isAfter(dateStart) && x.date.isBefore(dateEnd) && x.persons.filter(y => y._1.dni.equals(person.dni)).size > 0)
    if (res.isEmpty) {
      res = List()
    }
    res
  }

  def addAttendance(professor: Person, alum: Person, position: (Int, Int), subject: String) : Attendance = {
    var res: Attendance = null
    // We check that the user has this subject in his schedule
    if (checkValidUserSchedule(alum, subject) && professor.isProfessor) {
      var attendance = databaseController.searchAttendance(LocalDate.now(), subject)
      if (attendance != null) {
        if (checkValidUser(attendance, alum, position)) {
          attendance.persons.put(alum, position)
        }
      } else {
        attendance = new Attendance(Map(alum -> position), LocalDate.now(), subject)
      }
      if (databaseController.addAttendanceToDb(attendance)) {
        res = attendance
      } else {
        // Error saving
        res = null
      }
    }
    res
  }

  def checkValidUser(attendance: Attendance, alum: Person, position: (Int, Int)) : Boolean = {
    var res = true
    res = res && checkAddPersonToAttendance(attendance, alum,position)
    // Last user status is a positive test, cannot access class
    // TODO Create a log
    res = res && databaseController.getLastTestByPerson(alum).result.equals(false)
    res
  }

  //This method will avoid adding a person who already exists in the care and will also prevent two people from having the same position
  def checkAddPersonToAttendance(attendance: Attendance, alum: Person, position: (Int, Int)) : Boolean = {
    if (attendance.persons.contains(alum)) {
      throw new IllegalArgumentException("Student already registered")
    } else {
      if (attendance.persons.containsValue(position)) {
        throw new IllegalArgumentException("Position already occupied")
      }
    }
    true
  }

  def checkValidUserSchedule(user: Person, subject: String) : Boolean = {
    var res = true
    // The user has not registered any schedule in the system
    if (user.schedule == null) {
      // TODO: Create a log
      res = res && false
    } else {
      // The validity date of the schedule has expired
      if (user.schedule.validityDate.isBefore(
        LocalDate.now()) ) {
        // TODO: Create a log
        res = res && false
      }
      // The user does not have the subject in his schedule
      if (!user.schedule.subjects.contains(subject)) {
        // TODO: Create a log
        res = res && false
      }
    }
    res
  }
}

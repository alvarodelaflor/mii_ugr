package controllers

import models.{Person, Schedule}

import java.time.LocalDate

class SchedulerController {

  def addSchedule(person: Person, subjects: List[String], days: Int = 150) : Schedule = {
    var schedule: Schedule = null
    if (person.schedule == null) {
      // TODO Check login user is the same that schedule user
      if (true) {
        // The maximum duration cannot exceed 6 months and he/she must no be a professor
        if (days <= 190 && days > 1) {
          schedule = new Schedule(LocalDate.now().plusDays(days), subjects)
        } else {
          throw new IllegalArgumentException("The validity of the schedule must be between 1 and 190 days")
        }
        if (subjects.size != subjects.toSet.size) {
          throw new IllegalArgumentException("The schedule has repeated subjects")
        }
      }
    }
    schedule
  }

  // The system administrator can extend the validity of the schedule
  def extendValidityTime(user: Person, days: Int = 31) : Schedule = {
    var schedule: Schedule = null
    if (user.schedule != null) {
      // TODO Check login user is a system admin
      if (true) {
        // The maximum duration cannot exceed 6 months and he/she must no be a professor
        if (days <= 190 && days > 1) {
          schedule = user.schedule
          schedule.validityDate = LocalDate.now().plusDays(days)
          // TODO Save in DB
        }
      }
    }
    schedule
  }

  def editSchedule(person: Person, subjects: List[String]) : Schedule = {
    var schedule: Schedule = null
    // TODO Check login user is the same that schedule user
    if (person.schedule != null) {
      schedule = person.schedule
      // Check validity of the subject
      if (checkValiditySubjects(subjects)) {
        schedule.subjects = subjects
        // TODO Save in DB
      }
    } else {
      throw new IllegalArgumentException("The user does not have any schedule")
    }
    schedule
  }

  // The user deletes his schedule from the system
  def deleteSchedule(person: Person) = {
    var res: Boolean = false
    var schedule: Schedule = person.schedule
    // TODO Check that the logged-in user is the owner of the schedule
    if (true) {
      // TODO Check that the database deletion is done correctly
      person.schedule = null
      if (true) {
        // TODO Create log
      } else {
        // Go Back
        person.schedule = schedule
        throw new IllegalArgumentException("The schedule could not be deleted correctly")
      }
    } else {
      throw new IllegalArgumentException("Only the owner of the schedule can remove it")
    }
  }

  def checkValiditySubjects(subjects: List[String]): Boolean = {
    var res: Boolean = true
    // Check that no repeated subjects are added
    if (subjects.size != subjects.toSet.size) {
      throw new IllegalArgumentException("The same subject has been inserted twice")
    }
    if (subjects.size < 1) {
      throw new IllegalArgumentException("New schedule dont have any subject")
    }
    res
  }
}

package models

import java.time.LocalDate

class Attendance(var persons: Map[Person, (Int, Int)], var date: LocalDate, var subject: String) {
  override def toString: String = {
    return "models.Attendance: [persons = " + persons +
      ", date = " + date +
      ", subject = " + subject + "]";
  }
}

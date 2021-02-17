package models

import java.time.LocalDate

class Schedule(var validityDate: LocalDate, var subjects: List[String]) {
  override def toString: String = {
    return "models.Schedule: [validityDate = " + validityDate +
      ", subjects = " + subjects+"]";
  }


  def canEqual(other: Any): Boolean = other.isInstanceOf[Schedule]

  override def equals(other: Any): Boolean = other match {
    case that: Schedule =>
      (that canEqual this) &&
        validityDate == that.validityDate &&
        subjects == that.subjects
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(validityDate, subjects)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}

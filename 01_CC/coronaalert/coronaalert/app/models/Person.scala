package models

class Person(var dni: String, var name: String, var surname: String, var isProfessor: Boolean, var age: Int, var phoneNumber: String, var university: String, var email: String, var schedule: Schedule, var activate: Boolean) {
  override def toString: String = {
    return "models.Person: [dni = " + dni +
      ", name = " + name +
      ", surname = " + surname+
      ", isProfessor = " + isProfessor+
      ", age = " + age+
      ", phoneNumber = " + phoneNumber+
      ", university = " + university+
      ", email = " + email+
      ", schedule = " + schedule+
      ", activate = " + activate+"]";
  }


  def canEqual(other: Any): Boolean = other.isInstanceOf[Person]

  override def equals(other: Any): Boolean = other match {
    case that: Person =>
      (that canEqual this) &&
        dni == that.dni &&
        name == that.name &&
        surname == that.surname &&
        isProfessor == that.isProfessor &&
        age == that.age &&
        phoneNumber == that.phoneNumber &&
        university == that.university &&
        email == that.email &&
        schedule == that.schedule &&
        activate == that.activate
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(dni, name, surname, isProfessor, age, phoneNumber, university, email, schedule, activate)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}

package models

import java.time.LocalDate

class Test(var id: String, var result: Boolean, var date: LocalDate, var person: Person, var typeTest: String) {
  override def toString: String = {
    return "models.Test: [result = " + result +
      ", date = " + date+
      ", person = " + person+
      ", id = " + id+
      ", typeTest = " + typeTest+ "]";
  }
}

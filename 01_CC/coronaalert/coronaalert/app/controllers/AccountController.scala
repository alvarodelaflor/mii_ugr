package controllers

import models.Person

class AccountController {

  def createProfessorAccount(professor: Person) = {
    if (!professor.isProfessor) {
      throw new IllegalArgumentException("This person does not have the role of professor")
      // TODO: Check that the professor does not already exist
    } else if (false) {
      // TODO
    } else{
      professor.activate = true
      // TODO: Save in database
    }
  }

  def createStudentAccount(professor: Person) = {
    if (professor.isProfessor) {
      throw new IllegalArgumentException("This person does not have the role of student")
      // TODO: Check that the professor does not already exist
    } else if (false) {
      // TODO
    } else{
      professor.activate = true
      // TODO: Save in database
    }
  }

  // This method will check that the logged-in person is the same as the one passed as parameter
  def checkLoginSameEdit(person: Person) : Boolean = {
    var res: Boolean = true;
    // TODO
    res
  }

  def isValidPhoneNumber(str: String): Boolean =
    str.startsWith("+") && 6 <= str.size && str.size <= 18 &&
      str.drop(1).forall(_.isDigit)

  def isValidEmail(email: String, isProfessor: Boolean): Boolean = {
    var res: Boolean = false
    if (isProfessor && """^[-a-z0-9!#$%&'*+/=?^_`{|}~]+(\.[-a-z0-9!#$%&'*+/=?^_`{|}~]+)*@*(ugr.es)$""".r.findFirstIn(email) != None) {
      res = true
    } else if (!isProfessor && """^[-a-z0-9!#$%&'*+/=?^_`{|}~]+(\.[-a-z0-9!#$%&'*+/=?^_`{|}~]+)*@*(correo.ugr.es)$""".r.findFirstIn(email) != None) {
      res = true
    }
    res
  }

  def checkData(person: Person): (Boolean, String) = {
    var check: Boolean = true
    var msg: String = ""
    if (!isValidEmail(person.email, person.isProfessor)) {
      check = check && false
      msg += "The mail has an incorrect format\n"
    }
    if (!isValidPhoneNumber(person.phoneNumber)) {
      check = check && false
      msg += "The phone has an incorrect format\n"
    }
    (check, msg)
  }

  def editContactDetails(person: Person): Boolean = {
    if (!checkLoginSameEdit(person)) {
      throw new IllegalArgumentException("The person you want to edit is not the same as the one logged in")
    } else {
      var check: (Boolean, String) = checkData(person)
      if (!check._1) {
        throw new IllegalArgumentException(check._2)
      } else {
        // TODO Save new info in DB
        true
      }
    }
  }
}

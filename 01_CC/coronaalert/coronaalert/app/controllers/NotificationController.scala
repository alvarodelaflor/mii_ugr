package controllers

import models.Person

class NotificationController {

  def sendNotification(users: List[Person]) : Boolean = {
    // TODO
    // Sending notifications to potentially affected users
    true
  }

  def checkValidations(users: List[Person]) : List[Person] = {
    // TODO
    // This method aims to analyse users' responses to the notification sent to confirm that they have received it
    null
  }
}

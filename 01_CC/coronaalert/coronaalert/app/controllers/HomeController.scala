package controllers

import models.DataRepository

import javax.inject._
import play.api.mvc._
import play.api.libs.json._

import scala.concurrent.ExecutionContext

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(repo: DataRepository,
                               cc: MessagesControllerComponents
                              )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */


  // SCHEDULLE
  def addSchedulle() = Action(parse.json) { implicit request => 
    Ok(Json.obj("received" -> Json.toJson(request.body)))
  }

  def editSchedulle(id: String) = Action(parse.json) { implicit request =>
    Ok(Json.obj("received" -> Json.toJson(request.body), "idReceived" -> id))
  }

  def getSchedulle(id: String) = Action { _ =>
    val res = Json.obj("id" -> id, "names" -> List("CC", "CC1", "PGPI", "PGPI1"))
    Ok(res)
  }

  def deleteSchedulle(id: String) = Action {
    Ok("DELETE")
  }
  // SCHEDULE

  // ATTENDANCE
  def addAttendance() = Action(parse.json) { implicit request => 
    Ok(Json.obj("received" -> Json.toJson(request.body)))
  }

  def getAttendance(id: String) = Action { _ =>
    val res = Json.obj("date" -> "2021-02-02", "subject" -> "CC", "persons" -> List("35448843H", "35448844V", "35448842Z"))
    Ok(res)
  }

  def deleteAttendance(id: String) = Action {
    Ok("DELETE")
  }
  // ATTENDANCE

  // TEST
  def addTest() = Action(parse.json) { implicit request => 
    Ok(Json.obj("received" -> Json.toJson(request.body)))
  }

  def getTest(id: String) = Action { _ =>
    val res = Json.obj("date" -> "2021-02-02", "userId" -> "15596843V", "typeTest" -> "PCR", "result" -> false)
    Ok(res)
  }
  // TEST

  // INFECTION
  def getInfection(id: String) = Action { _ =>
    val res = Json.obj("persons" -> List("35448843H", "35448844V", "35448842Z"))
    Ok(res)
  }
  //INFECTION

  // NOTIFICATION
 def notification(id: String) = Action { _ =>
    Ok("Notification done")
  }
  // NOTIFICATION

  // EMAIL
  def email(userId: String, testId: String) = Action { _ =>
    try {
      val msg: String = "A positive case has been detected with whom he may have had contact."
      var contactController = new ContactController
      var userEmail: String = contactController.alertUser(userId, testId, msg)
      Ok("Email send to " + userEmail)
    }
  }

  def tracking(testId: String) = Action { _ =>
    val contactController: ContactController = new ContactController
    val user = contactController.trackingAndReporting(testId)
    Ok("Tracking " + user)
  }

  // DATABASE

  def addData(name: String) = Action { _ =>
    repo.create(name)
    Ok("Create")
  }

  def getData = Action.async { implicit request =>
    repo.list().map { people =>
      Ok(Json.toJson(people))
    }
  }
  // DATABASE
}

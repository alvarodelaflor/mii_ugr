package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._
import akka.stream.{ActorMaterializer, Materializer}
import akka.actor._
import play.api.libs.json._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 *
 * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
 */
class HomeControllerSpec extends PlaySpec with GuiceOneAppPerTest {

  // SCHEDULLE
  "Schedulle POST" should {
    "answer Ok" in {
      implicit val materializer = ActorMaterializer()(ActorSystem())

      val controllerComponents =
        Helpers.stubControllerComponents(
          playBodyParsers = Helpers.stubPlayBodyParsers(materializer)
        )

      val request = FakeRequest(POST, "/schedulle").withBody(Json.obj("example" -> "ok"))
      val answer = route(app, request).get

      status(answer) mustBe OK
    }
  }

  "Schedulle PUT" should {
    "answer Ok" in {
      implicit val materializer = ActorMaterializer()(ActorSystem())

      val controllerComponents =
        Helpers.stubControllerComponents(
          playBodyParsers = Helpers.stubPlayBodyParsers(materializer)
        )

      val request = FakeRequest(PUT, "/schedulle/1").withBody(Json.obj("example" -> "ok"))
      val answer = route(app, request).get

      status(answer) mustBe OK
    }
  }

  "Schedulle GET" should {
    "answer Ok" in {
      implicit val materializer = ActorMaterializer()(ActorSystem())

      val controllerComponents =
        Helpers.stubControllerComponents(
          playBodyParsers = Helpers.stubPlayBodyParsers(materializer)
        )
      val request = FakeRequest(GET, "/schedulle/1")
      val answer = route(app, request).get
      status(answer) mustBe OK
    }
  }

  "Schedulle DELETE" should {
    "answer Ok" in {
      implicit val materializer = ActorMaterializer()(ActorSystem())

      val controllerComponents =
        Helpers.stubControllerComponents(
          playBodyParsers = Helpers.stubPlayBodyParsers(materializer)
        )

      val request = FakeRequest(DELETE, "/schedulle/1")
      val answer = route(app, request).get
      status(answer) mustBe OK
    }
  }
  // SCHEDULLE
  // ATTENDANCE
  "Attendance POST" should {
    "answer Ok" in {
      implicit val materializer = ActorMaterializer()(ActorSystem())

      val controllerComponents =
        Helpers.stubControllerComponents(
          playBodyParsers = Helpers.stubPlayBodyParsers(materializer)
        )

      val request = FakeRequest(POST, "/attendance").withBody(Json.obj("example" -> "ok"))
      val answer = route(app, request).get

      status(answer) mustBe OK
    }
  }

  "Attendance GET" should {
    "answer Ok" in {
      implicit val materializer = ActorMaterializer()(ActorSystem())

      val controllerComponents =
        Helpers.stubControllerComponents(
          playBodyParsers = Helpers.stubPlayBodyParsers(materializer)
        )

      val request = FakeRequest(GET, "/attendance/1")
      val answer = route(app, request).get
      status(answer) mustBe OK
    }
  }

  "Attendance DELETE" should {
    "answer Ok" in {
      implicit val materializer = ActorMaterializer()(ActorSystem())

      val controllerComponents =
        Helpers.stubControllerComponents(
          playBodyParsers = Helpers.stubPlayBodyParsers(materializer)
        )

      val request = FakeRequest(DELETE, "/attendance/1")
      val answer = route(app, request).get
      status(answer) mustBe OK
    }
  }
  // ATTENDANCE

  // TEST
  "Test POST" should {
    "answer Ok" in {
      implicit val materializer = ActorMaterializer()(ActorSystem())

      val controllerComponents =
        Helpers.stubControllerComponents(
          playBodyParsers = Helpers.stubPlayBodyParsers(materializer)
        )

      val request = FakeRequest(POST, "/test").withBody(Json.obj("example" -> "ok"))
      val answer = route(app, request).get

      status(answer) mustBe OK
    }
  }

  "Test GET" should {
    "answer Ok" in {
      implicit val materializer = ActorMaterializer()(ActorSystem())

      val controllerComponents =
        Helpers.stubControllerComponents(
          playBodyParsers = Helpers.stubPlayBodyParsers(materializer)
        )

      val request = FakeRequest(GET, "/test/1")
      val answer = route(app, request).get
      status(answer) mustBe OK
    }
  }
  // TEST

  // INFECTION
  "Infection GET" should {
    "answer Ok" in {
      implicit val materializer = ActorMaterializer()(ActorSystem())

      val controllerComponents =
        Helpers.stubControllerComponents(
          playBodyParsers = Helpers.stubPlayBodyParsers(materializer)
        )

      val request = FakeRequest(GET, "/infection/do/1")
      val answer = route(app, request).get
      status(answer) mustBe OK
    }
  }
  // INFECTION
}

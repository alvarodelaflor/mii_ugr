package models

import play.api.libs.json._

case class Data(id: Long, name: String)

object Data {
  implicit val personFormat = Json.format[Data]
}

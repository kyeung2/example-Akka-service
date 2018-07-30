package io.flyingnimbus


import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait JsonSupport extends SprayJsonSupport {
  import DefaultJsonProtocol._

  implicit val bookJsonFormat: RootJsonFormat[Book] = jsonFormat6(Book)
  implicit val booksJsonFormat: RootJsonFormat[Books] = jsonFormat1(Books)

}
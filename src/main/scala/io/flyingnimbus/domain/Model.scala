package io.flyingnimbus.domain

import io.circe._
import io.circe.syntax._
import org.bson.types.ObjectId

case class Books(books: Seq[Book])
case class Book(_id: ObjectId = ObjectId.get(), title: String, subTitle: String = "", genre: String, description: String, isbn: String, author: String)

/**
 * @author Kye
 */
object Book {

  implicit val encoder: Encoder[Book] = (a: Book) => {
    Json.obj(
      "id" -> a._id.toHexString.asJson,
      "title" -> a.title.asJson,
      "subTitle" -> a.subTitle.asJson,
      "genre" -> a.genre.asJson,
      "description" -> a.description.asJson,
      "isbn" -> a.isbn.asJson,
      "author" -> a.author.asJson)
  }

  implicit val decoder: Decoder[Book] = (c: HCursor) => {
    for {
      title <- c.downField("title").as[String]
      subTitle <- c.downField("subTitle").as[String]
      genre <- c.downField("genre").as[String]
      description <- c.downField("description").as[String]
      isbn <- c.downField("isbn").as[String]
      author <- c.downField("author").as[String]

    } yield Book(ObjectId.get(), title, subTitle, genre, description, isbn, author)
  }
}
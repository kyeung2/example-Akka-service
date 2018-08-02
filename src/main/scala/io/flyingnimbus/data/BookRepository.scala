package io.flyingnimbus.data

import io.flyingnimbus.domain.Book
import org.mongodb.scala._

import scala.concurrent.Future

/**
  * @author Kye
  */
class BookRepository(collection: MongoCollection[Book]) {

  def findAll(): Future[Seq[Book]] = collection.find().collect().head()

  def save(user: Book): Unit = collection.insertOne(user)

}
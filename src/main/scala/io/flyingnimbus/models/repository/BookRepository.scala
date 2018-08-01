package io.flyingnimbus.models.repository

import io.flyingnimbus.models.Book
import org.mongodb.scala._

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author Kye
  */
class BookRepository(collection: MongoCollection[Book])(implicit ec: ExecutionContext) {

  def findAll(): Future[Seq[Book]] =
    collection
      .find()
      .collect()
      .head()

  def save(user: Book): Future[String] =
    collection
      .insertOne(user)
      .head
      .map { _ => user._id.toHexString }
}
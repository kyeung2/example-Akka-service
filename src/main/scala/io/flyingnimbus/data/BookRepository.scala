package io.flyingnimbus.data

import io.flyingnimbus.domain.Book
import org.mongodb.scala._

import scala.concurrent.{ ExecutionContext, Future }

/**
 * @author Kye
 */

object BookRepository{

  def apply(collection: MongoCollection[Book])(implicit executionContext: ExecutionContext): BookRepository = new BookRepository(collection)
}


class BookRepository(collection: MongoCollection[Book])(implicit executionContext: ExecutionContext) {

  def findAll(): Future[Seq[Book]] = collection.find().collect().head()

  def save(user: Book): Future[String] = collection.insertOne(user).head().map { _ => user._id.toHexString }

}
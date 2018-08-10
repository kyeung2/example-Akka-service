package io.flyingnimbus.data

object EmbeddedMongo {

  /* use embedded if external Mongo DB not available

import com.github.fakemongo.async.FongoAsync
import com.mongodb.async.client.{FongoAsyncMongoDatabase, MongoDatabase}
import com.typesafe.config.ConfigFactory
import org.mongodb.scala.MongoCollection
import io.flyingnimbus.domain.Book

lazy val embeddedDb: MongoDatabase = {
  val fongo: FongoAsync = new FongoAsync("in-memory-mongo")
  val str: String = ConfigFactory.load().getString("mongo.database")
  val db: FongoAsyncMongoDatabase = fongo.getDatabase(str)
  db.withCodecRegistry(Mongo.codecRegistry)
}
lazy val bookCollection = MongoCollection(embeddedDb.getCollection("books", classOf[Book]))
*/
}
package io.flyingnimbus.data


import com.typesafe.config.{Config, ConfigFactory}
import org.bson.codecs.configuration.CodecRegistries._
import org.mongodb.scala._
import io.flyingnimbus.domain.Book
import org.bson.codecs.configuration.CodecRegistry
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.bson.codecs.Macros._

object Mongo {
  lazy val config: Config = ConfigFactory.load()
  lazy val codecRegistry: CodecRegistry = fromRegistries(fromProviders(classOf[Book]), DEFAULT_CODEC_REGISTRY)

  //TODO this is to connect to an external mongo. probably should have a local container version rather than the in memory
//  lazy val mongoClient: MongoClient = MongoClient(config.getString("mongo.uri"))
//  lazy val database: MongoDatabase = mongoClient.getDatabase(config.getString("mongo.database")).withCodecRegistry(codecRegistry)
//  lazy val userCollection: MongoCollection[Book] = database.getCollection[Book]("books")
}
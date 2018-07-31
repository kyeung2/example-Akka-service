package io.flyingnimbus.mongo


import com.typesafe.config.{Config, ConfigFactory}
import org.bson.codecs.configuration.CodecRegistries._
import org.mongodb.scala._
import io.flyingnimbus.models.User
import org.bson.codecs.configuration.CodecRegistry
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.bson.codecs.Macros._

object Mongo {
  lazy val config: Config = ConfigFactory.load()
  lazy val codecRegistry: CodecRegistry = fromRegistries(fromProviders(classOf[User]), DEFAULT_CODEC_REGISTRY)


  //this is to connect to a "real", external mongo. probably should have a local container version rather than the in memory
  lazy val mongoClient: MongoClient = MongoClient(config.getString("mongo.uri"))
  lazy val database: MongoDatabase = mongoClient.getDatabase(config.getString("mongo.database")).withCodecRegistry(codecRegistry)
  lazy val userCollection: MongoCollection[User] = database.getCollection[User]("users")
}
package io.flyingnimbus

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.LazyLogging
import io.flyingnimbus.models.repository.UserRepository
import io.flyingnimbus.mongo.Mongo

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContextExecutor}
import scala.util.{Failure, Success}


import com.github.fakemongo.async.FongoAsync
import com.mongodb.async.client.MongoDatabase
import com.typesafe.config.ConfigFactory
import models.User
import org.mongodb.scala.MongoCollection

object Application extends App with LibraryRoutes with LazyLogging {


  //https://github.com/gabfssilva/akka-http-microservice-templates/tree/master/simple-http-server-json-mongodb
  implicit val system: ActorSystem = ActorSystem("libraryService")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher


  val db: MongoDatabase = {
    val fongo = new FongoAsync("akka-http-mongodb-microservice")
    val str = ConfigFactory.load().getString("mongo.database")
    val db = fongo.getDatabase(str)
    db.withCodecRegistry(Mongo.codecRegistry)
  }

  private val fakeMongoCollection = MongoCollection(db.getCollection("col", classOf[User]))

  //Mongo.userCollection
  val userRepository: UserRepository = new UserRepository(fakeMongoCollection)
  val booksActor: ActorRef = system.actorOf(BooksActor.props(userRepository), "booksActor")

  Http().bindAndHandle(libraryRoutes, "localhost", 8080)
    .onComplete {
      case Success(result) => logger.info("libraryService started. {}", result)
      case Failure(error) => logger.error("libraryService failed to start: {}", error.getMessage)
    }
  Await.result(system.whenTerminated, Duration.Inf)
}
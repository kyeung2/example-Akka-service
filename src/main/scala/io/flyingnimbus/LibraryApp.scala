package io.flyingnimbus

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.github.fakemongo.async.FongoAsync
import com.mongodb.async.client.{FongoAsyncMongoDatabase, MongoDatabase}
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging
import io.flyingnimbus.api.LibraryRoutes
import io.flyingnimbus.data.{BookRepository, Mongo}
import io.flyingnimbus.domain.{Book, Stubs}
import org.mongodb.scala.MongoCollection

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

object LibraryApp extends App with LazyLogging {

  implicit val system: ActorSystem = ActorSystem("libraryService")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val bulkheadDispatcher: ExecutionContext = system.dispatchers.lookup("bulkhead-dispatcher")

  val fakeDb: MongoDatabase = {
    val fongo: FongoAsync = new FongoAsync("in-memory-mongo")
    val str: String = ConfigFactory.load().getString("mongo.database")
    val db: FongoAsyncMongoDatabase = fongo.getDatabase(str)
    db.withCodecRegistry(Mongo.codecRegistry)
  }

  val fakeMongoCollection = MongoCollection(fakeDb.getCollection("books", classOf[Book]))
  val bookRepository: BookRepository = new BookRepository(fakeMongoCollection)

  bookRepository.save(Stubs.book)
  bookRepository.save(Stubs.book2)
  bookRepository.save(Stubs.book3)

  val booksActor: ActorRef = system.actorOf(BooksActor.props(bookRepository), "booksActor")

  Http().bindAndHandle(LibraryRoutes(booksActor).libraryRoutes, "localhost", 8080)
    .onComplete {
      case Success(result) => logger.info("libraryService started. {}", result)
      case Failure(error) => logger.error("libraryService failed to start: {}", error.getMessage)
    }
}
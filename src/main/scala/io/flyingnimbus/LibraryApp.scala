package io.flyingnimbus

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.LazyLogging
import io.flyingnimbus.api.LibraryRoutes
import io.flyingnimbus.data.{BookRepository, Mongo}
import io.flyingnimbus.domain.Stubs

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

object LibraryApp extends App with LazyLogging {

  implicit val system: ActorSystem = ActorSystem("libraryService")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val bulkheadDispatcher: ExecutionContext = system.dispatchers.lookup("bulkhead-dispatcher")

  val repo: BookRepository = BookRepository(Mongo.bookCollection)
  repo.save(Stubs.book)
  repo.save(Stubs.book2)
  repo.save(Stubs.book3)

  val booksActor: ActorRef = system.actorOf(BooksActor.props(repo), "booksActor")

  Http().bindAndHandle(LibraryRoutes(booksActor).libraryRoutes, "localhost", 8080)
    .onComplete {
      case Success(result) => logger.info("libraryService started. {}", result)
      case Failure(error) => logger.error("libraryService failed to start: {}", error.getMessage)
    }
}
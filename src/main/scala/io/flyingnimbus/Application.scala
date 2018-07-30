package io.flyingnimbus

//#quick-start-server
import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContextExecutor}
import scala.util.{Failure, Success}

//#main-class
object Application extends App with LibraryRoutes with LazyLogging {

  implicit val system: ActorSystem = ActorSystem("libraryService")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val booksActor: ActorRef = system.actorOf(BooksActor.props, "booksActor")

  Http().bindAndHandle(libraryRoutes, "localhost", 8080)
    .onComplete {
      case Success(result) => logger.info("libraryService started. {}", result)
      case Failure(error) => logger.error("libraryService failed to start", error)
    }
  Await.result(system.whenTerminated, Duration.Inf)
}
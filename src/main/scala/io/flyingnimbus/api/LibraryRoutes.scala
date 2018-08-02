package io.flyingnimbus.api

import akka.actor.ActorRef
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.MethodDirectives.get
import akka.http.scaladsl.server.directives.PathDirectives.path
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.pattern.ask
import akka.util.Timeout
import com.typesafe.scalalogging.LazyLogging
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.flyingnimbus.BooksActor._
import io.flyingnimbus.domain.Book

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.{Failure, Success}

/**
  * @author Kye
  */
object LibraryRoutes {
  def apply(booksActor: ActorRef): LibraryRoutes = new LibraryRoutes(booksActor)
}

class LibraryRoutes(booksActor: ActorRef) extends LazyLogging {

  implicit lazy val timeout: Timeout = Timeout(5.seconds)

  lazy val libraryRoutes: Route =
    path("books") {
      get {

        logger.info("1. request received: a dispatcher thread will pick this up")
        val result: Future[Seq[Book]] = (booksActor ? GetBooks).mapTo[Seq[Book]]
        onComplete(result) {
          case Success(books: Seq[Book]) =>
            logger.info("4. ask on complete: same or different dispatcher thread will complete route")
            complete(books)
          case Failure(f: Throwable) =>
            logger.error(s"error occurred dealing with request: ${f.getMessage}", f)
            complete(s"error occurred dealing with request: $f")
        }
      }
    }
}

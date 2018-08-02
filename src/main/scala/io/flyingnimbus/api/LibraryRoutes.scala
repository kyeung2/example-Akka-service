package io.flyingnimbus.api

import akka.actor.{ActorRef, ActorSystem}
import akka.event.Logging
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
trait LibraryRoutes extends LazyLogging {

  implicit def system: ActorSystem

  lazy val log = Logging(system, classOf[LibraryRoutes])

  def booksActor: ActorRef

  implicit lazy val timeout: Timeout = Timeout(5.seconds)

  lazy val libraryRoutes: Route =
    path("books") {
      get {

        logger.info("GET /books request. start")
        val books: Future[Seq[Book]] = (booksActor ? GetBooks).mapTo[Seq[Book]]
        onComplete(books) {
          case Success(result: Seq[Book]) =>
            logger.info("GET /books request. end")
            complete(result)
          case Failure(f: Throwable) =>
            logger.error(s"error occurred dealing with request: ${f.getMessage}", f)
            complete(s"error occurred dealing with request: $f")
        }
      }
    }
}

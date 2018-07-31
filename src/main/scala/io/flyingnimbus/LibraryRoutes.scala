package io.flyingnimbus

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
import io.flyingnimbus.BooksActor._

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.{Failure, Success}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._

trait LibraryRoutes  extends LazyLogging {

  implicit def system: ActorSystem

  lazy val log = Logging(system, classOf[LibraryRoutes])

  def booksActor: ActorRef

  implicit lazy val timeout: Timeout = Timeout(5.seconds)

  lazy val libraryRoutes: Route =
    path("books") {
      get {
        val books: Future[Books] = (booksActor ? GetBooks).mapTo[Books]
        onComplete(books) {
          case Success(result) => complete(result)
          case Failure(f) =>
            logger.error("something went wrong", f.getCause)
            complete("not good")
        }
      }
    }
}

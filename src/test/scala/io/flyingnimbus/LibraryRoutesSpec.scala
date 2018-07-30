package io.flyingnimbus

import akka.actor.ActorRef
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, WordSpec}

class LibraryRoutesSpec extends WordSpec with Matchers with ScalaFutures with ScalatestRouteTest with LibraryRoutes {

  // Here we need to implement all the abstract members of UserRoutes.
  // We use the real UserRegistryActor to test it while we hit the Routes, 
  // but we could "mock" it by implementing it in-place or by using a TestProbe() 
  override val booksActor: ActorRef =
  system.actorOf(BooksActor.props, "booksActor")

  lazy val routes: Route = libraryRoutes

  "LibraryRoutes" should {
    "return no books if no present (GET /books)" in {
      val request = HttpRequest(uri = "/books")
      request ~> routes ~> check {
        status should ===(StatusCodes.OK)
        contentType should ===(ContentTypes.`application/json`)
        entityAs[String] should ===("""{"books":[]}""")
      }
    }
  }
}


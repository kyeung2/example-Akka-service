package io.flyingnimbus

import akka.actor.{Actor, ActorLogging, Props}
import io.flyingnimbus.models.repository.BookRepository


object BooksActor {

  case object GetBooks

  def props(repository: BookRepository): Props = Props(new BooksActor(repository))
}

class BooksActor(repository: BookRepository) extends Actor with ActorLogging {

  import BooksActor._

  def receive: Receive = {
    case GetBooks => sender() ! repository.findAll()
  }
}

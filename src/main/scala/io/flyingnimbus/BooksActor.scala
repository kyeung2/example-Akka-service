package io.flyingnimbus

import akka.actor.{Actor, ActorLogging, Props}
import akka.pattern.pipe
import io.flyingnimbus.BooksActor._
import io.flyingnimbus.data.BookRepository

import scala.concurrent.ExecutionContext

object BooksActor {

  case object GetBooks

  def props(repository: BookRepository)(implicit bulkheadContext: ExecutionContext): Props = Props(new BooksActor(repository))
}

class BooksActor(repository: BookRepository)(implicit bulkheadContext: ExecutionContext) extends Actor with ActorLogging {

  def receive: Receive = {
    case GetBooks => repository.findAll() pipeTo sender()
  }
}
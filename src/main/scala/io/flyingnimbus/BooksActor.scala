package io.flyingnimbus

import akka.actor.{ Actor, Props }
import akka.pattern.pipe
import com.typesafe.scalalogging.LazyLogging
import io.flyingnimbus.BooksActor._
import io.flyingnimbus.data.BookRepository

import scala.concurrent.ExecutionContext

object BooksActor {

  case object GetBooks

  def props(repository: BookRepository)(implicit bulkheadContext: ExecutionContext): Props = Props(new BooksActor(repository))
}

class BooksActor(repository: BookRepository)(implicit bulkheadContext: ExecutionContext) extends Actor with LazyLogging {

  def receive: Receive = {
    case GetBooks =>
      logger.info("2. BooksActor message received: should be another dispatcher thread executing, since this is an Actor processing a message")
      logger.info("3. there is a pipTo sender() call here. A thread from the bulkheadContext. But no logging so cannot see. Breakpoint in PipeToSupport.pipTo shows this.")
      repository.findAll() pipeTo sender()
  }
}
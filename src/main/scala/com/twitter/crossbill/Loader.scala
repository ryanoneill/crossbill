package com.twitter.crossbill

import com.twitter.io.{Buf, Reader}
import com.twitter.util.Future
import java.io.File
import scala.util.control.NonFatal

case class Loader(file: File) {

  private[this] def go(reader: Reader[Buf], read: Future[Buf] = Loader.Empty): Future[Buf] = {
    val result = reader.read()
    result.flatMap { buf => 
      buf match {
        case Some(b) => 
          go(reader, read.map(_.concat(b)))
        case None => 
          reader.discard()
          read
      }
    }.respond(result => if (result.isThrow) reader.discard())
  }

  def load(): Future[Buf] = {
    val reader = Reader.fromFile(file)
    go(reader)
  }

}

object Loader {
  private val Empty = Future.value(Buf.Empty)

  def apply(name: String): Loader = {
    val file = new File(name)
    Loader(file)
  }
}

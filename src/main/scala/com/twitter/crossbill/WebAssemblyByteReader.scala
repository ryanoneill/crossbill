package com.twitter.crossbill

import com.twitter.io.{Buf, ByteReader, ProxyByteReader}
import java.nio.charset.Charset

abstract class WebAssemblyByteReader(buf: Buf) extends ProxyByteReader {
  protected val reader = ByteReader(buf)

  protected def position: Int = buf.length - remaining

  protected def verify(byte: Byte): Unit = {
    val actual = readByte()
    if (!(actual == byte))
      throw InvalidFormatException(s"Expected $byte at position $position, found $actual")
  }

  protected def readUtf8String(): String = {
    val size = readByte()
    readString(size, WebAssemblyByteReader.Utf8)
  }

}

object WebAssemblyByteReader {
  private val Utf8 = Charset.forName("UTF-8")
}

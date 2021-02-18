package com.twitter.crossbill

import com.twitter.io.{Buf, ByteReader, ProxyByteReader}

case class LittleEndianBaseByteReader(buf: Buf) extends ProxyByteReader {
  protected val reader = ByteReader(buf)

  // TODO: This stuffs an unsigned 32 bit integer into a signed 32 bit integer
  def readUnsigned32(): Int = {
    var count: Int = 0
    var result: Int = 0
    var shift: Int = 0

    var next: Byte = 0
    var done: Boolean = false
    while (!done && count < 4) {
      count += 1
      next = readByte()
      done = ((next & 0x80.toByte) == 0)
      result |= (next & 0x7F.toByte) << shift
      shift += 7
    }
    if (!done) throw InvalidFormatException(s"readUnsigned32() produced $result")
    result
  }

}

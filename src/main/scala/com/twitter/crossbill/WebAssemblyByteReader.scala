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

  protected def fill[T](n: Int, f: => T): Seq[T] = Seq.fill(n)(f)

  protected def readUtf8String(): String = readString(readByte(), WebAssemblyByteReader.Utf8)

  protected def readLimits(): Limits = readByte() match {
    case 0x00 => Limits.Min(readByte())
    case 0x01 => Limits.MinMax(readByte(), readByte())
    case actual => throw NotInRangeException(0x00.toByte, 0x01.toByte, actual)
  }

  protected def readValueType(): ValueType = readByte() match {
    case 0x7F => ValueType.I32
    case 0x7E => ValueType.I64
    case 0x7D => ValueType.F32
    case 0x7C => ValueType.F64
    case actual => throw NotInRangeException(0x7C.toByte, 0x7F.toByte, actual)
  }

}

object WebAssemblyByteReader {
  private val Utf8 = Charset.forName("UTF-8")
}

package com.twitter.crossbill

import com.twitter.io.{Buf, ByteReader, ProxyByteReader}

case class ModuleByteReader(buf: Buf) extends ProxyByteReader {
  protected val reader = ByteReader(buf)

  private[this] def verify(byte: Byte): Unit = {
    val actual = readByte()
    if (!(actual == byte))
      throw InvalidFormatException(s"Expected $byte at position $position, found $actual")
  }

  def position: Int = buf.length - remaining

  def readMagic(): Unit = {
    verify(0x00.toByte) // 0
    verify(0x61.toByte) // a
    verify(0x73.toByte) // s
    verify(0x6D.toByte) // m
  }

  def readVersion(): Unit = {
    // Version 1 is all that's supported.
    verify(0x01.toByte) // 1
    verify(0x00.toByte) // 0
    verify(0x00.toByte) // 0
    verify(0x00.toByte) // 0
  }
}

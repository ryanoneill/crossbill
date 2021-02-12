package com.twitter.crossbill

import com.twitter.io.{Buf, ByteReader, ProxyByteReader}

// TODO: The use of readByte() in many places is wrong. It is used here
// as a starting point to get off the ground.
case class ModuleByteReader(buf: Buf) extends WebAssemblyByteReader(buf) {

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

  def readTypeSection(): TypeSection = {
    // TODO: This needs additional work
    verify(0x01.toByte) // Type Section is Section 1
    val size = readByte()
    val tsbr = TypeSectionByteReader(readBytes(size))
    tsbr.read()
  }

  def readImportSection(): ImportSection = {
    // TODO: This needs additional work
    verify(0x02.toByte) // Import Section is Section 2
    val size = readByte()
    val isbr = ImportSectionByteReader(readBytes(size))
    isbr.read()
  }

  def readExportSection(): ExportSection = {
    // TODO: This needs additional work
    verify(0x07.toByte) // Export Section is Section 7
    val size = readByte()
    val esbr = ExportSectionByteReader(readBytes(size))
    esbr.read()
  }

}

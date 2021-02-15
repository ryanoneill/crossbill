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
    val size = readByte()
    val tsbr = TypeSectionByteReader(readBytes(size))
    tsbr.read()
  }

  def readImportSection(): ImportSection = {
    val size = readByte()
    val isbr = ImportSectionByteReader(readBytes(size))
    isbr.read()
  }

  def readFunctionSection(): FunctionSection = {
    val size = readByte()
    val fsbr = FunctionSectionByteReader(readBytes(size))
    fsbr.read()
  }

  def readTableSection(): TableSection = {
    val size = readByte()
    val tsbr = TableSectionByteReader(readBytes(size))
    tsbr.read()
  }

  def readMemorySection(): MemorySection = {
    val size = readByte()
    val msbr = MemorySectionByteReader(readBytes(size))
    msbr.read()
  }

  def readExportSection(): ExportSection = {
    val size = readByte()
    val esbr = ExportSectionByteReader(readBytes(size))
    esbr.read()
  }

  def readCodeSection(): CodeSection = {
    val size = readByte()
    val csbr = CodeSectionByteReader(readBytes(size))
    csbr.read()
  }

  def read(): Module = {
    // TODO: This needs additional work
    readMagic()
    readVersion()

    var module = Module()
    while (remaining > 0) {
      readByte() match {
        case 0x01 => module = module.copy(typeSection = readTypeSection())
        case 0x02 => module = module.copy(importSection = readImportSection())
        case 0x03 => module = module.copy(functionSection = readFunctionSection())
        case 0x04 => module = module.copy(tableSection = readTableSection())
        case 0x05 => module = module.copy(memorySection = readMemorySection())
        case 0x07 => module = module.copy(exportSection = readExportSection())
        case 0x0A => module = module.copy(codeSection = readCodeSection())
        case actual => throw UnsupportedFeatureException(s"Module 0x${actual.toHexString}")
      }
    }
    module
  }

}

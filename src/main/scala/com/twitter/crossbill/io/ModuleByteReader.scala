package com.twitter.crossbill.io

import com.twitter.crossbill._
import com.twitter.io.{Buf, ByteReader, ProxyByteReader}

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
    val size = readUnsigned32()
    val tsbr = TypeSectionByteReader(readBytes(size))
    tsbr.read()
  }

  def readImportSection(): ImportSection = {
    val size = readUnsigned32()
    val isbr = ImportSectionByteReader(readBytes(size))
    isbr.read()
  }

  def readFunctionSection(): FunctionSection = {
    val size = readUnsigned32()
    val fsbr = FunctionSectionByteReader(readBytes(size))
    fsbr.read()
  }

  def readTableSection(): TableSection = {
    val size = readUnsigned32()
    val tsbr = TableSectionByteReader(readBytes(size))
    tsbr.read()
  }

  def readMemorySection(): MemorySection = {
    val size = readUnsigned32()
    val msbr = MemorySectionByteReader(readBytes(size))
    msbr.read()
  }

  def readGlobalSection(): GlobalSection = {
    val size = readUnsigned32()
    val gsbr = GlobalSectionByteReader(readBytes(size))
    gsbr.read()
  }

  def readExportSection(): ExportSection = {
    val size = readUnsigned32()
    val esbr = ExportSectionByteReader(readBytes(size))
    esbr.read()
  }

  def readElementSection(): ElementSection = {
    val size = readUnsigned32()
    val esbr = ElementSectionByteReader(readBytes(size))
    esbr.read()
  }

  def readCodeSection(): CodeSection = {
    val size = readUnsigned32()
    val csbr = CodeSectionByteReader(readBytes(size))
    csbr.read()
  }

  def readDataSection(): DataSection = {
    val size = readUnsigned32()
    val dsbr = DataSectionByteReader(readBytes(size))
    dsbr.read()
  }

  def readCustomSection(): CustomSection = {
    val size = readUnsigned32()
    val csbr = CustomSectionByteReader(readBytes(size))
    csbr.read()
  }

  def read(): Module = {
    // TODO: This needs additional work
    readMagic()
    readVersion()

    var module = Module()
    while (remaining > 0) {
      readByte() match {
        // TODO: Multiple custom sections are not currently supported.
        case 0x00 => module = module.copy(customSection = readCustomSection())
        case 0x01 => module = module.copy(typeSection = readTypeSection())
        case 0x02 => module = module.copy(importSection = readImportSection())
        case 0x03 => module = module.copy(functionSection = readFunctionSection())
        case 0x04 => module = module.copy(tableSection = readTableSection())
        case 0x05 => module = module.copy(memorySection = readMemorySection())
        case 0x06 => module = module.copy(globalSection = readGlobalSection())
        case 0x07 => module = module.copy(exportSection = readExportSection())
        case 0x09 => module = module.copy(elementSection = readElementSection())
        case 0x0A => module = module.copy(codeSection = readCodeSection())
        case 0x0B => module = module.copy(dataSection = readDataSection())
        case actual => throw UnsupportedFeatureException(s"Module 0x${actual.toHexString}")
      }
    }
    module
  }

}

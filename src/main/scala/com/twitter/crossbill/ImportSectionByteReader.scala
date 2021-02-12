package com.twitter.crossbill

import com.twitter.io.Buf

case class ImportSectionByteReader(buf: Buf) extends WebAssemblyByteReader(buf) {

  def readImportDescription(): ImportDescription = {
    readByte() match {
      case 0x00 => ImportDescription.Func(readByte())
      case 0x01 => throw UnsupportedFeatureException("ImportDescription.Table")
      case 0x02 => throw UnsupportedFeatureException("ImportDescription.Mem")
      case 0x03 => throw UnsupportedFeatureException("ImportDescription.Global")
      case actual => throw NotInRangeException(0x00.toByte, 0x03.toByte, actual)
    }
  }

  def readImport(): Import = Import(
    readUtf8String(),
    readUtf8String(),
    readImportDescription()
  )

  def read(): ImportSection = {
    val size = readByte()
    ImportSection(1.to(size).map(_ => readImport()))
  }

}

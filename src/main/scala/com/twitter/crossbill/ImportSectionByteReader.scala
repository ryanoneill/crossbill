package com.twitter.crossbill

import com.twitter.io.Buf

case class ImportSectionByteReader(buf: Buf) extends WebAssemblyByteReader(buf) {

  def readImportDescription(): ImportDescription = {
    // TODO: This needs additional work
    verify(0x00.toByte)
    ImportDescription.Func(readByte())
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

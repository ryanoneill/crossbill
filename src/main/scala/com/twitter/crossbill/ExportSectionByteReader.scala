package com.twitter.crossbill

import com.twitter.io.Buf

case class ExportSectionByteReader(buf: Buf) extends WebAssemblyByteReader(buf) {

  def readExportDescription(): ExportDescription = {
    // TODO: This needs additional work
    // TODO: Combine with readImportDescription?
    verify(0x00.toByte)
    ExportDescription.Func(readByte())
  }

  def readExport(): Export =
    Export(readUtf8String(), readExportDescription())

  def read(): ExportSection = 
    ExportSection(fill(readByte(), readExport))

}

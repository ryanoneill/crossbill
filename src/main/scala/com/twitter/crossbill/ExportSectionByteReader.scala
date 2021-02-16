package com.twitter.crossbill

import com.twitter.io.Buf

case class ExportSectionByteReader(buf: Buf) extends WebAssemblyByteReader(buf) {

  def readExportDescription(): ExportDescription = readByte() match {
    case 0x00 => ExportDescription.Func(readByte())
    case 0x01 => ExportDescription.Table(readByte())
    case 0x02 => ExportDescription.Mem(readByte())
    case 0x03 => ExportDescription.Global(readByte())
    case actual => throw NotInRangeException(0x00.toByte, 0x03.toByte, actual)
  }

  def readExport(): Export =
    Export(readUtf8String(), readExportDescription())

  def read(): ExportSection = 
    ExportSection(fill(readByte(), readExport()))

}

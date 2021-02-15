package com.twitter.crossbill

import com.twitter.io.Buf

case class TableSectionByteReader(buf: Buf) extends WebAssemblyByteReader(buf) {

  def readTable(): Table = {
    verify(0x70.toByte) // Only ElemType (Funcref)
    Table(readLimits())
  }

  def read(): TableSection =
    TableSection(fill(readByte(), readTable()))

}

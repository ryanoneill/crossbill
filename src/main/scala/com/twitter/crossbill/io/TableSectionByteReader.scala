package com.twitter.crossbill.io

import com.twitter.crossbill.{Table, TableSection}
import com.twitter.io.Buf

case class TableSectionByteReader(buf: Buf) extends WebAssemblyByteReader(buf) {

  def readTable(): Table = {
    verify(0x70.toByte) // Only ElemType (Funcref)
    Table(readLimits())
  }

  def read(): TableSection =
    TableSection(fill(readUnsigned32(), readTable()))

}

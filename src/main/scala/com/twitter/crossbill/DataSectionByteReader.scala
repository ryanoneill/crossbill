package com.twitter.crossbill

import com.twitter.io.Buf

case class DataSectionByteReader(buf: Buf) extends WebAssemblyByteReader(buf) {

  def readData(): Data =
    Data(readUnsigned32(), readInstructions(), fill(readUnsigned32(), readByte()))

  def read(): DataSection =
    DataSection(fill(readUnsigned32(), readData()))

}

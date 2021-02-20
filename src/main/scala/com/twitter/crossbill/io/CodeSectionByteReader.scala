package com.twitter.crossbill.io

import com.twitter.crossbill.{CodeEntry, CodeSection}
import com.twitter.io.Buf

case class CodeSectionByteReader(buf: Buf) extends WebAssemblyByteReader(buf) {

  def readCodeEntry(): CodeEntry = {
    val size = readUnsigned32()
    val cebr = CodeEntryByteReader(readBytes(size))
    cebr.read()
  }

  def read(): CodeSection =
    CodeSection(fill(readUnsigned32(), readCodeEntry()))

}

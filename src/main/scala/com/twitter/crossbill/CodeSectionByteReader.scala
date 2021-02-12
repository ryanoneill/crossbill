package com.twitter.crossbill

import com.twitter.io.Buf

case class CodeSectionByteReader(buf: Buf) extends WebAssemblyByteReader(buf) {

  def readCodeEntry(): CodeEntry = {
    val size = readByte()
    val cebr = CodeEntryByteReader(readBytes(size))
    cebr.read()
  }

  def read(): CodeSection = 
    CodeSection(fill(readByte(), readCodeEntry()))

}

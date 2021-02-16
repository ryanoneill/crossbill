package com.twitter.crossbill

import com.twitter.io.Buf

case class CodeEntryByteReader(buf: Buf) extends WebAssemblyByteReader(buf) {

  def readLocal(): Local = Local(readByte(), readValueType())

  def readLocals(): Seq[Local] = readByte() match {
    case 0 => Seq.empty
    case size => fill(size, readLocal())
  }

  def read(): CodeEntry = CodeEntry(readLocals(), readInstructions())

}

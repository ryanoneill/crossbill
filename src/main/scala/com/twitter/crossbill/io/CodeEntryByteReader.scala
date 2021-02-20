package com.twitter.crossbill.io

import com.twitter.crossbill.{CodeEntry, Local}
import com.twitter.io.Buf

case class CodeEntryByteReader(buf: Buf) extends WebAssemblyByteReader(buf) {

  def readLocal(): Local = Local(readUnsigned32(), readValueType())

  def readLocals(): Seq[Local] = readUnsigned32() match {
    case 0 => Seq.empty
    case size => fill(size, readLocal())
  }

  def read(): CodeEntry = CodeEntry(readLocals(), readInstructions())

}

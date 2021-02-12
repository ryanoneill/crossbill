package com.twitter.crossbill

import com.twitter.io.Buf
import scala.collection.mutable.ArrayBuffer

case class CodeEntryByteReader(buf: Buf) extends WebAssemblyByteReader(buf) {

  def readInstructions(): Seq[Instruction] = {
    val results = ArrayBuffer[Instruction]()

    var current = readByte()
    while (current != 0x0B.toByte) {
      current match {
        // TODO: This needs tons of additional work
        case 0x41 =>
          results.append(Instruction.I32Constant(readByte()))
        case 0x10 =>
          results.append(Instruction.Call(readByte()))
        case _ => 
          // TODO: This needs additional work
          verify(0x0B.toByte)
      }
      current = readByte()
    }
    results.toSeq
  }

  def read(): CodeEntry = {
    verify(0x00.toByte) // TODO: Handle Locals
    CodeEntry(readInstructions())
  }

}

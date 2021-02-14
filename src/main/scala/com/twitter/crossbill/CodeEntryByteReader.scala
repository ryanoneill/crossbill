package com.twitter.crossbill

import com.twitter.io.Buf
import scala.collection.mutable.ArrayBuffer

case class CodeEntryByteReader(buf: Buf) extends WebAssemblyByteReader(buf) {

  def readMemarg(): Memarg = Memarg(readByte(), readByte())

  def readLocal(): Local = Local(readByte(), readValueType())

  def readLocals(): Seq[Local] = readByte() match {
    case 0 => Seq.empty
    case size => fill(size, readLocal)
  }

  def readInstructions(): Seq[Instruction] = {
    val results = ArrayBuffer[Instruction]()

    var current = readByte()
    while (current != 0x0B.toByte) {
      current match {
        // TODO: This needs tons of additional work
        case 0x02 =>
          // TODO: Only read the empty block type for now.
          verify(0x40)
          results.append(Instruction.BlockEnd(BlockType.Empty, readInstructions()))
        case 0x03 =>
          // TODO: Only read the empty block type for now.
          verify(0x40)
          results.append(Instruction.LoopEnd(BlockType.Empty, readInstructions()))
        case 0x0C =>
          results.append(Instruction.Br(readByte()))
        case 0x0D =>
          results.append(Instruction.BrIf(readByte()))
        case 0x10 =>
          results.append(Instruction.Call(readByte()))
        case 0x20 =>
          results.append(Instruction.LocalGet(readByte()))
        case 0x21 =>
          results.append(Instruction.LocalSet(readByte()))
        case 0x28 =>
          results.append(Instruction.I32Load(readMemarg()))
        case 0x41 =>
          results.append(Instruction.I32Constant(readByte()))
        case 0x46 =>
          results.append(Instruction.I32Eq)
        case 0x6A =>
          results.append(Instruction.I32Add)
        case 0x6C =>
          results.append(Instruction.I32Mul)
        case _ => 
          throw UnsupportedFeatureException(s"Instruction 0x${current.toHexString}")
      }
      current = readByte()
    }
    results.toSeq
  }

  def read(): CodeEntry = CodeEntry(readLocals(), readInstructions())

}

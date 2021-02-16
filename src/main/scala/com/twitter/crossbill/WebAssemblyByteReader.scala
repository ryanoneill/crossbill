package com.twitter.crossbill

import com.twitter.io.{Buf, ByteReader, ProxyByteReader}
import java.nio.charset.Charset
import scala.collection.mutable.ArrayBuffer

abstract class WebAssemblyByteReader(buf: Buf) extends ProxyByteReader {
  protected val reader = ByteReader(buf)

  protected def position: Int = buf.length - remaining

  protected def verify(byte: Byte): Unit = {
    val actual = readByte()
    if (!(actual == byte))
      throw InvalidFormatException(s"Expected $byte at position $position, found $actual")
  }

  protected def fill[T](n: Int, f: => T): Seq[T] = Seq.fill(n)(f)

  protected def readUtf8String(): String = readString(readByte(), WebAssemblyByteReader.Utf8)

  protected def readMemarg(): Memarg = Memarg(readByte(), readByte())

  protected def readLimits(): Limits = readByte() match {
    case 0x00 => Limits.Min(readByte())
    case 0x01 => Limits.MinMax(readByte(), readByte())
    case actual => throw NotInRangeException(0x00.toByte, 0x01.toByte, actual)
  }

  protected def readValueType(): ValueType = readByte() match {
    case 0x7F => ValueType.I32
    case 0x7E => ValueType.I64
    case 0x7D => ValueType.F32
    case 0x7C => ValueType.F64
    case actual => throw NotInRangeException(0x7C.toByte, 0x7F.toByte, actual)
  }

  protected def readInstructions(): Seq[Instruction] = {
    val results = ArrayBuffer[Instruction]()

    var current = readByte()
    while (current != 0x0B.toByte) {
      current match {
        // TODO: This needs tons of additional work
        case 0x00 =>
          results.append(Instruction.Unreachable)
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
        case -128 => // 0x80 // TODO: Clean up how unsigned/signed bytes work
          results.append(Instruction.I64DivU)
        case -119 => // 0x89
          results.append(Instruction.I64Rotl)
        case -64 => // 0xC0 
          results.append(Instruction.I32Extend8S)
        case _ => 
          throw UnsupportedFeatureException(s"Instruction 0x${current.toHexString}")
      }
      current = readByte()
    }
    results.toSeq
  }

}

object WebAssemblyByteReader {
  private val Utf8 = Charset.forName("UTF-8")
}

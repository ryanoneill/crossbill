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

  protected def readUtf8String(): String = readString(readUnsigned32(), WebAssemblyByteReader.Utf8)

  protected def readMemarg(): Memarg = Memarg(readUnsigned32(), readUnsigned32())

  protected def readLimits(): Limits = readByte() match {
    case 0x00 => Limits.Min(readUnsigned32())
    case 0x01 => Limits.MinMax(readUnsigned32(), readUnsigned32())
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
          results.append(Instruction.Br(readUnsigned32()))
        case 0x0D =>
          results.append(Instruction.BrIf(readUnsigned32()))
        case 0x10 =>
          results.append(Instruction.Call(readUnsigned32()))
        case 0x20 =>
          results.append(Instruction.LocalGet(readUnsigned32()))
        case 0x21 =>
          results.append(Instruction.LocalSet(readUnsigned32()))
        case 0x28 =>
          results.append(Instruction.I32Load(readMemarg()))
        case 0x41 =>
          // TODO: The use of readSigned32 may still be incorrect.
          results.append(Instruction.I32Constant(readSigned32()))
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

  // TODO: This stuffs an unsigned 32 bit integer into a signed 32 bit integer
  def readUnsigned32(): Int = {
    var count: Int = 0
    var result: Int = 0
    var shift: Int = 0

    var next: Byte = 0
    var done: Boolean = false
    while (!done && count < 4) {
      count += 1
      next = readByte()
      done = ((next & 0x80.toByte) == 0)
      result |= (next & 0x7F.toByte) << shift
      shift += 7
    }
    if (!done) throw InvalidFormatException(s"readUnsigned32() produced $result")
    result
  }

  def readSigned32(): Int = {
    var count: Int = 0
    var result: Int = 0
    var shift: Int = 0

    var next: Byte = 0
    var done: Boolean = false
    while (!done && count < 4) {
      next = readByte()
      done = ((next & 0x80.toByte) == 0)
      result |= (next & 0x7F.toByte) << shift
      shift += 7
    }
    if (shift < 32 && ((next & 0x40.toByte) != 0))
      result |= (~0 << shift)
    result
  }
}

object WebAssemblyByteReader {
  private val Utf8 = Charset.forName("UTF-8")
}

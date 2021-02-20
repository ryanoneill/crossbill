package com.twitter.crossbill.io

import com.twitter.crossbill.{BlockType, Instruction, Limits, Memarg, ValueType}
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

  protected def readBlockType(): BlockType = readByte() match {
    case 0x40 => BlockType.Empty
    case 0x7F => BlockType.Value(ValueType.I32)
    case 0x7E => BlockType.Value(ValueType.I64)
    case 0x7D => BlockType.Value(ValueType.F32)
    case 0x7C => BlockType.Value(ValueType.F64)
    case actual => throw UnsupportedFeatureException(s"BlockType $actual")
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

    try {
    var current = readByte()
    while (current != 0x0B.toByte) {
      current match {
        // TODO: This needs tons of additional work
        case 0x00 =>
          results.append(Instruction.Unreachable)
        case 0x02 =>
          results.append(Instruction.BlockEnd(readBlockType(), readInstructions()))
        case 0x03 =>
          results.append(Instruction.LoopEnd(readBlockType(), readInstructions()))
        case 0x0C =>
          results.append(Instruction.Br(readUnsigned32()))
        case 0x0D =>
          results.append(Instruction.BrIf(readUnsigned32()))
        case 0x0E =>
          results.append(
            Instruction.BrTable(
              fill(readUnsigned32(), readUnsigned32()), readUnsigned32()
            )
          )
        case 0x0F =>
          results.append(Instruction.Return)
        case 0x10 =>
          results.append(Instruction.Call(readUnsigned32()))
        case 0x11 =>
          results.append(Instruction.CallIndirect(readUnsigned32()))
          verify(0x00.toByte)
        case 0x1A =>
          results.append(Instruction.Drop)
        case 0x1B =>
          results.append(Instruction.Select)
        case 0x20 =>
          results.append(Instruction.LocalGet(readUnsigned32()))
        case 0x21 =>
          results.append(Instruction.LocalSet(readUnsigned32()))
        case 0x22 =>
          results.append(Instruction.LocalTee(readUnsigned32()))
        case 0x23 =>
          results.append(Instruction.GlobalGet(readUnsigned32()))
        case 0x24 =>
          results.append(Instruction.GlobalSet(readUnsigned32()))
        case 0x28 =>
          results.append(Instruction.I32Load(readMemarg()))
        case 0x29 =>
          results.append(Instruction.I64Load(readMemarg()))
        case 0x2D =>
          results.append(Instruction.I32Load8U(readMemarg()))
        case 0x2F =>
          results.append(Instruction.I32Load16U(readMemarg()))
        case 0x35 =>
          results.append(Instruction.I64Load32U(readMemarg()))
        case 0x36 =>
          results.append(Instruction.I32Store(readMemarg()))
        case 0x37 =>
          results.append(Instruction.I64Store(readMemarg()))
        case 0x3A =>
          results.append(Instruction.I32Store8(readMemarg()))
        case 0x3B =>
          results.append(Instruction.I32Store16(readMemarg()))
        case 0x40 =>
          results.append(Instruction.MemoryGrow)
          verify(0x00.toByte)
        case 0x41 =>
          // TODO: The use of readSigned32 may still be incorrect.
          results.append(Instruction.I32Constant(readSigned32()))
        case 0x42 =>
          results.append(Instruction.I64Constant(readSigned64()))
        case 0x45 =>
          results.append(Instruction.I32Eqz)
        case 0x46 =>
          results.append(Instruction.I32Eq)
        case 0x47 =>
          results.append(Instruction.I32Ne)
        case 0x48 =>
          results.append(Instruction.I32LtS)
        case 0x49 =>
          results.append(Instruction.I32LtU)
        case 0x4B =>
          results.append(Instruction.I32GtU)
        case 0x4C =>
          results.append(Instruction.I32LeS)
        case 0x4D =>
          results.append(Instruction.I32LeU)
        case 0x4E =>
          results.append(Instruction.I32GeS)
        case 0x4F =>
          results.append(Instruction.I32GeU)
        case 0x5A =>
          results.append(Instruction.I64GeU)
        case 0x56 =>
          results.append(Instruction.I64GtU)
        case 0x67 =>
          results.append(Instruction.I32Clz)
        case 0x68 =>
          results.append(Instruction.I32Ctz)
        case 0x6A =>
          results.append(Instruction.I32Add)
        case 0x6B =>
          results.append(Instruction.I32Sub)
        case 0x6C =>
          results.append(Instruction.I32Mul)
        case 0x6E =>
          results.append(Instruction.I32DivU)
        case 0x71 =>
          results.append(Instruction.I32And)
        case 0x72 =>
          results.append(Instruction.I32Or)
        case 0x73 =>
          results.append(Instruction.I32Xor)
        case 0x74 =>
          results.append(Instruction.I32Shl)
        case 0x76 =>
          results.append(Instruction.I32ShrU)
        case 0x77 =>
          results.append(Instruction.I32Rotl)
        case 0x7D =>
          results.append(Instruction.I64Sub)
        case 0x7E =>
          results.append(Instruction.I64Mul)
        case -128 => // 0x80 // TODO: Clean up how unsigned/signed bytes work
          results.append(Instruction.I64DivU)
        case -119 => // 0x89
          results.append(Instruction.I64Rotl)
        case -89 => // 0xA7 
          results.append(Instruction.I32WrapI64)
        case -64 => // 0xC0
          results.append(Instruction.I32Extend8S)
        case _ =>
          throw UnsupportedFeatureException(s"Instruction 0x${current.toHexString}")
      }
      current = readByte()
    }
    } finally {
      println(results)
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

  def readSigned64(): Int = {
    var count: Int = 0
    var result: Int = 0
    var shift: Int = 0

    var next: Byte = 0
    var done: Boolean = false
    while (!done && count < 8) {
      next = readByte()
      done = ((next & 0x80.toByte) == 0)
      result |= (next & 0x7F.toByte) << shift
      shift += 7
    }
    if (shift < 64 && ((next & 0x40.toByte) != 0))
      result |= (~0 << shift)
    result
  }
}

object WebAssemblyByteReader {
  private val Utf8 = Charset.forName("UTF-8")
}

package com.twitter.crossbill.io

import com.twitter.crossbill.{FunctionType, ResultType, TypeSection, ValueType}
import com.twitter.io.Buf

case class TypeSectionByteReader(buf: Buf) extends WebAssemblyByteReader(buf) {

  def readResultType(): ResultType = {
    val size = readByte()
    if (size == 0) ResultType.Nil
    else if (size == 1) {
      readValueType() match {
        case _: ValueType.I32.type => ResultType.I32
        case _: ValueType.I64.type => ResultType.I64
        case _: ValueType.F32.type => ResultType.F32
        case _: ValueType.F64.type => ResultType.F64
      }
    }
    else ResultType(1.to(size).map(_ => readValueType()))
  }

  def readFunctionType(): FunctionType = {
    verify(0x60.toByte)
    FunctionType(readResultType(), readResultType())
  }

  def read(): TypeSection = {
    val size = readUnsigned32()
    TypeSection(1.to(size).map(_ => readFunctionType()))
  }

}

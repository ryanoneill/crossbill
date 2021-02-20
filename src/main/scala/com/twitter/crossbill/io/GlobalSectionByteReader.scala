package com.twitter.crossbill.io

import com.twitter.crossbill.{Global, GlobalSection, GlobalType, Mutabliity}
import com.twitter.io.Buf

case class GlobalSectionByteReader(buf: Buf) extends WebAssemblyByteReader(buf) {

  def readMutability(): Mutabliity = readByte() match {
    case 0x00 => Mutabliity.Const
    case 0x01 => Mutabliity.Var
    case actual => throw NotInRangeException(0x00.toByte, 0x01.toByte, actual)
  }

  def readGlobalType(): GlobalType =
    GlobalType(readValueType(), readMutability())

  def readGlobal(): Global =
    Global(readGlobalType(), readInstructions())

  def read(): GlobalSection = 
    GlobalSection(fill(readByte(), readGlobal()))

}

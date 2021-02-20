package com.twitter.crossbill.io

import com.twitter.crossbill.FunctionSection
import com.twitter.io.Buf

case class FunctionSectionByteReader(buf: Buf) extends WebAssemblyByteReader(buf) {

  def read(): FunctionSection = 
    FunctionSection(fill(readUnsigned32(), readUnsigned32()))

}

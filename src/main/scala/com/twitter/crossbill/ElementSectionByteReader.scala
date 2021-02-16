package com.twitter.crossbill

import com.twitter.io.Buf

case class ElementSectionByteReader(buf: Buf) extends WebAssemblyByteReader(buf) {

  def readElement(): Element =
    Element(readByte(), readInstructions(), fill(readByte(), readByte()))

  def read(): ElementSection =
    ElementSection(fill(readByte(), readElement()))

}

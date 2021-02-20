package com.twitter.crossbill.io

import com.twitter.crossbill.{Element, ElementSection}
import com.twitter.io.Buf

case class ElementSectionByteReader(buf: Buf) extends WebAssemblyByteReader(buf) {

  def readElement(): Element =
    Element(readUnsigned32(), readInstructions(), fill(readUnsigned32(), readUnsigned32()))

  def read(): ElementSection =
    ElementSection(fill(readUnsigned32(), readElement()))

}

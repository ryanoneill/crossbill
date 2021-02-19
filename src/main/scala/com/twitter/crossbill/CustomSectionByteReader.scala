package com.twitter.crossbill

import com.twitter.io.Buf

case class CustomSectionByteReader(buf: Buf) extends WebAssemblyByteReader(buf) {

  def read(): CustomSection =
    CustomSection(readUtf8String(), readBytes(remaining))

}

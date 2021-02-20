package com.twitter.crossbill.io

import com.twitter.crossbill.CustomSection
import com.twitter.io.Buf

case class CustomSectionByteReader(buf: Buf) extends WebAssemblyByteReader(buf) {

  def read(): CustomSection =
    CustomSection(readUtf8String(), readBytes(remaining))

}

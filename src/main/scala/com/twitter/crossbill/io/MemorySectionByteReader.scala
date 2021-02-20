package com.twitter.crossbill.io

import com.twitter.crossbill.{Memory, MemorySection}
import com.twitter.io.Buf

case class MemorySectionByteReader(buf: Buf) extends WebAssemblyByteReader(buf) {

  def readMemory(): Memory = 
    Memory(readLimits())

  def read(): MemorySection =
    MemorySection(fill(readUnsigned32(), readMemory()))

}

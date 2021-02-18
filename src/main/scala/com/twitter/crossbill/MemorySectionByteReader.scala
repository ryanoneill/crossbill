package com.twitter.crossbill

import com.twitter.io.Buf

case class MemorySectionByteReader(buf: Buf) extends WebAssemblyByteReader(buf) {

  def readMemory(): Memory = 
    Memory(readLimits())

  def read(): MemorySection =
    MemorySection(fill(readUnsigned32(), readMemory()))

}

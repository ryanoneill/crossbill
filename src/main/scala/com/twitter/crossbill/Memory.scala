package com.twitter.crossbill

case class Memory(mt: MemoryType)

object Memory {
  def apply(lim: Limits): Memory =
    Memory(MemoryType(lim))
}

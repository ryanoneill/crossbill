package com.twitter.crossbill

case class MemorySection(memories: Seq[Memory])

object MemorySection {
  val Empty = MemorySection(Seq.empty)
}

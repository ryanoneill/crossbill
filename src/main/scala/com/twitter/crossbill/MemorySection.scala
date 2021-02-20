package com.twitter.crossbill

case class MemorySection(memories: Seq[Memory])

object MemorySection {
  val Empty = new MemorySection(Seq.empty) {
    override def toString(): String = "MemorySection.Empty"
  }
}

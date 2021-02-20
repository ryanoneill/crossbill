package com.twitter.crossbill

case class GlobalSection(globals: Seq[Global])

object GlobalSection {
  val Empty = new GlobalSection(Seq.empty) {
    override def toString(): String = "GlobalSection.Empty"
  }
}

package com.twitter.crossbill

case class GlobalSection(globals: Seq[Global])

object GlobalSection {
  val Empty = GlobalSection(Seq.empty)
}

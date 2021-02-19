package com.twitter.crossbill

case class DataSection(segments: Seq[Data])

object DataSection {
  val Empty = DataSection(Seq.empty)
}

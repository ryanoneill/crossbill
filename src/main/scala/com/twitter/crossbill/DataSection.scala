package com.twitter.crossbill

case class DataSection(segments: Seq[Data])

object DataSection {
  val Empty = new DataSection(Seq.empty) {
    override def toString(): String = "DataSection.Empty"
  }
}

package com.twitter.crossbill

case class TableSection(tables: Seq[Table])

object TableSection {
  val Empty = TableSection(Seq.empty)
}

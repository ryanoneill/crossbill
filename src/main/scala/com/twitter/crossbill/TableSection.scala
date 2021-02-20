package com.twitter.crossbill

case class TableSection(tables: Seq[Table])

object TableSection {
  val Empty = new TableSection(Seq.empty) {
    override def toString(): String = "TableSection.Empty"
  }
}

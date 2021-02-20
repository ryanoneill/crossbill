package com.twitter.crossbill

case class ImportSection(imports: Seq[Import])

object ImportSection {
  val Empty = new ImportSection(Seq.empty) {
    override def toString(): String = "ImportSection.Empty"
  }
}

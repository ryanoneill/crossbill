package com.twitter.crossbill

case class ImportSection(imports: Seq[Import])

object ImportSection {
  val Empty = ImportSection(Seq.empty)
}

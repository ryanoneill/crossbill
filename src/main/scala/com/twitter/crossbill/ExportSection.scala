package com.twitter.crossbill

case class ExportSection(exports: Seq[Export])

object ExportSection {
  val Empty = ExportSection(Seq.empty)
}

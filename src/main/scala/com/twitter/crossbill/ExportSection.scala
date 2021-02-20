package com.twitter.crossbill

case class ExportSection(exports: Seq[Export])

object ExportSection {
  val Empty = new ExportSection(Seq.empty) {
    override def toString(): String = "ExportSection.Empty"
  }
}

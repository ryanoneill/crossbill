package com.twitter.crossbill

case class CodeSection(entries: Seq[CodeEntry])

object CodeSection {
  val Empty = new CodeSection(Seq.empty) {
    override def toString(): String = "CodeSection.Empty"
  }
}

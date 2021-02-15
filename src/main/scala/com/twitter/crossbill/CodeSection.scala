package com.twitter.crossbill

case class CodeSection(entries: Seq[CodeEntry])

object CodeSection {
  val Empty = CodeSection(Seq.empty)
}

package com.twitter.crossbill

case class TypeSection(funcs: Seq[FunctionType])

object TypeSection {
  val Empty = TypeSection(Seq.empty)
}

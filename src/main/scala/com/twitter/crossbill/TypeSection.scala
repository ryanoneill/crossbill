package com.twitter.crossbill

case class TypeSection(funcs: Seq[FunctionType])

object TypeSection {
  val Empty = new TypeSection(Seq.empty) {
    override def toString(): String = "TypeSection.Empty"
  }
}

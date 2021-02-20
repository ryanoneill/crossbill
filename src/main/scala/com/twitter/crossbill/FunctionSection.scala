package com.twitter.crossbill

// TODO: This needs additional work
case class FunctionSection(typeIndices: Seq[Int])

object FunctionSection {
  val Empty = new FunctionSection(Seq.empty) {
    override def toString(): String = "FunctionSection.Empty"
  }
}

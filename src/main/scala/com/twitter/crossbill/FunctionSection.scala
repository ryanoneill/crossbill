package com.twitter.crossbill

// TODO: This needs additional work
case class FunctionSection(typeIndices: Seq[Int])

object FunctionSection {
  val Empty = FunctionSection(Seq.empty)
}

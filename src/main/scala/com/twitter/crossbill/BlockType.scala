package com.twitter.crossbill

// TODO: This needs additional work
sealed trait BlockType

object BlockType {
  case object Empty extends BlockType
  case class Value(t: ValueType) extends BlockType
  // TODO: Add the other cases
}


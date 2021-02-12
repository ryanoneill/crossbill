package com.twitter.crossbill

// TODO: This needs additional work
sealed trait BlockType

object BlockType {
  case object Empty extends BlockType
  // TODO: Add the other cases
}


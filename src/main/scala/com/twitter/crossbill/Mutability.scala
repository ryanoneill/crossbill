package com.twitter.crossbill

sealed trait Mutabliity

object Mutabliity {
  case object Const extends Mutabliity
  case object Var extends Mutabliity
}

package com.twitter.crossbill

sealed trait Limits

object Limits {
  case class Min(n: Int) extends Limits
  case class MinMax(n: Int, m: Int) extends Limits
}

package com.twitter.crossbill

case class FunctionType(rt1: ResultType, rt2: ResultType) {
  override def toString: String = s"$rt1 -> $rt2"
}

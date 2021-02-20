package com.twitter.crossbill.io

case class NotInRangeException(lower: Byte, upper: Byte, actual: Byte)
  extends Exception(s"Expected $actual to be within range [$lower, $upper]")

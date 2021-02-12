package com.twitter.crossbill

sealed trait ImportDescription

object ImportDescription {
  case class Func(typeidx: Int) extends ImportDescription
  // TODO: This needs additional work
  case object Table extends ImportDescription
  case class Mem(limits: Limits) extends ImportDescription
  case object Global extends ImportDescription
}

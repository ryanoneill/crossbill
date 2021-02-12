package com.twitter.crossbill

// TODO: Combine with ImportDescription
sealed trait ExportDescription

object ExportDescription {
  case class Func(typeidx: Int) extends ExportDescription
  // TODO: This needs additional work
  case object Table extends ExportDescription
  case object Mem extends ExportDescription
  case object Global extends ExportDescription
}

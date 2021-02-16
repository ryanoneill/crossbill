package com.twitter.crossbill

// TODO: Combine with ImportDescription?
sealed trait ExportDescription

object ExportDescription {
  case class Func(functionIndex: Int) extends ExportDescription
  case class Table(tableIndex: Int) extends ExportDescription
  case class Mem(memoryIndex: Int) extends ExportDescription
  case class Global(globalIndex: Int) extends ExportDescription
}

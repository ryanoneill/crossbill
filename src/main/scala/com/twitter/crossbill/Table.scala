package com.twitter.crossbill

case class Table(tt: TableType)

object Table {
  // TODO: Remove multiple levels
  def apply(lim: Limits): Table = 
    Table(TableType(lim = lim))
}

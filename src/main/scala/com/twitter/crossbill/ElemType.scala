package com.twitter.crossbill

sealed trait ElemType

object ElemType {
  case object Funcref extends ElemType
}

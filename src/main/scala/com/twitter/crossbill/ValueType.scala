package com.twitter.crossbill

sealed trait ValueType

object ValueType {
  case object I32 extends ValueType
  case object I64 extends ValueType
  case object F32 extends ValueType
  case object F64 extends ValueType
}

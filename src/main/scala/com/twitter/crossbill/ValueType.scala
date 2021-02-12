package com.twitter.crossbill

sealed trait ValueType

object ValueType {
  case object I32 extends ValueType {
    override def toString: String = "i32"
  }
  case object I64 extends ValueType {
    override def toString: String = "i64"
  }
  case object F32 extends ValueType {
    override def toString: String = "f32"
  }
  case object F64 extends ValueType {
    override def toString: String = "f64"
  }
}

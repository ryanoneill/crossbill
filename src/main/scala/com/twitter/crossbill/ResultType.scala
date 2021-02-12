package com.twitter.crossbill

case class ResultType(values: Seq[ValueType])

object ResultType {
  val Nil = new ResultType(Seq.empty) {
    override def toString: String = "ResultType.Nil"
  }
  val I32 = new ResultType(Seq(ValueType.I32)) {
    override def toString: String = "ResultType.I32"
  }
  val I64 = new ResultType(Seq(ValueType.I64)) {
    override def toString: String = "ResultType.I64"
  }
  val F32 = new ResultType(Seq(ValueType.F32)) {
    override def toString: String = "ResultType.F32"
  }
  val F64 = new ResultType(Seq(ValueType.F64)) {
    override def toString: String = "ResultType.F64"
  }
}

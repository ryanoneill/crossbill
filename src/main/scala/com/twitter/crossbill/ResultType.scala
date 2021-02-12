package com.twitter.crossbill

case class ResultType(values: Seq[ValueType]) {
  override def toString: String = 
    if (values == Seq.empty) "nil"
    else values.mkString("(", ",", ")")
}

object ResultType {
  val Nil = new ResultType(Seq.empty)
  val I32 = new ResultType(Seq(ValueType.I32))
  val I64 = new ResultType(Seq(ValueType.I64))
  val F32 = new ResultType(Seq(ValueType.F32))
  val F64 = new ResultType(Seq(ValueType.F64))
}

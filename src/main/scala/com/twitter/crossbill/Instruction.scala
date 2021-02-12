package com.twitter.crossbill

sealed trait Instruction

object Instruction {
  case object Unreachable extends Instruction
  case object Nop extends Instruction
  case class BlockEnd(bt: BlockType, in: Seq[Instruction]) extends Instruction
  case class LoopEnd(bt: BlockType, in: Seq[Instruction]) extends Instruction
  case class IfEnd(bt: BlockType, in: Seq[Instruction]) extends Instruction
  // TODO: More Here
  case class Call(functionIndex: Int) extends Instruction
  // TODO: A Ton More
  case class I32Constant(n: Int) extends Instruction
  case class I64Constant(n: Long) extends Instruction
  case class F32Constant(z: Float) extends Instruction
  case class F64Constant(z: Double) extends Instruction

}

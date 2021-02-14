package com.twitter.crossbill

sealed trait Instruction

// TODO: Clean up all these names for consistency
object Instruction {
  case object Unreachable extends Instruction
  case object Nop extends Instruction
  case class BlockEnd(bt: BlockType, in: Seq[Instruction]) extends Instruction
  case class LoopEnd(bt: BlockType, in: Seq[Instruction]) extends Instruction
  case class IfEnd(bt: BlockType, in: Seq[Instruction]) extends Instruction
  case class IfElseEnd(bt: BlockType, in1: Seq[Instruction], in2: Seq[Instruction]) extends Instruction
  case class Br(labelIndex: Int) extends Instruction
  case class BrIf(labelIndex: Int) extends Instruction
  // TODO: More Here
  case class Call(functionIndex: Int) extends Instruction
  // TODO: More Here
  case object Drop extends Instruction
  case object Select extends Instruction
  case class LocalGet(localIndex: Int) extends Instruction
  case class LocalSet(localIndex: Int) extends Instruction
  case class LocalTee(localIndex: Int) extends Instruction
  case class GlobalGet(globalIndex: Int) extends Instruction
  case class GlobalSet(globalIndex: Int) extends Instruction
  case class I32Load(memarg: Memarg) extends Instruction
  case class I64Load(memarg: Memarg) extends Instruction
  // TODO: A Ton More
  case class I32Constant(n: Int) extends Instruction
  case class I64Constant(n: Long) extends Instruction
  case class F32Constant(z: Float) extends Instruction
  case class F64Constant(z: Double) extends Instruction
  case object I32Eqz extends Instruction
  case object I32Eq extends Instruction
  case object I32Ne extends Instruction
  case object I32LtS extends Instruction
  case object I32LtU extends Instruction
  case object I32GtS extends Instruction
  case object I32GtU extends Instruction
  case object I32LeS extends Instruction
  case object I32LeU extends Instruction
  case object I32GeS extends Instruction
  case object I32GeU extends Instruction
  // TODO: A Ton More
  case object I32Add extends Instruction
  case object I32Sub extends Instruction
  case object I32Mul extends Instruction
  case object I32DivS extends Instruction
  case object I32DivU extends Instruction
  case object I32RemS extends Instruction
  case object I32RemU extends Instruction
  case object I32And extends Instruction
  case object I32Or extends Instruction
  // TODO: A Ton More
}

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
  case class BrTable(labelIndexes: Seq[Int], label: Int) extends Instruction
  // TODO: More Here
  case object Return extends Instruction
  case class Call(functionIndex: Int) extends Instruction
  case class CallIndirect(labelIndex: Int) extends Instruction
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
  case class F32Load(memarg: Memarg) extends Instruction
  case class F64Load(memarg: Memarg) extends Instruction
  case class I32Load8S(memarg: Memarg) extends Instruction
  case class I32Load8U(memarg: Memarg) extends Instruction
  case class I32Load16S(memarg: Memarg) extends Instruction
  case class I32Load16U(memarg: Memarg) extends Instruction
  case class I64Load8S(memarg: Memarg) extends Instruction
  case class I64Load8U(memarg: Memarg) extends Instruction
  case class I64Load16S(memarg: Memarg) extends Instruction
  case class I64Load16U(memarg: Memarg) extends Instruction
  case class I64Load32S(memarg: Memarg) extends Instruction
  case class I64Load32U(memarg: Memarg) extends Instruction
  case class I32Store(memarg: Memarg) extends Instruction
  case class I64Store(memarg: Memarg) extends Instruction
  case class F32Store(memarg: Memarg) extends Instruction
  case class F64Store(memarg: Memarg) extends Instruction
  case class I32Store8(memarg: Memarg) extends Instruction
  case class I32Store16(memarg: Memarg) extends Instruction
  case class I64Store8(memarg: Memarg) extends Instruction
  case class I64Store16(memarg: Memarg) extends Instruction
  case class I64Store32(memarg: Memarg) extends Instruction
  case object MemorySize extends Instruction
  case object MemoryGrow extends Instruction
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
  case object I64Eqz extends Instruction
  case object I64Eq extends Instruction
  case object I64Ne extends Instruction
  case object I64LtS extends Instruction
  case object I64LtU extends Instruction
  case object I64GtS extends Instruction
  case object I64GtU extends Instruction
  case object I64LeS extends Instruction
  case object I64LeU extends Instruction
  case object I64GeS extends Instruction
  case object I64GeU extends Instruction
  // TODO: A Ton More
  case object I32Clz extends Instruction
  case object I32Ctz extends Instruction
  case object I32Popcnt extends Instruction
  case object I32Add extends Instruction
  case object I32Sub extends Instruction
  case object I32Mul extends Instruction
  case object I32DivS extends Instruction
  case object I32DivU extends Instruction
  case object I32RemS extends Instruction
  case object I32RemU extends Instruction
  case object I32And extends Instruction
  case object I32Or extends Instruction
  case object I32Xor extends Instruction
  case object I32Shl extends Instruction
  case object I32ShrS extends Instruction
  case object I32ShrU extends Instruction
  case object I32Rotl extends Instruction
  case object I32Rotr extends Instruction
  // TODO: A Ton More
  case object I64Clz extends Instruction
  case object I64Ctz extends Instruction
  case object I64Popcnt extends Instruction
  case object I64Add extends Instruction
  case object I64Sub extends Instruction
  case object I64Mul extends Instruction
  case object I64DivS extends Instruction
  case object I64DivU extends Instruction
  case object I64RemS extends Instruction
  case object I64RemU extends Instruction
  case object I64And extends Instruction
  case object I64Or extends Instruction
  case object I64Xor extends Instruction
  case object I64Shl extends Instruction
  case object I64ShrS extends Instruction
  case object I64ShrU extends Instruction
  case object I64Rotl extends Instruction
  case object I64Rotr extends Instruction
  // TODO: A Ton More
  case object I32WrapI64 extends Instruction
  case object I32TruncF32S extends Instruction
  // TODO: A Ton More
  case object I32Extend8S extends Instruction
  case object I32Extend16S extends Instruction
  case object I64Extend8S extends Instruction
  case object I64Extend16S extends Instruction
  case object I64Extend32S extends Instruction
}

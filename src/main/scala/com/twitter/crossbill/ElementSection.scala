package com.twitter.crossbill

case class ElementSection(segments: Seq[Element])

object ElementSection {
  val Empty = new ElementSection(Seq.empty) {
    override def toString(): String = "ElementSection.Empty"
  }
}

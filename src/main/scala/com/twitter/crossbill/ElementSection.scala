package com.twitter.crossbill

case class ElementSection(segments: Seq[Element])

object ElementSection {
  val Empty = ElementSection(Seq.empty)
}

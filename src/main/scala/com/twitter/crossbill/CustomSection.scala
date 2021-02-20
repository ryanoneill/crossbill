package com.twitter.crossbill

import com.twitter.io.Buf

case class CustomSection(name: String, bytes: Buf)

object CustomSection {
  val Empty = new CustomSection("", Buf.Empty) {
    override def toString(): String = "CustomSection.Empty"
  }
}

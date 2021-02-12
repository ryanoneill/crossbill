package com.twitter.crossbill

import com.twitter.io.{Buf, ByteReader}
import com.twitter.util.Future

case class Parser(buf: Buf) {

  def parse(): Future[Module] = {
    val mbr = ModuleByteReader(buf)
    mbr.readMagic()
    mbr.readVersion()
    mbr.readTypeSection()
    Future.???
  }

}

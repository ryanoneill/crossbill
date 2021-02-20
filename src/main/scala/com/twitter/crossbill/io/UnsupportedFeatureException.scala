package com.twitter.crossbill.io

case class UnsupportedFeatureException(feature: String)
  extends Exception(s"$feature is not currently supported")

package com.twitter.crossbill

case class UnsupportedFeatureException(feature: String)
  extends Exception(s"$feature is not currently supported")

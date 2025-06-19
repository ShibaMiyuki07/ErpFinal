package com.example.utils

import scala.xml.NodeSeq

trait XmlReadable[T] {
  def fromXml(node : NodeSeq) : T
}

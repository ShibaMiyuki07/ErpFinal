package com.example.models

import scala.xml.NodeSeq

case class Category
(
  categoryId : Option[Int] = None,
  categoryName : String
)

object Category {
  implicit val categoryXmlReader : XmlReadable[Category] = new XmlReadable[Category] {
    override def fromXml(node: NodeSeq): Category = {
      val categoryId = (node \ "categoryId").text.toInt
      val categoryName = (node \ "categoryName").text
      
      Category(Some(categoryId),categoryName)
    }
  }
}

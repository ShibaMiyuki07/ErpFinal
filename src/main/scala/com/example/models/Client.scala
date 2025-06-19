package com.example.models

import scala.xml.NodeSeq

case class Client
(
  clientId :  Option[Int] = None,
  clientName : String,
  phone : String,
  email : String
)

object Client {
  implicit val clientXmlReader : XmlReadable[Client] = (node: NodeSeq) => {
    val clientId = (node \ "clientId").text.toInt
    val clientName = (node \ "clientName").text
    val phone = (node \ "phone").text
    val email = (node \ "email").text
    
    Client(clientId = Some(clientId), clientName = clientName, phone = phone, email = email)
  }
}



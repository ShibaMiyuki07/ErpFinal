package com.example.formats

import com.example.models.Client
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

object ClientFormat {
  implicit val clientFormat: RootJsonFormat[Client] = jsonFormat4(Client.apply)
  implicit val clientsFormat: RootJsonFormat[List[Client]] = listFormat(clientFormat)
}

package com.example.formats

import com.example.models.Category
import spray.json.{DefaultJsonProtocol, RootJsonFormat}
import org.apache.pekko.http.scaladsl.marshallers.sprayjson.SprayJsonSupport

object CategoryFormat extends SprayJsonSupport with DefaultJsonProtocol{
  implicit val categoryFormat: RootJsonFormat[Category] = jsonFormat2(Category.apply)
  implicit val categoriesFormat: RootJsonFormat[List[Category]] = listFormat(categoryFormat)
}

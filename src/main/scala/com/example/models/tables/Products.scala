package com.example.models.tables

import slick.jdbc.PostgresProfile.Table
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

class Products(tag : Tag) extends Table[com.example.models.Product](tag,"products"){
  def productId = column[Int]("productid",O.AutoInc,O.PrimaryKey)
  def productName = column[String]("productname")
  def price = column[BigDecimal]("price")
  def pictures = column[String]("pictures")
  def description = column[String]("description")
  def remain = column[Int]("stock")
  def minStock = column[Int]("minstock")

  def * = (productId.?,productName,price,pictures,description,remain,minStock).mapTo[com.example.models.Product]
}

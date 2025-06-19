package com.example.models.tables

import com.example.models.CommandProduct
import slick.jdbc.PostgresProfile.Table
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

class CommandProducts(tag : Tag) extends Table[CommandProduct](tag,"command_products"){
  def commandProductid = column[Int]("commandproductid",O.PrimaryKey,O.AutoInc)
  def commandId = column[Int]("commandid")
  def productId = column[Int]("productid")
  def quantity = column[Int]("quantity")
  def price = column[BigDecimal]("price")

  def * = (commandProductid.?,commandId,productId,quantity,price).mapTo[CommandProduct]
}

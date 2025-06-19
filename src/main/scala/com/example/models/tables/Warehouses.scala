package com.example.models.tables

import com.example.models.Warehouse
import slick.jdbc.PostgresProfile.Table
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

class Warehouses(tag:Tag) extends Table[Warehouse](tag,"warehouses"){
  def warehouseId = column[Int]("warehouseid",O.AutoInc,O.PrimaryKey)
  def warehouseName = column[String]("warehousename")
  def maxStock = column[Int]("capacity")
  def location = column[String]("location")

  def * = (warehouseId.?,warehouseName,maxStock,location).mapTo[Warehouse]
}

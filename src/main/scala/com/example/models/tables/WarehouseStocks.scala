package com.example.models.tables

import com.example.models.WarehouseStock
import slick.jdbc.PostgresProfile.Table
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

class WarehouseStocks(tag: Tag) extends Table[WarehouseStock](tag,"warehouses_stocks"){
  def warehouseStockId = column[Int]("warehousestockid",O.AutoInc,O.PrimaryKey)
  def warehouseId = column[Int]("warehouseid")
  def productId = column[Int]("productid")
  def remain = column[Int]("remain")

  def * = (warehouseStockId.?,warehouseId,productId,remain).mapTo[WarehouseStock]
}

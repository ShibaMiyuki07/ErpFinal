package com.example.models

case class WarehouseStock
(
  warehouseStockId : Option[Int],
  productId : Int,
  warehouseId : Int,
  remain : Int
)

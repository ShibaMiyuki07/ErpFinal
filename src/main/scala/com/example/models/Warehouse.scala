package com.example.models

case class Warehouse
(
  warehouseId : Option[Int],
  warehouseName : String,
  maxStock : Int,
  location : String
)

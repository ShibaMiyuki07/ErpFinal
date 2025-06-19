package com.example.models

import java.sql.Timestamp

case class Move
(
  moveId : Option[Int] = None,
  productId : Int,
  warehouseId : Int,
  commandId : Int,
  number : Int,
  moveDate : Timestamp,
  status : String,
  moveType : String
)

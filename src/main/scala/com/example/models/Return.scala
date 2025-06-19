package com.example.models

import java.sql.Timestamp

case class Return
(
  returnId : Option[Int],
  commandId : Int,
  productId : Int,
  quantity : Int,
  reason : String,
  status : String,
  state : String,
  returnDate : Timestamp
)

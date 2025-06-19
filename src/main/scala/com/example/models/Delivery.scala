package com.example.models

import java.sql.Timestamp

case class Delivery
(
  deliveryId : Option[Int] = None,
  commandId : Int,
  status : String,
  deliveryDate: Timestamp,
  deliveryAddress : String
)

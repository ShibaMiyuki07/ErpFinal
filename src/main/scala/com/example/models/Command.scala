package com.example.models

import java.sql.Timestamp

case class Command
(
  commandId : Option[Int] = None,
  clientId : Option[Int],
  providerId : Option[Int],
  commandDate : Timestamp,
  status : String,
  totalPrice : BigDecimal,
  typeCommand : String
)

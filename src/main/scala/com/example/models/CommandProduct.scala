package com.example.models

case class CommandProduct
(
  commandProductId : Option[Int] = None,
  commandId : Int,
  productId : Int,
  quantity : Int,
  totalPrice : BigDecimal
)

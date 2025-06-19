package com.example.models

case class Product
(
  productId: Option[Int],
  productName: String,
  price: BigDecimal,
  pictures : String,
  description : String,
  remain : Int,
  minStock : Int
)

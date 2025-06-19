package com.example.models

case class ProductCategory
(
  productCategoryId : Option[Int] = None,
  productId : Int,
  categoryId : Int
)

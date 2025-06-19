package com.example.models.tables

import com.example.models.ProductCategory
import slick.jdbc.PostgresProfile.Table
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

class ProductCategories(tag:Tag) extends Table[ProductCategory](tag,"product_categories"){
  def productCategoryId = column[Int]("productcategoryid",O.PrimaryKey,O.AutoInc)
  def productId = column[Int]("productid")
  def categoryId = column[Int]("categoryid")
  
  def * = (productCategoryId.?,productId,categoryId).mapTo[ProductCategory]
}

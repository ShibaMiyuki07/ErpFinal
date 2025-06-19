package com.example.models.tables

import com.example.models.Category
import slick.jdbc.PostgresProfile.Table
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

class Categories(tag : Tag) extends Table[Category](tag, "categories"){
  def categoryId = column[Int]("categoryid",O.PrimaryKey,O.AutoInc)
  def categoryName = column[String]("categoryname")

  def * = (categoryId.?,categoryName).mapTo[Category]
}

package com.example.models.tables

import com.example.models.Return
import slick.jdbc.PostgresProfile.Table
import slick.lifted.Tag

import java.sql.Timestamp
import slick.jdbc.PostgresProfile.api._
class Returns (tag:Tag) extends Table[Return](tag,"returns"){
  def returnId = column[Int]("returnid",O.AutoInc,O.AutoInc)
  def commandId = column[Int]("commandid")
  def productId = column[Int]("productid")
  def quantity = column[Int]("quantity")
  def reason = column[String]("reason")
  def status = column[String]("status")
  def state = column[String]("state")
  def returnDate = column[Timestamp]("returndate")

  def * = (returnId.?,commandId,productId,quantity,reason,status,state,returnDate).mapTo[Return]
}

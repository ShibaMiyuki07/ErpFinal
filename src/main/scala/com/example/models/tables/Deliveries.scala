package com.example.models.tables

import com.example.models.Delivery
import slick.jdbc.PostgresProfile.Table
import slick.lifted.Tag
import slick.jdbc.PostgresProfile.api._
import java.sql.Timestamp

class Deliveries(tag : Tag) extends Table[Delivery](tag,"deliveries"){
  def deliveryId = column[Int]("deliveryid",O.AutoInc,O.PrimaryKey)
  def commandId = column[Int]("commandid")
  def status = column[String]("status")
  def deliveryDate = column[Timestamp]("deliverydate")
  def deliveryAddress = column[String]("deliveryaddress")

  def * = (deliveryId.?,commandId,status,deliveryDate,deliveryAddress).mapTo[Delivery]
}

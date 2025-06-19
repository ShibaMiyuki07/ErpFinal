package com.example.models.tables

import com.example.models.Move
import slick.jdbc.PostgresProfile.Table
import slick.lifted.Tag

import java.sql.Timestamp

class Moves(tag : Tag) extends Table[Move](tag,"moves") {
  def moveId = column[Int]("moveid",O.PrimaryKey,O.AutoInc)
  def productId = column[Int]("productid")
  def warehouseId = column[Int]("warehouseid")
  def commandId = column[Int]("commandid")
  def number = column[Int]("quantity")
  def moveDate = column[Timestamp]("movedate")
  def status = column[String]("status")
  def moveType = column[String]("movetype")

  def * = (moveId.?,productId,warehouseId,commandId,number,moveDate,status,moveType).mapTo[Move]
}

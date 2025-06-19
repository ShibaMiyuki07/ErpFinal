package com.example.models.tables

import com.example.models.Command
import slick.jdbc.PostgresProfile.Table
import slick.lifted.{TableQuery, Tag}
import slick.jdbc.PostgresProfile.api._
import java.sql.Timestamp

class Commands (tag : Tag) extends Table[Command](tag,"commands"){
  def commandId = column[Int]("commandid",O.PrimaryKey,O.AutoInc)
  def clientId = column[Option[Int]]("clientid")
  def providerId = column[Option[Int]]("providerid")
  def commandDate = column[Timestamp]("commanddate")
  def status = column[String]("status")
  def totalPrice = column[BigDecimal]("totalamount")
  def typeCommand = column[String]("typecommand")

  //def client = foreignKey("client_fk",clientId,TableQuery[Clients])
  def * = (commandId.?,clientId,providerId,commandDate,status,totalPrice,typeCommand).mapTo[Command]
}

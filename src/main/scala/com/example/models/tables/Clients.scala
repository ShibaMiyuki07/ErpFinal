package com.example.models.tables

import com.example.models.Client
import slick.jdbc.PostgresProfile.Table
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

class Clients(tag : Tag) extends Table[Client](tag,"clients"){
  def clientId = column[Int]("clientid",O.PrimaryKey,O.AutoInc)
  def clientName = column[String]("clientname")
  def phone = column[String]("phone")
  def email = column[String]("email")

  def * = (clientId.?,clientName,phone,email).mapTo[Client]
}

package com.example.models.tables

import com.example.models.Provider
import slick.jdbc.PostgresProfile.Table
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

class Providers (tag : Tag) extends Table[Provider](tag,"providers"){
  def providerId = column[Int]("providerid",O.PrimaryKey,O.AutoInc)
  def providerName = column[String]("providername")
  def location =  column[String]("location")
  def email = column[String]("email")
  def phone = column[String]("phone")

  def * = (providerId.?,providerName,location,email,phone).mapTo[Provider]
}

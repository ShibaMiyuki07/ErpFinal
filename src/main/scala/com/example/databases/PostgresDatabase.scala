package com.example.databases
import slick.jdbc.JdbcBackend.Database
object PostgresDatabase {
  val db = Database.forConfig("my-app.db")
}

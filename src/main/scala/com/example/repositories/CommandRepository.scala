package com.example.repositories

import com.example.models.Command
import slick.jdbc.JdbcBackend.Database
import slick.lifted.TableQuery

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
@Singleton
class CommandRepository @Inject()(db : Database)(implicit ec : ExecutionContext) extends TRepository[Command]{
  private val commands = TableQuery[Commands]

  def create(command : Command) : Future[Command] = {
    val query = (commands.map(c => (c.clientId,c.providerId,c.commandDate,c.status,c.totalPrice,c.typeCommand)) returning commands.map(_.commandId)) += (command.clientId,command.providerId,command.commandDate,command.status,command.totalPrice,command.typeCommand)
    db.run(query).map(id => command.copy(commandId = Some(id)))
  }

  def getAll : Future[Seq[Command]] = {
    db.run(commands.result)
  }
  
  def getById(id : Int) : Future[Option[Command]] = {
    db.run(commands.filter(_.commandId === id).result.headOption)
  }

  def updateCommand(command : Command) : DBIO[Int] = {
    commands.filter(_.commandId === command.commandId).update(command)
  }

  override def update(obj: Command): Future[Int] = {
    db.run(updateCommand(obj))
  }

  override def delete(id: Int): Future[Int] = {
    db.run(commands.filter(_.commandId === id).delete)
  }
}

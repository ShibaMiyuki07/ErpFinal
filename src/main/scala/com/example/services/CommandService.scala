package com.example.services

import com.example.models.Command
import com.example.repositories.{CommandProductRepository, CommandRepository}
import slick.jdbc.JdbcBackend.Database

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CommandService @Inject()(commandRepo : CommandRepository,commandProductRepository: CommandProductRepository,db : Database)(implicit ec : ExecutionContext){
  def createCommand(command : Command) : Future[Command] = {
    commandRepo.create(command)
  }

  def getAllCommands : Future[Seq[Command]] = {
    commandRepo.getAll
  }

  def updateCommand(command: Command,id : Int) : Future[Int] = {
    commandRepo.update(command.copy(commandId = Some(id)))
  }

  def validateCommand(command: Command): Future[Int] = {
    command.commandId.fold(Future.successful(0)) { id =>
      commandProductRepository.getByCommandId(id).flatMap { products =>
        if (products.nonEmpty) {
          val transaction: DBIO[Int] = for {
            result <- commandRepo.updateCommand(command)
          } yield result
          db.run(transaction.transactionally)
        } else {
          Future.successful(1)
        }
      }
    }
  }

  def deleteCommand(id : Int) : Future[Int]={
    commandRepo.delete(id)
  }
}

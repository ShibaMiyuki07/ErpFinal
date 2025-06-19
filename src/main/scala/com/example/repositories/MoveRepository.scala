package com.example.repositories

import com.example.models.Move
import com.example.models.tables.Moves
import com.example.repositories.traits.TRepository
import slick.jdbc.JdbcBackend.Database
import slick.lifted.TableQuery

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import slick.jdbc.PostgresProfile.api._
@Singleton
class MoveRepository @Inject() (db : Database)(implicit ec : ExecutionContext) extends TRepository[Move]{

  private val moves = TableQuery[Moves]

  override def create(move: Move): Future[Move] = {
    val query = (moves.map(m => (m.productId,m.warehouseId,m.commandId,m.number,m.status,m.moveDate,m.moveType)) returning moves.map(_.moveId)) += (move.productId,move.warehouseId,move.commandId,move.number,move.status,move.moveDate,move.moveType)
    db.run(query).map(id => move.copy(moveId = Some(id)))
  }

  override def getAll: Future[Seq[Move]] = {
    db.run(moves.result)
  }

  override def update(move: Move): Future[Int] = {
    db.run(moves.filter(_.moveId === move.moveId).update(move))
  }

  override def delete(id: Int): Future[Int] = {
    db.run(moves.filter(_.moveId === id).delete)
  }
}

package com.example.services

import com.example.models.Move
import com.example.repositories.MoveRepository

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class MoveService @Inject()(moveRepo : MoveRepository)(implicit val ec : ExecutionContext){
  def createMove(move : Move) : Future[Move] = {
    moveRepo.create(move)
  }

  def getAll : Future[Seq[Move]] = {
    moveRepo.getAll
  }

  def update(move: Move,id : Int) : Future[Int] = {
    moveRepo.update(move.copy(moveId = Some(id)))
  }

  def delete(id : Int) : Future[Int] = {
    moveRepo.delete(id)
  }
}

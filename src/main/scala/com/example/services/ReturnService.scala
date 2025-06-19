package com.example.services

import com.example.models.Return
import com.example.repositories.ReturnRepository

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class ReturnService @Inject()(returnRepo : ReturnRepository)(implicit val ec : ExecutionContext){
  def createReturn(ret : Return) : Future[Return] = {
    returnRepo.create(ret)
  }

  def getAll : Future[Seq[Return]] = {
    returnRepo.getAll
  }

  def update(ret: Return, id: Int): Future[Int] = {
    returnRepo.update(ret.copy(returnId = Some(id)))
  }

  def delete(id : Int) : Future[Int] = {
    returnRepo.delete(id)
  }
}

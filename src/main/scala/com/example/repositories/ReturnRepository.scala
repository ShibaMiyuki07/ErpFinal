package com.example.repositories

import com.example.models.Return
import slick.jdbc.JdbcBackend.Database
import slick.lifted.TableQuery

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ReturnRepository @Inject() (db : Database)(implicit ec : ExecutionContext) extends TRepository[Return]{

  private val returns = TableQuery[Returns]

  override def create(obj: Return): Future[Return] = {
    val query = (returns.map(r => (r.commandId,r.productId,r.quantity,r.reason,r.status,r.state,r.returnDate)) returning returns.map(_.returnId)) += (obj.commandId,obj.productId,obj.quantity,obj.reason,obj.status,obj.state,obj.returnDate)
    db.run(query).map(id => obj.copy(returnId = Some(id)))
  }

  override def getAll: Future[Seq[Return]] = {
    db.run(returns.result)
  }

  override def update(obj: Return): Future[Int] = {
    db.run(returns.filter(_.returnId === obj.returnId).update(obj))
  }

  override def delete(id: Int): Future[Int] = {
    db.run(returns.filter(_.returnId === id).delete)
  }
}

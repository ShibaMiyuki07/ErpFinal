package com.example.repositories

import com.example.models.CommandProduct
import slick.jdbc.JdbcBackend.Database
import slick.lifted.TableQuery

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CommandProductRepository @Inject()(db : Database)(implicit ec : ExecutionContext) extends TRepository[CommandProduct]{
  private val commandProducts = TableQuery[CommandProducts]

  def createQuery(commandProduct: CommandProduct) : DBIO[Int] = {
    val query = (commandProducts.map(c => (c.commandId,c.productId,c.quantity,c.price)) returning commandProducts.map(_.commandProductid)) += (commandProduct.commandId,commandProduct.productId,commandProduct.quantity,commandProduct.totalPrice)
    query
  }


  def getByCommandId(id : Int) : Future[Seq[CommandProduct]] = {
    db.run(commandProducts.filter(_.commandId === id).result)
  }

  override def getAll: Future[Seq[CommandProduct]] = {
    db.run(commandProducts.result)
  }

  override def create(obj: CommandProduct): Future[CommandProduct] = {
    db.run(createQuery(obj)).map(id => obj.copy(commandProductId = Some(id)))
  }

  override def update(obj: CommandProduct): Future[Int] = {
    db.run(commandProducts.filter(_.commandProductid === obj.commandProductId).update(obj))
  }

  override def delete(id: Int): Future[Int] = {
    db.run(commandProducts.filter(_.commandProductid === id).delete)
  }
}

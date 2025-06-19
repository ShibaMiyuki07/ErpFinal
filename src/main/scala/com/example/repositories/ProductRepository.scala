package com.example.repositories

import com.example.models
import slick.jdbc.JdbcBackend.Database
import slick.lifted.TableQuery

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProductRepository @Inject(db : Database)(implicit ec : ExecutionContext) extends TRepository[com.example.models.Product]{
  private val products = TableQuery[Products]

  def create(product: com.example.models.Product) : Future[com.example.models.Product] = {
    val query = (products.map(c => (c.productName,c.price,c.pictures,c.description,c.remain,c.minStock)) returning products.map(_.productId)) += (product.productName,product.price,product.pictures,product.description,product.remain,product.minStock)
    db.run(query).map(id => product.copy(productId = Some(id)))
  }
  def getAll : Future[Seq[com.example.models.Product]] = {
    db.run(products.result)
  }

  def getById(id : Int) : Future[Option[com.example.models.Product]] = {
    db.run(products.filter(_.productId === id).result.headOption)
  }
  
  def updateQuery(product: com.example.models.Product) : DBIO[Int] = {
    products.filter(_.productId === product.productId).update(product)
  }

  def batchInsert(productList : List[com.example.models.Product]): Future[Option[Int]] = {
    db.run(products ++= productList)
  }

  override def update(obj: models.Product): Future[Int] = {
    db.run(updateQuery(obj))
  }

  override def delete(id: Int): Future[Int] = {
    db.run(products.filter(_.productId === id).delete)
  }
}

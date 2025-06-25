package com.example.repositories

import com.example.models
import com.example.models.tables.Products
import com.example.repositories.traits.TRepository
import slick.jdbc.JdbcBackend.Database
import slick.lifted.TableQuery

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import slick.jdbc.PostgresProfile.api._

import scala.util.Try

@Singleton
class ProductRepository @Inject()(db : Database)(implicit ec : ExecutionContext) extends TRepository[com.example.models.Product]{
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

  def insertExcel(list : List[Map[String,String]]): Unit = {
    var productList : List[models.Product] = List()
    list.foreach(products =>{
      val product = models.Product(
        productId = None,
        productName = products.getOrElse("productName", ""),
        price = BigDecimal.apply(products.getOrElse("Price", "0")),
        pictures = products.getOrElse("Pictures", ""),
        description = products.getOrElse("Description", ""),
        remain = BigDecimal.apply(products.getOrElse("Remain", "0")).toInt,
        minStock = BigDecimal.apply(products.getOrElse("minStock", "0")).toInt
      )
      productList = productList :+ product
    })

    db.run(products ++= productList)
    println("Executed")
  }


}

package com.example.repositories

import com.example.models.ProductCategory
import com.example.models.tables.ProductCategories
import com.example.repositories.traits.TRepository
import slick.jdbc.JdbcBackend.Database
import slick.lifted.TableQuery

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import slick.jdbc.PostgresProfile.api._
@Singleton
class ProductCategoryRepository @Inject() (db : Database)(implicit ec : ExecutionContext) extends TRepository[ProductCategory]{
  private val productCategories = TableQuery[ProductCategories]


  override def create(productCategory: ProductCategory): Future[ProductCategory] = {
    db.run(productCategories += productCategory).map(id => productCategory)
  }

  override def getAll: Future[Seq[ProductCategory]] = {
    db.run(productCategories.result)
  }

  override def update(obj: ProductCategory): Future[Int] = {
    db.run(productCategories.filter(_.productCategoryId === obj.productCategoryId).update(obj))
  }

  override def delete(id: Int): Future[Int] = {
    db.run(productCategories.filter(_.productCategoryId === id).delete)
  }
}

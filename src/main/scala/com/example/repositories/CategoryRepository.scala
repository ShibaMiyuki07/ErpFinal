package com.example.repositories

import com.example.models.Category
import slick.jdbc.JdbcBackend.Database
import slick.lifted.TableQuery

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CategoryRepository @Inject() (db : Database)(implicit ec : ExecutionContext) extends TRepository[Category]{
  private val categories = TableQuery[Categories]

  override def create(category: Category): Future[Category] = {
    val query = (categories.map(c => (c.categoryName)) returning categories.map(_.categoryId)) += (category.categoryName)
    db.run(query).map(id => category.copy(categoryId = Some(id)))
  }

  override def getAll: Future[Seq[Category]] = {
    db.run(categories.result)
  }

  override def update(obj: Category): Future[Int] = {
    db.run(categories.filter(_.categoryId === obj.categoryId).update(obj))
  }

  override def delete(categoryId : Int): Future[Int] = {
    db.run(categories.filter(_.categoryId === categoryId).delete)
  }
}

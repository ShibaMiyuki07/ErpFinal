package com.example.services

import com.example.models.Category
import com.example.repositories.CategoryRepository

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class CategoryService @Inject()(categoryRepo : CategoryRepository)(implicit ec: ExecutionContext) {

  def createCategory(category : Category) : Future[Category] = {
    categoryRepo.create(category)
  }

  def getAll : Future[Seq[Category]] = {
    categoryRepo.getAll
  }

  def update(category: Category,id : Int) : Future[Int] = {
    categoryRepo.update(category.copy(categoryId = Some(id)))
  }

  def delete(id : Int) : Future[Int] = {
    categoryRepo.delete(id)
  }
}

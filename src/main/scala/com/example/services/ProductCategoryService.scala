package com.example.services

import com.example.models.ProductCategory
import com.example.repositories.ProductCategoryRepository

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProductCategoryService @Inject()(productCategoryRepo : ProductCategoryRepository)(implicit val ec : ExecutionContext){
  def create(productCategory : ProductCategory) : Future[ProductCategory] = {
    productCategoryRepo.create(productCategory)
  }

  def getAll : Future[Seq[ProductCategory]] = {
    productCategoryRepo.getAll
  }

  def update(productCategory: ProductCategory,id : Int) : Future[Int] = {
    productCategoryRepo.update(productCategory)
  }

  def delete(id : Int) : Future[Int] = {
    productCategoryRepo.delete(id)
  }
}

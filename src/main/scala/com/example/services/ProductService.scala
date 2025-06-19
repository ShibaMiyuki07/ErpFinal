package com.example.services

import com.example.repositories.ProductRepository

import javax.inject.{Inject, Singleton}
import scala.collection.mutable.ListBuffer
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProductService @Inject()(productRepo : ProductRepository)(implicit  ec : ExecutionContext) {

  def createProduct(product: com.example.models.Product ) : Future[com.example.models.Product] = {
    productRepo.create(product)
  }
  def getAllProducts : Future[Seq[com.example.models.Product]] = {
    productRepo.getAll
  }

  def update(product : com.example.models.Product,id : Int) : Future[Int] = {
    productRepo.update(product.copy(productId = Some(id)))
  }

  def batchInsert: Future[Option[Int]] = {
    val bufferedSource = io.Source.fromFile("C:/Projet/Classeur1.csv")
    val productsList = new ListBuffer[com.example.models.Product]
    for(line <- bufferedSource.getLines){
      val cols = line.split(";").map(_.trim)
      productsList += com.example.models.Product(None,cols(0),BigDecimal(cols(1)),cols(2),cols(3),cols(4).toInt,cols(5).toInt)
    }
    println(productsList)
    productRepo.batchInsert(productsList.toList)
  }

  def delete(id : Int) : Future[Int] = {
    productRepo.delete(id)
  }
}

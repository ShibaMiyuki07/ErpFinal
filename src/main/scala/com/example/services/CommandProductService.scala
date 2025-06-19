package com.example.services

import com.example.models.{Command, CommandProduct}
import com.example.repositories.{CommandProductRepository, CommandRepository, ProductRepository}
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.PostgresProfile.api._
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class CommandProductService @Inject()(commandProductRepo : CommandProductRepository,commandRepo : CommandRepository,productRepository: ProductRepository,db : Database)(implicit ec : ExecutionContext) {
  def addProductToCommand(commandProduct : CommandProduct): Future[Int] = {
    commandRepo.getById(commandProduct.commandId).flatMap {
      case Some(cmd) => {
        productRepository.getById(commandProduct.productId).flatMap{
          case Some(product) => {
            if(product.remain >= commandProduct.quantity){
              val command = cmd.copy(totalPrice = cmd.totalPrice + (commandProduct.quantity * product.price))
              val product_updated = product.copy(remain = product.remain - commandProduct.quantity)
              handleTransaction(product_updated,command, commandProduct)
            }
            else{
              throw new Exception("There isn't enough product in stock")
            }
          }
          case None =>
            {
              throw new Exception("Product doesn't exist")
            }
        }
      }
      case None => Future.failed(new Exception(""))
    }
  }

  private def handleTransaction(product: com.example.models.Product, command: Command, commandProduct: CommandProduct) : Future[Int] = {
    val transaction = for {
      _ <- productRepository.updateQuery(product)
      _ <- commandRepo.updateCommand(command)
      result <- commandProductRepo.createQuery(commandProduct)
    } yield result
    db.run(transaction.transactionally)
  }
  
  def getAll : Future[Seq[CommandProduct]] = {
    commandProductRepo.getAll
  }

  def update(commandProduct: CommandProduct,id : Int) : Future[Int] = {
    commandProductRepo.update(commandProduct.copy(commandProductId = Some(id)))
  }

  def delete(id : Int) : Future[Int] = {
    commandProductRepo.delete(id)
  }
}

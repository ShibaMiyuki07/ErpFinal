package com.example.services

import com.example.models.WarehouseStock
import com.example.repositories.WarehouseStockRepository

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class WarehouseStockService @Inject()(warehouseStockRepo : WarehouseStockRepository)(implicit val ec : ExecutionContext){
  def create(warehouseStock : WarehouseStock) : Future[WarehouseStock] = {
    warehouseStockRepo.create(warehouseStock)
  }

  def getAll : Future[Seq[WarehouseStock]] = {
    warehouseStockRepo.getAll
  }

  def update(warehouseStock: WarehouseStock,id : Int) : Future[Int] = {
    warehouseStockRepo.update(warehouseStock.copy(warehouseStockId = Some(id)))
  }

  def delete(id : Int) : Future[Int] = {
    warehouseStockRepo.delete(id)
  }
}

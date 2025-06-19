package com.example.services

import com.example.models.Warehouse
import com.example.repositories.WarehouseRepository

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class WarehouseService @Inject()(warehouseRepo : WarehouseRepository)(implicit ec : ExecutionContext){
  def createWarehouse(warehouse: Warehouse) : Future[Warehouse] = {
    warehouseRepo.create(warehouse)
  }

  def getAllWarehouses : Future[Seq[Warehouse]] = {
    warehouseRepo.getAll
  }

  def update(warehouse: Warehouse,id : Int) : Future[Int] = {
    warehouseRepo.update(warehouse.copy(warehouseId = Some(id)))
  }

  def delete(id : Int) : Future[Int] = {
    warehouseRepo.delete(id)
  }
}

package com.example.repositories

import com.example.models.Warehouse
import slick.jdbc.JdbcBackend.Database
import slick.lifted.TableQuery

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class WarehouseRepository @Inject(db : Database)(implicit ec : ExecutionContext) extends TRepository[Warehouse]{
  private val warehouses = TableQuery[Warehouses]

  override def create(warehouse: Warehouse) : Future[Warehouse] = {
    val query = (warehouses.map(w =>(w.warehouseName,w.maxStock,w.location)) returning warehouses.map(_.warehouseId)) += (warehouse.warehouseName,warehouse.maxStock,warehouse.location)
    db.run(query).map(id => warehouse.copy(warehouseId = Some(id)))
  }

  override def getAll: Future[Seq[Warehouse]] = {
    db.run(warehouses.result)
  }

  override def update(obj: Warehouse): Future[Int] = {
    db.run(warehouses.filter(_.warehouseId === obj.warehouseId).update(obj))
  }

  override def delete(id: Int): Future[Int] = {
    db.run(warehouses.filter(_.warehouseId === id).delete)
  }
}

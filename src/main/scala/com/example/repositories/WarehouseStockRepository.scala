package com.example.repositories

import com.example.models.WarehouseStock
import com.example.models.tables.WarehouseStocks
import com.example.repositories.traits.TRepository
import slick.jdbc.JdbcBackend.Database
import slick.lifted.TableQuery

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import slick.jdbc.PostgresProfile.api._
@Singleton
class WarehouseStockRepository @Inject() (db : Database)(implicit ec : ExecutionContext) extends TRepository[WarehouseStock]{

  private val warehousesStocks = TableQuery[WarehouseStocks]

  override def create(warehouseStock: WarehouseStock): Future[WarehouseStock] = {
    val query = (warehousesStocks.map(w => (w.warehouseId,w.productId,w.remain)) returning warehousesStocks.map(_.warehouseStockId)) += (warehouseStock.warehouseId,warehouseStock.productId,warehouseStock.remain)
    db.run(query).map(id => warehouseStock.copy(warehouseStockId = Some(id)))
  }

  override def getAll: Future[Seq[WarehouseStock]] = {
    db.run(warehousesStocks.result)
  }

  override def update(warehouseStock: WarehouseStock): Future[Int] = {
    db.run(warehousesStocks.filter(_.warehouseStockId === warehouseStock.warehouseStockId).update(warehouseStock))
  }

  override def delete(id: Int): Future[Int] = {
    db.run(warehousesStocks.filter(_.warehouseStockId === id).delete)
  }
}

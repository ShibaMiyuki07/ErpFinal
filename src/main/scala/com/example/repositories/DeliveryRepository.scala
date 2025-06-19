package com.example.repositories

import com.example.models.Delivery
import slick.jdbc.JdbcBackend.Database
import slick.lifted.TableQuery

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class DeliveryRepository @Inject() (db : Database)(implicit ec : ExecutionContext) extends TRepository[Delivery]{
  private val deliveries = TableQuery[Deliveries]

  override def create(delivery: Delivery): Future[Delivery] = {
    val query = (deliveries.map(d => (d.commandId,d.status,d.deliveryDate,d.deliveryAddress)) returning deliveries.map(_.deliveryId)) += (delivery.commandId,delivery.status,delivery.deliveryDate,delivery.deliveryAddress)
    db.run(query).map(id => delivery.copy(deliveryId = Some(id)))
  }

  override def getAll: Future[Seq[Delivery]] = {
    db.run(deliveries.result)
  }

  override def update(obj: Delivery): Future[Int] = {
    db.run(deliveries.filter(_.deliveryId === obj.deliveryId).update(obj))
  }

  override def delete(id: Int): Future[Int] = {
    db.run(deliveries.filter(_.deliveryId === id).delete)
  }
}

package com.example.services

import com.example.models.Delivery
import com.example.repositories.DeliveryRepository

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class DeliveryService @Inject()(deliveryRepository: DeliveryRepository)(implicit ec: ExecutionContext){
  def createDelivery(delivery: Delivery) : Future[Delivery] = {
    deliveryRepository.create(delivery)
  }

  def getAll : Future[Seq[Delivery]] = {
    deliveryRepository.getAll
  }

  def update(delivery: Delivery,id : Int) : Future[Int] = {
    deliveryRepository.update(delivery.copy(deliveryId = Some(id)))
  }
  def delete(id : Int) : Future[Int] = {
    deliveryRepository.delete(id)
  }
}

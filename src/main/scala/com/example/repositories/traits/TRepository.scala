package com.example.repositories.traits

import scala.concurrent.Future

trait TRepository[T] {
  def create(obj : T) : Future[T]

  def getAll : Future[Seq[T]]

  def update(obj : T) : Future[Int]

  def delete(id : Int) : Future[Int]
}

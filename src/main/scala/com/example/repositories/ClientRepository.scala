package com.example.repositories

import com.example.models.Client
import com.example.models.tables.Clients
import com.example.repositories.traits.TRepository
import slick.jdbc.JdbcBackend.Database
import slick.lifted.TableQuery

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import slick.jdbc.PostgresProfile.api._
@Singleton
class ClientRepository @Inject()(db : Database)(implicit ec : ExecutionContext) extends TRepository[Client]{
  private val clients = TableQuery[Clients]

  def create(client : Client) : Future[Client] = {
    val query = (clients.map(c => (c.clientName,c.phone,c.email)) returning clients.map(_.clientId)) += (client.clientName,client.phone,client.email)
    db.run(query).map(id => client.copy(clientId = Some(id)))
  }

  def getAll : Future[Seq[Client]] = {
    db.run(clients.result)
  }
  
  def getById(id : Int) : Future[Option[Client]] = {
    db.run(clients.filter(_.clientId === id).result.headOption)
  }

  def update(client: Client): Future[Int] = {
    db.run(clients.filter(_.clientId === client.clientId).update(client))
  }

  override def delete(id: Int): Future[Int] = {
    db.run(clients.filter(_.clientId === id).delete)
  }
}

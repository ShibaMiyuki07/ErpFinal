package com.example.services

import com.example.models.Client
import com.example.repositories.ClientRepository

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class ClientService @Inject() (repo : ClientRepository)(implicit ec : ExecutionContext) {
  def createClient(client : Client) : Future[Client] = {
    repo.create(client)
  }

  def getAllClients: Future[Seq[Client]] = {
    repo.getAll
  }

  def getByIdClient(id : Int) : Future[Option[Client]] = {
    repo.getById(id)
  }

  def update(client: Client,id : Int) : Future[Int] = {
    repo.update(client.copy(clientId = Some(id)))
  }

  def delete(id : Int) : Future[Int] = {
    repo.delete(id)
  }
}

package com.example.services

import com.example.models.Provider
import com.example.repositories.ProviderRepository

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProviderService @Inject()(providerRepository : ProviderRepository)(implicit val ec : ExecutionContext){
  def createProvider(provider: Provider) : Future[Provider] = {
    providerRepository.create(provider)
  }

  def getAll : Future[Seq[Provider]] = {
    providerRepository.getAll
  }

  def update(provider: Provider, id: Int): Future[Int] = {
    providerRepository.update(provider.copy(providerId = Some(id)))
  }

  def delete(id : Int) : Future[Int] = {
    providerRepository.delete(id)
  }
}

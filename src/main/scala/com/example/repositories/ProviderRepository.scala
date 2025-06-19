package com.example.repositories

import com.example.models.Provider
import slick.jdbc.JdbcBackend.Database
import slick.lifted.TableQuery

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProviderRepository @Inject() (db : Database)(implicit ec : ExecutionContext) extends TRepository[Provider]{

  private val providers = TableQuery[Providers]

  override def create(provider: Provider): Future[Provider] = {
    val query = (providers.map(p => (p.providerName,p.location,p.email,p.phone)) returning providers.map(_.providerId)) += (provider.providerName,provider.location,provider.email,provider.phone)
    db.run(query).map(id => provider.copy(providerId = Some(id)))
  }

  override def getAll: Future[Seq[Provider]] = {
    db.run(providers.result)
  }

  override def update(provider: Provider): Future[Int] = {
    db.run(providers.filter(_.providerId === provider.providerId).update(provider))
  }

  override def delete(id: Int): Future[Int] = {
    db.run(providers.filter(_.providerId === id).delete)
  }
}

package com.example.routes

import com.example.databases.PostgresDatabase.db
import com.example.repositories.{CategoryRepository, ClientRepository, CommandProductRepository, CommandRepository, DeliveryRepository, MoveRepository, ProductCategoryRepository, ProductRepository, ProviderRepository, WarehouseRepository, WarehouseStockRepository}
import com.example.routes.modelRoutes.{CategoryRoutes, ClientRoutes, CommandProductRoutes, CommandRoutes, DeliveryRoutes, MoveRoutes, ProductRoutes, ProviderRoutes, WarehouseRoutes}
import com.example.services.{CategoryService, ClientService, CommandProductService, CommandService, DeliveryService, MoveService, ProductService, ProviderService, WarehouseService}
import org.apache.pekko.actor.ActorSystem
import org.apache.pekko.http.scaladsl.server.Route

import scala.concurrent.ExecutionContext

class AllRoutes(implicit val ec : ExecutionContext,val system: ActorSystem) {

  def allControllers : List[Route] = {
    //All repositories
    val categoryRepository = new CategoryRepository(db)
    val clientRepository = new ClientRepository(db)
    val commandRepository = new CommandRepository(db)
    val commandProductRepository = new CommandProductRepository(db)
    val deliveryRepository = new DeliveryRepository(db)
    val moveRepository = new MoveRepository(db)
    val productCategoryRepository = new ProductCategoryRepository(db)
    val productRepository = new ProductRepository(db)
    val providerRepository = new ProviderRepository(db)
    val warehouseRepository = new WarehouseRepository(db)
    val warehouseStockRepository = new WarehouseStockRepository(db)

    //All routes
    val categoryRoutes = new CategoryRoutes(new CategoryService(categoryRepository))
    val clientRoutes = new ClientRoutes(new ClientService(clientRepository))
    val commandRoutes = new CommandRoutes(new CommandService(commandRepository,commandProductRepository,db))
    val commandProductRoutes = new CommandProductRoutes(new CommandProductService(commandProductRepository,commandRepository,productRepository,db))
    val deliveryRoutes = new DeliveryRoutes(new DeliveryService(deliveryRepository))
    val moveRoutes = new MoveRoutes(new MoveService(moveRepository))
    //val productCategoryController = new ProductCategoryController(new ProductCategoryService(productCategoryRepository))
    val productRoutes = new ProductRoutes(new ProductService(productRepository))
    val providerRoutes = new ProviderRoutes(new ProviderService(providerRepository))
    val warehouseRoutes = new WarehouseRoutes(new WarehouseService(warehouseRepository))

    List(
      categoryRoutes.routes,
      clientRoutes.routes,
      commandRoutes.routes,
      commandProductRoutes.routes,
      deliveryRoutes.routes,
      moveRoutes.routes,
      productRoutes.routes,
      providerRoutes.routes,
      warehouseRoutes.routes
    )
  }
}

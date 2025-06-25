package com.example.routes

import com.example.databases.PostgresDatabase.db
import com.example.repositories.{CategoryRepository, ClientRepository, CommandProductRepository, CommandRepository, DeliveryRepository, MoveRepository, ProductCategoryRepository, ProductRepository, ProviderRepository, ReturnRepository, WarehouseRepository, WarehouseStockRepository}
import com.example.routes.modelRoutes.{CategoryRoutes, ClientRoutes, CommandProductRoutes, CommandRoutes, DeliveryRoutes, MoveRoutes, ProductCategoryRoutes, ProductRoutes, ProviderRoutes, ReturnRoutes, WarehouseRoutes}
import com.example.routes.utilsRoutes.ExcelRoutes
import com.example.services.{CategoryService, ClientService, CommandProductService, CommandService, DeliveryService, MoveService, ProductCategoryService, ProductService, ProviderService, ReturnService, WarehouseService}
import org.apache.pekko.actor.ActorSystem
import org.apache.pekko.http.scaladsl.server.Route

import scala.concurrent.ExecutionContext

class AllRoutes(implicit val ec : ExecutionContext,implicit val system : ActorSystem) {


  def getAllRoutes : List[Route] = {
    val categoryRepository = new CategoryRepository(db)
    val clientRepository = new ClientRepository(db)
    val commandProductRepository = new CommandProductRepository(db)
    val commandRepository = new CommandRepository(db)
    val deliveryRepository = new DeliveryRepository(db)
    val moveRepository = new MoveRepository(db)
    val productCategoryRepository = new ProductCategoryRepository(db)
    val productRepository = new ProductRepository(db)
    val providerRepository = new ProviderRepository(db)
    val returnRepository = new ReturnRepository(db)
    val warehouseRepository = new WarehouseRepository(db)
    val warehouseStockRepository = new WarehouseStockRepository(db)


    val categoryRoutes = new CategoryRoutes(new CategoryService(categoryRepository))
    val clientRoutes = new ClientRoutes(new ClientService(clientRepository))
    val commandProductRoutes = new CommandProductRoutes(new CommandProductService(commandProductRepo = commandProductRepository, commandRepo = commandRepository, productRepository = productRepository, db = db))
    val commandRoutes = new CommandRoutes(new CommandService(commandRepo = commandRepository, commandProductRepository = commandProductRepository, db = db))
    val deliveryRoutes = new DeliveryRoutes(new DeliveryService(deliveryRepository))
    val moveRoutes = new MoveRoutes(new MoveService(moveRepository))
    //val productCategoryRoutes = new ProductCategoryRoutes(new ProductCategoryService(productCategoryRepository))
    val productRoutes = new ProductRoutes(new ProductService(productRepository))
    val providerRoutes = new ProviderRoutes(new ProviderService(providerRepository))
    //val returnRoutes = new ReturnRoutes(new ReturnService(returnRepository))
    val warehouseRoutes = new WarehouseRoutes(new WarehouseService(warehouseRepository))

    val excelRoutes = new ExcelRoutes(productRepository)

    List(
      categoryRoutes.routes,
      clientRoutes.routes,
      commandProductRoutes.routes,
      commandRoutes.routes,
      deliveryRoutes.routes,
      moveRoutes.routes,
      productRoutes.routes,
      providerRoutes.routes,
      warehouseRoutes.routes,
      excelRoutes.routes
    )
  }
}

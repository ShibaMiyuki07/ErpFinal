package com.example.routes.modelRoutes

import com.example.models.Warehouse
import com.example.services.WarehouseService
import org.apache.pekko.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import org.apache.pekko.http.scaladsl.model.StatusCodes.{BadRequest, InternalServerError, NoContent}
import org.apache.pekko.http.scaladsl.server.Directives._
import org.apache.pekko.http.scaladsl.server.Route
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

class WarehouseRoutes(warehouseService: WarehouseService)(implicit ec : ExecutionContext) extends SprayJsonSupport with DefaultJsonProtocol{
  implicit val warehouseFormat : RootJsonFormat[Warehouse] = jsonFormat4(Warehouse.apply)
  implicit val warehousesFormat : RootJsonFormat[List[Warehouse]] = listFormat(warehouseFormat)

  val routes : Route = pathPrefix("warehouses"){
    concat(
      pathEnd{
        concat(
          get{
            complete(warehouseService.getAllWarehouses.map(_.toList))
          },
          post{
            entity(as[Warehouse]){
              warehouse => onComplete(warehouseService.createWarehouse(warehouse)){
                case Success(value) => complete(value)
                case Failure(exception) => complete(InternalServerError,exception)
              }
            }
          }
        )
      },
      path(IntNumber){
        id => concat(
          put{
            entity(as[Warehouse]){
              warehouse => onSuccess(warehouseService.update(warehouse, id)){
                rowsUpdated => if(rowsUpdated > 0){
                  complete(NoContent)
                }
                else
                  complete(InternalServerError,"")
              }
            }
          },
          delete{
            onComplete(warehouseService.delete(id)){
              case Success(value) => complete(NoContent)
              case Failure(exception) => complete(BadRequest,exception.getMessage)
            }
          }
        )
      }
    )
  }
}

package com.example.routes.modelRoutes

import com.example.services.ProductService
import org.apache.pekko.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import org.apache.pekko.http.scaladsl.model.StatusCodes.{BadRequest, InternalServerError, NoContent, OK}
import org.apache.pekko.http.scaladsl.server.Directives._
import org.apache.pekko.http.scaladsl.server.Route
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

class ProductRoutes(productService: ProductService)(implicit ec : ExecutionContext) extends SprayJsonSupport with DefaultJsonProtocol{
  implicit val productFormat : RootJsonFormat[com.example.models.Product] = jsonFormat7(com.example.models.Product.apply)
  implicit val productsFormat : RootJsonFormat[List[com.example.models.Product]] = listFormat(productFormat)
  
  val routes : Route = pathPrefix("products"){
    concat(
      pathEnd{
        concat(
          get{
            complete(productService.getAllProducts.map(_.toList))
          },
          post{
            entity(as[com.example.models.Product]){
              product => onComplete(productService.createProduct(product)){
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
            entity(as[com.example.models.Product]){
              product => onSuccess(productService.update(product,id)){
                rowsUpdated => if(rowsUpdated > 0){
                  complete(OK,s"Product with id $id was updated successfully")
                }
                else
                  complete(InternalServerError,"Update error")
              }
            }
          },
          delete{
            onComplete(productService.delete(id)){
              case Success(value) => complete(NoContent)
              case Failure(exception) => complete(BadRequest,exception.getMessage)
            }
          }
        )
      },
      pathPrefix("batch"){
        get{
          productService.batchInsert
          complete("OK")
        }

      }
    )
  }
}

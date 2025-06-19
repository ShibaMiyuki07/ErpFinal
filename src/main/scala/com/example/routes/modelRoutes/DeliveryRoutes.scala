package com.example.routes.modelRoutes

import com.example.models.Delivery
import com.example.services.DeliveryService
import org.apache.pekko.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import org.apache.pekko.http.scaladsl.server.Directives._
import org.apache.pekko.http.scaladsl.server.Route
import spray.json.{DefaultJsonProtocol, RootJsonFormat}
import com.example.formats.TimestampFormat._
import org.apache.pekko.http.scaladsl.model.StatusCodes.{BadRequest, InternalServerError, NoContent}

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

class DeliveryRoutes(deliveryService: DeliveryService)(implicit ec: ExecutionContext) extends SprayJsonSupport with DefaultJsonProtocol{
  implicit val deliveryFormat : RootJsonFormat[Delivery] = jsonFormat5(Delivery.apply)
  implicit val deliveriesFormat : RootJsonFormat[List[Delivery]] = listFormat(deliveryFormat)

  val routes : Route = pathPrefix("deliveries"){
    concat(
      pathEnd{
        concat(
          get{
            complete(deliveryService.getAll.map(_.toList))
          },
          post{
            entity(as[Delivery]){
              delivery => onComplete(deliveryService.createDelivery(delivery)){
                case Success(value) => complete(value)
                case Failure(exception) => complete(InternalServerError,exception.getMessage)
              }
            }
          }
        )
      },
      path(IntNumber){
        id => concat(
          put{
            entity(as[Delivery]){
              delivery => onSuccess(deliveryService.update(delivery,id)){
                rowsUpdated => if(rowsUpdated > 0){
                  complete(NoContent,s"Delivery with id $id was updated successfully")
                }
                else
                  complete(InternalServerError,"Update error")
              }
            }
          },
          delete{
            onComplete(deliveryService.delete(id)){
              case Success(value) => complete(NoContent)
              case Failure(exception) => complete(BadRequest,exception.getMessage)
            }
          }
        )
      }
    )
  }
}

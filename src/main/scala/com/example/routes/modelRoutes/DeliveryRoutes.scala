package com.example.routes.modelRoutes

import com.example.models.Delivery
import spray.json.RootJsonFormat

import scala.concurrent.ExecutionContext

class DeliveryRoutes(deliveryService: DeliveryService)(implicit ec: ExecutionContext) extends SprayJsonSupport{
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

package com.example.routes.modelRoutes

import com.example.models.Provider
import com.example.services.ProviderService
import org.apache.pekko.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import org.apache.pekko.http.scaladsl.model.StatusCodes.{BadRequest, InternalServerError, NoContent, OK}
import org.apache.pekko.http.scaladsl.server.Directives._
import org.apache.pekko.http.scaladsl.server.Route
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

class ProviderRoutes(providerService : ProviderService)(implicit val ec : ExecutionContext) extends SprayJsonSupport with DefaultJsonProtocol{
  implicit val providerFormat : RootJsonFormat[Provider] = jsonFormat5(Provider.apply)
  implicit val providersFormat : RootJsonFormat[List[Provider]] = listFormat(providerFormat)

  val routes : Route = pathPrefix("providers"){
    concat(
      pathEnd{
        concat(
          get{
            complete(providerService.getAll.map(_.toList))
          },
          post{
            entity(as[Provider]){
              provider => onComplete(providerService.createProvider(provider)){
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
            entity(as[Provider]){
              provider => onSuccess(providerService.update(provider, id)){
                rowsUpdated => if(rowsUpdated > 0){
                  complete(OK,s"Provider with id $id was updated successfully")
                }
                else
                  complete(InternalServerError,"Update error")
              }
            }
          },
          delete{
            onComplete(providerService.delete(id)){
              case Success(value) => complete(NoContent)
              case Failure(exception) => complete(BadRequest,exception.getMessage)
            }
          }
        )
      }
    )
  }

}

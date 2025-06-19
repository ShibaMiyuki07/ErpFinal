package com.example.routes.modelRoutes

import com.example.models.CommandProduct
import com.example.services.CommandProductService
import org.apache.pekko.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import org.apache.pekko.http.scaladsl.model.StatusCodes.{BadRequest, InternalServerError, NoContent, OK}
import org.apache.pekko.http.scaladsl.server.Directives._
import org.apache.pekko.http.scaladsl.server.Route
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

class CommandProductRoutes(commandProductService: CommandProductService)(implicit ec : ExecutionContext) extends SprayJsonSupport with DefaultJsonProtocol{
  implicit val commandProductFormat : RootJsonFormat[CommandProduct] = jsonFormat5(CommandProduct.apply)
  implicit val commandProductsFormat : RootJsonFormat[List[CommandProduct]] = listFormat(commandProductFormat)

  val routes : Route = pathPrefix("commandProducts"){
    concat(
      /*
      * Routes("/")
      * */
      pathEnd{
        concat(
          get{
            complete(commandProductService.getAll.map(_.toList))
          },
          post{
            entity(as[CommandProduct]){
              commandProduct =>
                onComplete(commandProductService.addProductToCommand(commandProduct)){
                  case Success(_) => complete(commandProduct)
                  case Failure(exception) => complete(InternalServerError,exception.getMessage)
                }
            }
          }
        )
      },
      /*
      * Routes("/:id")
      * */
      path(IntNumber){
        id => concat(
          put{
            entity(as[CommandProduct]){
              commandProduct => onSuccess(commandProductService.update(commandProduct, id)){
                rowsUpdated => if(rowsUpdated > 0){
                  complete(OK,s"product in command with id $id was updated")
                }
                else
                  complete(InternalServerError,"Update error")
              }
            }
          },
          delete{
            onComplete(commandProductService.delete(id)){
              case Success(value) => complete(NoContent)
              case Failure(exception) => complete(BadRequest,exception.getMessage)
            }
          }
        )
      }
    )
  }
}

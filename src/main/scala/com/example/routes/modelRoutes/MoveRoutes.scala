package com.example.routes.modelRoutes

import com.example.models.Move
import com.example.services.MoveService
import org.apache.pekko.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import org.apache.pekko.http.scaladsl.model.StatusCodes.{BadRequest, Created, InternalServerError, NoContent, OK}
import org.apache.pekko.http.scaladsl.server.Directives._
import org.apache.pekko.http.scaladsl.server.Route
import spray.json.{DefaultJsonProtocol, RootJsonFormat}
import com.example.formats.TimestampFormat._
import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

class MoveRoutes(moveService: MoveService)(implicit val ec : ExecutionContext) extends SprayJsonSupport with DefaultJsonProtocol{
  implicit val moveFormat : RootJsonFormat[Move] = jsonFormat8(Move.apply)
  implicit val movesFormat : RootJsonFormat[List[Move]] = listFormat(moveFormat)

  val routes : Route = pathPrefix("moves"){
    concat(
      pathEnd{
        concat(
          get{
            complete(moveService.getAll.map(_.toList))
          },
          post{
            entity(as[Move]){
              move => onComplete(moveService.createMove(move)){
                case Success(value) => complete(Created,value)
                case Failure(exception) => complete(InternalServerError,exception.getMessage)
              }
            }
          }
        )
      },
      path(IntNumber){
        id => concat(
          put{
            entity(as[Move]){
              move => onSuccess(moveService.update(move,id)){
                rowsUpdated => if(rowsUpdated > 0){
                  complete(OK,s"Move with id $id was updated successfully")
                }
                else
                  complete(InternalServerError,"Update error")
              }
            }
          },
          delete{
            onComplete(moveService.delete(id)){
              case Success(value) => complete(NoContent)
              case Failure(exception) => complete(BadRequest,exception.getMessage)
            }
          }
        )
      }
    )
  }
}

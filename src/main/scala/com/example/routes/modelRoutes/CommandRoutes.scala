package com.example.routes.modelRoutes

import com.example.models.Command
import com.example.services.CommandService
import org.apache.pekko.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import org.apache.pekko.http.scaladsl.server.Directives._
import org.apache.pekko.http.scaladsl.server.Route
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}
import com.example.formats.TimestampFormat._

class CommandRoutes(commandService : CommandService)(implicit ec : ExecutionContext) extends SprayJsonSupport with DefaultJsonProtocol{

  implicit val commandFormat : RootJsonFormat[Command] = jsonFormat7(Command.apply)
  implicit val commandsFormat : RootJsonFormat[List[Command]] = listFormat(commandFormat)

  val routes :Route = pathPrefix("commands"){
    concat(
      /*
      * Routes("/")
      * */
      pathEnd{
        concat(
          get{
            complete(commandService.getAllCommands.map(_.toList))
          },
          post{
            entity(as[Command]){
              command => complete(Created,commandService.createCommand(command))
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
            entity(as[Command]){
              command => onSuccess(commandService.updateCommand(command,id)){
                rowsUpdated => if(rowsUpdated > 0){
                  complete(OK,s"Command with id $id was updated successfully")
                }
                else
                  complete(InternalServerError,"")
              }
            }
          },
          delete{
            onComplete(commandService.deleteCommand(id)){
              case Success(value) => complete(NoContent)
              case Failure(exception) => complete(BadRequest,exception.getMessage)
            }
          }
        )
      },
      /*
      * Routes("/validate")
      * */
      pathPrefix("validate"){
        concat(
          post{
            entity(as[Command]){
              command => onSuccess(commandService.validateCommand(command)){
                id => complete(id.toString)
              }
            }
          }
        )
      }
    )
  }
}

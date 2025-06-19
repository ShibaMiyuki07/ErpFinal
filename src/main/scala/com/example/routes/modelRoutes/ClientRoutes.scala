package com.example.routes.modelRoutes

import com.example.formats.ClientFormat.clientFormat
import com.example.models.Client
import com.example.services.ClientService
import org.apache.pekko.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import org.apache.pekko.http.scaladsl.model.StatusCodes.{BadRequest, Created, InternalServerError, NoContent, OK}
import org.apache.pekko.http.scaladsl.server.Directives._
import org.apache.pekko.http.scaladsl.server.Route

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}
import com.example.formats.ClientFormat._

class ClientRoutes(clientService : ClientService)(implicit ec : ExecutionContext) extends SprayJsonSupport{
  

  val routes: Route = pathPrefix("clients") {
    concat(
        /*
        *   Routes("/")
        * */
        pathEnd {
          concat(
            //GET
            get{
              onComplete(clientService.getAllClients.map(_.toList)){
                case Success(value) => complete(OK,value)
                case Failure(ex) => complete(InternalServerError,ex)
              }
            },
            //POST
            post {
              entity(as[Client]) { client =>
                complete(Created,clientService.createClient(client))
              }
            }
          )
        },
        /*
        *   Routes("/:id")
        * */
        path(IntNumber){
          id =>
            //GET
            concat(
              get{
                rejectEmptyResponse{
                  complete(clientService.getByIdClient(id))
                }
              },
              //PUT
              put{
                entity(as[Client]){
                  client => onSuccess(clientService.update(client,id)){
                    rowsUpdated => if(rowsUpdated > 0){
                      complete(OK,s"Client with ID $id updated successfully.")
                    }
                    else
                      complete(InternalServerError,"Update error")
                  }
                }
              },
              delete{
                onComplete(clientService.delete(id)){
                  case Success(value) => complete(NoContent)
                  case Failure(exception) => complete(BadRequest,exception.getMessage)
                }
              }
            )
        }
    )
  }
}



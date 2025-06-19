package com.example.routes.modelRoutes

import com.example.models.Category
import com.example.services.CategoryService
import org.apache.pekko.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import org.apache.pekko.http.scaladsl.model.StatusCodes.{Created, InternalServerError, NoContent, OK}
import org.apache.pekko.http.scaladsl.server.Directives._
import org.apache.pekko.http.scaladsl.server.Route
import com.example.formats.CategoryFormat._

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

class CategoryRoutes(categoryService: CategoryService)(implicit val ec : ExecutionContext) extends SprayJsonSupport{

  val routes  : Route = pathPrefix("categories"){
    concat(
      pathEnd{
        /*
        *   Routes("/")
        * */
        concat(
          get{
            onComplete(categoryService.getAll.map(_.toList)){
              case Success(value) => complete(OK,value)
              case Failure(exception) => complete(InternalServerError,exception.getMessage)
            }
          },
          post{
            entity(as[Category]){
              category => onComplete(categoryService.createCategory(category)){
                case Success(value) => complete(Created,value)
                case Failure(exception) => complete(InternalServerError,exception.getMessage)
              }
            }
          }
        )
      },
      /*
      *   /:id
      * */
      path(IntNumber){
        id => concat(
            //PUT
            put{
              entity(as[Category]){
                category => onSuccess(categoryService.update(category,id)){
                  rowsUpdated => if(rowsUpdated > 0){
                    complete(OK,s"Category with $id was updated")
                  }
                  else
                    complete(InternalServerError,"Update error")
                }
              }
            },
          delete{
              onComplete(categoryService.delete(id)){
                case Success(value) => complete(NoContent)
                case Failure(exception) => complete(InternalServerError,exception.getMessage)
              }
            }
        )
      }
    )
  }
}

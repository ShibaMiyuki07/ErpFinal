package com.example.routes.utilsRoutes

import com.example.repositories.ProductRepository
import com.example.routes.CorsSupport._
import com.example.utils.ExcelReader
import org.apache.pekko.actor.ActorSystem
import org.apache.pekko.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import org.apache.pekko.http.scaladsl.server.Route
import org.apache.pekko.stream.Materializer
import org.apache.pekko.stream.scaladsl.FileIO
import spray.json._
import spray.json.DefaultJsonProtocol._

import java.nio.file.{Files, Paths, StandardOpenOption}

class ExcelRoutes(productRepository: ProductRepository) (implicit val system : ActorSystem) {
  private val uploadDir = Paths.get("uploads")
  private implicit val mat: Materializer = Materializer(system)
  if (!Files.exists(uploadDir)) Files.createDirectories(uploadDir)

  val routes : Route = pathPrefix("excel"){
    concat(
      post{
        fileUpload("upload"){
          case(fileInfo,fileStream) =>{
            val destPath = uploadDir.resolve(fileInfo.fileName)
            val sink = FileIO.toPath(destPath, Set(StandardOpenOption.CREATE, StandardOpenOption.WRITE))
            val writeResult = fileStream.runWith(sink)
            onSuccess(writeResult) { result =>
              if (result.wasSuccessful) {
                // Serve the uploaded file back as a download
                val jsonData = ExcelReader.readExcelAsJson(destPath.toFile)
                productRepository.insertExcel(jsonData)
                complete(HttpEntity(ContentTypes.`application/json`, jsonData.toJson.prettyPrint))
              } else {
                complete(StatusCodes.InternalServerError, "Failed to upload and process Excel file.")
              }
            }
          }
        }
      }
    )
  }
}

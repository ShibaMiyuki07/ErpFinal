package com.example.routes

import org.apache.pekko.http.scaladsl.model.headers._
import org.apache.pekko.http.scaladsl.server.{Directive0, Directives, Route}
import org.apache.pekko.http.scaladsl.model.HttpMethods._
import org.apache.pekko.http.scaladsl.model._

object CorsSupport extends Directives {

  private val ipOrigin = HttpOrigin("http://192.168.1.153")
  private val allowedOrigin = HttpOriginRange.*
  private val allowedHeaders = List("Origin", "X-Requested-With", "Content-Type", "Accept", "Authorization")
  private val allowedMethods = List(GET, POST, PUT, DELETE, OPTIONS)

  private def addAccessControlHeaders: Directive0 = {
    respondWithHeaders(
      `Access-Control-Allow-Origin`(allowedOrigin),
      `Access-Control-Allow-Headers`(allowedHeaders),
      `Access-Control-Allow-Methods`(allowedMethods)
    )
  }

  def corsHandler(route: Route): Route = addAccessControlHeaders {
    options {complete(HttpResponse(StatusCodes.OK))} ~ route
  }
}

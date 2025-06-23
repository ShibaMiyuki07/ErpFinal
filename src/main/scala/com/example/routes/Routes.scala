package com.example.routes

import org.apache.pekko.http.cors.javadsl.settings.CorsSettings
import org.apache.pekko.http.cors.scaladsl.CorsDirectives.cors
import org.apache.pekko.http.scaladsl.server.{Directives, Route}

class Routes(routes : List[Route]) {
  def getRoutes : Route = cors(){
    Directives.concat(routes: _*)
  }
}

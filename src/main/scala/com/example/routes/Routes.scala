package com.example.routes

import org.apache.pekko.http.scaladsl.server.{Directives, Route}

class Routes(routes : List[Route]) {
  def getRoutes : Route =CorsSupport.corsHandler {
    Directives.concat(routes: _*)
  }
}

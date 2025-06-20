package com.example

import com.example.routes.{AllRoutes, Routes}
import org.apache.pekko
import org.apache.pekko.actor
import pekko.actor.typed.ActorSystem
import pekko.actor.typed.scaladsl.Behaviors
import pekko.http.scaladsl.Http
import pekko.http.scaladsl.server.Route

import java.util.concurrent.Executors
import scala.concurrent.ExecutionContext
import scala.util.Failure
import scala.util.Success

//#main-class
object QuickstartApp {
  //#start-http-server
  private def startHttpServer(routes: Route)(implicit system: ActorSystem[_]): Unit = {
    // Pekko HTTP still needs a classic ActorSystem to start
    import system.executionContext

    val futureBinding = Http().newServerAt("localhost", 8080).bind(routes)
    futureBinding.onComplete {
      case Success(binding) =>
        val address = binding.localAddress
        system.log.info("Server online at http://{}:{}/", address.getHostString, address.getPort)
      case Failure(ex) =>
        system.log.error("Failed to bind HTTP endpoint, terminating system", ex)
        system.terminate()
    }
  }
  //#start-http-server
  def main(args: Array[String]): Unit = {
    val dbEc = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(4))

    implicit val ec: ExecutionContext = dbEc
    //#server-bootstrapping
    val rootBehavior = Behaviors.setup[Nothing] { context =>
      implicit val system : pekko.actor.ActorSystem = pekko.actor.ActorSystem("")
      startHttpServer(new Routes(new AllRoutes().getAllRoutes).getRoutes)(context.system)

      Behaviors.empty
    }
    val system = ActorSystem[Nothing](rootBehavior, "HelloPekkoHttpServer")
    //#server-bootstrapping
  }
}
//#main-class

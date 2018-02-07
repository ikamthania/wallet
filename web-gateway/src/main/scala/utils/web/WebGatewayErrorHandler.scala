package utils.web

import org.slf4j.LoggerFactory
import play.api._
import play.api.http.DefaultHttpErrorHandler
import play.api.mvc.Results._
import play.api.mvc._
import play.api.routing.Router
import play.core.SourceMapper

import scala.concurrent.Future

/**
 * Created by shubham.k on 03-01-2017.
 */
class WebGatewayErrorHandler(
    env: play.api.Environment, config: Configuration,
    sourceMapper: Option[SourceMapper], router: Option[Router]
) extends DefaultHttpErrorHandler(env, config, sourceMapper, router) {

  private val log = LoggerFactory.getLogger(classOf[WebGatewayErrorHandler])

  override def onClientError(request: RequestHeader, statusCode: Int, message: String) = {
    log.debug("Client error....")
    Future.successful(
      Status(statusCode)("A client error occurred: " + message)
    )
  }

  override def onServerError(request: RequestHeader, exception: Throwable) = {
    log.error("Server error " + exception + "for uri" + request.uri)
    log.error("Server error", exception)
    Future.successful(
      InternalServerError("A server error occurred: " + exception)
    )
  }
}

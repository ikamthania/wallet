package helpers

import controllers.api.v1.MainController
import play.api.mvc.RequestHeader
import utils.AppLogger

/**
 * Created by shubham.k on 14-03-2017.
 */
trait OriginCheckHelper extends AppLogger {
  this: MainController =>

  /**
   * Checks that the WebSocket comes from the same origin.  This is necessary to protect
   * against Cross-Site WebSocket Hijacking as WebSocket does not implement Same Origin Policy.
   *
   * See https://tools.ietf.org/html/rfc6455#section-1.3 and
   * http://blog.dewhurstsecurity.com/2013/08/30/security-testing-html5-websockets.html
   */
  def sameOriginCheck(rh: RequestHeader): Boolean = {
    rh.headers.get("Origin") match {
      case Some(originValue) if originMatches(originValue) =>
        log.debug(s"originCheck: originValue = $originValue")
        true

      case Some(badOrigin) =>
        log.error(s"originCheck: rejecting request because Origin header value ${badOrigin} is not in the same origin")
        false

      case None =>
        log.error("originCheck: rejecting request because no Origin header found")
        false
    }
  }

  /**
   * Returns true if the value of the Origin header contains an acceptable value.
   */
  def originMatches(origin: String): Boolean = {
    // todo read from the application conf
    // something similar to
    // val origin: Set[String] = configuration.underlying.as[Set[String]]("http.filters.origin")

    origin.contains("localhost:9000")
  }

}

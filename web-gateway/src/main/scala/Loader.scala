

import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.api.{ ServiceAcl, ServiceInfo }
import com.lightbend.lagom.scaladsl.client.{ ConfigurationServiceLocatorComponents, LagomServiceClientComponents }
//import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents

import com.livelygig.product.content.api.WalletService
import controllers.api.v1.wallet._
import play.api.{ ApplicationLoader, BuiltInComponentsFromContext, LoggerConfigurator, Mode }
//import controllers.api.v1._

import com.softwaremill.macwire.wire
import controllers.api.v1.wallet.auth.MobileWalletController
import controllers.{ Assets, ViewController }
import play.api.ApplicationLoader.Context
import play.api.http.{ HttpErrorHandler, HttpRequestHandler }
import play.api.mvc.EssentialFilter
import play.filters.gzip.GzipFilterComponents

import scala.collection.immutable
import scala.concurrent.ExecutionContext
//import play.filters.headers.SecurityHeadersComponents
import utils.web.{ Filters, RequestHandler, WebGatewayErrorHandler }
//import utils.{AppLogger, ServicesHolder}
import router.Routes

abstract class WebGateway(context: Context) extends BuiltInComponentsFromContext(context)
  with LagomConfigComponent
  with LagomServiceClientComponents
  with GzipFilterComponents
  with AssetsComponents
  with AhcWSComponents with I18nComponents {
  LoggerConfigurator(context.environment.classLoader).foreach {
    _.configure(context.environment)
  }

  override lazy val serviceInfo: ServiceInfo = ServiceInfo(
    "web-gateway",
    Map(
      "web-gateway" -> immutable.Seq(ServiceAcl.forPathRegex("(?!/api/).*"))))

  override implicit lazy val executionContext: ExecutionContext = actorSystem.dispatcher
  lazy val routerOption = None
  override lazy val router = {
    // split route
    lazy val apiRoute: api.v1.Routes = {
      val prefix = "/v1"
      wire[api.v1.Routes]
    }
    val prefix = "/"
    wire[Routes]
  }

  // assets
  override lazy val assets = wire[Assets]
  lazy val webjarAssets: WebJarsUtil = wire[WebJarsUtil]
  lazy val webjarAsset: WebJarAssets = wire[WebJarAssets]

  // services
  lazy val walletService = serviceClient.implement[WalletService]

  // controllers
  lazy val viewController: ViewController = wire[ViewController]
  lazy val walletController: WalletController = wire[WalletController]

  // For Mobile
  lazy val mobileWalletController: MobileWalletController = wire[MobileWalletController]
  lazy val mobileApiWalletController: MobileApiWalletController = wire[MobileApiWalletController]

  override lazy val httpRequestHandler: HttpRequestHandler = wire[RequestHandler]
  override lazy val httpErrorHandler: HttpErrorHandler = wire[WebGatewayErrorHandler]
  lazy val filtersWire = wire[Filters]
  override lazy val httpFilters: Seq[EssentialFilter] = filtersWire.filters
}

class WebGatewayLoader extends ApplicationLoader with AppLogger {
  log.info(s"Web gateway is loading.")
  override def load(context: Context) = context.environment.mode match {
    case Mode.Dev =>
      new WebGateway(context) with LagomDevModeComponents {}.application
    /* new WebGateway(context) {
        override def serviceLocator = NoServiceLocator
      }.application*/

    case _ =>
      (new WebGateway(context) with ConfigurationServiceLocatorComponents).application
  }
}


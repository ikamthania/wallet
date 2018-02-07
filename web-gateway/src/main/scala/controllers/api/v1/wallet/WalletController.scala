package controllers.api.v1.wallet

import com.livelygig.product.content.api.WalletService
import net.ceedubs.ficus.Ficus._
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import play.api.{Configuration, Environment, Mode}
import utils.Mocker

import scala.concurrent.{ExecutionContext, Future}

/**
 * Created by shubham.k on 27-02-2017.
 */
class WalletController(
    walletService: WalletService, configuration: Configuration,
    environment: Environment
)(implicit val ec: ExecutionContext) extends Controller with Mocker {

  override val mockdataLocation: String = {
    if (environment.mode == Mode.Dev) {
      "web-gateway/src/main/scala/mockdata"
    } else {
      //s"${System.getProperty("user.home")}/work/livelygig/mockdata"
      "/home/ubuntu/work/livelygig/mockdata"
    }
  }
  val i18ndataLocation: String = {
    if (environment.mode == Mode.Dev) {
      "web-gateway/src/main/scala/i18n/wallet/"
    } else {
      //s"${System.getProperty("user.home")}/work/livelygig/mockdata"
      "/home/ubuntu/work/livelygig/staging/web-gateway/src/main/scala/i18n/wallet/"
    }
  }

  private val isMock: Boolean = configuration.underlying.as[Boolean]("appsettings.ismock")

  def language(lang: String) = Action.async { implicit request =>
    // val path = environment.getFile(s"/src/main/scala/i18n/wallet/${lang}.json").getAbsolutePath
    val mockdata =
      scala
        .io
        .Source.fromFile(s"${i18ndataLocation + lang}.json", "utf-8").mkString
    Future(Ok(mockdata.toString))
  }

  def getNetworkInfo() = Action.async { implicit request =>
    walletService.getETHNetConnected()
      .invoke()
      .map(e => Ok(e))
  }

  def getMarketPrice() = Action.async { implicit request =>
    val currList = getLiveMarketPrices()
    Future(
      Ok(
        Json.toJson(currList)
      )
    )
  }

}


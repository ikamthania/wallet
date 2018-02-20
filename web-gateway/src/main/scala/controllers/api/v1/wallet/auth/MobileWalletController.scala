package controllers.api.v1.wallet.auth

import com.livelygig.product.content.api.WalletService
import controllers.WebJarAssets
import forms.UserForms
import forms.UserForms.WalletLoginData
import play.api.Configuration
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, Controller}
import utils.AppLogger

import scala.concurrent.{ExecutionContext, Future}

class MobileWalletController(
    val messagesApi: MessagesApi,
    implicit val webJarAssets: WebJarAssets,
    implicit val config: Configuration,
    configuration: Configuration,
    walletService: WalletService
)(implicit val ec: ExecutionContext) extends Controller with I18nSupport with AppLogger {
  val allowedContentType = Seq(Some("application/json"), Some("application/octet-stream"))

  def signup = Action { implicit request =>

    Redirect(controllers.api.v1.wallet.auth.routes.MobileWalletController.walletLogin())
      .flashing("info" -> s"Registered successfully. Please login now.")

  }
  def walletLogin() = Action.async { implicit request =>
    val form = UserForms.walletLogInForm.fill(WalletLoginData(
      walletIdentity = "",
      password = "",
      rememberMe = false
    ))
    Future.successful(Ok( /*views.html.wallet.mobileWalletLogin(form)*/ ""))
  }

  def walletApp = Action.async { implicit request =>
    Future.successful(Ok( /*views.html.wallet.wallet()*/ ""))
  }
}

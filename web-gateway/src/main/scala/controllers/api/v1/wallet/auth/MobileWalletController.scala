package controllers.api.v1.wallet.auth

import com.livelygig.product.content.api.WalletService
import play.api.Configuration
import play.api.i18n.{ I18nSupport }
import play.api.mvc.{ BaseController, ControllerComponents }

import scala.concurrent.ExecutionContext

class MobileWalletController(
  val controllerComponents: ControllerComponents,
  configuration: Configuration,
  walletService: WalletService)(implicit val ec: ExecutionContext) extends BaseController with I18nSupport /* with AppLogger */ {
  val allowedContentType = Seq(Some("application/json"), Some("application/octet-stream"))

  def signup = Action { implicit request =>

    Redirect( /*controllers.api.v1.wallet.auth.routes.MobileWalletController.walletLogin()*/ controllers.routes.ViewController.wallet())
      .flashing("info" -> s"Registered successfully. Please login now.")

  }
  /* def walletLogin() = Action { implicit request =>
    val form = UserForms.walletLogInForm.fill(WalletLoginData(
      walletIdentity = "",
      password = "",
      rememberMe = false))
    Future.successful(Ok(views.html.wallet.mobileWalletLogin(form)))
  }

  def walletApp = Action { implicit request =>
    Future.successful(Ok( /*views.html.wallet.wallet()*/ ""))
  }*/
}


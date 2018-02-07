package controllers

import forms.UserForms
import forms.UserForms.{LoginData, RegistrationData, WalletLoginData, WalletRegistrationData}
import play.api.{Configuration, Mode}
import play.api.i18n.{I18nSupport, Messages, MessagesApi}
import play.api.mvc.{Action, Controller}
import utils.AppLogger

import scala.concurrent.{ExecutionContext, Future}

/**
 * Created by shubham.k on 27-02-2017.
 */

class ViewController(
  val messagesApi: MessagesApi,
  implicit val webJarAssets: WebJarAssets,
  implicit val config: Configuration,
  implicit val ec: ExecutionContext,
  env: play.api.Environment
)
    extends Controller with AppLogger with I18nSupport {

  def walletApp(alias: String) = Action { implicit request =>
    Ok(views.html.wallet.wallet())
  }
  def walletWeb(alias: String) = Action { implicit request =>
    Ok(views.html.wallet.walletWeb())
  }

  def walletSignup(email: Option[String] = None) = Action { implicit request =>
    val form = UserForms.walletRegistrationForm.fill(WalletRegistrationData(
      alias = "",
      emailAddress = None,
      password = ""
    ))
    Ok(views.html.wallet.walletSignUp(form))
  }

  /*Action*/
  def walletOldLogin(publicAddr: Option[String]) = Action { implicit request =>
    val form = UserForms.walletLogInForm.fill(WalletLoginData(
      walletIdentity = publicAddr.getOrElse(""),
      password = "",
      rememberMe = false
    ))
    Ok(views.html.wallet.walletOldLogin(form))
  }
  def walletLogin() = Action { implicit request =>
    val form = UserForms.walletLogInForm.fill(WalletLoginData(
      walletIdentity = "",
      password = "",
      rememberMe = false
    ))
    Ok(views.html.wallet.mobileWalletLogin(form))
  }

  def wallet() = Action { implicit request =>
    Ok(views.html.wallet.wallet())
  }

  def eulaView() = Action { implicit request =>
    Ok(views.html.wallet.eulaView())
  }

  def qrScan(alias: String) = Action { implicit request =>
    Ok(views.html.wallet.qrScan())
  }

  def setUpView1() = Action { implicit request =>
    Ok(views.html.wallet.setUp1())
  }
  def setUpSignUpView(email: Option[String] = None) = Action { implicit request =>
    val form = UserForms.walletRegistrationForm.fill(WalletRegistrationData(
      alias = "",
      emailAddress = None,
      password = ""
    ))
    Ok(views.html.wallet.setUp())
  }
  def termsOfServiceView() = Action { implicit request =>
    Ok(views.html.wallet.termService())
  }

  def backupAccountView() = Action { implicit request =>
    Ok(views.html.wallet.backupAccount())
  }

  def backupIdentityView() = Action { implicit request =>
    Ok(views.html.wallet.backupIdentity())
  }

  def setUpView2() = Action { implicit request =>
    Ok(views.html.wallet.setUp2())
  }
  def serveAppFile(os: String) = Action { request =>
    val filePath = if (env.mode != Mode.Dev) {
      "/home/ubuntu/work/livelygig/mobile-app/"
    } else BadRequest("Ubunda mobile version not available")

    os match {
      case "android" =>
        val file = new java.io.File(s"$filePath/$os/app-release.apk")
        if (file.exists()) {
          Ok.sendFile(
            content = file,
            fileName = _ => "Ubunda.apk"
          )
        } else
          Redirect(controllers.routes.ViewController.walletOldLogin()).flashing("error" -> Messages("wallet.error"))

      case "ios" => Redirect(controllers.routes.ViewController.walletOldLogin()).flashing("info" -> Messages("wallet.ios.unavailable"))
      case _ => Redirect(controllers.routes.ViewController.walletOldLogin()).flashing("error" -> Messages("wallet.error"))
    }
  }

  //Mobile-App --->

  def walletMobileApp(publicKey: String) = Action { implicit request =>
    Ok(views.html.wallet.wallet())
  }
}


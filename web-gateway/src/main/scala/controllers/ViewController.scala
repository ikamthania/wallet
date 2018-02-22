package controllers

import forms.UserForms
import forms.UserForms.{ LoginData, RegistrationData, WalletLoginData, WalletRegistrationData }
import play.api.{ Configuration, Mode }
import play.api.i18n.{ I18nSupport, Messages, MessagesApi }
import play.api.mvc.{ Action, Controller }
//pot utils.AppLogger

import scala.concurrent.{ ExecutionContext, Future }

/**
 * Created by shubham.k on 27-02-2017.
 */
class ViewController(
  val messagesApi: MessagesApi,
  implicit val webJarAssets: WebJarAssets,
  implicit val config: Configuration,
  implicit val ec: ExecutionContext,
  env: play.api.Environment)
  extends Controller with I18nSupport {

  def wallet() = Action { implicit request =>
    Ok(views.html.wallet.wallet())
  }
  // @(projectName: String, assets: String => String, resourceExists: String => Boolean,htmlAttributes: Html = Html(""))
  def bundleUrl(projectName: String): Option[String] = {
    val name = projectName.toLowerCase
    Seq(s"$name-opt.js", s"$name-fastopt.js")
      .find(name => getClass.getResource(s"/public/$name") != null)
      .map(controllers.routes.Assets.versioned(_).url)
  }
}
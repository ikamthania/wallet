package controllers

import org.webjars.play.{ WebJarAssets, WebJarsUtil }
import play.api.i18n.{ I18nSupport, Messages, MessagesApi }
import play.api.mvc.{ BaseController, ControllerComponents, RequestHeader }

import scala.concurrent.Future
/**
 * Created by shubham.k on 27-02-2017.
 */

class ViewController(
  val controllerComponents: ControllerComponents,
  implicit val webJarsUtil: WebJarsUtil,
  //  val messages: Messages,
  //  val requestHeader: RequestHeader,
  implicit val webJarsAssets: WebJarAssets)
  extends BaseController with I18nSupport {

  def wallet() = Action { implicit request =>
    //    request.identity match {
    //      case Some(identity) =>
    //        userCache.getUserProfile(identity.userUri)
    //          .map {
    //            case Some(userProfile) => Redirect(controllers.routes.ViewController.walletApp(userProfile.emailOrEthAddress))
    //            case None => Ok(views.html.wallet.landingview())
    //          }
    //      case None => Future.successful(Ok(views.html.wallet.landingview()))
    //    }
    val messages: Messages = request.messages
    val message: RequestHeader = request
    Ok(views.html.wallet.landingview(message, messages, webJarsAssets))

  }

  //  def wallet() = Action { implicit request =>
  //    Ok(views.html.wallet.wallet(webJarsUtil))
  //  }

  def bundleUrl(projectName: String): Option[String] = {
    val name = projectName.toLowerCase
    Seq(s"$name-opt.js", s"$name-fastopt.js")
      .find(name => getClass.getResource(s"/public/$name") != null)
      .map(controllers.routes.Assets.versioned(_).url)
  }
}
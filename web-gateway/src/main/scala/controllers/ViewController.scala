package controllers

import org.webjars.play.WebJarsUtil
import play.api.Mode
import play.api.i18n.{ I18nSupport, Messages }
import play.api.mvc.{ BaseController, ControllerComponents }
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future
/**
 * Created by shubham.k on 27-02-2017.
 */

class ViewController(
  val controllerComponents: ControllerComponents,
  implicit val webJarsUtil: WebJarsUtil)
  extends BaseController with I18nSupport {

  def app() = Action { implicit request =>
    Redirect(routes.ViewController.wallet())
  }

  def wallet() = Action { implicit request =>
    Ok(views.html.wallet.wallet(webJarsUtil))

  }

  def serveAppFile(os: String) = Action.async { implicit request =>
    val filePath = "/home/ubuntu/work/livelygig/mobile-app/"

    os match {
      case "android" =>
        val file = new java.io.File(s"$filePath/$os/app-debug.apk")

        Future(Ok.sendFile(
          content = file,
          fileName = _ => "Ubunda.apk"))

    }
  }

}
package controllers

import org.webjars.play.WebJarsUtil
import play.api.i18n.I18nSupport
import play.api.mvc.{ BaseController, ControllerComponents }
/**
 * Created by shubham.k on 27-02-2017.
 */

class ViewController(
  val controllerComponents: ControllerComponents,
  implicit val webJarsUtil: WebJarsUtil)
  extends BaseController with I18nSupport {

  def wallet() = Action { implicit request =>
    Ok(views.html.wallet.wallet(webJarsUtil))
  }

  def bundleUrl(projectName: String): Option[String] = {
    val name = projectName.toLowerCase
    Seq(s"$name-opt.js", s"$name-fastopt.js")
      .find(name => getClass.getResource(s"/public/$name") != null)
      .map(controllers.routes.Assets.versioned(_).url)
  }
}
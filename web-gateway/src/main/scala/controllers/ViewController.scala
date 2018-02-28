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
}
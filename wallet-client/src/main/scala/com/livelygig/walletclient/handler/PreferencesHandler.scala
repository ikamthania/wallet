package com.livelygig.walletclient.handler

import com.livelygig.shared.models.wallet._
import com.livelygig.walletclient.facades.jquery.JQueryFacade.jQuery
import diode.{ ActionHandler, ActionResult, ModelRW }
import org.scalajs.dom.raw.Element

import scala.scalajs.LinkingInfo

case class SelectTheme(themeName: String)

class PreferencesHandler[M](modelRW: ModelRW[M, Preferences]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case SelectTheme(themeName) =>
      {
        val themeUrl = if (LinkingInfo.productionMode) {
          s"./assets/stylesheets/wallet/themes/wallet-main-theme-${themeName}.min.css"

        } else {
          s"../assets/stylesheets/wallet/themes/wallet-main-theme-${themeName}.min.css"
        }
        jQuery("#theme-stylesheet")
          .each((ele: Element) =>
            ele
              .setAttribute("href", themeUrl))

        updated(value.copy(selectedTheme = themeName))
      }
  }
}

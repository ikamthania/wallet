package com.livelygig.product.walletclient.handler

import com.livelygig.product.shared.models.wallet._
import com.livelygig.product.walletclient.components.SidebarMenuComponent.toggleNav
import com.livelygig.product.walletclient.facades.jquery.JQueryFacade.jQuery
import com.livelygig.product.walletclient.utils.Defaults
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

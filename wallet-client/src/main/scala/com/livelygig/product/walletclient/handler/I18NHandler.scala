package com.livelygig.product.walletclient.handler

import com.livelygig.product.walletclient.rootmodel.I18NRootModel
import diode.{ActionHandler, ActionResult, ModelRW}
import scala.scalajs.js

case class ChangeLang(lang: js.Dynamic)

class I18NHandler[M](modelRW: ModelRW[M, I18NRootModel]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case ChangeLang(lang) => {
      updated(value.copy(language = lang))
    }

  }

}

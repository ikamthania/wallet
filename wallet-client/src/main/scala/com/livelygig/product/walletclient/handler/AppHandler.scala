package com.livelygig.product.walletclient.handler

import com.livelygig.product.shared.models.wallet.Account
import com.livelygig.product.walletclient.rootmodel.AppRootModel
import diode.{ ActionHandler, ActionResult, ModelRW }

import scala.scalajs.js

case class AddAccount(account: Account)

class AppHandler[M](modelRW: ModelRW[M, AppRootModel]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case AddAccount(account) => {
      updated(value)
    }

  }

}

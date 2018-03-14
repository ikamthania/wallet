package com.livelygig.product.walletclient.handler

import com.livelygig.product.shared.models.wallet.{ Account, Keyring }
import diode.{ ActionHandler, ActionResult, ModelRW }

import scala.scalajs.js

case class AddNewAccount(account: Account)

class AccountHandler[M](modelRW: ModelRW[M, Keyring]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case AddNewAccount(account) => {
      println(value.accounts :+ account)
      updated(value.copy(accounts = value.accounts :+ account))
    }

  }

}

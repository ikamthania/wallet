package com.livelygig.product.walletclient.handler

import com.livelygig.product.shared.models.wallet.{ Account, Keyring, Vault }
import diode.{ ActionHandler, ActionResult, ModelRW }

import scala.scalajs.js

case class AddNewAccount(account: Account)

case class UpdateVault(vault: Option[Vault])

class KeyringHandler[M](modelRW: ModelRW[M, Keyring]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case AddNewAccount(account) => {
      updated(value.copy(accounts = value.accounts :+ account, selectedAddress = account.address))
    }
    case UpdateVault(vault) => {
      updated(value.copy(vault = vault))
    }
  }
}

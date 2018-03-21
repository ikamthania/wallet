package com.livelygig.product.walletclient.handler

import com.livelygig.product.shared.models.wallet.{ Account, Keyring, Vault }
import diode.{ ActionHandler, ActionResult, ModelRW }

import scala.scalajs.js

case class UpdateVault(vault: Vault)

class KeyringHandler[M](modelRW: ModelRW[M, Keyring]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case UpdateVault(vault) => {
      updated(value.copy(vault = vault))
    }
  }
}

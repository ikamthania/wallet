package com.livelygig.product.walletclient.handler

import com.livelygig.product.shared.models.wallet.{ Account, Keyring, Vault, VaultData }
import com.livelygig.product.walletclient.facades.{ BrowserPassworder, VaultGaurd }
import diode.{ Action, ActionHandler, ActionResult, ModelRW }
import play.api.libs.json.Json

import scala.scalajs.js

case class UpdateVault(vault: Vault)
case class CreateVault(password: String)

class KeyringHandler[M](modelRW: ModelRW[M, Keyring]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case UpdateVault(vault) => {
      updated(value.copy(vault = vault))
    }
    case CreateVault(password) => {
      VaultGaurd.encryptWallet(password, VaultData("", ""))
      noChange
    }
  }
}

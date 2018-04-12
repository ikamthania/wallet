package com.livelygig.walletclient.handler

import com.livelygig.shared.models.wallet.{ Keyring, Vault, VaultData }
import com.livelygig.walletclient.facades.VaultGaurd
import diode.{ ActionHandler, ActionResult, ModelRW }

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

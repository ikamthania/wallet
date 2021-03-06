package com.livelygig.walletclient.handler

import com.livelygig.shared.models.wallet._
import com.livelygig.walletclient.utils.Defaults
import diode.{ ActionHandler, ActionResult, ModelRW }

case class AddAccount(newAccount: Account)

case class UpdateAccount(account: Account)

case class UpdateDefaultAccount(address: String)

case class SelectAddress(address: String)

class AccountHandler[M](modelRW: ModelRW[M, AccountInfo]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case AddAccount(newAccount: Account) => {
      updated(value.copy(accounts = value.accounts :+ newAccount))
    }
    case UpdateAccount(account: Account) => {
      updated(value.copy(accounts = value.accounts.map(e => if (e.address == account.address) account else e)))
    }

    case UpdateDefaultAccount(address: String) => {
      updated(value.copy(
        accounts =
          value.accounts.map(e =>
            if (e.address == Defaults.defaultAccountPublicKey) e.copy(address = address) else e),
        selectedAddress = address))
    }

    case SelectAddress(address) => {
      updated(value.copy(selectedAddress = address))
    }
  }
}

package com.livelygig.product.walletclient.services

import com.livelygig.product.shared.models.wallet.{ Account, AppModel }
import com.livelygig.product.walletclient.handler.{ LoginUser, UpdateRootModer }
import play.api.libs.json.Json
import org.scalajs.dom
import diode.AnyAction._

object LocalStorageApi {

  def updateModelInLocalStorage() = {
    dom.window.localStorage.setItem("config", Json.toJson(WalletCircuit.zoomTo(_.appRootModel).value.appModel).toString())
  }

  def subscribeToAppRootChanges() = {
    val config = dom.window.localStorage.getItem("config")
    if (config == null) {
      updateModelInLocalStorage()
    } else {
      Json.parse(config).validate[AppModel].map {
        e =>
          WalletCircuit.dispatch(UpdateRootModer(e))
          if (e.data.keyrings.accounts.nonEmpty) {
            WalletCircuit.dispatch(LoginUser(true))
          }
      }
    }
    WalletCircuit.subscribe(WalletCircuit.zoomTo(_.appRootModel)) { modelRO =>
      updateModelInLocalStorage()
    }
  }

}

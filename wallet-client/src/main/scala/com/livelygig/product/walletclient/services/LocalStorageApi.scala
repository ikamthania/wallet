package com.livelygig.product.walletclient.services

import com.livelygig.product.shared.models.wallet._
import com.livelygig.product.walletclient.handler.{ SelectTheme, UpdateRootModer }
import diode.AnyAction._
import org.scalajs.dom
import play.api.libs.json.JsResult.Exception
import play.api.libs.json.{ JsError, Json }

object LocalStorageApi {

  def updateModelInLocalStorage() = {
    dom.window.localStorage.setItem("config", Json.toJson(WalletCircuit.zoomTo(_.appRootModel).value.appModel).toString())
  }

  def updateRootModelFromStorage() = {
    val config = dom.window.localStorage.getItem("config")
    if (config == null) {
      updateModelInLocalStorage()
    } else {
      Json.parse(config).validate[AppModel].map {
        e =>
          WalletCircuit.dispatch(SelectTheme(e.data.preferencess.selectedTheme))
          WalletCircuit.dispatch(UpdateRootModer(e))
        /*if (e.data.keyrings.vault.data != "") {
            WalletCircuit.dispatch(LoginUser())
          }*/
      }.recover {
        case err: JsError => {
          throw Exception(JsError("Error in parsing app root model"))
        }
      }
    }
  }

  def subscribeToAppRootChanges() = {
    updateRootModelFromStorage()
    WalletCircuit.subscribe(WalletCircuit.zoomTo(_.appRootModel)) { modelRO =>
      updateModelInLocalStorage()
    }
  }

}

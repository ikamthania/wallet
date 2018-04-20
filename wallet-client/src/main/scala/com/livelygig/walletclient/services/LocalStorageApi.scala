package com.livelygig.walletclient.services

import com.livelygig.shared.models.wallet._
import com.livelygig.walletclient.handler.{ SelectTheme, UpdateRootModer }
import diode.AnyAction._
import org.scalajs.dom
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
      }.recover {
        case err: JsError => {
          // incompatible version of app data in local storage
          println(s"Error in parsing root model ${err.toString}")
          dom.window.localStorage.removeItem("config")
          dom.window.location.href = "/wallet"
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

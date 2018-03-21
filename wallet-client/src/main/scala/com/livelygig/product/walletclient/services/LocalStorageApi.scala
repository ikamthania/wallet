package com.livelygig.product.walletclient.services

import com.livelygig.product.shared.models.wallet._
import com.livelygig.product.walletclient.facades.BrowserPassworder
import com.livelygig.product.walletclient.handler.{ LoginUser, UpdateRootModer }
import play.api.libs.json.{ JsError, Json }
import org.scalajs.dom

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import diode.AnyAction._
import play.api.libs.json.JsResult.Exception

import scala.concurrent.Future

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
          WalletCircuit.dispatch(UpdateRootModer(e))
          if (e.data.keyrings.vault.data != "") {
            WalletCircuit.dispatch(LoginUser(true))
          }
      }.recover {
        case err: JsError => {
          //          println(err)
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

package com.livelygig.product.walletclient.services

import com.livelygig.product.shared.models.wallet.Account
import play.api.libs.json.Json
import org.scalajs.dom

object LocalStorageApi {

  def init(): Unit = {

  }
  def subscribeToAppRootChanges() = {
    WalletCircuit.subscribe(WalletCircuit.zoomTo(_.appRootModel))(yo =>
      println(Json.toJson(WalletCircuit.zoom(_.appRootModel.appModel).value)))
  }

}

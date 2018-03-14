package com.livelygig.product.walletclient.rootmodel

import com.livelygig.product.shared.models.wallet.AppModel

case class AppRootModel(appModel: AppModel) {
  def updated(updatedAppModel: AppModel) = {
    println("Yo I am in app root model")
    AppRootModel(updatedAppModel)
  }
}

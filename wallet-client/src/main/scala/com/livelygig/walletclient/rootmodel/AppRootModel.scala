package com.livelygig.walletclient.rootmodel

import com.livelygig.shared.models.wallet.AppModel

case class AppRootModel(appModel: AppModel) {
  def updated(updatedAppModel: AppModel) = {
    AppRootModel(updatedAppModel)
  }
}

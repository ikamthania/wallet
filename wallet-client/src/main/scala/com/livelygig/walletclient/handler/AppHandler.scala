package com.livelygig.walletclient.handler

import com.livelygig.shared.models.wallet.AppModel
import com.livelygig.walletclient.rootmodel.AppRootModel
import diode.{ ActionHandler, ActionResult, ModelRW }

case class UpdateRootModer(appModel: AppModel)

class AppHandler[M](modelRW: ModelRW[M, AppRootModel]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case UpdateRootModer(appModel) => {
      updated(value.copy(appModel = appModel))
    }

  }

}

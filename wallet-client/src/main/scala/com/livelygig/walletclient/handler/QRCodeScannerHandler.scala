package com.livelygig.walletclient.handler

import com.livelygig.walletclient.rootmodel.QRCodeScannerRootModel
import diode.{ ActionHandler, ActionResult, ModelRW }

case class GetSenderAddress(pubKey: String)

class QRCodeScannerHandler[M](modelRW: ModelRW[M, QRCodeScannerRootModel]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case GetSenderAddress(pubKey) =>
      updated(value.copy(pubKey = pubKey))

  }
}
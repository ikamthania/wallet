package com.livelygig.walletclient.handler

import com.livelygig.shared.models.wallet._
import diode.{ ActionHandler, ActionResult, ModelRW }

case class AcceptTermsOfServices()

class NoticesHandler[M](modelRW: ModelRW[M, Seq[Notice]]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case AcceptTermsOfServices() => {
      val newValue = value.map(e => if (e.isInstanceOf[TermsOfServiceNotice]) e.asInstanceOf[TermsOfServiceNotice].copy(read = true) else e)
      updated(newValue)
    }
  }
}

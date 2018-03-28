package com.livelygig.product.walletclient.handler

import com.livelygig.product.walletclient.rootmodel.UserRootModel
import com.livelygig.product.walletclient.utils.SessionKeys
import diode.{ ActionHandler, ActionResult, ModelRW }
import org.scalajs.dom

case class LoginUser()
case class UpdatePassword(password: String)

class UserHandler[M](modelRW: ModelRW[M, UserRootModel]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case LoginUser() =>
      updated(value.copy(isloggedIn = true))
    case UpdatePassword(pw) =>
      updated(value.copy(userPassword = pw))
  }
}
package com.livelygig.walletclient.handler

import com.livelygig.walletclient.rootmodel.UserRootModel
import diode.{ ActionHandler, ActionResult, ModelRW }

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
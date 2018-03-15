package com.livelygig.product.walletclient.handler

import com.livelygig.product.shared.models.wallet.UserDetails
import com.livelygig.product.walletclient.rootmodel.UserRootModel
import diode.{ ActionHandler, ActionResult, ModelRW }

case class GetUserDetails(userDetails: UserDetails)
case class LoginUser(isLoggedIn: Boolean)
case class UpdatePassword(password: String)

class UserHandler[M](modelRW: ModelRW[M, UserRootModel]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case GetUserDetails(updatedWalletDetails) =>
      updated(value.copy(userDetails = updatedWalletDetails))
    case LoginUser(isLoggedIn) =>
      updated(value.copy(isloggedIn = isLoggedIn))
    case UpdatePassword(pw) =>
      updated(value.copy(userPassword = pw))
  }
}
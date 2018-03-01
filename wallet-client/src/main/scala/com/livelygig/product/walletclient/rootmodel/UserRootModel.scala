package com.livelygig.product.walletclient.rootmodel

import com.livelygig.product.shared.models.wallet.UserDetails

//todo add wallet user name as well.
case class UserRootModel(userDetails: UserDetails, isloggedIn: Boolean = false) {

  def updated(updatedUserDetails: UserDetails): UserRootModel = {
    UserRootModel(updatedUserDetails)
  }
}
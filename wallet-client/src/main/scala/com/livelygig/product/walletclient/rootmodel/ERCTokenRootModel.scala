package com.livelygig.product.walletclient.rootmodel

import com.livelygig.product.shared.models.wallet.ERC20ComplientToken

case class ERCTokenRootModel(accountTokenDetails: Seq[ERC20ComplientToken]) {

  def updated(newMessagesResponse: ERC20ComplientToken): ERCTokenRootModel = {
    accountTokenDetails.indexWhere(e => e.contractAddress == newMessagesResponse.contractAddress || e.balance == newMessagesResponse.balance) match {
      case -1 => ERCTokenRootModel(accountTokenDetails :+ newMessagesResponse)
      case target => ERCTokenRootModel(accountTokenDetails.updated(target, newMessagesResponse))
    }
  }
}
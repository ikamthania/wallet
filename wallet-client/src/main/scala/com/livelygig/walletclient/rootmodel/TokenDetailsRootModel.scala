package com.livelygig.walletclient.rootmodel

import com.livelygig.shared.models.wallet.TokenDetails

case class TokenDetailsRootModel(accountTokenDetails: Seq[TokenDetails]) {

  def updated(newMessagesResponse: TokenDetails): TokenDetailsRootModel = {
    accountTokenDetails.indexWhere(e => e.contractAddress == newMessagesResponse.contractAddress || e.balance == newMessagesResponse.balance) match {
      case -1 => TokenDetailsRootModel(accountTokenDetails :+ newMessagesResponse)
      case target => TokenDetailsRootModel(accountTokenDetails.updated(target, newMessagesResponse))
    }
  }
}
package com.livelygig.walletclient.rootmodel

import com.livelygig.shared.models.wallet.CoinExchange

case class MarketPricesRootModel(coinExchange: CoinExchange) {

  def updated(updatedCurrencies: CoinExchange): MarketPricesRootModel = {
    MarketPricesRootModel(updatedCurrencies)
  }
}
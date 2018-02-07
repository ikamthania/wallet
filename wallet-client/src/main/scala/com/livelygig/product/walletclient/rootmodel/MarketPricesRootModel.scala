package com.livelygig.product.walletclient.rootmodel

import com.livelygig.product.shared.models.wallet.{CoinExchange, Currency}

case class MarketPricesRootModel(coinExchange: CoinExchange) {

  def updated(updatedCurrencies: CoinExchange): MarketPricesRootModel = {
    MarketPricesRootModel(updatedCurrencies)
  }
}
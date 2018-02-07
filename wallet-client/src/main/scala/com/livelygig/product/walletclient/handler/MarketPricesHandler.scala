package com.livelygig.product.walletclient.handler

import com.livelygig.product.shared.models.wallet.{CoinExchange, Currency}
import com.livelygig.product.walletclient.rootmodel.MarketPricesRootModel
import diode.{ActionHandler, ActionResult, ModelRW}

case class GetCurrencies(coinExchange: CoinExchange)

class MarketPricesHandler[M](modelRW: ModelRW[M, MarketPricesRootModel]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case GetCurrencies(updatedCurrencies) =>
      updated(value.copy(coinExchange = updatedCurrencies))
  }
}
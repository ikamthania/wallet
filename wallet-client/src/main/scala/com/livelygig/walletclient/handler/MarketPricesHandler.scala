package com.livelygig.walletclient.handler

import com.livelygig.shared.models.wallet.CoinExchange
import com.livelygig.walletclient.rootmodel.MarketPricesRootModel
import diode.{ ActionHandler, ActionResult, ModelRW }

case class GetCurrencies(coinExchange: CoinExchange)

class MarketPricesHandler[M](modelRW: ModelRW[M, MarketPricesRootModel]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case GetCurrencies(updatedCurrencies) =>
      updated(value.copy(coinExchange = updatedCurrencies))
  }
}
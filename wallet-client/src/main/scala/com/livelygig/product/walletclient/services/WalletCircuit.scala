package com.livelygig.product.walletclient.services

import com.livelygig.product.shared.models.wallet._
import com.livelygig.product.walletclient.handler._
import com.livelygig.product.walletclient.rootmodel._
import com.livelygig.product.walletclient.utils.I18N
import diode._
import diode.data.{ Empty, Pot }
import diode.react.ReactConnector

import scala.scalajs.js.JSON

case class RootModel(user: UserRootModel, ERCToken: Pot[ERCTokenRootModel], transaction: Pot[TransactionRootModel], i18n: I18NRootModel, currencies: MarketPricesRootModel, qrCodeRslt: QRCodeScannerRootModel)

object WalletCircuit extends Circuit[RootModel] with ReactConnector[RootModel] {

  // initial application model
  override protected def initialModel = RootModel(UserRootModel(UserDetails("", WalletDetails("", ""))), Empty, Empty,
    I18NRootModel(JSON.parse(I18N.en_us)), MarketPricesRootModel(CoinExchange(Seq(CurrencyList("", Seq(Currency("", 0, "")))))), QRCodeScannerRootModel(""))

  // combine all handlers into one
  override protected val actionHandler = composeHandlers(
    new UserHandler(zoomRW(_.user)((m, v) => m.copy(user = v))),
    new ERCTokenHandler(zoomRW(_.ERCToken)((m, v) => m.copy(ERCToken = v))),
    new TransactionHandler(zoomRW(_.transaction)((m, v) => m.copy(transaction = v))),
    new I18NHandler(zoomRW(_.i18n)((m, v) => m.copy(i18n = v))),
    new MarketPricesHandler(zoomRW(_.currencies)((m, v) => m.copy(currencies = v))),
    new QRCodeScannerHandler(zoomRW(_.qrCodeRslt)((m, v) => m.copy(qrCodeRslt = v))))
}
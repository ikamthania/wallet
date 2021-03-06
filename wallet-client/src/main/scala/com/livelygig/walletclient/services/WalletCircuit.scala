package com.livelygig.walletclient.services

import com.livelygig.shared.models.wallet._
import com.livelygig.walletclient.handler._
import com.livelygig.walletclient.rootmodel._
import com.livelygig.walletclient.utils.I18N
import diode._
import diode.data.{ Empty, Pot }
import diode.react.ReactConnector

import scala.scalajs.js.JSON

case class RootModel(appRootModel: AppRootModel, user: UserRootModel,
  ERCToken: Pot[TokenDetailsRootModel], transaction: Pot[TransactionRootModel], i18n: I18NRootModel, currencies: MarketPricesRootModel)

object WalletCircuit extends Circuit[RootModel] with ReactConnector[RootModel] {

  // initial application model
  override protected def initialModel = RootModel(AppRootModel(AppModel()), UserRootModel(false, ""), Empty, Empty,
    I18NRootModel(JSON.parse(I18N.en_us)), MarketPricesRootModel(CoinExchange(Seq(CurrencyList("", Seq(Currency("", 0, "")))))))

  val appHandler = new AppHandler(zoomRW(_.appRootModel)((m, v) => m.copy(appRootModel = v)))

  val keyringHandler = new KeyringHandler(zoomRW(_.appRootModel.appModel.data.keyrings)((m, v) =>
    m.copy(appRootModel = m.appRootModel
      .copy(appModel = m.appRootModel.appModel.copy(data = m.appRootModel.appModel.data.copy(keyrings = v))))))

  val noticesHandler = new NoticesHandler(zoomRW(_.appRootModel.appModel.data.notices)((m, v) =>
    m.copy(appRootModel = m.appRootModel
      .copy(appModel = m.appRootModel.appModel.copy(data = m.appRootModel.appModel.data.copy(notices = v))))))

  val accountHandler = new AccountHandler(zoomRW(_.appRootModel.appModel.data.accountInfo)((m, v) =>
    m.copy(appRootModel = m.appRootModel
      .copy(appModel = m.appRootModel.appModel.copy(data = m.appRootModel.appModel.data.copy(accountInfo = v))))))

  val preferencesHandler = new PreferencesHandler(zoomRW(_.appRootModel.appModel.data.preferencess)((m, v) =>
    m.copy(appRootModel = m.appRootModel
      .copy(appModel = m.appRootModel.appModel.copy(data = m.appRootModel.appModel.data.copy(preferencess = v))))))

  val appRootHandler = foldHandlers(appHandler, keyringHandler, accountHandler, noticesHandler, preferencesHandler)

  // combine all handlers into one
  override protected val actionHandler = composeHandlers(
    appRootHandler,
    new UserHandler(zoomRW(_.user)((m, v) => m.copy(user = v))),
    new ERCTokenHandler(zoomRW(_.ERCToken)((m, v) => m.copy(ERCToken = v))),
    new TransactionHandler(zoomRW(_.transaction)((m, v) => m.copy(transaction = v))),
    new I18NHandler(zoomRW(_.i18n)((m, v) => m.copy(i18n = v))),
    new MarketPricesHandler(zoomRW(_.currencies)((m, v) => m.copy(currencies = v))))
}
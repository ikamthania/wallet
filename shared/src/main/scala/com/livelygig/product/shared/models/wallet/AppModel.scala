package com.livelygig.product.shared.models.wallet

import play.api.libs.json.{ Format, Json }

case class AppModel(ubunda: Version, data: AppData)

case class Version(version: String)

case class AppData(
  networkController: NetworkController,
  noticeController: NoticeController,
  currencyController: CurrencyController,
  keyringController: KeyringController,
  preferencesController: PreferencesController,
  infuraController: InfuraController)

case class NetworkController(provider: NetworkControllerProvider, network: String, firstTimeInfo: NetworkControllerFirstTimeInfo)

case class NetworkControllerProvider(`type`: String, rpcTarget: String)

case class NetworkControllerFirstTimeInfo(version: String, date: String)

case class NoticeController(noticesList: Seq[NoticeControllerNoticesList])

case class NoticeControllerNoticesList(read: Boolean, date: String, title: String, body: String, id: Int)

case class CurrencyController(currentCurrency: String, conversionRate: Double, conversionDate: Int)

case class KeyringController(wallets: KeyringControllerWallets)

case class KeyringControllerWallets(publicKey: String, privateKey: String, accountName: String)

case class PreferencesController(
  tokens: ERC20ComplientToken,
  useBlockie: Boolean,
  featureFlags: Seq[String],
  selectedAddress: String,
  selectedTheme: String,
  selectedLanguage: String)

case class InfuraController(infuraNetworkStatus: InfuraNetworkStatus)

case class InfuraNetworkStatus(
  mainnet: String,
  ropsten: String,
  kovan: String,
  rinkeby: String)
object AppModel {
  implicit val format: Format[AppModel] = Json.format
}

object Version {
  implicit val format: Format[Version] = Json.format
}

object AppData {
  implicit val format: Format[AppData] = Json.format
}

object NetworkController {
  implicit val format: Format[NetworkController] = Json.format
}

object NetworkControllerProvider {
  implicit val format: Format[NetworkControllerProvider] = Json.format
}

object NetworkControllerFirstTimeInfo {
  implicit val format: Format[NetworkControllerFirstTimeInfo] = Json.format
}

object NoticeController {
  implicit val format: Format[NoticeController] = Json.format
}

object NoticeControllerNoticesList {
  implicit val format: Format[NoticeControllerNoticesList] = Json.format
}

object CurrencyController {
  implicit val format: Format[CurrencyController] = Json.format
}

object KeyringController {
  implicit val format: Format[KeyringController] = Json.format
}

object KeyringControllerWallets {
  implicit val format: Format[KeyringControllerWallets] = Json.format
}

object PreferencesController {
  implicit val format: Format[PreferencesController] = Json.format
}

object InfuraController {
  implicit val format: Format[InfuraController] = Json.format
}

object InfuraNetworkStatus {
  implicit val format: Format[InfuraNetworkStatus] = Json.format
}

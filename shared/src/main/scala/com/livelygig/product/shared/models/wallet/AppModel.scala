package com.livelygig.product.shared.models.wallet

import play.api.libs.json.{ Format, Json }

case class AppModel(ubunda: Version, data: AppData)

case class Version(version: String)

case class AppData(
  networks: Networks,
  notices: Notices,
  currencys: Currencys,
  keyrings: Keyrings,
  preferencess: Preferencess,
  infuraNetworkStatus: InfuraNetworkStatus)

case class Networks(provider: Provider, network: String, firstTimeInfo: FirstTimeInfo)

case class Provider(`type`: String, rpcTarget: String)

case class FirstTimeInfo(version: String, date: String)

case class Notices(noticesList: Seq[NoticesList])

case class NoticesList(read: Boolean, date: String, title: String, body: String, id: Int)

case class Currencys(currentCurrency: String, conversionRate: Double, conversionDate: Int)

case class Keyrings(wallets: Wallets)

case class Wallets(publicKey: String, accountName: String)

case class Preferencess(
  tokens: ERC20ComplientToken,
  useBlockie: Boolean,
  featureFlags: Seq[String],
  selectedAddress: String,
  selectedTheme: String,
  selectedLanguage: String)

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

object Networks {
  implicit val format: Format[Networks] = Json.format
}

object Provider {
  implicit val format: Format[Provider] = Json.format
}

object FirstTimeInfo {
  implicit val format: Format[FirstTimeInfo] = Json.format
}

object Notices {
  implicit val format: Format[Notices] = Json.format
}

object NoticesList {
  implicit val format: Format[NoticesList] = Json.format
}

object Currencys {
  implicit val format: Format[Currencys] = Json.format
}

object Keyrings {
  implicit val format: Format[Keyrings] = Json.format
}

object Wallets {
  implicit val format: Format[Wallets] = Json.format
}

object Preferencess {
  implicit val format: Format[Preferencess] = Json.format
}

object InfuraNetworkStatus {
  implicit val format: Format[InfuraNetworkStatus] = Json.format
}

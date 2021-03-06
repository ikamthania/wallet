package com.livelygig.shared.models.wallet

import play.api.libs.json.{ Format, Json }

case class AppModel(ubunda: UbundaConfig, data: AppData)

case class UbundaConfig(version: String)

case class AppData(
  networks: Seq[Network],
  notices: Seq[Notice],
  currencies: Seq[Curency],
  keyrings: Keyring,
  accountInfo: AccountInfo,
  preferencess: Preferences,
  tokens: Seq[TokenDetails],
  infuraNetworkStatus: InfuraNetworkStatus)

case class Network(name: String, rpcTarget: String)

case class Curency(currentCurrency: String, conversionRate: Double, conversionDate: Int)

case class Keyring(vault: Vault)

case class AccountInfo(accounts: Seq[Account], selectedAddress: String)

object AccountInfo {
  implicit val format: Format[AccountInfo] = Json.format
}

case class Vault(data: String, iv: String, salt: String)

object Vault {
  implicit val format: Format[Vault] = Json.format
}

case class Account(address: String, accountName: String)

// will be encrypted
case class VaultData(privateExtendedKey: String, mnemonicPhrase: String, hdDerivePath: String = "m/44'/60'/0'/0")

object VaultData {
  implicit val format: Format[VaultData] = Json.format
}

case class Preferences(
  useBlockie: Boolean,
  featureFlags: Seq[String],
  selectedTheme: String,
  selectedLanguage: String)

case class InfuraNetworkStatus(
  mainnet: String,
  ropsten: String,
  kovan: String,
  rinkeby: String)

object AppModel {
  implicit val format: Format[AppModel] = Json.format
  def apply(): AppModel =
    AppModel(UbundaConfig("v1.0"), AppData(Nil, Seq(TermsOfServiceNotice()), Nil, Keyring(Vault("", "", "")),
      AccountInfo(Nil, ""), Preferences(true, Nil, "default", "en_us"), Nil, InfuraNetworkStatus("", "", "", "")))
}

object UbundaConfig {
  implicit val format: Format[UbundaConfig] = Json.format
}

object AppData {
  implicit val format: Format[AppData] = Json.format
}

object Network {
  implicit val format: Format[Network] = Json.format
}

object Curency {
  implicit val format: Format[Curency] = Json.format
}

object Keyring {
  implicit val format: Format[Keyring] = Json.format
}

object Account {
  implicit val format: Format[Account] = Json.format
}

object Preferences {
  implicit val format: Format[Preferences] = Json.format
}

object InfuraNetworkStatus {
  implicit val format: Format[InfuraNetworkStatus] = Json.format
}

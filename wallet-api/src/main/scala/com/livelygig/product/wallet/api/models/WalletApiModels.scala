package com.livelygig.product.wallet.api.models
import play.api.libs.json.{Format, Json}

case class Wallet(keyStoreFileContent: String, publicKey: String)

case class ValidateWalletFile(fileContent: String, unlockPassword: String)

case class NewWallet(walletContent: Option[String], unlockPassword: Option[String])

object Wallet {
  implicit val format = Json.format[Wallet]
}

object ValidateWalletFile {
  implicit val format = Json.format[ValidateWalletFile]
}

object NewWallet {
  implicit val format: Format[NewWallet] = Json.format
}

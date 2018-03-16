package com.livelygig.product.walletclient.facades

import com.livelygig.product.shared.models.wallet.{ VaultData, Vault }
import com.livelygig.product.walletclient.services.WalletCircuit
import play.api.libs.json.{ JsObject, Json }

import scala.concurrent.Future
import scala.scalajs.js
import scala.scalajs.js.Promise
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

@js.native
@JSImport("browser-passworder", JSImport.Namespace)
object BrowserPassworder extends js.Object {
  def encrypt(password: String, secrets: String): Promise[String] = js.native

  def decrypt(password: String, blob: String): Promise[js.Any] = js.native

  def decryptVault(password: String): Future[Option[VaultData]] = {
    val currentVault = WalletCircuit.zoom(_.appRootModel.appModel.data.keyrings.vault).value
    if (currentVault.isDefined) {
      BrowserPassworder.decrypt(password, Json.toJson(currentVault).toString()).toFuture.map {
        decrypted =>
          Json.parse(decrypted.toString()).validate[VaultData].asOpt
      }
    } else {
      Future(None)
    }
  }

  def encryptWallet(vaultData: VaultData, password: String): Future[Option[Vault]] = {
    encrypt(password, Json.toJson(vaultData).toString)
      .toFuture
      .map {
        str =>
          Json.parse(str).validate[Vault].asOpt
      }
  }

}

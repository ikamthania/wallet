package com.livelygig.product.walletclient.facades

import com.livelygig.product.shared.models.wallet.{ Vault, VaultData }
import com.livelygig.product.walletclient.services.WalletCircuit
import play.api.libs.json.{ JsError, JsObject, JsSuccess, Json }

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

  def decryptVault(password: String): Future[VaultData] = {
    val currentVault = WalletCircuit.zoom(_.appRootModel.appModel.data.keyrings.vault).value
    BrowserPassworder.decrypt(password, Json.toJson(currentVault).toString()).toFuture.map {
      decrypted =>
        Json.parse(decrypted.toString()).validate[VaultData] match {
          case vaultData: JsSuccess[VaultData] => vaultData.value
          case JsError(error) => throw new Exception(s"Error in parsing the vault data ${error.toString()}") // should never reach here
        }
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

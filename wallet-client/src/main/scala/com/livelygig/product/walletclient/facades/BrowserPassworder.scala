package com.livelygig.product.walletclient.facades

import com.livelygig.product.shared.models.wallet.{ Vault, VaultData }
import com.livelygig.product.walletclient.handler.UpdateVault
import com.livelygig.product.walletclient.services.WalletCircuit
import diode.AnyAction._
import play.api.libs.json.{ JsError, JsSuccess, Json }

import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js
import scala.scalajs.js.Promise
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("browser-passworder", JSImport.Namespace)
object BrowserPassworder extends js.Object {
  def encrypt(password: String, secrets: String): Promise[String] = js.native

  def decrypt(password: String, blob: String): Promise[js.Any] = js.native

}

object VaultGaurd {
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

  def encryptWallet(password: String, vaultData: VaultData): Future[Unit] = {
    BrowserPassworder.encrypt(password, Json.toJson(vaultData).toString)
      .toFuture
      .map {
        str =>
          val vault = Json.parse(str).validate[Vault].get
          WalletCircuit.dispatch(UpdateVault(vault))

      }
  }
}

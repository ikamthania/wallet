package com.livelygig.product.walletclient.facades

import com.livelygig.product.shared.models.wallet.{ Locker, Vault, Wallet }
import com.livelygig.product.walletclient.services.WalletCircuit
import play.api.libs.json.Json

import scala.concurrent.Future
import scala.scalajs.js
import scala.scalajs.js.Promise
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

@js.native
@JSImport("browser-passworder", JSImport.Namespace)
object BrowserPassworder extends js.Object {
  def encrypt(password: String, secrets: String): Promise[String] = js.native

  def decrypt(password: String, blob: String): Promise[String] = js.native

  def decryptVault: Future[Option[Locker]] = {
    val currentVault = WalletCircuit.zoom(_.appRootModel.appModel.data.keyrings.vault).value
    if (currentVault.isDefined) {
      BrowserPassworder.decrypt(WalletCircuit.zoomTo(_.user.userPassword).value, Json.toJson(currentVault).toString()).toFuture.map {
        decrypted =>
          Json.parse(decrypted).validate[Locker].asOpt
      }
    } else {
      Future(None)
    }
  }

  def encryptWallet(wallets: Seq[Wallet]): Future[Option[Vault]] = {
    encrypt(WalletCircuit.zoomTo(_.user.userPassword).value, Json.toJson(wallets).toString)
      .toFuture
      .map {
        str =>
          Json.parse(str).validate[Vault].asOpt
      }
  }

  def addWalletToVault(newWallet: Wallet): Future[Option[Vault]] = {
    decryptVault.flatMap {
      lockerRes =>
        lockerRes match {
          case Some(locker) => encryptWallet(locker.wallets :+ newWallet)
          case None => encryptWallet(Seq(newWallet))
        }
    }
  }
}

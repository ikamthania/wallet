package com.livelygig.product.walletclient.facades

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("ethereumjs-wallet/index", JSImport.Namespace)
object Wallet extends js.Object {
  def fromExtendedPrivateKey(priv: String): Wallet.type = js.native
  def getPrivateKey(): Any = js.native
  def getPublicKey(): Any = js.native
}

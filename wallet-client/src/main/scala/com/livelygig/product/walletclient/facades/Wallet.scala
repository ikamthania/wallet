package com.livelygig.product.walletclient.facades

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("ethereumjs-wallet", JSImport.Namespace)
object Wallet extends js.Object {
  def fromExtendedPrivateKey(priv: String): Wallet.type = js.native
  def getPrivateKeyString(): String = js.native
  def getAddressString(): String = js.native
}

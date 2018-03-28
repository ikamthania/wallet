package com.livelygig.product.walletclient.facades

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("ethereumjs-wallet", JSImport.Namespace)
object EthereumJSWallet extends js.Object {
  def fromExtendedPrivateKey(priv: String): EthereumJSWallet.type = js.native
  def getPrivateKeyString(): String = js.native
  def getAddressString(): String = js.native
}

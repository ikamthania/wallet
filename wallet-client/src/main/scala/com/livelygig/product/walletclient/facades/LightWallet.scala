package com.livelygig.product.walletclient.facades

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@JSImport("eth-lightwallet/index.js", JSImport.Default)
@js.native
object LightWallet extends js.Object {
  def keystore: js.Dynamic = js.native
}

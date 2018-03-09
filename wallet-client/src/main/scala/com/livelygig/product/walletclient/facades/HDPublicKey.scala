package com.livelygig.product.walletclient.facades

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("hdkey", JSImport.Namespace)
class HDPublicKey(extendedPrivateKey: String) extends js.Object {
  val xpubkey: js.Dynamic = js.native
}

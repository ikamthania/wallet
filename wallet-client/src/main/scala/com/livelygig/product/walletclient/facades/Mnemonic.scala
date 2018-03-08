package com.livelygig.product.walletclient.facades

import io.scalajs.nodejs.buffer.Buffer

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("bitcore-mnemonic/lib/mnemonic", JSImport.Namespace)
class Mnemonic extends js.Object {
  def toHDPrivateKey() = js.native
  def toSeed(passphrase: String): Buffer = js.native

}

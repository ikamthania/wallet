package com.livelygig.walletclient.facades

import io.scalajs.nodejs.buffer.Buffer

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("bip39", JSImport.Namespace)
object Mnemonic extends js.Object {
  def generateMnemonic(): String = js.native
  def mnemonicToSeed(passphrase: String): Buffer = js.native

}

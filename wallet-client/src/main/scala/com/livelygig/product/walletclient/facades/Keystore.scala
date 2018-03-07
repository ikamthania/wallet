package com.livelygig.product.walletclient.facades

import com.livelygig.product.walletclient.CreateVaultOptions

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

//@JSImport("keystore", JSImport.Default)
@js.native
@JSImport("eth-lightwallet/lib/keystore.js", JSImport.Namespace)
object KeyStore extends js.Object {
  def createVault(option: CreateVaultOptions, callback: js.Function2[String, js.Any, Unit]) = js.native

  def generateRandomSeed(str: String): String = js.native
}

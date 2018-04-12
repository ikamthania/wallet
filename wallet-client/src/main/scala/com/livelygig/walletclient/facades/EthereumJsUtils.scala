package com.livelygig.walletclient.facades

import io.scalajs.nodejs.buffer.Buffer

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("ethereumjs-util", JSImport.Namespace)
object EthereumJsUtils extends js.Object {
  def publicToAddress(pubKey: Buffer): Buffer = js.native
  def isValidPrivate(privateKey: Buffer): Boolean = js.native
  def privateToAddress(privateKey: Buffer): Buffer = js.native
}
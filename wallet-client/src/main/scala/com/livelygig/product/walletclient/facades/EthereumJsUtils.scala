package com.livelygig.product.walletclient.facades

import io.scalajs.nodejs.buffer.Buffer

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("ethereumjs-util", JSImport.Namespace)
object EthereumJsUtils extends js.Object {
  def bufferToHex(buffer: Buffer): String = js.native
}
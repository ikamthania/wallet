package com.livelygig.product.walletclient.facades

import io.scalajs.nodejs.buffer.Buffer

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("ethereumjs-tx/index.js", JSImport.Namespace)
class Transaction(nonce: String = js.native, gasPrice: String = js.native,
  gasLimit: String = js.native, to: String = js.native, value: String = js.native,
  s: String = js.native, v: String = js.native, r: String = js.native, chainId: Int = js.native) extends js.Object {
  def sign(privateKey: Buffer = js.native): Unit = js.native
  def serialize(): Buffer = js.native
  def getSenderAddress(): Buffer = js.native
  def verifySignature(): Boolean = js.native
  def getUpfrontCost(): js.Dynamic = js.native
  val from = js.native
  //  def Transaction(params: String): this.type = js.native
}


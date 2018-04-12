package com.livelygig.walletclient.facades

import io.scalajs.nodejs.buffer.Buffer

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("ethereumjs-tx/index.js", JSImport.Namespace)
class Transaction() extends js.Object {

  @inline
  def this(data: js.Dictionary[String]) = this()

  def sign(privateKey: Buffer = js.native): Unit = js.native
  def serialize(): Buffer = js.native
  def getSenderAddress(): Buffer = js.native
  def verifySignature(): Boolean = js.native
  def getUpfrontCost(): js.Dynamic = js.native
  val from = js.native
  //  def Transaction(params: String): this.type = js.native
}

case class TransactionParams(nonce: String, gasPrice: String, gasLimit: String, to: String, value: String, data: String)

object Ethereumjstx {
  def apply(transactionParams: TransactionParams) =
    new Transaction(js.Dictionary("nonce" -> transactionParams.nonce, "gasPrice" -> transactionParams.gasPrice,
      "gasLimit" -> transactionParams.gasLimit, "to" -> transactionParams.to, "value" -> transactionParams.value,
      "data" -> transactionParams.data, "chainId" -> "3"))
}

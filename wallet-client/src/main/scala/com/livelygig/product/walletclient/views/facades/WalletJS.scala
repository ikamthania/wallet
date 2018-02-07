package com.livelygig.product.walletclient.views.facades

import org.scalajs.dom.raw

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

@js.native
@JSGlobal("walletjs")
object WalletJS extends js.Object {

  def postRawTxn(userPassword: String, amount: String, txTo: String, txnType: String, nonce: String, encodedFunction: String): String = js.native
  def getKey(): String = js.native
  def getnumberFormat(curr: String): String = js.native

}


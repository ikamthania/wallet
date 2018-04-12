package com.livelygig.walletclient.facades

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("./signtxn", JSImport.Default)
object WalletJS extends js.Object {

  def getSignTxn(privKey: String, amount: String, txTo: String, nonce: String, encodedFunction: String, gasPrice: String, gasLimit: String): String = js.native
  def getnumberFormat(curr: String): String = js.native
}


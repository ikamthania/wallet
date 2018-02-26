package com.livelygig.product.walletclient.views.facades

import scala.scalajs.js
import scala.scalajs.js.annotation.{ JSImport }
@JSImport("./signtxn", JSImport.Default)
object WalletJS extends js.Object {

  def postRawTxn(userPassword: String, amount: String, txTo: String, txnType: String, nonce: String, encodedFunction: String): String = js.native
  def getKey(): String = js.native
  def getnumberFormat(curr: String): String = js.native
  def deleteAccount(publicKey: String, currentAccount: String): String = js.native

}


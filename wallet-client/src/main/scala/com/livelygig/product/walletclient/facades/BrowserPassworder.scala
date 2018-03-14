package com.livelygig.product.walletclient.facades

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("browser-passworder", JSImport.Namespace)
class BrowserPassworder extends js.Object {
  def encrypt(password: String, secrets: String): String = js.native
  def decrypt(password: String, blob: String): String = js.native
}

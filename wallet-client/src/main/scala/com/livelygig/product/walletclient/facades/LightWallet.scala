package com.livelygig.product.walletclient.facades

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@JSImport("./", JSImport.Default)
@js.native
object LightWallet extends js.Object {

  def generateRandomSeed(str: String): String = js.native

}

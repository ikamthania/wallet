package com.livelygig.product.walletclient

import scala.scalajs.js

@js.native
trait CreateVaultOptions extends js.Object {
  var password: String = js.native
  var seedPhrase: String = js.native
  var hdPathString: String = js.native
}
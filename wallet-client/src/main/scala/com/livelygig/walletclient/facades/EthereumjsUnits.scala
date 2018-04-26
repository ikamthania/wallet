package com.livelygig.walletclient.facades

import scala.scalajs.js
import scala.scalajs.js.annotation.{ JSGlobal, JSImport }

@js.native
@JSImport("ethereumjs-units", JSImport.Namespace)
object EthereumjsUnits extends js.Object {
  def convert(value: String, from: String, to: String): String = js.native
}
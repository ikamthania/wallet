package com.livelygig.walletclient.facades

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

//@JSGlobal("pseudoloc")
@JSImport("pseudoloc/src/pseudoloc.js", JSImport.Namespace)
@js.native
object Pseudoloc extends js.Object {
  def str(str: String): String = js.native
}

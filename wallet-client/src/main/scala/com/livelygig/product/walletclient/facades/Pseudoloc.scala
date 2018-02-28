package com.livelygig.product.walletclient.facades

import scala.scalajs.js
import scala.scalajs.js.annotation.{ JSGlobal, JSImport }

//@JSGlobal("pseudoloc")
@JSImport("./pseudoloc", JSImport.Namespace)
@js.native
object Pseudoloc extends js.Object {
  def str(str: String): String = js.native
}

package com.livelygig.product.walletclient.views.facades

import org.scalajs.dom.raw

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

@js.native
@JSGlobal
class QRCode(text: raw.Element, vOptions: String) extends js.Object {
  def clear(): Unit = js.native
  def makeCode(url: String): Unit = js.native
}

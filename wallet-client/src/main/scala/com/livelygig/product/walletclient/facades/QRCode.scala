package com.livelygig.product.walletclient.facades

import org.scalajs.dom.raw.HTMLCanvasElement

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native //@JSGlobal("QRCode")
@JSImport("qrcode-generator", JSImport.Namespace)
class QRCode(typeNumber: String, errorCorrectLevel: String) extends js.Object {
  def addData(data: String): Any = js.native
  def make(): Any = js.native
  def createImgTag(cellSize: Int): HTMLCanvasElement = js.native
  def createSvgTag(): HTMLCanvasElement = js.native
}


package com.livelygig.product.walletclient.views.facades

import org.scalajs.dom.raw.HTMLCanvasElement

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

@JSGlobal("blockies")
@js.native
object Blockies extends js.Object {

  //  @ScalaJSDefined
  //  trait opts extends js.Object {
  //    val size: Int //Option[Int] = None,
  //    val scale: Int //Option[String] = None,
  //    val seed: String
  //    val bgcolor: String //Option[String] = None,
  //    val color: String //Option[String] = None
  //  }

  def create(opts: js.Object): HTMLCanvasElement = js.native

}
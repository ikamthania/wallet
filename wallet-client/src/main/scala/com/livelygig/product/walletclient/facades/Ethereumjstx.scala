package com.livelygig.product.walletclient.facades

import org.scalajs.dom.raw

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

@js.native
@JSGlobal("ethereumjstx")
object Transaction extends js.Object {
  def sign(privateKey: String) = js.native
  def serialize() = js.native
  //  def Transaction(params: String): this.type = js.native
}


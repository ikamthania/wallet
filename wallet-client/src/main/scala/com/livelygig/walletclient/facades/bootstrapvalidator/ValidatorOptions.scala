package com.livelygig.walletclient.facades.bootstrapvalidator

import scala.scalajs.js

@js.native
trait ValidatorOptions extends js.Object {
  def delay: Double = js.native
  def html: Boolean = js.native
  def disable: Boolean = js.native
  def focus: Boolean = js.native
  def feedback: js.Any = js.native
  def custom: js.Any = js.native
}

